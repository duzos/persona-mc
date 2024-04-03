package mc.duzo.persona.client.battle;

import mc.duzo.persona.client.battle.data.ClientBattleData;
import mc.duzo.persona.client.data.ClientData;
import mc.duzo.persona.common.battle.data.ServerBattleData;
import mc.duzo.persona.data.ServerData;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Optional;

public class ClientBattleHandler {
	/**
	 * Costly
	 * finds any battle a player is a part of
	 * @param player player to search for
	 * @return possibly found battle data
	 */
	public static Optional<ClientBattleData> findPlayersBattle(AbstractClientPlayerEntity player) {
		for (ClientBattleData data : ClientData.getBattles()) {
			if (data.getPlayers().contains(player)) {
				return Optional.of(data);
			}
		}

		return Optional.empty();
	}
}
