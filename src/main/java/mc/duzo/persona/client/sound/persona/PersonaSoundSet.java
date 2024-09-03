package mc.duzo.persona.client.sound.persona;

import java.util.List;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import mc.duzo.persona.PersonaMod;

public interface PersonaSoundSet {
    Identifier getId();
    List<SoundEvent> getBattleMusic();
    default SoundEvent getRandomBattleMusic() {
        return getBattleMusic().get(PersonaMod.RANDOM.nextInt(getBattleMusic().size()));
    }
}
