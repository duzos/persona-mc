package mc.duzo.persona.client.sound.persona;

import mc.duzo.persona.PersonaMod;
import mc.duzo.persona.common.PersonaSounds;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class PersonaFiveSoundSet implements PersonaSoundSet {
	@Override
	public Identifier getId() {
		return new Identifier(PersonaMod.MOD_ID, "five");
	}

	@Override
	public SoundEvent getBattleMusic() {
		return PersonaSounds.MUSIC_TAKE_OVER;
	}
}
