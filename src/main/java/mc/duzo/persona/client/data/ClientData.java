package mc.duzo.persona.client.data;

import mc.duzo.persona.PersonaMod;
import mc.duzo.persona.client.battle.ClientBattleCache;
import mc.duzo.persona.client.battle.data.ClientBattleData;
import mc.duzo.persona.common.battle.data.ServerBattleData;
import mc.duzo.persona.data.PlayerData;
import mc.duzo.persona.data.ServerData;
import mc.duzo.persona.network.PersonaMessages;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

/**
 * The clients version of {@link ServerData}
 * Should be synced within reason to avoid overload
 * Loads from NBT
 *
 * @author duzo
 */
public class ClientData {
    private static ClientData instance;
    private HashMap<UUID, PlayerData> players = new HashMap<>();
    private HashMap<UUID, ClientBattleData> battles = new HashMap<>();

    public static ClientData getInstance() {
        if (instance == null) {
            PersonaMod.LOGGER.warn("ClientData has not been initialized, creating new instance.");
            instance = new ClientData();
        }
        return instance;
    }

    public static void addPlayer(UUID uuid, NbtCompound data) {
        PlayerData playerData = PlayerData.createFromNbt(data);
        getInstance().players.put(uuid, playerData);
    }

    public static PlayerData getPlayerState(UUID uuid) {
        return getInstance().players.computeIfAbsent(uuid, key -> new PlayerData());
    }
    public static PlayerData getPlayerState(LivingEntity player) {
        return getPlayerState(player.getUuid());
    }

    public static void addBattle(UUID uuid, ClientBattleData battleData) {
        getInstance().battles.put(uuid, battleData);
    }
    public static void addBattle(UUID uuid, NbtCompound data) {
        addBattle(uuid, new ClientBattleData(data));
    }
    public static void addBattle(ClientBattleData data) {
        addBattle(data.getUuid(), data);
    }
    public static void addBattle(NbtCompound data) {
        addBattle(new ClientBattleData(data));
    }

    public static void removeBattle(UUID uuid) {
        PersonaMod.LOGGER.info("Removing battle " + uuid);

        getInstance().battles.remove(uuid);
    }
    public static void clearBattles() {
        getInstance().battles.clear();
        ClientBattleCache.clear();
    }

    public static Optional<ClientBattleData> getBattleState(UUID uuid) {
        return Optional.ofNullable(getInstance().battles.get(uuid));
    }
    public static Collection<ClientBattleData> getBattles() {
        return getInstance().battles.values();
    }

    public static void tick(MinecraftClient client) {
        for (ClientBattleData data : getInstance().getBattles()) {
            data.tick(client);
        }
    }
}
