package mc.duzo.persona.common.battle;

import mc.duzo.persona.common.battle.data.BattleData;
import mc.duzo.persona.common.battle.data.ServerBattleData;
import mc.duzo.persona.data.ServerData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;
import java.util.Optional;

/**
 * Static class for handling battle data.
 */
public class BattleHandler {
	public static boolean hasEnemiesWon(BattleData data) {
		for (PlayerEntity player : data.getPlayers()) {
			if (player.isAlive()) {
				return false;
			}
		}

		return true;
	}

	public static boolean hasPlayersWon(BattleData data) {
		for (LivingEntity target : data.getTargets()) {
			if (target.isAlive()) {
				return false;
			}
		}

		return true;
	}

	public static boolean isDistanceTooFar(BattleData data) {
		double maxDistance = 10;

		// Im a bad coder.
		for (PlayerEntity player : data.getPlayers()) {
			for (LivingEntity target : data.getTargets()) {
				if (player.distanceTo(target) > maxDistance) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean isBattleFinished(BattleData data) {
		return hasEnemiesWon(data) || hasPlayersWon(data) || isDistanceTooFar(data);
	}

	/**
	 * Validates a battle.
	 * @return whether the data was valid / whether it wasnt removed
	 */
	public static boolean validateBattle(MinecraftServer server, ServerBattleData data) {
		if (!isBattleFinished(data)) return true;

		ServerData.removeBattle(server, data.getUuid(), true);
		return false;
	}

	/**
	 * Costly
	 * finds any battle a player is a part of
	 * @param player player to search for
	 * @return possibly found battle data
	 */
	private static Optional<ServerBattleData> findPlayersBattle(ServerPlayerEntity player) {
		for (ServerBattleData data : ServerData.getBattles(player.getServer())) {
			if (data.getPlayers().contains(player)) {
				return Optional.of(data);
			}
		}

		return Optional.empty();
	}
	private static Optional<ServerBattleData> findPlayersBattleAndValidate(ServerPlayerEntity player) {
		Optional<ServerBattleData> found = findPlayersBattle(player);
		if (found.isEmpty()) return found;

		boolean valid = validateBattle(player.getServer(), found.get());
		if (!valid) return Optional.empty();

		return found;
	}

	public static Optional<ServerBattleData> findPlayersBattle(ServerPlayerEntity player, boolean validate) {
		if (!validate) return findPlayersBattle(player);
		return findPlayersBattleAndValidate(player);
	}

	public static ServerBattleData createBattle(List<ServerPlayerEntity> players, List<LivingEntity> targets) {
		ServerBattleData created = new ServerBattleData(players, targets);

		ServerData.addBattle(players.stream().findAny().get().getServer(), created);

		for (ServerPlayerEntity player : players) {
			created.toClient(player);
		}

		return created;
	}
}
