package mc.duzo.persona.commands.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import mc.duzo.persona.PersonaMod;
import mc.duzo.persona.commands.argument.PersonaArgumentType;
import mc.duzo.persona.common.persona.AbstractPersona;
import mc.duzo.persona.data.global.server.ServerData;
import mc.duzo.persona.data.player.PlayerData;
import mc.duzo.persona.data.player.server.ServerPlayerData;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class SetPersonaCommand {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(literal(PersonaMod.MOD_ID)
				.then(literal("persona").requires(source -> source.hasPermissionLevel(2))
						.then(literal("set")
								.executes(SetPersonaCommand::runClearCommand)
								.then(argument("persona", PersonaArgumentType.persona())
								.executes(SetPersonaCommand::runCommand)))
				));
	}

	private static int runCommand(CommandContext<ServerCommandSource> context) {
		ServerPlayerEntity player = context.getSource().getPlayer();

		if (player == null) return 0;

		ServerPlayerData data = ServerData.getPlayerState(player);
		AbstractPersona persona = PersonaArgumentType.getPersona(context, "persona");

		data.setPersona(persona);

		return Command.SINGLE_SUCCESS;
	}
	private static int runClearCommand(CommandContext<ServerCommandSource> context) {
		ServerPlayerEntity player = context.getSource().getPlayer();

		if (player == null) return 0;

		PlayerData data = ServerData.getPlayerState(player);

		data.setPersona(null);
		data.hidePersona();

		return Command.SINGLE_SUCCESS;
	}
}
