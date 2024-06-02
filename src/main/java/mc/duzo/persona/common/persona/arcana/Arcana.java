package mc.duzo.persona.common.persona.arcana;

import mc.duzo.persona.PersonaMod;
import mc.duzo.persona.datagen.provider.lang.Translatable;
import mc.duzo.persona.util.Identifiable;
import net.minecraft.util.Identifier;

public enum Arcana implements Identifiable, Translatable {
	FOOL,
	MAGICIAN,
	PRIESTESS,
	EMPRESS,
	EMPEROR,
	HIEROPHANT,
	LOVERS,
	CHARIOT,
	JUSTICE,
	HERMIT,
	FORTUNE,
	STRENGTH,
	HANGED_MAN,
	DEATH,
	TEMPERANCE,
	DEVIL,
	TOWER,
	STAR,
	MOON,
	SUN,
	JUDGEMENT,
	WORLD;

	private final Identifier id;
	private final int number;

	Arcana(Identifier id, int number) {
		this.id = id;
		this.number = number;
	}
	Arcana(Identifier id) {
		this.id = id;
		this.number = this.ordinal();
	}
	Arcana(String name) {
		this(new Identifier(PersonaMod.MOD_ID, name));
	}
	Arcana() {
		this.id = new Identifier(PersonaMod.MOD_ID, this.name().toLowerCase());
		this.number = this.ordinal();
	}

	@Override
	public Identifier id() {
		return this.id;
	}

	@Override
	public String getTranslationKey() {
		return PersonaMod.MOD_ID + "." + this.name().toLowerCase();
	}
}
