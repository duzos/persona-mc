package mc.duzo.persona.client.render.model.persona;

import mc.duzo.persona.common.persona.Persona;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public abstract class PersonaModel extends EntityModel<LivingEntity> {

	// TODO - use this to render the persona, instead it calls the other render method, so this isnt ever called.
	public abstract void render(LivingEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light);
	public Identifier getTexture() {
		return this.getPersona().texture();
	}
	public abstract Persona getPersona();
}
