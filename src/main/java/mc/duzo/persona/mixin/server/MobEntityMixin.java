package mc.duzo.persona.mixin.server;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;

import mc.duzo.persona.common.battle.BattleHandler;

@Mixin(MobEntity.class)
public class MobEntityMixin {
    @Inject(method = "isAiDisabled", at = @At("HEAD"), cancellable = true)
    public void persona$isAiDisabled(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity thisEntity = (LivingEntity) (Object) this;

        // Good code is my enemy.
        if (BattleHandler.findTargetsBattle(thisEntity).isPresent()) {
            cir.setReturnValue(true);
        }
    }
}
