package mc.duzo.persona.client.render.animation;

import mc.duzo.animation.generic.AnimationInfo;
import mc.duzo.animation.player.holder.PlayerAnimationHolder;
import mc.duzo.animation.registry.AnimationRegistry;
import mc.duzo.persona.PersonaMod;
import mc.duzo.persona.client.render.animation.player.PersonaPlayerAnimations;
import mc.duzo.persona.client.render.animation.player.holder.PlayerAwakeningAnimation;
import mc.duzo.persona.client.render.animation.player.holder.PlayerIdleAnimation;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class PersonaAnimationRegistry {
	public static class Players {
		public static final Supplier<PlayerAnimationHolder> TOUCH_MASK = AnimationRegistry.instance().register(() -> new PlayerAnimationHolder(new Identifier(PersonaMod.MOD_ID, "touch_mask"), PersonaPlayerAnimations.TOUCH_MASK, new AnimationInfo(AnimationInfo.RenderType.ALL, null, AnimationInfo.Movement.DISABLE, AnimationInfo.Transform.TARGETED)));
		public static final Supplier<PlayerAnimationHolder> TOUCH_MASK_BATTLE = AnimationRegistry.instance().register(() -> new PlayerAnimationHolder(new Identifier(PersonaMod.MOD_ID, "touch_mask_battle"), PersonaPlayerAnimations.PERSONA_BATTLE_MASK_TOUCH, new AnimationInfo(AnimationInfo.RenderType.ALL, null, AnimationInfo.Movement.DISABLE, AnimationInfo.Transform.TARGETED)));
		public static final Supplier<PlayerAwakeningAnimation> AWAKENING = AnimationRegistry.instance().register(PlayerAwakeningAnimation::new);
		public static final Supplier<PlayerIdleAnimation> IDLE = AnimationRegistry.instance().register(PlayerIdleAnimation::new);


		public static void init() {

		}
	}

	public static void init() {
		Players.init();
	}
}
