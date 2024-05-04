package mc.duzo.persona.client.render.animation.player.holder;

import mc.duzo.persona.client.render.animation.player.PlayerAnimationHelper;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.AnimationState;

public class PlayerAnimationHolder {
	private final AnimationState state;
	private final Animation animation;

	public PlayerAnimationHolder(Animation anim) {
		this.state = new AnimationState();
		this.animation = anim;
	}

	public void update(PlayerEntityModel<?> model, float progress) {
		this.state.startIfNotRunning(0);
		PlayerAnimationHelper.updateAnimation(this.state, this.animation, progress, model);
	}

	public boolean isFinished(AbstractClientPlayerEntity entity) {
		if (this.animation.looping()) return false; // Looping animations should extend this class so they properly finish

		return this.getRunningSeconds() >= this.animation.lengthInSeconds();
	}

	private float getRunningSeconds() {
		return PlayerAnimationHelper.getRunningSeconds(this.animation, this.state.getTimeRunning());
	}
	public Animation getAnimation() {
		return animation;
	}
}
