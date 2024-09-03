package mc.duzo.persona.common;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import mc.duzo.persona.PersonaMod;

public class PersonaSounds {
    public static final SoundEvent WELCOME_VELVET = register("welcome_velvet");


    public static final SoundEvent ATTACK = register("attack");
    public static final SoundEvent DIA = register("dia");
    public static final SoundEvent WEAK_PHYS = register("weak_phys");


    public static final SoundEvent FAIL = register("fail");
    public static final SoundEvent SKILL_SWITCH = register("skill_switch");

    public static final SoundEvent PERSONA_SHOUT = register("persona_shout");
    public static final SoundEvent ARSENE_SHOUT = register("arsene");
    public static final SoundEvent ORPHEUS_SHOUT = register("orpheus");


    public static final SoundEvent MUSIC_REACH_OUT = register("reach_out_to_the_truth");
    public static final SoundEvent MUSIC_GOING_DOWN = register("going_down");
    public static final SoundEvent MUSIC_MAKE_HISTORY = register("make_history");
    public static final SoundEvent MUSIC_TAKE_OVER = register("take_over");
    public static final SoundEvent MUSIC_VELVET = register("velvet_room");
    public static final SoundEvent MUSIC_AWAKENING = register("awakening");

    public static void init() {

    }

    private static SoundEvent register(String name) {
        return register(new Identifier(PersonaMod.MOD_ID, name));
    }
    private static SoundEvent register(Identifier id) {
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
}
