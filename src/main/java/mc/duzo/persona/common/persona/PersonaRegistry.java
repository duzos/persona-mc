package mc.duzo.persona.common.persona;

import mc.duzo.persona.PersonaMod;
import mc.duzo.persona.common.PersonaSounds;
import mc.duzo.persona.common.skill.SkillRegistry;
import mc.duzo.persona.common.skill.SkillSet;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;

public class PersonaRegistry {
    public static final SimpleRegistry<AbstractPersona> REGISTRY = FabricRegistryBuilder.createSimple(RegistryKey.<AbstractPersona>ofRegistry(new Identifier(PersonaMod.MOD_ID, "persona"))).buildAndRegister();

    public static AbstractPersona register(AbstractPersona persona) {
        return Registry.register(REGISTRY, persona.id(), persona);
    }

    public static AbstractPersona get(Identifier id) {
        return REGISTRY.get(id);
    }

    public static AbstractPersona DEV;
    public static AbstractPersona ARSENE;

    public static void init() {
        DEV = register(new Persona(
                new Identifier(PersonaMod.MOD_ID, "dev"),
                new SkillSet(
                        SkillRegistry.DIA,
                        SkillRegistry.CLEAVE,
                        SkillRegistry.ZIO,
                        SkillRegistry.TRAFURI,
                        SkillRegistry.VELVET
                ),
                new Identifier(PersonaMod.MOD_ID, "textures/skins/igor.png")
        ));
        ARSENE = register(new Persona(
                new Identifier(PersonaMod.MOD_ID, "arsene"),
                new SkillSet(
                        SkillRegistry.CLEAVE
                ),
                new Identifier(PersonaMod.MOD_ID, "textures/persona/arsene.png"),
                PersonaSounds.ARSENE_SHOUT
        ));
    }
}
