package mc.duzo.persona.client.render.model.persona;

import mc.duzo.persona.common.persona.Persona;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

import java.util.Optional;

public abstract class PersonaModel extends EntityModel<LivingEntity> {

	/**
	 * This will be called to render the model, perform all adjustments here and render the model using the proper method.
	 */
	public abstract void render(LivingEntity entity, float tickDelta, MatrixStack matrices, VertexConsumer vertexConsumers, int light, float r, float g, float b, float alpha);
	public Identifier getTexture() {
		return this.getPersona().texture();
	}
	public Optional<Identifier> getEmission() {
		return Optional.empty(); // TODO
	}
	public abstract Persona getPersona();

	/**
	 * @return The root model part
	 */
	public abstract ModelPart getPart();

	/**
	 * @return the name of the root model part
	 */
	protected String getPartName() {
		return "root";
	}
	public Optional<ModelPart> getChild(String name) {
		if (name.equals(this.getPartName())) {
			return Optional.of(this.getPart());
		}
		return this.getPart().traverse().filter(part -> part.hasChild(name)).findFirst().map(part -> part.getChild(name));
	}
	protected void resetTransforms() {
		this.getPart().resetTransform();
		this.getPart().traverse().forEach(ModelPart::resetTransform);
	}
}
