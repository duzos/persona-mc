package mc.duzo.persona.client.feature;

import mc.duzo.animation.player.PlayerAnimationHelper;
import mc.duzo.animation.player.PlayerAnimationTracker;
import mc.duzo.persona.client.render.animation.player.holder.PlayerAwakeningAnimation;
import mc.duzo.persona.common.item.MaskItem;
import mc.duzo.persona.data.global.client.ClientData;
import mc.duzo.persona.data.player.PlayerData;
import mc.duzo.persona.data.player.client.ClientPlayerData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;

/**
 * A slightly transparent mask on the players face which shows if they have a persona and its hidden
 * Might remove - not too sure if I like it.
 *
 * @author duzo
 */
@Environment(value= EnvType.CLIENT)
public class MaskFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>>
        extends FeatureRenderer<T, M> {

    private final PlayerEntityModel<T> model;

    public MaskFeatureRenderer(FeatureRendererContext<T, M> context, EntityModelLoader loader) {
        super(context);
        this.model = new PlayerEntityModel<>(loader.getModelPart(EntityModelLayers.PLAYER), false);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
        if (!MaskItem.isWearingMask(livingEntity)) return;
        MaskItem mask = (MaskItem) livingEntity.getEquippedStack(EquipmentSlot.HEAD).getItem();

        if (!(livingEntity instanceof AbstractClientPlayerEntity player)) return;

        if (!shouldMaskBeVisible(player)) return;

        matrixStack.push();

        this.getContextModel().copyStateTo(this.model);
        this.model.setAngles(livingEntity, f, g, j, k, l);
        this.model.sneaking = player.isSneaking();

        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(mask.getTexture()));
        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1f);

        matrixStack.pop();
    }

    private boolean shouldMaskBeVisible(AbstractClientPlayerEntity player) {
        if (isRunningAwakening(player)) {
            return ((PlayerAwakeningAnimation) PlayerAnimationTracker.getInstance().get(player)).shouldMaskBeVisible();
        }

        ClientPlayerData data = ClientData.getPlayerState(player);

        return !((data.isPersonaRevealed()));
    }

    private boolean isRunningAwakening(AbstractClientPlayerEntity player) {
        return PlayerAnimationHelper.isRunningAnimations(player) && PlayerAnimationTracker.getInstance().get(player) instanceof PlayerAwakeningAnimation;
    }
}

