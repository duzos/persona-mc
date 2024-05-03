package mc.duzo.persona.commands.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import mc.duzo.persona.PersonaMod;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class PersonaArgumentRegister {
	public static void register() {
		register("persona", PersonaArgumentType.class, PersonaArgumentType::persona);
	}

	private static <T extends ArgumentType<?>> void register(String name, Class<T> t, Supplier<T> supplier) {
		ArgumentTypeRegistry.registerArgumentType(new Identifier(PersonaMod.MOD_ID, name), t, ConstantArgumentSerializer.of(supplier));
	}
}
