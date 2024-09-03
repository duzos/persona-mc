package mc.duzo.persona.util;

import net.minecraft.server.network.ServerPlayerEntity;

import mc.duzo.persona.data.global.server.ServerData;
import mc.duzo.persona.network.PersonaMessages;

public class DataHelper {
    /**
     * Marks ServerData as dirty
     * Syncs the players data to themselves and the tracking players
     * Should be called after modifying PlayerData
     * @author duzo
     */
    public static void markDirty(ServerPlayerEntity player) {
        ServerData.getServerState(player.getServer()).markDirty();
        PersonaMessages.syncData(player);
    }
}
