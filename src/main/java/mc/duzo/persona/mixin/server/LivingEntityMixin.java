package mc.duzo.persona.mixin.server;

import mc.duzo.persona.common.battle.BattleHandler;
import mc.duzo.persona.data.PlayerData;
import mc.duzo.persona.data.ServerData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@Inject(method = "damage", at = @At(value = "HEAD"), cancellable = true)
	public void persona$damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		if (source.getAttacker() instanceof ServerPlayerEntity player) {
			PlayerData data = ServerData.getPlayerState(player);

			if (!data.isPersonaRevealed()) return;
			if (BattleHandler.findPlayersBattle(player, true).isPresent()) return;

			LivingEntity thisEntity = (LivingEntity) (Object) this;

			if (thisEntity instanceof PlayerEntity) return; // Cannot attack players (yet)

			BattleHandler.createBattle(List.of(player), List.of(thisEntity));

			cir.setReturnValue(false);
		}
	}
}
