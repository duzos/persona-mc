package mc.duzo.persona.client.render.model.persona;

import mc.duzo.persona.common.persona.AbstractPersona;
import mc.duzo.persona.common.persona.PersonaRegistry;
import net.minecraft.client.model.*;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

public class OrpheusModel extends PersonaModel {
	private final ModelPart root;
	public OrpheusModel(ModelPart root) {
		this.root = root.getChild("root");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData harp = root.addChild("harp", ModelPartBuilder.create().uv(60, 65).cuboid(-3.0F, -2.0F, -1.0F, 6.0F, 6.0F, 3.0F, new Dilation(0.01F))
				.uv(60, 48).cuboid(-5.0F, 3.0F, 0.0F, 10.0F, 9.0F, 0.0F, new Dilation(0.01F))
				.uv(0, 0).cuboid(-4.5F, -30.0F, 0.0F, 10.0F, 29.0F, 0.0F, new Dilation(0.01F))
				.uv(44, 12).cuboid(-5.0F, -43.0F, 0.0F, 11.0F, 13.0F, 0.0F, new Dilation(0.01F))
				.uv(20, 0).cuboid(9.0F, -28.0F, -1.0F, 3.0F, 25.0F, 3.0F, new Dilation(0.01F)), ModelTransform.pivot(0.0F, -28.0F, 4.0F));

		ModelPartData cube_r1 = harp.addChild("cube_r1", ModelPartBuilder.create().uv(77, 54).cuboid(4.0F, -2.3294F, -1.0F, 4.0F, 3.0F, 3.0F, new Dilation(-0.1F)), ModelTransform.of(-8.5151F, -29.0125F, -0.1F, 0.0F, 0.0F, -0.4363F));

		ModelPartData cube_r2 = harp.addChild("cube_r2", ModelPartBuilder.create().uv(42, 34).cuboid(-3.0F, -3.0F, -1.0F, 11.0F, 3.0F, 3.0F, new Dilation(-0.2F)), ModelTransform.of(-7.0F, -26.0F, 0.0F, 0.0F, 0.0F, -0.4363F));

		ModelPartData cube_r3 = harp.addChild("cube_r3", ModelPartBuilder.create().uv(50, 0).cuboid(-3.0F, -3.0F, -1.0F, 11.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(4.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2618F));

		ModelPartData bone7 = harp.addChild("bone7", ModelPartBuilder.create().uv(20, 0).cuboid(5.0F, -28.0F, -1.0F, 3.0F, 25.0F, 3.0F, new Dilation(0.01F))
				.uv(18, 70).cuboid(7.0F, -28.0F, -1.0F, 5.0F, 6.0F, 3.0F, new Dilation(-0.2F))
				.uv(34, 71).cuboid(1.0F, -7.0F, -1.0F, 5.0F, 5.0F, 3.0F, new Dilation(-0.2F))
				.uv(71, 30).cuboid(-14.0F, -7.0F, -1.0F, 5.0F, 5.0F, 3.0F, new Dilation(-0.2F))
				.uv(68, 20).cuboid(-20.0F, -28.0F, -1.0F, 5.0F, 6.0F, 3.0F, new Dilation(-0.2F)), ModelTransform.of(-4.0F, 0.0F, 1.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData cube_r4 = bone7.addChild("cube_r4", ModelPartBuilder.create().uv(75, 3).cuboid(4.0F, -3.0F, -1.0F, 4.0F, 3.0F, 3.0F, new Dilation(-0.1F)), ModelTransform.of(-13.2317F, -28.4048F, 0.1F, 0.0F, 0.0F, -0.4363F));

		ModelPartData cube_r5 = bone7.addChild("cube_r5", ModelPartBuilder.create().uv(42, 34).cuboid(-3.0F, -3.0F, -1.0F, 11.0F, 3.0F, 3.0F, new Dilation(-0.2F)), ModelTransform.of(-12.0F, -26.0F, 0.0F, 0.0F, 0.0F, -0.4363F));

		ModelPartData cube_r6 = bone7.addChild("cube_r6", ModelPartBuilder.create().uv(50, 0).cuboid(-3.0F, -3.0F, -1.0F, 11.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2618F));

		ModelPartData right_leg = root.addChild("right_leg", ModelPartBuilder.create().uv(22, 60).cuboid(-2.0F, -1.0F, -2.5F, 5.0F, 5.0F, 5.0F, new Dilation(0.2F))
				.uv(50, 71).cuboid(-1.0F, 6.0F, -1.5F, 3.0F, 9.0F, 3.0F, new Dilation(0.0F))
				.uv(77, 62).cuboid(-1.0F, 12.0F, -1.5F, 3.0F, 3.0F, 3.0F, new Dilation(0.3F))
				.uv(74, 70).cuboid(-1.5F, 2.5F, -2.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.2F))
				.uv(62, 74).cuboid(-1.5F, 3.0F, -2.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, -32.0F, 0.0F, -0.5996F, 0.1245F, 0.1796F));

		ModelPartData right_pants = right_leg.addChild("right_pants", ModelPartBuilder.create(), ModelTransform.pivot(6.0F, 30.0F, 0.0F));

		ModelPartData bone5 = right_leg.addChild("bone5", ModelPartBuilder.create().uv(0, 75).cuboid(-0.5F, 0.0F, -2.0F, 4.0F, 2.0F, 4.0F, new Dilation(0.2F))
				.uv(44, 42).cuboid(-0.5F, 1.9F, -2.0F, 4.0F, 15.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 15.0F, 0.0F, 0.9877F, 0.2201F, -0.143F));

		ModelPartData left_leg = root.addChild("left_leg", ModelPartBuilder.create().uv(22, 60).cuboid(-2.0F, -1.0F, -2.5F, 5.0F, 5.0F, 5.0F, new Dilation(0.2F))
				.uv(50, 71).cuboid(-1.0F, 6.0F, -1.5F, 3.0F, 9.0F, 3.0F, new Dilation(0.0F))
				.uv(77, 62).cuboid(-1.0F, 12.0F, -1.5F, 3.0F, 3.0F, 3.0F, new Dilation(0.3F))
				.uv(62, 74).cuboid(-1.5F, 3.0F, -2.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.0F))
				.uv(74, 70).cuboid(-1.5F, 2.5F, -2.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.2F)), ModelTransform.of(2.0F, -32.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

		ModelPartData bone6 = left_leg.addChild("bone6", ModelPartBuilder.create().uv(0, 75).cuboid(-2.5F, 1.0F, -2.0F, 4.0F, 2.0F, 4.0F, new Dilation(0.2F))
				.uv(44, 42).cuboid(-2.5F, 2.9F, -2.0F, 4.0F, 15.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, 14.0F, 0.0F));

		ModelPartData left_pants = left_leg.addChild("left_pants", ModelPartBuilder.create(), ModelTransform.pivot(-4.0F, 30.0F, 0.0F));

		ModelPartData right_arm = root.addChild("right_arm", ModelPartBuilder.create(), ModelTransform.of(-5.0F, -30.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

		ModelPartData left_arm_r1 = right_arm.addChild("left_arm_r1", ModelPartBuilder.create().uv(42, 61).cuboid(6.952F, -40.7417F, -3.5F, 4.0F, 5.0F, 5.0F, new Dilation(0.24F)), ModelTransform.of(4.0F, 23.0F, -1.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData bone3 = right_arm.addChild("bone3", ModelPartBuilder.create().uv(77, 62).cuboid(-4.452F, -10.3449F, -5.1236F, 3.0F, 3.0F, 3.0F, new Dilation(0.1F))
				.uv(66, 6).cuboid(-4.452F, -16.3449F, -5.1236F, 3.0F, 11.0F, 3.0F, new Dilation(0.0F))
				.uv(78, 9).cuboid(-4.452F, -16.3449F, -5.1236F, 3.0F, 2.0F, 3.0F, new Dilation(0.2F)), ModelTransform.of(-2.0F, 4.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

		ModelPartData bone4 = right_arm.addChild("bone4", ModelPartBuilder.create().uv(32, 12).cuboid(-10.452F, -15.9669F, -9.5343F, 3.0F, 7.0F, 3.0F, new Dilation(0.31F))
				.uv(78, 14).cuboid(-10.452F, -15.9669F, -9.5343F, 3.0F, 1.0F, 3.0F, new Dilation(0.4F)), ModelTransform.of(4.0F, 14.0F, 1.0F, -0.3054F, 0.0F, 0.0F));

		ModelPartData right_sleve = right_arm.addChild("right_sleve", ModelPartBuilder.create(), ModelTransform.pivot(2.0F, 8.0F, 0.0F));

		ModelPartData left_arm = root.addChild("left_arm", ModelPartBuilder.create().uv(42, 61).cuboid(2.952F, -18.7417F, -2.5F, 4.0F, 5.0F, 5.0F, new Dilation(0.24F)), ModelTransform.of(4.0F, -29.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

		ModelPartData bone2 = left_arm.addChild("bone2", ModelPartBuilder.create().uv(66, 6).cuboid(1.452F, -16.5985F, -3.6852F, 3.0F, 11.0F, 3.0F, new Dilation(0.0F))
				.uv(78, 9).cuboid(1.452F, -16.5985F, -3.6852F, 3.0F, 2.0F, 3.0F, new Dilation(0.2F))
				.uv(77, 62).cuboid(1.452F, -10.5985F, -3.6852F, 3.0F, 3.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(2.0F, 3.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

		ModelPartData bone = left_arm.addChild("bone", ModelPartBuilder.create().uv(78, 14).cuboid(2.1171F, -15.0075F, -8.2768F, 3.0F, 1.0F, 3.0F, new Dilation(0.4F))
				.uv(32, 12).cuboid(2.1171F, -15.0075F, -8.2768F, 3.0F, 7.0F, 3.0F, new Dilation(0.31F)), ModelTransform.of(2.0F, 13.0F, -2.0F, -0.4796F, -0.0201F, -0.0387F));

		ModelPartData left_sleve = left_arm.addChild("left_sleve", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 7.0F, 0.0F));

		ModelPartData body = root.addChild("body", ModelPartBuilder.create().uv(0, 56).cuboid(-4.0F, -19.0F, -2.0F, 7.0F, 7.0F, 4.0F, new Dilation(0.0F))
				.uv(56, 57).cuboid(-4.5F, -23.0F, -2.0F, 8.0F, 4.0F, 4.0F, new Dilation(0.7F))
				.uv(0, 67).cuboid(-3.0F, -23.0F, -2.0F, 5.0F, 4.0F, 4.0F, new Dilation(0.72F))
				.uv(20, 49).cuboid(-4.0F, -19.0F, -2.0F, 7.0F, 7.0F, 4.0F, new Dilation(0.1F))
				.uv(24, 30).cuboid(-0.5F, -19.0F, -3.0F, 0.0F, 7.0F, 1.0F, new Dilation(0.0F))
				.uv(20, 0).cuboid(-0.5F, -16.0F, -4.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(17, 46).cuboid(-4.0F, -15.5F, -3.0F, 7.0F, 0.0F, 1.0F, new Dilation(0.0F))
				.uv(19, 0).cuboid(-1.0F, -15.5F, -4.0F, 1.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -24.0F, 0.0F));

		ModelPartData body_r1 = body.addChild("body_r1", ModelPartBuilder.create().uv(60, 40).cuboid(-4.0F, -17.3F, -2.0F, 7.0F, 4.0F, 4.0F, new Dilation(1.0F)), ModelTransform.of(-1.0F, -25.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

		ModelPartData jacket = body.addChild("jacket", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData head = root.addChild("head", ModelPartBuilder.create().uv(0, 41).cuboid(-3.5F, -24.0F, -3.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.4F))
				.uv(24, 34).cuboid(-3.5F, -24.0F, -3.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.62F))
				.uv(32, 0).cuboid(-3.5F, -24.0F, -3.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.64F))
				.uv(0, 29).cuboid(-3.5F, -24.0F, -3.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.81F))
				.uv(26, 22).cuboid(-3.0F, -24.0F, -3.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.94F))
				.uv(50, 25).cuboid(-3.5F, -18.4F, -3.0F, 6.0F, 2.0F, 6.0F, new Dilation(0.8F)), ModelTransform.pivot(0.0F, -31.0F, 0.0F));

		ModelPartData head_r1 = head.addChild("head_r1", ModelPartBuilder.create().uv(20, 25).cuboid(-4.7F, -3.0F, -4.5F, 0.0F, 3.0F, 3.0F, new Dilation(-0.5F)), ModelTransform.of(-2.0F, -21.0F, -2.0F, 3.1416F, 0.0F, 3.1416F));

		ModelPartData headwear = head.addChild("headwear", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 3.0F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public AbstractPersona getPersona() {
		return PersonaRegistry.ORPHEUS;
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}

	@Override
	public void setAngles(LivingEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}

	@Override
	public void render(LivingEntity entity, float tickDelta, MatrixStack matrices, VertexConsumer vertexConsumers, int light, float r, float g, float b, float alpha) {
		matrices.push();

		matrices.scale(0.75f, 0.75f, 0.75f);
		matrices.translate(0f, 1f, 0.5f);

		render(matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV, r, g, b, alpha);

		matrices.pop();
	}
}