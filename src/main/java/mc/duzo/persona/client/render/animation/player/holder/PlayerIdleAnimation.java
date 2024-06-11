package mc.duzo.persona.client.render.animation.player.holder;

import mc.duzo.persona.client.battle.ClientBattleHandler;
import mc.duzo.persona.client.battle.data.ClientBattleData;
import mc.duzo.persona.client.render.animation.player.PersonaPlayerAnimations;
import net.minecraft.client.network.AbstractClientPlayerEntity;

import java.util.Optional;

public class PlayerIdleAnimation extends PlayerAnimationHolder {
	public PlayerIdleAnimation() {
		super(PersonaPlayerAnimations.PERSONA_BATTLE_IDLE);
	}

	@Override
	public boolean isFinished(AbstractClientPlayerEntity entity) {
		Optional<ClientBattleData> data = ClientBattleHandler.findBattle(entity);

		return super.isFinished(entity) || data.isEmpty();
	}
}
