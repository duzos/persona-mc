package mc.duzo.persona.client.render.model.persona;

import mc.duzo.persona.PersonaMod;
import mc.duzo.persona.client.render.model.door.P4DoorModel;
import mc.duzo.persona.client.render.model.door.VelvetDoorModel;
import mc.duzo.persona.client.render.model.door.VelvetDoorRegistry;
import mc.duzo.persona.common.entity.door.VelvetDoorVariant;
import mc.duzo.persona.common.persona.Persona;
import mc.duzo.persona.common.persona.PersonaRegistry;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class PersonaModelRegistry {
	// static registry stuff
	private static final HashMap<Identifier, PersonaModel> models = new HashMap<>();

	public static PersonaModel get(Identifier variant) {
		getInstance();

		if (!models.containsKey(variant) && PersonaRegistry.get(variant) != null) {
			PersonaMod.LOGGER.info("Registering generic skin model for " + variant); // Risky ?
			register(variant, new PersonaSkinModel(PersonaRegistry.get(variant)));
		}

		return models.get(variant);
	}

	public static PersonaModel register(Identifier variant, PersonaModel model) {
		models.put(variant, model);
		return model;
	}


	// initialising and adding our default stuff


	public PersonaModelRegistry() {
		this.init();
	}

	private void init() {
		/*
		for (Persona persona : PersonaRegistry.REGISTRY.stream().toList()) {
			if (persona.performsDataGen()) {
				PersonaMod.LOGGER.info("Registering model for " + persona.id());
				register(persona.id(), new PersonaSkinModel(persona));
			}
		}
		 */
	}

	// for obtaining our models statically
	// idk why i did it this way, i might change it later so this entire class is static.

	private static PersonaModelRegistry INSTANCE;

	public static PersonaModelRegistry getInstance() {
		if (INSTANCE == null) INSTANCE = new PersonaModelRegistry();

		return INSTANCE;
	}
}
