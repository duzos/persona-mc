package mc.duzo.persona.client.battle.data;

import mc.duzo.persona.PersonaMod;
import mc.duzo.persona.common.battle.data.BattleData;
import mc.duzo.persona.network.PersonaMessages;
import mc.duzo.persona.util.DeltaTimeManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientBattleData extends BattleData {
	public ClientBattleData(NbtCompound nbt) {
		super(nbt);
	}

	@Override
	protected boolean shouldUpdateCache() {
		return DeltaTimeManager.isOnDelay(this.getCacheKey());
	}
	private void createCacheDelay() {
		DeltaTimeManager.createDelay(this.getCacheKey(), this.getCacheDelay());
	}
	private long getCacheDelay() {
		return (long) ((PersonaMod.RANDOM.nextDouble(10,12)) * 1000L);
	}
	private String getCacheKey() {
		return this.getUuid().toString() + "-cache";
	}

	@Override
	public List<? extends PlayerEntity> getPlayers() {
		if (!this.shouldUpdateCache() && this.playersCache != null) {
			return this.playersCache;
		}

		ClientWorld world = MinecraftClient.getInstance().world;

		if (world == null) {
			PersonaMod.LOGGER.error("Tried to get players from world without a client world!");
			return List.of();
		}

		List<ClientPlayerEntity> list = new ArrayList<>();

		ClientPlayerEntity found;
		for (UUID id : this.players) {
			found = (ClientPlayerEntity) world.getPlayerByUuid(id);
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

		ClientWorld world = MinecraftClient.getInstance().world;

		if (world == null) {
			PersonaMod.LOGGER.error("Tried to get targets from world without a client world!");
			return List.of();
		}

		List<LivingEntity> list = new ArrayList<>();

		for (Entity entity : world.getEntities()) {
			if (!(entity instanceof LivingEntity)) continue;

			if (this.targets.contains(entity.getUuid())) {
				list.add((LivingEntity) entity);
			}
		}

		this.targetsCache = list;
		this.createCacheDelay();

		return list;
	}
}
