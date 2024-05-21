package mc.duzo.persona.common.battle.data;

import mc.duzo.persona.PersonaMod;
import mc.duzo.persona.util.DeltaTimeManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import java.util.List;

public abstract class CachableBattleData extends BattleData {
	protected List<? extends PlayerEntity> playersCache;
	protected List<? extends LivingEntity> targetsCache;

	public CachableBattleData(NbtCompound nbt) {
		super(nbt);
	}

	public CachableBattleData() {
		super();
	}

	protected void createCacheDelay() {
		DeltaTimeManager.createDelay(this.getCacheKey(), this.getCacheDelay());
	}
	protected long getCacheDelay() {
		return (long) ((PersonaMod.RANDOM.nextDouble(2,5)) * 1000L);
	}
	protected String getCacheKey() {
		return this.getUuid().toString() + "-cache";
	}
	protected abstract boolean shouldUpdateCache();

}
