package mc.duzo.persona.common.battle.turn;

import mc.duzo.persona.PersonaMod;
import mc.duzo.persona.common.battle.data.BattleData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import java.util.List;
import java.util.UUID;

public abstract class BattleTurn {
	protected UUID current; // The uuid of the current entity whos turn it is
	private BattleData data;

	protected BattleTurn(BattleData data, UUID current) {
		this.data = data;
		this.current = current;
	}
	protected BattleTurn(BattleData data) {
		this(data, data.getPlayers().get(0).getUuid());
	}

	public abstract LivingEntity getCurrent();
	public BattleData getData() { return this.data; }

	protected boolean isCurrent(UUID id) { return this.current.equals(id); }
	public boolean isCurrent(LivingEntity entity) { return this.isCurrent(entity.getUuid()); }

	public void next() {
		int position = 0;

		List<? extends LivingEntity> targets = this.getData().getTargets();
		List<? extends PlayerEntity> players = this.getData().getPlayers();

		if (this.getData().hasTarget(this.current)) {
			// Select next target or select first player
			LivingEntity currentTarget = null;

			for (LivingEntity entity : targets) {
				if (entity.getUuid().equals(this.current)) {
					currentTarget = entity;
					break;
				}
			}

			position = targets.indexOf(currentTarget);
			if (position + 1 < targets.size()) {
				this.current = targets.get(position + 1).getUuid();
				return;
			}

			this.current = ((PlayerEntity) players.toArray()[0]).getUuid();
			return;
		}

		if (this.getData().hasPlayer(this.current)) {
			// Select next player or select first target
			PlayerEntity currentPlayer = null;

			for (PlayerEntity entity : players) {
				if (entity.getUuid().equals(this.current)) {
					currentPlayer = entity;
					break;
				}
			}

			position = players.indexOf(currentPlayer);
			if (position + 1 < players.size()) {
				this.current = players.get(position + 1).getUuid();
				return;
			}

			this.current = ((LivingEntity) targets.toArray()[0]).getUuid();
			return;
		}

		PersonaMod.LOGGER.error("Could not find next entity in turn!");
		// Throw exception?
	}

	public NbtCompound toNbt() {
		NbtCompound nbt = new NbtCompound();

		nbt.putUuid("Current", this.current);

		return nbt;
	}

	public BattleTurn loadNbt(NbtCompound nbt) {
		this.current = nbt.getUuid("Current");

		return this;
	}
}
