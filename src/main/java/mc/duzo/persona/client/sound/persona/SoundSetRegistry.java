package mc.duzo.persona.client.sound.persona;

import mc.duzo.persona.PersonaMod;
import mc.duzo.persona.client.PersonaModClient;
import mc.duzo.persona.client.sound.MusicSound;
import net.minecraft.util.Identifier;

import java.util.HashMap;

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
			if (PersonaModClient.sounds.isPlaying(set.getBattleMusic())) {
				return true;
			}
		}
		return false;
	}
	public static PersonaSoundSet playRandomBattleMusic() {
		PersonaSoundSet set = findRandom();
		PersonaModClient.sounds.startIfNotPlaying(new MusicSound(set.getBattleMusic(), 0.25f));
		return set;
	}
	public static void stopBattleMusic() {
		for (PersonaSoundSet set : REGISTRY.values()) {
			if (PersonaModClient.sounds.isPlaying(set.getBattleMusic()))
				PersonaModClient.sounds.stopSound(set.getBattleMusic());
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
