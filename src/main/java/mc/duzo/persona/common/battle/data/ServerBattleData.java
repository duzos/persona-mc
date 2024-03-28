package mc.duzo.persona.common.battle.data;

import mc.duzo.persona.PersonaMod;
import mc.duzo.persona.network.PersonaMessages;
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

public class ServerBattleData extends BattleData {
	public ServerBattleData(List<ServerPlayerEntity> players, List<LivingEntity> targets) {
		super();

		for (ServerPlayerEntity player : players) {
			this.players.add(player.getUuid());
		}
		for (LivingEntity target : targets) {
			this.targets.add(target.getUuid());
		}
	}
	public ServerBattleData(NbtCompound nbt) {
		super(nbt);
	}

	public void toClient(ServerPlayerEntity target) {
		PersonaMessages.syncBattleData(target, this);
	}

	@Override
	public List<? extends PlayerEntity> getPlayers() {
		if (!PersonaMod.hasServer()) {
			PersonaMod.LOGGER.error("Tried to get players from server without a server!");
			return List.of();
		}

		MinecraftServer server = PersonaMod.SERVER;
		List<ServerPlayerEntity> list = new ArrayList<>();

		ServerPlayerEntity found;
		for (UUID id : this.players) {
			found = server.getPlayerManager().getPlayer(id);
			if (found == null) continue;

			list.add(found);
		}

		return list;
	}

	@Override
	public List<? extends LivingEntity> getTargets() {
		if (!PersonaMod.hasServer()) {
			PersonaMod.LOGGER.error("Tried to get targets from server without a server!");
			return List.of();
		}

		MinecraftServer server = PersonaMod.SERVER;

		ArrayList<UUID> searchList = new ArrayList<>(this.targets);
		List<LivingEntity> list = new ArrayList<>();

		Entity found;
		// Search through every world to try and find the entities, shouldn't be needed as they should be in the same world though.
		for (ServerWorld world : server.getWorlds()) {
			for (UUID id : searchList) {
				found = world.getEntity(id);
				if (!(found instanceof LivingEntity)) continue;

				list.add((LivingEntity) found);
				searchList.remove(id);
			}
		}

		return list;
	}


}
