package mc.duzo.persona.common.battle.data;

import mc.duzo.persona.PersonaMod;
import mc.duzo.persona.common.battle.BattleHandler;
import mc.duzo.persona.common.battle.ai.BattleAI;
import mc.duzo.persona.common.battle.turn.BattleTurn;
import mc.duzo.persona.common.battle.turn.ServerBattleTurn;
import mc.duzo.persona.common.skill.Skill;
import mc.duzo.persona.network.PersonaMessages;
import mc.duzo.persona.util.AbsoluteBlockPos;
import mc.duzo.persona.util.DeltaTimeManager;
import mc.duzo.persona.util.PersonaUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServerBattleData extends CachableBattleData {
	private AbsoluteBlockPos battlePos;
	private ServerBattleTurn turn;

	public ServerBattleData(List<ServerPlayerEntity> players, List<LivingEntity> targets, AbsoluteBlockPos pos) {
		super();

		for (ServerPlayerEntity player : players) {
			this.players.add(player.getUuid());
		}
		for (LivingEntity target : targets) {
			this.targets.add(target.getUuid());
		}

		this.battlePos = pos;
	}
	public ServerBattleData(List<ServerPlayerEntity> players, List<LivingEntity> targets) {
		this(players,targets,null);

		if (players.isEmpty()) return;
		ServerPlayerEntity found = players.get(0);

		this.battlePos = new AbsoluteBlockPos(found.getBlockPos(), found.getServerWorld());
	}
	public ServerBattleData(NbtCompound nbt) {
		super(nbt);
	}

	public void toClient(ServerPlayerEntity target) {
		PersonaMessages.syncBattleData(target, this);
	}
	public void toClient() {
		for (PlayerEntity player : this.getPlayers()) {
			this.toClient((ServerPlayerEntity) player);
		}
		for (LivingEntity target : this.getTargets()) {
			if (target instanceof ServerPlayerEntity) {
				this.toClient((ServerPlayerEntity) target);
			}
		}
	}

	@Override
	protected boolean shouldUpdateCache() {
		return DeltaTimeManager.isOnDelay(this.getCacheKey());
	}

	@Override
	public BattleTurn getTurn() {
		if (this.turn == null) {
			this.turn = new ServerBattleTurn(this);
		}

		return this.turn;
	}

	public ServerBattleTurn getServerTurn() {
		return (ServerBattleTurn) this.getTurn();
	}



	public AbsoluteBlockPos getBattlePos() {
		return this.battlePos;
	}

	@Override
	protected void addTarget(UUID id) {
		super.addTarget(id);

		this.toClient();
	}

	@Override
	protected void addPlayer(UUID id) {
		super.addPlayer(id);

		this.toClient();
	}

	public void tick(MinecraftServer server) {
		if (server.getTicks() % 32 == 0) {
			BattleHandler.validateBattle(server, this);
			BattleHandler.applyPositionTransforms(this, true);

			if (!(this.getTurn().getCurrent() instanceof ServerPlayerEntity)) {
				BattleAI.performTurn(this.getServerTurn());
			}
		}
	}

	@Override
	public List<? extends PlayerEntity> getPlayers() {
		if (!this.shouldUpdateCache() && this.playersCache != null) {
			return this.playersCache;
		}

		MinecraftServer server = PersonaMod.getServer().orElse(null);

		if (server == null) {
			PersonaMod.LOGGER.error("Tried to get players from server without a server!");
			return List.of();
		}

		List<ServerPlayerEntity> list = new ArrayList<>();

		ServerPlayerEntity found;
		for (UUID id : this.players) {
			found = server.getPlayerManager().getPlayer(id);
			if (found == null) continue;

			list.add(found);
		}

		this.playersCache = list;
		this.createCacheDelay();

		return list;
	}

	@Override
	public List<? extends LivingEntity> getTargets() {
		if (!this.shouldUpdateCache() && this.targetsCache != null) {
			return this.targetsCache;
		}

		MinecraftServer server = PersonaMod.getServer().orElse(null);

		if (server == null) {
			PersonaMod.LOGGER.error("Tried to get targets from server without a server!");
			return List.of();
		}

		ArrayList<UUID> searchList = new ArrayList<>(this.targets);
		List<LivingEntity> list = new ArrayList<>();

		Entity found;
		// Search through every world to try and find the entities, shouldn't be needed as they should be in the same world though.
		for (ServerWorld world : server.getWorlds()) {
			for (UUID id : searchList) {
				found = world.getEntity(id);
				if (!(found instanceof LivingEntity)) continue;

				list.add((LivingEntity) found);
				// searchList.remove(id); // fixme - comodification error
			}
		}

		this.targetsCache = list;
		this.createCacheDelay();

		return list;
	}

	@Override
	public NbtCompound toNbt() {
		NbtCompound nbt = super.toNbt();

		// Null check?
		nbt.put("BattlePos", this.battlePos.toNbt());

		return nbt;
	}

	@Override
	public BattleData loadNbt(NbtCompound nbt) {
		super.loadNbt(nbt);

		this.battlePos = AbsoluteBlockPos.fromNbt(nbt.getCompound("BattlePos"));

		this.turn = new ServerBattleTurn(this);
		this.turn.loadNbt(nbt.getCompound("Turn"));

		return this;
	}
}
