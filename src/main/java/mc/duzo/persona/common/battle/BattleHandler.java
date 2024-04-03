package mc.duzo.persona.common.battle;

import mc.duzo.persona.PersonaMod;
import mc.duzo.persona.common.affinities.Affinity;
import mc.duzo.persona.common.battle.data.BattleData;
import mc.duzo.persona.common.battle.data.ServerBattleData;
import mc.duzo.persona.common.skill.Skill;
import mc.duzo.persona.common.skill.SkillRegistry;
import mc.duzo.persona.data.ServerData;
import mc.duzo.persona.util.AbsoluteBlockPos;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

		return findTargetsBattle(player);
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
	public static Optional<ServerBattleData> findTargetsBattle(LivingEntity target) {
		if (target == null) return Optional.empty();
		if (target.getServer() == null) return Optional.empty();

		for (ServerBattleData data : ServerData.getBattles(target.getServer())) {
			if (data.getTargets().contains(target)) {
				return Optional.of(data);
			}
		}

		return Optional.empty();
	}

	public static Optional<ServerBattleData> findBattle(LivingEntity target) {
		if (target instanceof ServerPlayerEntity player) {
			return findPlayersBattle(player, true);
		}

		return findTargetsBattle(target);
	}

	public static LivingEntity findRandomPlayer(BattleData data) {
		List<? extends PlayerEntity> players = data.getPlayers();

		int chosen = PersonaMod.RANDOM.nextInt(players.size());
		return players.get(chosen);
	}
	public static LivingEntity findRandomTarget(BattleData data) {
		List<? extends LivingEntity> targets = data.getTargets();

		int chosen = PersonaMod.RANDOM.nextInt(targets.size());
		return targets.get(chosen);
	}
	public static Skill findRandomSkill() {
		int chosen = PersonaMod.RANDOM.nextInt(SkillRegistry.REGISTRY.size());
		return SkillRegistry.REGISTRY.get(chosen);
	}
	public static Skill findRandomDamageSkill() {
		Skill found = findRandomSkill();

		int max = 128;
		int count = 0;
		while (count < max && !isDamageAffinity(found.getAffinity())) {
			found = findRandomSkill();
			count++;
		}

		return found;
	}
	public static boolean isDamageAffinity(Affinity aff) {
		// BAD!!

		return switch(aff) {
			default -> true;
			case HEAL, SUPPORT -> false;
		};
	}

	public static ServerBattleData createBattle(List<ServerPlayerEntity> players, List<LivingEntity> targets) {
		ServerBattleData created = new ServerBattleData(players, targets);

		applyPositionTransforms(created);

		ServerData.addBattle(players.stream().findAny().get().getServer(), created);

		created.toClient();

		return created;
	}

	public static void applyPositionTransforms(ServerBattleData data) {
		AbsoluteBlockPos centre = data.getBattlePos();
		BlockPos enemyCentre = centre.north(2);
		BlockPos playerCentre = centre.south(2);

		// todo cleanup / document

		int count = 1;
		BlockPos pos = enemyCentre;

		for (LivingEntity target : data.getTargets()) {
			if (count % 2 == 0) { // if even go east
				pos = enemyCentre.east((count / 2));
			} else {
				pos = enemyCentre.west(count / 2);
			}

			if (target instanceof PlayerEntity) {
				target.teleport((ServerWorld) centre.getWorld(), pos.getX() + 0.5f, pos.getY(), pos.getZ() + 0.5f, Set.of(), target.headYaw, target.getPitch());
			}
			else {
				target.teleport((ServerWorld) centre.getWorld(), pos.getX() + 0.5f, pos.getY(), pos.getZ() + 0.5f, Set.of(), Direction.SOUTH.asRotation(), 0f);
			}

			pos = enemyCentre;
			count++;
		}

		count = 0;
		pos = playerCentre;

		for (PlayerEntity target : data.getPlayers()) {
			if (count % 2 == 0) { // if even go east
				pos = playerCentre.east((count / 2));
			} else {
				pos = playerCentre.west(count / 2);
			}

			target.teleport((ServerWorld) centre.getWorld(), pos.getX() + 0.5f, pos.getY(), pos.getZ() + 0.5f, Set.of(), target.headYaw, target.getPitch());

			pos = playerCentre;
			count++;
		}
	}
}
