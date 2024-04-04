package mc.duzo.persona.mixin.server;

import mc.duzo.persona.common.battle.BattleHandler;
import mc.duzo.persona.common.battle.data.BattleData;
import mc.duzo.persona.common.battle.data.ServerBattleData;
import mc.duzo.persona.data.PlayerData;
import mc.duzo.persona.data.ServerData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	@Shadow public abstract void remove(Entity.RemovalReason reason);

	@Inject(method = "damage", at = @At(value = "HEAD"), cancellable = true)
	public void persona$damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		if (source.getAttacker() instanceof ServerPlayerEntity player) {
			PlayerData data = ServerData.getPlayerState(player);

			if (!data.isPersonaRevealed()) return;

			LivingEntity thisEntity = (LivingEntity) (Object) this;

			if (thisEntity instanceof PlayerEntity) return; // Cannot attack players (yet)

			Optional<ServerBattleData> playersBattle = BattleHandler.findBattle(player);
			Optional<ServerBattleData> entityBattle = BattleHandler.findBattle(thisEntity);

			if (entityBattle.isPresent()) {
				if (entityBattle.get().getTurn().isCurrent(player)) {
					entityBattle.get().getTurn().next();
					return;
				}

				entityBattle.get().addPlayer(player);

				cir.setReturnValue(false);
				return;
			}

			if (playersBattle.isPresent()) {
				if (playersBattle.get().getTurn().isCurrent(player)) {
					playersBattle.get().getTurn().next();
					return;
				}

				playersBattle.get().addTarget(thisEntity);

				cir.setReturnValue(false);
				return;
			}

			BattleHandler.createBattle(List.of(player), List.of(thisEntity));

			cir.setReturnValue(false);
		}
	}
}
