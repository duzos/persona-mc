package mc.duzo.persona.client.battle.turn;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

import mc.duzo.persona.PersonaMod;
import mc.duzo.persona.client.battle.data.ClientBattleData;
import mc.duzo.persona.common.battle.turn.BattleTurn;


public class ClientBattleTurn extends BattleTurn {
    public ClientBattleTurn(ClientBattleData data) {
        super(data);
    }

    @Override
    public LivingEntity getCurrent() {
        ClientWorld world = MinecraftClient.getInstance().world;

        if (world == null) {
            PersonaMod.LOGGER.error("Tried to get current turn entity from world without a client world!");
            return null;
        }

        for (Entity entity : world.getEntities()) {
            if (!(entity instanceof LivingEntity)) continue;

            if (entity.getUuid().equals(this.current)) {
                return (LivingEntity) entity;
            }
        }

        return null;
    }
}
