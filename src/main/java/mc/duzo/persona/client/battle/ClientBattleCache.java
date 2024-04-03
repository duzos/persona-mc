package mc.duzo.persona.client.battle;

import mc.duzo.persona.PersonaMod;
import mc.duzo.persona.client.PersonaModClient;
import mc.duzo.persona.client.battle.data.ClientBattleData;
import mc.duzo.persona.client.data.ClientData;
import mc.duzo.persona.common.battle.BattleHandler;
import mc.duzo.persona.util.DeltaTimeManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

import java.util.Optional;

public class ClientBattleCache {
	private static ClientBattleData current;

	private static boolean shouldUpdateCache() {
		return DeltaTimeManager.isOnDelay(getCacheKey());
	}
	private static void createCacheDelay() {
		DeltaTimeManager.createDelay(getCacheKey(), getCacheDelay());
	}
	private static long getCacheDelay() {
		return (long) ((PersonaMod.RANDOM.nextDouble(10,12)) * 1000L);
	}
	private static String getCacheKey() {
		return "client-battle" + "-cache";
	}

	/**
	 * Removes the current cache
	 * @return the current cache if one existed
	 */
	public static Optional<ClientBattleData> clear() {
		PersonaMod.LOGGER.info("Clearing battle cache");

		Optional<ClientBattleData> cur = Optional.ofNullable(current);
		current = null;
		return cur;
	}

	public static Optional<ClientBattleData> findCurrentBattle() {
		if (!shouldUpdateCache() && current != null){
			if (!BattleHandler.isBattleFinished(current)) { // Often doesnt work
				return Optional.of(current);
			}

			ClientData.removeBattle(current.getUuid());
			clear();
		}

		ClientPlayerEntity player = MinecraftClient.getInstance().player;
		if (player == null) return Optional.empty();

		Optional<ClientBattleData> found = ClientBattleHandler.findPlayersBattle(player);
		if (found.isEmpty()) {
			clear();
			return found;
		}

		current = found.get();

		return found;
	}
}
