package mc.duzo.persona.mixin.server;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import mc.duzo.persona.common.battle.BattleHandler;
import mc.duzo.persona.util.VelvetUtil;

@Mixin(ServerPlayerInteractionManager.class)
public abstract class ServerPlayerInteractionManagerMixin {

    @Shadow
    protected ServerWorld world;

    @Shadow public abstract boolean isCreative();

    @Shadow @Final protected ServerPlayerEntity player;

    @Inject(method = "tryBreakBlock", at = @At(value = "HEAD"), cancellable = true)
    public void tryBreakBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (!isCreative() && VelvetUtil.isVelvetRoom(this.world)) {
            cir.setReturnValue(false);
            cir.cancel();
        }

        if (BattleHandler.findBattle(this.player).isPresent()) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
