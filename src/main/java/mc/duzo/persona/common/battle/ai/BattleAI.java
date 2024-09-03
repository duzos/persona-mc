package mc.duzo.persona.common.battle.ai;

import net.minecraft.entity.LivingEntity;

import mc.duzo.persona.common.battle.turn.ServerBattleTurn;
import mc.duzo.persona.util.PersonaUtil;

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
