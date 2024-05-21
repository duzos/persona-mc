package mc.duzo.persona.client.sound.persona;

import mc.duzo.persona.PersonaMod;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.List;

public interface PersonaSoundSet {
	Identifier getId();
	List<SoundEvent> getBattleMusic();
	default SoundEvent getRandomBattleMusic() {
		return getBattleMusic().get(PersonaMod.RANDOM.nextInt(getBattleMusic().size()));
	}
}
