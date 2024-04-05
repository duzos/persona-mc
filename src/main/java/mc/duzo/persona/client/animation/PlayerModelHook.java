package mc.duzo.persona.client.animation;

import net.minecraft.client.model.ModelPart;

import java.util.Optional;

/**
 * For creating custom methods in classes
 * Not very good, and should be changed
 *
 * @author duzo
 */
public interface PlayerModelHook {
	Optional<ModelPart> persona$getChild(String name);
	ModelPart persona$getPart();
}