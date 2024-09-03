package mc.duzo.persona.data.player.client;

import java.util.Optional;
import java.util.UUID;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import mc.duzo.persona.PersonaMod;
import mc.duzo.persona.data.global.client.ClientData;
import mc.duzo.persona.data.player.PlayerData;
import mc.duzo.persona.util.DeltaTimeManager;

public class ClientPlayerData extends PlayerData {
    private AbstractClientPlayerEntity player;

    public ClientPlayerData(UUID player) {
        super(player);
    }

    public ClientPlayerData(NbtCompound data) {
        super(data);
    }

    @Override
    protected Optional<PlayerEntity> getPlayer() {
        if (this.player == null || !this.isPlayerCacheOnDelay()) {
            this.findAndSetPlayer();
            this.createPlayerCacheDelay();
        }

        return Optional.ofNullable(this.player);
    }
    private void findAndSetPlayer() {
        this.player = ClientData.findPlayer(this.getPlayerId()).orElse(null);
    }

    private boolean isPlayerCacheOnDelay() {
        return DeltaTimeManager.isOnDelay(this.getPlayerCacheKey());
    }
    private void createPlayerCacheDelay() {
        DeltaTimeManager.createDelay(this.getPlayerCacheKey(), this.getCacheDelay());
    }
    private long getCacheDelay() {
        return (long) ((PersonaMod.RANDOM.nextDouble(2,5)) * 1000L);
    }
    private String getPlayerCacheKey() {
        return "player-cache-" + this.getPlayerId().toString();
    }
}
