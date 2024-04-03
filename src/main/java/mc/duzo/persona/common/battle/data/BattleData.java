package mc.duzo.persona.common.battle.data;

import mc.duzo.persona.PersonaMod;
import mc.duzo.persona.util.AbsoluteBlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class BattleData {
	protected final UUID uuid;
	protected final List<UUID> players;
	protected final List<UUID> targets;

	protected List<? extends PlayerEntity> playersCache;
	protected List<? extends LivingEntity> targetsCache;

	protected BattleData(UUID uuid) {
		this.uuid = uuid;

		this.players = new ArrayList<>();
		this.targets = new ArrayList<>();
	}
	protected BattleData(NbtCompound data) {
		this(data.getUuid("Uuid"));

		this.loadNbt(data);
	}
	protected BattleData() {
		this(UUID.randomUUID());
	}
	public UUID getUuid() { return this.uuid; }


	public abstract List<? extends PlayerEntity> getPlayers();
	public abstract List<? extends LivingEntity> getTargets();
	protected void addTarget(UUID id) {
		if (this.targets.contains(id)) return;

		this.targets.add(id);
	}
	public void addTarget(LivingEntity entity) {
		this.addTarget(entity.getUuid());
	}
	protected abstract boolean shouldUpdateCache();


	public NbtCompound toNbt() {
		NbtCompound nbt = new NbtCompound();

		nbt.putUuid("Uuid", this.uuid);

		NbtCompound playersNbt = new NbtCompound();
		for (UUID player : this.players) {
			playersNbt.putUuid(player.toString(), player);
		}
		nbt.put("Players", playersNbt);

		NbtCompound targetsNbt = new NbtCompound();
		for (UUID target : this.targets) {
			targetsNbt.putUuid(target.toString(), target);
		}
		nbt.put("Targets", targetsNbt);

		return nbt;
	}

	public BattleData loadNbt(NbtCompound nbt) {
		this.players.clear();
		this.targets.clear();

		if (!this.getUuid().equals(nbt.getUuid("Uuid"))) {
			PersonaMod.LOGGER.warn("Loading a battle data with a different UUID than the original!");
		}

		NbtCompound playersNbt = nbt.getCompound("Players");
		playersNbt.getKeys().forEach(key -> {
			UUID uuid = UUID.fromString(key);
			this.players.add(uuid);
		});

		NbtCompound targetsNbt = nbt.getCompound("Players");
		targetsNbt.getKeys().forEach(key -> {
			UUID uuid = UUID.fromString(key);
			this.targets.add(uuid);
		});

		return this;
	}
}
