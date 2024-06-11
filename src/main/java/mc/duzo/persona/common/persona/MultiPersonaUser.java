package mc.duzo.persona.common.persona;

import java.util.List;

public interface MultiPersonaUser extends PersonaUser {
	default List<AbstractPersona> getPersonas() {
		AbstractPersona found = this.findPersona().orElse(null);
		if (found == null) return List.of();
		return List.of(found);
	}
}
