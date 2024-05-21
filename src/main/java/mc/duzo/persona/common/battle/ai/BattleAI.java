package mc.duzo.persona.common.battle.ai;

import mc.duzo.persona.common.battle.turn.BattleTurn;
import mc.duzo.persona.common.battle.turn.ServerBattleTurn;
import mc.duzo.persona.util.PersonaUtil;
import net.minecraft.entity.LivingEntity;

public class BattleAI {
	// TODO

	public static void performTurn(ServerBattleTurn turn) {
		// TEMPORARY
		LivingEntity current = turn.getCurrent();

		if (current == null || !current.isAlive()) {
			turn.next();
			return;
		}

		PersonaUtil.useSkill(current);
	}
}
