package mc.duzo.persona.mixin.client;

import mc.duzo.persona.client.battle.ClientBattleCache;
import mc.duzo.persona.client.battle.data.ClientBattleData;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(Camera.class)
public abstract class CameraMixin {
	@Shadow protected abstract void moveBy(double x, double y, double z);

	@Shadow protected abstract void setRotation(float yaw, float pitch);

	@Shadow protected abstract void setPos(double x, double y, double z);

	@Inject(method = "update", at = @At("TAIL"))
	public void persona$updateCamera(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
		if (thirdPerson) {
			Optional<ClientBattleData> battle = ClientBattleCache.findCurrentBattle();
			if (battle.isEmpty()) return;

			BlockPos pos = battle.get().getPosAdjustment(inverseView);
			this.setPos(pos.getX(), pos.getY(), pos.getZ());

			this.setRotation(battle.get().getYawAdjustment(inverseView), battle.get().getPitchAdjustment(inverseView));
		}
	}
}
