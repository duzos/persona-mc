package mc.duzo.persona.client.render.model.persona;

import mc.duzo.persona.common.persona.AbstractPersona;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

/**
 * Using this causes the renderer to render the persona with a generic player model instead
 */
public class PersonaSkinModel extends PersonaModel{
	private final AbstractPersona persona;

	public PersonaSkinModel(AbstractPersona persona) {
		this.persona = persona;
	}


	@Override
	public void render(LivingEntity entity, float tickDelta, MatrixStack matrices, VertexConsumer vertexConsumers, int light, float r, float g, float b, float alpha) {

	}

	@Override
	public AbstractPersona getPersona() {
		return this.persona;
	}

	@Override
	public ModelPart getPart() {
		return null;
	}

	@Override
	public void setAngles(LivingEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {

	}
}
