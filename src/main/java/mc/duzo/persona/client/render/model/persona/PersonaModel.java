package mc.duzo.persona.client.render.model.persona;

import mc.duzo.persona.common.persona.Persona;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public abstract class PersonaModel extends EntityModel<LivingEntity> {

	/**
	 * This will be called to render the model, perform all adjustments here and render the model using the proper method.
	 */
	public abstract void render(LivingEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float r, float g, float b, float alpha);
	public Identifier getTexture() {
		return this.getPersona().texture();
	}
	public abstract Persona getPersona();
}
