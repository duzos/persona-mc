package mc.duzo.persona.commands.command;

import com.mojang.brigadier.CommandDispatcher;
import mc.duzo.persona.commands.command.EnterRoomCommand;
import mc.duzo.persona.commands.command.SetPersonaCommand;
import mc.duzo.persona.commands.command.SetSPCommand;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;

public class Commands {
    public static void init() {
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> registerCommands(dispatcher)));
    }

    private static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        EnterRoomCommand.register(dispatcher);
        SetSPCommand.register(dispatcher);
        SetPersonaCommand.register(dispatcher);
        AwakenPersonaCommand.register(dispatcher);
    }
}
