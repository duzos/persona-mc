package mc.duzo.persona.data.global.server;

import java.util.*;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import mc.duzo.persona.PersonaMod;
import mc.duzo.persona.common.battle.data.ServerBattleData;
import mc.duzo.persona.data.player.server.ServerPlayerData;
import mc.duzo.persona.network.PersonaMessages;

/**
 * Data that will be saved to the world in .nbt form
 * For saving across server restarts.
 * Remember to call markDirty() after setting a value to ensure it saves
 *
 * @author duzo
 */
public class ServerData extends PersistentState {
    public boolean hasVelvetRoom;
    private HashMap<UUID, ServerPlayerData> players = new HashMap<>();
    private HashMap<UUID, ServerBattleData> battles = new HashMap<>();

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtCompound playersNbt = new NbtCompound();

        players.forEach(((uuid, playerData) -> {
            playersNbt.put(uuid.toString(), playerData.serialize());
        }));

        nbt.put("players", playersNbt);

        nbt.putBoolean("HasVelvetRoom", hasVelvetRoom);

        NbtCompound battlesNbt = new NbtCompound();
        battles.forEach((uuid, battleData) -> {
            battlesNbt.put(uuid.toString(), battleData.toNbt());
        });
        nbt.put("Battles", battlesNbt);

        return nbt;
    }
    public static ServerData loadNbt(NbtCompound nbt) {
        ServerData data = new ServerData();

        NbtCompound playersNbt = nbt.getCompound("players");

        playersNbt.getKeys().forEach(key -> {
            ServerPlayerData playerData = new ServerPlayerData(playersNbt.getCompound(key));

            UUID uuid = UUID.fromString(key);
            data.players.put(uuid, playerData);
        });

        data.hasVelvetRoom = nbt.getBoolean("HasVelvetRoom");

        NbtCompound battlesNbt = nbt.getCompound("Battles");
        battlesNbt.getKeys().forEach(key -> {
            ServerBattleData battleData = new ServerBattleData(battlesNbt.getCompound(key));

            UUID uuid = UUID.fromString(key);
            data.battles.put(uuid, battleData);
        });

        return data;
    }
    public static ServerData getServerState(MinecraftServer server) {
        PersistentStateManager manager = server.getWorld(World.OVERWORLD).getPersistentStateManager();

        ServerData state = manager.getOrCreate(
                ServerData::loadNbt,
                ServerData::new,
                PersonaMod.MOD_ID
        );

        return state;
    }
    public static ServerData getServerState() {
        MinecraftServer server = PersonaMod.getServer().orElseThrow();

        return getServerState(server);
    }

    public static ServerPlayerData getPlayerState(LivingEntity player) {
        ServerData serverData = getServerState(player.getWorld().getServer());

        ServerPlayerData playerData = serverData.players.computeIfAbsent(player.getUuid(), ServerPlayerData::new);

        return playerData;
    }

    public static Set<UUID> getKeys(MinecraftServer server) {
        return getServerState(server).players.keySet();
    }

    public static Collection<ServerBattleData> getBattles(MinecraftServer server) {
        return getServerState(server).battles.values();
    }
    public static Optional<ServerBattleData> getBattleState(MinecraftServer server, UUID uuid) {
        ServerData serverData = getServerState(server);

        return Optional.ofNullable(serverData.battles.get(uuid));
    }

    public static void addBattle(MinecraftServer server, UUID uuid, ServerBattleData battleData) {
        ServerData serverData = getServerState(server);
        serverData.battles.put(uuid, battleData);
    }
    public static void addBattle(MinecraftServer server, ServerBattleData battleData) {
        addBattle(server, battleData.getUuid(), battleData);
    }
    private static void removeBattle(MinecraftServer server, UUID uuid) {
        PersonaMod.LOGGER.info("Removing battle " + uuid);

        ServerData serverData = getServerState(server);
        serverData.battles.remove(uuid);
    }
    public static void removeBattle(MinecraftServer server, UUID uuid, boolean sync) {
        if (sync) {
            ServerBattleData data = getBattleState(server, uuid).orElse(null);
            if (data == null) return; // No need to remove

            for (PlayerEntity player : data.getPlayers()) {
                PersonaMessages.syncBattleRemoval((ServerPlayerEntity) player, data);
            }
        }

        removeBattle(server, uuid);
    }

    public static void tick(MinecraftServer server) {
        for (ServerBattleData data : getBattles(server)) {
            data.tick(server);
        }
    }
}
