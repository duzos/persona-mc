package mc.duzo.persona.common.persona;

import mc.duzo.persona.common.PersonaSounds;
import mc.duzo.persona.common.persona.arcana.Arcana;
import mc.duzo.persona.common.persona.arcana.ArcanaHolder;
import mc.duzo.persona.common.skill.SkillSet;
import mc.duzo.persona.util.Identifiable;
import net.minecraft.entity.AnimationState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public abstract class AbstractPersona implements Identifiable, ArcanaHolder {
	private final Identifier id;
	public AnimationState animationState = new AnimationState(); // bad cod e

	protected AbstractPersona(Identifier id) {
		this.id = id;
	}

	@Override
	public Identifier id() {
		return this.id;
	}
	@Override
	public String toString() {
		return "Persona{" +
				"id=" + id +
				", skills=" + getSkillSet() +
				", level=" + getLevel() +
				'}';
	}
	public abstract SkillSet getSkillSet();

	public abstract int getLevel();
	public abstract void setLevel(int level);
	public void levelUp() {
		this.setLevel(MathHelper.clamp(this.getLevel() + 1, 1, 99));
	}

	public SoundEvent getSummonSound() {
		return PersonaSounds.PERSONA_SHOUT;
	}

	public abstract Identifier texture();
	public abstract Arcana getArcana();

	public NbtCompound toNbt() {
		NbtCompound nbt = new NbtCompound();

		nbt.putString("id", this.id.toString());
		nbt.put("SkillSet", this.getSkillSet().toNbt());
		nbt.putInt("Level", this.getLevel());
		nbt.putString("Texture", this.texture().toString());
		nbt.putString("Sound", this.getSummonSound().getId().toString());
		nbt.putInt("Arcana", this.getArcana().ordinal());

		return nbt;
	}

	public AbstractPersona loadNbt(NbtCompound nbt) {
		if (!(this.id.equals(new Identifier(nbt.getString("id"))))) {
			throw new RuntimeException("Attempted to load persona with mismatched id");
		}

		this.setLevel(nbt.getInt("Level"));

		return this;
	}
}
