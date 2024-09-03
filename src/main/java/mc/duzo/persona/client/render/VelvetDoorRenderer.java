package mc.duzo.persona.client.render;

import java.util.Optional;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

import mc.duzo.persona.client.render.model.door.VelvetDoorModel;
import mc.duzo.persona.client.render.model.door.VelvetDoorRegistry;
import mc.duzo.persona.common.entity.door.VelvetDoorEntity;
import mc.duzo.persona.common.entity.door.VelvetDoorVariant;

public class VelvetDoorRenderer extends EntityRenderer<VelvetDoorEntity> {

    VelvetDoorModel model;

    public VelvetDoorRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(VelvetDoorEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if (this.model == null) {
            Optional<VelvetDoorModel> found = findModel(entity);

            if (found.isEmpty()) return; // Add a warning message?

            this.model = found.get();
        }

        matrices.push();
        // Face towards the player

        if (MinecraftClient.getInstance().player != null) {
            AbstractClientPlayerEntity player = MinecraftClient.getInstance().player;

            matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(-yaw));
            matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(player.getHeadYaw() + 180)); // todo - should face the player, not copy its head
        }
        this.model.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);

        matrices.pop();
    }

    @Override
    public Identifier getTexture(VelvetDoorEntity entity) {
        return entity.getVariant().texture();
    }

    private static Optional<VelvetDoorModel> findModel(VelvetDoorEntity entity) {
        return findModel(entity.getVariant());
    }

    private static Optional<VelvetDoorModel> findModel(VelvetDoorVariant variant) {
        return Optional.ofNullable(VelvetDoorRegistry.getInstance().get(variant));
    }
}
