package mc.duzo.persona.data.global.client;

import mc.duzo.persona.PersonaMod;
import mc.duzo.persona.client.battle.ClientBattleCache;
import mc.duzo.persona.client.battle.ClientBattleHandler;
import mc.duzo.persona.client.battle.data.ClientBattleData;
import mc.duzo.animation.player.PlayerAnimationHelper;
import mc.duzo.animation.player.PlayerAnimationTracker;
import mc.duzo.persona.client.render.animation.player.PersonaPlayerAnimations;
import mc.duzo.persona.client.render.animation.player.holder.PlayerAwakeningAnimation;
import mc.duzo.persona.common.item.MaskItem;
import mc.duzo.persona.common.persona.AbstractPersona;
import mc.duzo.persona.data.player.PlayerData;
import mc.duzo.persona.data.global.server.ServerData;
import mc.duzo.persona.data.player.client.ClientPlayerData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * The clients version of {@link ServerData}
 * Should be synced within reason to avoid overload
 * Loads from NBT
 *
 * @author duzo
 */
public class ClientData {
    private static ClientData instance;
    private HashMap<UUID, ClientPlayerData> players = new HashMap<>();
    private HashMap<UUID, ClientBattleData> battles = new HashMap<>();

    public static ClientData getInstance() {
        if (instance == null) {
            PersonaMod.LOGGER.warn("ClientData has not been initialized, creating new instance.");
            instance = new ClientData();
        }
        return instance;
    }

    public static void addPlayer(UUID uuid, NbtCompound data) {
        ClientPlayerData playerData = new ClientPlayerData(data);

        ClientPlayerData stale = getInstance().players.get(uuid);

        getInstance().players.put(uuid, playerData);

        onUpdatePlayerData(uuid, stale, playerData);
    }
    private static void onUpdatePlayerData(UUID playerId, @Nullable PlayerData stale, PlayerData updated) {

    }
    public static Optional<AbstractClientPlayerEntity> findPlayer(UUID uuid) {
        ClientWorld world = MinecraftClient.getInstance().world;

        if (world == null) {
            PersonaMod.LOGGER.error("Tried to get players from world without a client world!");
            return Optional.empty();
        }

        if (!(world.getPlayerByUuid(uuid) instanceof AbstractClientPlayerEntity)) return Optional.empty();

        return Optional.of((AbstractClientPlayerEntity) world.getPlayerByUuid(uuid));
    }

    public static ClientPlayerData getPlayerState(UUID uuid) {
        return getInstance().players.computeIfAbsent(uuid, key -> new ClientPlayerData(uuid));
    }
    public static ClientPlayerData getPlayerState(LivingEntity player) {
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
    public static AbstractPersona findPersona(LivingEntity entity) {
        if (entity instanceof AbstractClientPlayerEntity) {
            return getPlayerState(entity).findPersona().orElse(null);
        }

        // TODO - Entities having personas
        return null;
    }
}
