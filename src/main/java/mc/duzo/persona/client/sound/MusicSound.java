package mc.duzo.persona.client.sound;

import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

public class MusicSound extends PlayerFollowingLoopingSound {
    public MusicSound(SoundEvent soundEvent, float volume, float pitch) {
        super(soundEvent, SoundCategory.MUSIC, volume, pitch);
    }

    public MusicSound(SoundEvent soundEvent, float volume) {
        super(soundEvent, SoundCategory.MUSIC, volume);
    }

    public MusicSound(SoundEvent soundEvent) {
        super(soundEvent, SoundCategory.MUSIC);
    }

    @Override
    public void tick() {
        super.tick();

        MinecraftClient.getInstance().getMusicTracker().stop();
    }
}
