package mc.duzo.persona.client.battle;

import mc.duzo.persona.client.battle.data.ClientBattleData;
import mc.duzo.persona.client.data.ClientData;
import mc.duzo.persona.common.battle.BattleHandler;
import mc.duzo.persona.common.battle.data.ServerBattleData;
import mc.duzo.persona.data.ServerData;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Optional;

public class ClientBattleHandler {
	/**
	 * Validates a battle.
	 * @return whether the data was valid / whether it wasnt removed
	 */
	public static boolean validateBattle(ClientBattleData data) {
		if (!BattleHandler.isBattleFinished(data)) return true;

		ClientData.removeBattle(data.getUuid());
		return false;
	}

	/**
	 * Costly
	 * finds any battle a player is a part of
	 * @param player player to search for
	 * @return possibly found battle data
	 */
	private static Optional<ClientBattleData> findPlayersBattle(AbstractClientPlayerEntity player) {
		for (ClientBattleData data : ClientData.getBattles()) {
			if (data.getPlayers().contains(player)) {
				return Optional.of(data);
			}
		}

		return findTargetsBattle(player);
	}
	private static Optional<ClientBattleData> findPlayersBattleAndValidate(AbstractClientPlayerEntity player) {
		Optional<ClientBattleData> found = findPlayersBattle(player);
		if (found.isEmpty()) return found;

		boolean valid = validateBattle(found.get());
		if (!valid) return Optional.empty();

		return found;
	}

	public static Optional<ClientBattleData> findPlayersBattle(AbstractClientPlayerEntity player, boolean validate) {
		if (!validate) return findPlayersBattle(player);
		return findPlayersBattleAndValidate(player);
	}
	public static Optional<ClientBattleData> findTargetsBattle(LivingEntity target) {
		if (target == null) return Optional.empty();
		if (target.getServer() == null) return Optional.empty();

		for (ClientBattleData data : ClientData.getBattles()) {
			if (data.getTargets().contains(target)) {
				return Optional.of(data);
			}
		}

		return Optional.empty();
	}

	public static Optional<ClientBattleData> findBattle(LivingEntity target) {
		if (target instanceof AbstractClientPlayerEntity player) {
			return findPlayersBattle(player, true);
		}

		return findTargetsBattle(target);
	}
}
