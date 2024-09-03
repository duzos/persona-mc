package mc.duzo.persona.client.sound.persona;

import java.util.HashMap;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import mc.duzo.persona.PersonaMod;
import mc.duzo.persona.client.PersonaModClient;
import mc.duzo.persona.client.sound.MusicSound;

public class SoundSetRegistry {
    private static final HashMap<Identifier, PersonaSoundSet> REGISTRY = new HashMap<>();
    public static PersonaSoundSet register(PersonaSoundSet soundSet) {
        return REGISTRY.put(soundSet.getId(), soundSet);
    }

    public static PersonaSoundSet fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    /**
     * For PersonaMod usage only!
     */
    public static PersonaSoundSet fromName(String name) {
        return REGISTRY.get(new Identifier(PersonaMod.MOD_ID, name));
    }

    public static PersonaSoundSet findRandom() {
        int size = REGISTRY.size();
        int chosen = PersonaMod.RANDOM.nextInt(size);
        return (PersonaSoundSet) REGISTRY.values().toArray()[chosen];
    }

    public static boolean isPlayingBattleMusic() {
        for (PersonaSoundSet set : REGISTRY.values()) {
            for (SoundEvent song : set.getBattleMusic()) {
                if (PersonaModClient.sounds.isPlaying(song)) return true;
            }
        }
        return false;
    }
    public static PersonaSoundSet playRandomBattleMusic() {
        PersonaSoundSet set = findRandom();
        PersonaModClient.sounds.startIfNotPlaying(new MusicSound(set.getRandomBattleMusic(), 0.25f));
        return set;
    }
    public static void stopBattleMusic() {
        for (PersonaSoundSet set : REGISTRY.values()) {
            for (SoundEvent song : set.getBattleMusic()) {
                if (PersonaModClient.sounds.isPlaying(song))
                    PersonaModClient.sounds.stopSound(song);
            }
        }
    }

    public static PersonaSoundSet FIVE;
    public static PersonaSoundSet FOUR;
    public static PersonaSoundSet THREE;

    public static void initialise() {
        FIVE = register(new PersonaFiveSoundSet());
        FOUR = register(new PersonaFourSoundSet());
        THREE = register(new PersonaThreeSoundSet());
    }
}
