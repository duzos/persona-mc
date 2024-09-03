package mc.duzo.persona.client.battle.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.math.BlockPos;

import mc.duzo.persona.PersonaMod;
import mc.duzo.persona.client.battle.turn.ClientBattleTurn;
import mc.duzo.persona.common.battle.data.BattleData;
import mc.duzo.persona.common.battle.data.CachableBattleData;
import mc.duzo.persona.common.battle.turn.BattleTurn;
import mc.duzo.persona.util.DeltaTimeManager;

public class ClientBattleData extends CachableBattleData {
    private ClientBattleTurn turn;
    private BlockPos battlePos;

    public ClientBattleData(NbtCompound nbt) {
        super(nbt);
    }

    @Override
    protected boolean shouldUpdateCache() {
        return DeltaTimeManager.isOnDelay(this.getCacheKey());
    }

    @Override
    public BattleTurn getTurn() {
        if (this.turn == null) {
            this.turn = new ClientBattleTurn(this);
            PersonaMod.LOGGER.error("Client is missing turn!");
        }

        return this.turn;
    }

    public BlockPos getBattlePos() {
        return this.battlePos;
    }

    public void tick(MinecraftClient client) {

    }

    public float getYawAdjustment(boolean frontFacing) {
        return (!frontFacing) ? 135f : -45f;
    }
    public float getPitchAdjustment(boolean frontFacing) {
        return 35f;
    }
    public BlockPos getPosAdjustment(boolean frontFacing) {
        if (!frontFacing)
            return this.getBattlePos().add(3,4,4);

        return this.getBattlePos().add(-3, 4, -4);
    }

    @Override
    public List<? extends PlayerEntity> getPlayers() {
        if (!this.shouldUpdateCache() && this.playersCache != null) {
            return this.playersCache;
        }

        ClientWorld world = MinecraftClient.getInstance().world;

        if (world == null) {
            PersonaMod.LOGGER.error("Tried to get players from world without a client world!");
            return List.of();
        }

        List<AbstractClientPlayerEntity> list = new ArrayList<>();

        AbstractClientPlayerEntity found;
        for (UUID id : this.players) {
            if (!(world.getPlayerByUuid(id) instanceof AbstractClientPlayerEntity)) continue;

            found = (AbstractClientPlayerEntity) world.getPlayerByUuid(id);
            if (found == null) continue;

            list.add(found);
        }

        this.playersCache = list;
        this.createCacheDelay();

        return list;
    }

    @Override
    public List<? extends LivingEntity> getTargets() {
        if (!this.shouldUpdateCache() && this.targetsCache != null) {
            return this.targetsCache;
        }

        ClientWorld world = MinecraftClient.getInstance().world;

        if (world == null) {
            PersonaMod.LOGGER.error("Tried to get targets from world without a client world!");
            return List.of();
        }

        List<LivingEntity> list = new ArrayList<>();

        for (Entity entity : world.getEntities()) {
            if (!(entity instanceof LivingEntity)) continue;

            if (this.targets.contains(entity.getUuid())) {
                list.add((LivingEntity) entity);
            }
        }

        this.targetsCache = list;
        this.createCacheDelay();

        return list;
    }

    @Override
    public BattleData loadNbt(NbtCompound nbt) {
        super.loadNbt(nbt);

        this.battlePos = NbtHelper.toBlockPos(nbt.getCompound("BattlePos").getCompound("pos"));

        this.turn = new ClientBattleTurn(this);
        this.turn.loadNbt(nbt.getCompound("Turn"));

        return this;
    }
}
