package mc.duzo.persona.mixin.server;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

import mc.duzo.persona.common.battle.BattleHandler;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Inject(method = "move", at = @At("HEAD"), cancellable = true)
    private void persona$move(MovementType movementType, Vec3d movement, CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;

        if (!(entity instanceof ServerPlayerEntity player)) return;

        boolean canMove = BattleHandler.findPlayersBattle(player, true).isEmpty();

        if (!canMove) {
            ci.cancel();
        }
    }

}