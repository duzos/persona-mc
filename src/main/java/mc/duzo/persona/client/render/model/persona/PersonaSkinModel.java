package mc.duzo.persona.client.render.model.persona;

import mc.duzo.persona.common.persona.Persona;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

/**
 * Using this causes the renderer to render the persona with a generic player model instead
 */
public class PersonaSkinModel extends PersonaModel{
	private final Persona persona;

	public PersonaSkinModel(Persona persona) {
		this.persona = persona;
	}
	@Override
	public void render(LivingEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {

	}

	@Override
	public Persona getPersona() {
		return this.persona;
	}

	@Override
	public void setAngles(LivingEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {

	}
}
