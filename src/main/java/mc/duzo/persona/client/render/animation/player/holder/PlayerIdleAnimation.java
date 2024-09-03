package mc.duzo.persona.client.render.animation.player.holder;

import mc.duzo.animation.player.holder.PlayerAnimationHolder;
import mc.duzo.persona.PersonaMod;
import mc.duzo.persona.client.battle.ClientBattleHandler;
import mc.duzo.persona.client.battle.data.ClientBattleData;
import mc.duzo.persona.client.render.animation.player.PersonaPlayerAnimations;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class PlayerIdleAnimation extends PlayerAnimationHolder {
	public PlayerIdleAnimation() {
		super(new Identifier(PersonaMod.MOD_ID, "idle"), PersonaPlayerAnimations.PERSONA_BATTLE_IDLE);
	}

	@Override
	public boolean isFinished(AbstractClientPlayerEntity entity) {
		Optional<ClientBattleData> data = ClientBattleHandler.findBattle(entity);

		return super.isFinished(entity) || data.isEmpty();
	}
}
