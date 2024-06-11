package mc.duzo.persona.client.render.animation.player.holder;

import mc.duzo.persona.client.render.animation.player.PersonaPlayerAnimations;
import mc.duzo.persona.common.PersonaSounds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.sound.SoundCategory;

public class PlayerAwakeningAnimation extends PlayerAnimationHolder{
	public PlayerAwakeningAnimation() {
		super(PersonaPlayerAnimations.AWAKENING);
	}

	@Override
	protected void onStart(AbstractClientPlayerEntity player) {
		super.onStart(player);

		player.clientWorld.playSound(MinecraftClient.getInstance().player, player.getBlockPos(), PersonaSounds.MUSIC_AWAKENING, SoundCategory.PLAYERS, 1f, 1f);
	}

	public boolean shouldMaskBeVisible() {
		return this.getRunningSeconds() < 13.5f;
	}
}
