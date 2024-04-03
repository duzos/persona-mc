package mc.duzo.persona.client.network;

import mc.duzo.persona.client.PersonaModClient;
import mc.duzo.persona.client.battle.ClientBattleCache;
import mc.duzo.persona.client.battle.data.ClientBattleData;
import mc.duzo.persona.client.data.ClientData;
import mc.duzo.persona.client.sound.MusicSound;
import mc.duzo.persona.common.PersonaSounds;
import mc.duzo.persona.network.PersonaMessages;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;

import java.util.UUID;

public class PersonaClientMessages {
    public static void initialise() {
        ClientPlayNetworking.registerGlobalReceiver(PersonaMessages.SYNC_DATA, ((client, handler, buf, responseSender) -> recievePlayerData(buf)));
        ClientPlayNetworking.registerGlobalReceiver(PersonaMessages.CHANGED_VELVET, ((client, handler, buf, responseSender) -> recieveVelvetChange(buf)));
        ClientPlayNetworking.registerGlobalReceiver(PersonaMessages.BATTLE_DATA, ((client, handler, buf, responseSender) -> receiveBattleData(buf)));
        ClientPlayNetworking.registerGlobalReceiver(PersonaMessages.BATTLE_FINISH, ((client, handler, buf, responseSender) -> receiveBattleFinish(buf)));
    }

    private static void recieveVelvetChange(boolean entry) {
        if (entry) {
            PersonaModClient.sounds.startSound(new MusicSound(PersonaSounds.MUSIC_VELVET));
            return;
        }

        PersonaModClient.sounds.stopSound(PersonaSounds.MUSIC_VELVET);
    }
    private static void recieveVelvetChange(PacketByteBuf buf) {
        boolean entry = buf.readBoolean();
        recieveVelvetChange(entry);
    }

    private static void recievePlayerData(UUID uuid, NbtCompound nbt) {
        ClientData.addPlayer(uuid, nbt);
    }
    private static void recievePlayerData(PacketByteBuf buf) {
        UUID uuid = buf.readUuid();
        NbtCompound nbt = buf.readNbt();
        recievePlayerData(uuid, nbt);
    }
    private static void receiveBattleData(PacketByteBuf buf) {
        NbtCompound nbt = buf.readNbt();
        ClientData.addBattle(nbt);
    }
    private static void receiveBattleFinish(PacketByteBuf buf) {
        UUID uuid = buf.readUuid();

        ClientData.removeBattle(uuid);

        ClientBattleData current = ClientBattleCache.findCurrentBattle().orElse(null);
        if (current == null) return;

        if (current.getUuid().equals(uuid)) {
            ClientBattleCache.clear();
        }
    }


    public static void askForPlayerData(UUID uuid) {
        ClientPlayNetworking.send(PersonaMessages.ASK_DATA, PacketByteBufs.create().writeUuid(uuid));
    }

    public static void sendTargetChangeRequest() {
        ClientPlayNetworking.send(PersonaMessages.PRESS_TARGET, PacketByteBufs.empty());
    }

    public static void sendChangeSkillRequest(boolean next) {
        PacketByteBuf buf = PacketByteBufs.create();

        buf.writeBoolean(next);

        ClientPlayNetworking.send(PersonaMessages.CHANGE_SKILL, buf);
    }

    public static void sendUseSkillRequest() {
        ClientPlayNetworking.send(PersonaMessages.USE_SKILL, PacketByteBufs.empty());
    }

    public static void sendPersonaToggleRequest() {
        ClientPlayNetworking.send(PersonaMessages.PERSONA_TOGGLE, PacketByteBufs.empty());
    }
}
