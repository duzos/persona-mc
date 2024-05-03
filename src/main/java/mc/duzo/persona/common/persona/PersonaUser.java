package mc.duzo.persona.common.persona;

import net.minecraft.util.math.MathHelper;

import java.util.Optional;

public interface PersonaUser {
	Optional<AbstractPersona> findPersona();
	int getSP();
	int getMaxSP();
	void setSP(int amount);
	default void addSP(int amount) {
		this.setSP(MathHelper.clamp(this.getSP() + amount, 0, this.getMaxSP()));
	}
	default void removeSP(int amount) {
		this.setSP(MathHelper.clamp(this.getSP() - amount, 0, this.getMaxSP()));
	}
	default boolean hasEnoughSP(int amount) {
		return this.getSP() >= amount;
	}
}
