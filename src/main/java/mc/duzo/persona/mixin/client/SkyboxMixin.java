package mc.duzo.persona.mixin.client;

import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;

import mc.duzo.persona.client.util.SkyboxUtil;
import mc.duzo.persona.util.VelvetUtil;

@Mixin(WorldRenderer.class)
public class SkyboxMixin {
    @Inject(method="renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", at = @At("HEAD"), cancellable = true)
    public void persona$renderSky(MatrixStack matrices, Matrix4f projectionMatrix, float tickDelta, Camera camera, boolean thickFog, Runnable fogCallback, CallbackInfo ci) {
        ClientWorld world = MinecraftClient.getInstance().world;
        AbstractClientPlayerEntity player = MinecraftClient.getInstance().player;

        if(world == null || player == null) return;

        if (VelvetUtil.isInVelvetRoom(player)) {
            SkyboxUtil.renderVelvetSky(matrices);
            ci.cancel();
        }
    }
}
