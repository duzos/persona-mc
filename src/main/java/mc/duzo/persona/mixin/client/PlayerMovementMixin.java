package mc.duzo.persona.mixin.client;

import mc.duzo.persona.client.battle.ClientBattleCache;
import mc.duzo.persona.client.battle.data.ClientBattleData;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(ClientPlayerEntity.class)
public class PlayerMovementMixin {

    @Inject(method = "tickMovement", at = @At("HEAD"), cancellable = true)
    private void persona$onTickMovement(CallbackInfo ci) {
        Optional<ClientBattleData> battle = ClientBattleCache.findCurrentBattle();
        if (battle.isEmpty()) return;
        ci.cancel();
    }

}
