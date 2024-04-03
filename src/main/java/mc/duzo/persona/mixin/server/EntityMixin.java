package mc.duzo.persona.mixin.server;

import mc.duzo.persona.common.battle.BattleHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
	@Inject(method = "move", at = @At("HEAD"), cancellable = true)
	private void ouroborus$move(MovementType movementType, Vec3d movement, CallbackInfo ci) {
		Entity entity = (Entity) (Object) this;

		if (!(entity instanceof ServerPlayerEntity player)) return;

		boolean canMove = BattleHandler.findPlayersBattle(player, true).isEmpty();

		if (!canMove) {
			ci.cancel();
		}
	}
}