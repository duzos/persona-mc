package mc.duzo.persona.common.battle.turn;

import mc.duzo.persona.PersonaMod;
import mc.duzo.persona.common.battle.data.ServerBattleData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;

public class ServerBattleTurn extends BattleTurn {
	LivingEntity currentEntity;

	public ServerBattleTurn(ServerBattleData data) {
		super(data);
	}

	@Override
	public LivingEntity getCurrent() {
		if (currentEntity != null) {
			if (currentEntity.getUuid().equals(this.current)) {
				return this.currentEntity;
			}
		}

		if (!PersonaMod.hasServer()) {
			PersonaMod.LOGGER.error("Tried to grab current turn entity without a server!");
			return null;
		}

		MinecraftServer server = PersonaMod.SERVER;

		Entity found = null;
		// Search through every world to try and find the entity, shouldn't be needed as they should be in the same world though.
		for (ServerWorld world : server.getWorlds()) {
			found = world.getEntity(this.current);
			if (found != null) {
				break;
			}
		}

		if (!(found instanceof LivingEntity)) {
			return null;
		}

		this.currentEntity = (LivingEntity) found;

		return (LivingEntity) found;
	}

	@Override
	public void next() {
		super.next();

		((ServerBattleData) this.getData()).toClient();
	}
}
