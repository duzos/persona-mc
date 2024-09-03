package mc.duzo.persona.common;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import mc.duzo.persona.PersonaMod;

public class PersonaDimensions {
    public static final RegistryKey<World> VELVET_DIM_WORLD = RegistryKey.of(RegistryKeys.WORLD, new Identifier(PersonaMod.MOD_ID, "velvet_room"));
}
