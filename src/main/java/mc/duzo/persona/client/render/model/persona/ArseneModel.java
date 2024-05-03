package mc.duzo.persona.client.render.model.persona;

import mc.duzo.persona.PersonaMod;
import mc.duzo.persona.client.data.ClientData;
import mc.duzo.persona.client.render.animation.persona.PersonaAnimationHelper;
import mc.duzo.persona.client.render.animation.persona.ArseneAnimations;
import mc.duzo.persona.common.persona.AbstractPersona;
import mc.duzo.persona.common.persona.PersonaRegistry;
import net.minecraft.client.model.*;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class ArseneModel extends PersonaModel {
	private static final Identifier TEXTURE = new Identifier(PersonaMod.MOD_ID, "textures/persona/arsene.png");
	private static final Identifier EMISSION = new Identifier(PersonaMod.MOD_ID, "textures/persona/arsene_emission.png");

	private final ModelPart root;
	public ArseneModel(ModelPart root) {
		this.root = root.getChild("arsene");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData arsene = modelPartData.addChild("arsene", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData heading = arsene.addChild("heading", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -47.0F, 0.0F));

		ModelPartData mask = heading.addChild("mask", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData cube_r1 = mask.addChild("cube_r1", ModelPartBuilder.create().uv(46, 145).cuboid(-1.0F, -9.0F, 1.0F, 14.0F, 9.0F, 0.0F, new Dilation(0.005F)), ModelTransform.of(1.4142F, -2.2422F, -4.618F, 0.0F, -0.7854F, 0.0F));

		ModelPartData cube_r2 = mask.addChild("cube_r2", ModelPartBuilder.create().uv(46, 122).cuboid(1.0F, -9.0F, -1.0F, 0.0F, 9.0F, 14.0F, new Dilation(0.005F)), ModelTransform.of(-1.4142F, -2.2422F, -4.618F, 0.0F, -0.7854F, 0.0F));

		ModelPartData hat = mask.addChild("hat", ModelPartBuilder.create().uv(129, 126).cuboid(-3.0F, -24.6252F, -3.153F, 6.0F, 11.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.1F, 0.25F, 2.0F));

		ModelPartData cube_r3 = hat.addChild("cube_r3", ModelPartBuilder.create().uv(0, 69).cuboid(-4.5F, -12.6252F, -4.653F, 9.0F, 2.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		ModelPartData bodylicious = arsene.addChild("bodylicious", ModelPartBuilder.create().uv(0, 81).cuboid(-6.0F, -20.0F, -3.0F, 12.0F, 20.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -27.0F, 0.0F));

		ModelPartData jacket = bodylicious.addChild("jacket", ModelPartBuilder.create().uv(49, 66).cuboid(-6.0F, -20.0F, -3.0F, 12.0F, 20.0F, 6.0F, new Dilation(0.25F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData ascot = bodylicious.addChild("ascot", ModelPartBuilder.create().uv(104, 144).cuboid(-6.0F, 0.0F, -3.0F, 12.0F, 9.0F, 0.0F, new Dilation(0.256F)), ModelTransform.of(0.0F, -19.5F, 0.0F, -0.2618F, 0.0F, 0.0F));

		ModelPartData cube_r4 = ascot.addChild("cube_r4", ModelPartBuilder.create().uv(103, 155).cuboid(-3.0F, -1.75F, -3.0F, 6.0F, 1.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

		ModelPartData chain = bodylicious.addChild("chain", ModelPartBuilder.create().uv(104, 134).cuboid(-6.0F, 0.5F, -3.0F, 12.0F, 9.0F, 0.0F, new Dilation(0.256F)), ModelTransform.of(0.0F, -20.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

		ModelPartData Wings = bodylicious.addChild("Wings", ModelPartBuilder.create(), ModelTransform.pivot(-7.0F, 6.0F, 3.55F));

		ModelPartData leftWing = Wings.addChild("leftWing", ModelPartBuilder.create().uv(65, 33).mirrored().cuboid(-32.0F, -32.0F, 0.0F, 32.0F, 27.0F, 0.0F, new Dilation(0.001F)).mirrored(false), ModelTransform.pivot(38.0F, 0.0F, 0.0F));

		ModelPartData cube_r5 = leftWing.addChild("cube_r5", ModelPartBuilder.create().uv(65, 0).cuboid(0.0F, -32.0F, 0.0F, 32.0F, 27.0F, 0.0F, new Dilation(0.001F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

		ModelPartData rightWing = Wings.addChild("rightWing", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData cube_r6 = rightWing.addChild("cube_r6", ModelPartBuilder.create().uv(0, 0).cuboid(-0.0405F, -27.0F, 0.0265F, 32.0F, 27.0F, 0.0F, new Dilation(0.001F)), ModelTransform.of(-24.0F, -5.0F, 0.0422F, 0.0F, 2.618F, 0.0F));

		ModelPartData cube_r7 = rightWing.addChild("cube_r7", ModelPartBuilder.create().uv(0, 33).mirrored().cuboid(-39.0F, -32.0F, 1.0F, 32.0F, 27.0F, 0.0F, new Dilation(0.001F)).mirrored(false), ModelTransform.of(-31.0F, 0.0F, 1.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData left_arm = arsene.addChild("left_arm", ModelPartBuilder.create().uv(23, 127).cuboid(-2.5F, -3.0F, -3.0F, 5.0F, 26.0F, 6.0F, new Dilation(-0.25F)), ModelTransform.pivot(8.5F, -44.0F, 0.0F));

		ModelPartData left_sleeve = left_arm.addChild("left_sleeve", ModelPartBuilder.create().uv(0, 108).cuboid(7.0F, -47.0F, -3.0F, 5.0F, 26.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-9.5F, 44.0F, 0.0F));

		ModelPartData right_arm = arsene.addChild("right_arm", ModelPartBuilder.create().uv(112, 99).cuboid(-2.5F, -3.0F, -3.0F, 5.0F, 26.0F, 6.0F, new Dilation(-0.25F)), ModelTransform.of(-8.5F, -44.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		ModelPartData right_sleeve = right_arm.addChild("right_sleeve", ModelPartBuilder.create().uv(111, 66).cuboid(-12.0F, -47.0F, -3.0F, 5.0F, 26.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(9.5F, 44.0F, 0.0F));

		ModelPartData left_legs = arsene.addChild("left_legs", ModelPartBuilder.create().uv(87, 100).cuboid(-2.5F, 0.0F, -3.0F, 6.0F, 27.0F, 6.0F, new Dilation(-0.25F)), ModelTransform.pivot(2.5F, -27.0F, 0.0F));

		ModelPartData left_pants = left_legs.addChild("left_pants", ModelPartBuilder.create().uv(86, 66).cuboid(0.0F, -27.0F, -3.0F, 6.0F, 27.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.5F, 27.0F, 0.0F));

		ModelPartData left_foot = left_legs.addChild("left_foot", ModelPartBuilder.create(), ModelTransform.pivot(-0.5F, 21.5F, -1.0F));

		ModelPartData cube_r8 = left_foot.addChild("cube_r8", ModelPartBuilder.create().uv(148, 108).cuboid(-1.0F, -2.0F, -2.0F, 0.0F, 14.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, -3.0F, 3.5F, 0.1745F, 0.0F, 0.0F));

		ModelPartData right_leg = arsene.addChild("right_leg", ModelPartBuilder.create().uv(62, 94).cuboid(-4.0F, 0.0F, -3.0F, 6.0F, 27.0F, 6.0F, new Dilation(-0.25F)), ModelTransform.pivot(-2.0F, -27.0F, 0.0F));

		ModelPartData right_pants = right_leg.addChild("right_pants", ModelPartBuilder.create().uv(37, 93).cuboid(-6.0F, -27.0F, -3.0F, 6.0F, 27.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, 27.0F, 0.0F));

		ModelPartData right_foot = right_leg.addChild("right_foot", ModelPartBuilder.create(), ModelTransform.pivot(-2.0F, 21.5F, -0.5F));

		ModelPartData cube_r9 = right_foot.addChild("cube_r9", ModelPartBuilder.create().uv(135, 105).cuboid(-1.0F, -2.0F, -2.0F, 0.0F, 14.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, -3.0F, 3.25F, 0.1745F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 256, 256);
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		root.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public void render(LivingEntity entity, float tickDelta, MatrixStack matrices, VertexConsumer vertexConsumers, int light, float r, float g, float b, float alpha) {
		matrices.push();

		matrices.scale(0.75f, 0.75f, 0.75f);
		matrices.translate(0, 1.5f, 0f);

		render(matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV, r, g, b, alpha);

		matrices.pop();
	}
	private void runAnimations(AbstractPersona persona, float progress) {
		this.resetTransforms();
		persona.animationState.startIfNotRunning(0);

		PersonaAnimationHelper.updateAnimation(persona.animationState, ArseneAnimations.IDLE, progress, this);
	}

	@Override
	public AbstractPersona getPersona() {
		return PersonaRegistry.ARSENE;
	}

	@Override
	public ModelPart getPart() {
		return root;
	}

	@Override
	protected String getPartName() {
		return "arsene";
	}

	@Override
	public Optional<Identifier> getEmission() {
		return Optional.of(EMISSION);
	}

	@Override
	public Identifier getTexture() {
		return TEXTURE;
	}

	@Override
	public void setAngles(LivingEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.runAnimations(ClientData.findPersona(entity), animationProgress);
	}
}
