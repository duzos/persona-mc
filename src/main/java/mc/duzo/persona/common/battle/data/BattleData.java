package mc.duzo.persona.common.battle.data;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class BattleData {
	protected final List<UUID> players;
	protected final List<UUID> targets;

	protected BattleData() {
		this.players = new ArrayList<>();
		this.targets = new ArrayList<>();
	}

	public abstract List<? extends PlayerEntity> getPlayers();
	public abstract List<? extends LivingEntity> getTargets();

	public NbtCompound toNbt() {
		NbtCompound nbt = new NbtCompound();

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
