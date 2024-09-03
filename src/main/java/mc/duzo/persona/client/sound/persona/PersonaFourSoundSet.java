package mc.duzo.persona.client.sound.persona;

import java.util.List;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import mc.duzo.persona.PersonaMod;
import mc.duzo.persona.common.PersonaSounds;

public class PersonaFourSoundSet implements PersonaSoundSet {
    @Override
    public Identifier getId() {
        return new Identifier(PersonaMod.MOD_ID, "four");
    }

    @Override
    public List<SoundEvent> getBattleMusic() {
        return List.of(PersonaSounds.MUSIC_REACH_OUT, PersonaSounds.MUSIC_MAKE_HISTORY);
    }
}
