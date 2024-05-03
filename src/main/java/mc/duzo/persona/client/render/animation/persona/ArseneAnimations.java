package mc.duzo.persona.client.render.animation.persona;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public class ArseneAnimations {
	public static final Animation IDLE = Animation.Builder.create(4f).looping()
			.addBoneAnimation("arsene",
					new Transformation(Transformation.Targets.TRANSLATE,
							new Keyframe(0f, AnimationHelper.createTranslationalVector(11f, 16f, 11f),
									Transformation.Interpolations.CUBIC),
							new Keyframe(1f, AnimationHelper.createTranslationalVector(11f, 14f, 11f),
									Transformation.Interpolations.CUBIC),
							new Keyframe(2f, AnimationHelper.createTranslationalVector(11f, 16f, 11f),
									Transformation.Interpolations.CUBIC),
							new Keyframe(3f, AnimationHelper.createTranslationalVector(11f, 14f, 11f),
									Transformation.Interpolations.CUBIC),
							new Keyframe(4f, AnimationHelper.createTranslationalVector(11f, 16f, 11f),
									Transformation.Interpolations.CUBIC)))
			.addBoneAnimation("arsene",
					new Transformation(Transformation.Targets.ROTATE,
							new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 30f, 0f),
									Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("heading",
					new Transformation(Transformation.Targets.ROTATE,
							new Keyframe(0f, AnimationHelper.createRotationalVector(15.36f, -17.35f, -2.36f),
									Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("left_arm",
					new Transformation(Transformation.Targets.ROTATE,
							new Keyframe(0f, AnimationHelper.createRotationalVector(-99.57f, -52.46f, 71.49f),
									Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("right_arm",
					new Transformation(Transformation.Targets.ROTATE,
							new Keyframe(0f, AnimationHelper.createRotationalVector(-15.96f, 4.18f, 19.62f),
									Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("left_legs",
					new Transformation(Transformation.Targets.ROTATE,
							new Keyframe(0f, AnimationHelper.createRotationalVector(-2.33f, -24.9f, -11.99f),
									Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("right_leg",
					new Transformation(Transformation.Targets.ROTATE,
							new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 15f, 15f),
									Transformation.Interpolations.LINEAR))).build();

	public static final Animation TEST = Animation.Builder.create(2f).looping()
			.addBoneAnimation("arsene",
					new Transformation(Transformation.Targets.TRANSLATE,
							new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
									Transformation.Interpolations.LINEAR),
							new Keyframe(1f, AnimationHelper.createTranslationalVector(0f, 15f, 0f),
									Transformation.Interpolations.LINEAR),
							new Keyframe(2f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
									Transformation.Interpolations.LINEAR))).build();
}
