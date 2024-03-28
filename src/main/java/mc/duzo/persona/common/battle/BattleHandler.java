package mc.duzo.persona.common.battle;

import mc.duzo.persona.common.battle.data.BattleData;
import mc.duzo.persona.common.battle.data.ServerBattleData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

/**
 * Static class for handling battle data.
 */
public class BattleHandler {
	public static boolean hasEnemiesWon(BattleData data) {
		for (PlayerEntity player : data.getPlayers()) {
			if (player.getHealth() > 0) {
				return false;
			}
		}

		return true;
	}

	public static boolean hasPlayersWon(BattleData data) {
		for (LivingEntity target : data.getTargets()) {
			if (target.getHealth() > 0) {
				return false;
			}
		}

		return true;
	}

	public static boolean isBattleFinished(BattleData data) {
		return hasEnemiesWon(data) || hasPlayersWon(data);
	}
}
