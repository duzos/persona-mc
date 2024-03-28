package mc.duzo.persona.common.battle;

import mc.duzo.persona.common.battle.data.BattleData;
import mc.duzo.persona.common.battle.data.ServerBattleData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

public class BattleHandler {
	private final BattleData data;

	private BattleHandler(List<ServerPlayerEntity> players, List<LivingEntity> targets) {
		this.data = new ServerBattleData(players, targets);
	}
}
