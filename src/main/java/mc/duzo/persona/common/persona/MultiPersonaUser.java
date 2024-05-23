package mc.duzo.persona.common.persona;

import java.util.List;

public interface MultiPersonaUser extends PersonaUser {
	List<AbstractPersona> getPersonas();
}
