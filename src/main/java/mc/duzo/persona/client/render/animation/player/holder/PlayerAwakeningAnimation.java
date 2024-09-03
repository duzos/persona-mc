package mc.duzo.persona.client.render.animation.player.holder;

import mc.duzo.animation.player.holder.PlayerAnimationHolder;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;

import mc.duzo.persona.PersonaMod;
import mc.duzo.persona.client.render.animation.player.PersonaPlayerAnimations;
import mc.duzo.persona.common.PersonaSounds;

public class PlayerAwakeningAnimation extends PlayerAnimationHolder {
    public PlayerAwakeningAnimation() {
        super(new Identifier(PersonaMod.MOD_ID, "awakening"), PersonaPlayerAnimations.AWAKENING);
    }

    @Override
    protected void onStart(AbstractClientPlayerEntity player) {
        super.onStart(player);

        player.clientWorld.playSound(null, player.getBlockPos(), PersonaSounds.MUSIC_AWAKENING, SoundCategory.PLAYERS, 1f, 1f);
    }

    public boolean shouldMaskBeVisible() {
        return this.getRunningSeconds() < 13.5f;
    }
}
