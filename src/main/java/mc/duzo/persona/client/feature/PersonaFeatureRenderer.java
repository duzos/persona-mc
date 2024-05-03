package mc.duzo.persona.client.feature;

import mc.duzo.persona.client.data.ClientData;
import mc.duzo.persona.client.render.model.persona.PersonaModel;
import mc.duzo.persona.client.render.model.persona.PersonaModelRegistry;
import mc.duzo.persona.client.render.model.persona.PersonaSkinModel;
import mc.duzo.persona.common.persona.Persona;
import mc.duzo.persona.data.PlayerData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

import java.util.Objects;

@Environment(value= EnvType.CLIENT)
public class PersonaFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>>
        extends FeatureRenderer<T, M> {
    private static final int MAX_LIGHT = 0xF000F0;

    private EntityModel<T> model;
    private Persona prevPersona;
    private final PlayerEntityModel<T> playerModel;

    public PersonaFeatureRenderer(FeatureRendererContext<T, M> context, EntityModelLoader loader) {
        super(context);
        this.playerModel = new PlayerEntityModel<>(loader.getModelPart(EntityModelLayers.PLAYER), false);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
        PlayerData data = ClientData.getPlayerState(livingEntity);

        if (!data.isPersonaRevealed() || data.findPersona().isEmpty()) return;
        Persona persona = data.findPersona().get();

        if (!(livingEntity instanceof AbstractClientPlayerEntity player)) return;

        if (!Objects.equals(persona, this.prevPersona)) {
            this.prevPersona = persona;

            this.model = (EntityModel<T>) PersonaModelRegistry.get(persona.id()); // shhh

            if (this.model instanceof PersonaSkinModel) {
                this.model = this.playerModel;
            }
        }

        if (this.model == null) return;

        Identifier identifier = (this.model instanceof PersonaModel) ? ((PersonaModel) this.model).getTexture() : persona.texture();
        matrixStack.push();
        matrixStack.translate(0.25f, -0.5f, 0.5f);

        matrixStack.translate(0f, livingEntity.getWorld().random.nextFloat() * 0.02, 0f);
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MinecraftClient.getInstance().getTickDelta() % 180));

        this.getContextModel().copyStateTo(this.model);

        this.model.setAngles(livingEntity, f, g, j, k, l);

        VertexConsumer textureVertex = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucentEmissive(identifier, true));
        float alpha = livingEntity.getWorld().random.nextInt(32) != 6 ? 0.4f : 0.05f;

        if (this.model instanceof PersonaModel pModel) {
            pModel.render(player, j, matrixStack, textureVertex, i, 1, 1, 1, alpha);

            if (pModel.getEmission().isPresent()) {
                pModel.render(player, MAX_LIGHT, matrixStack, vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucentEmissive(pModel.getEmission().get(), true)), i, 1, 1, 1, alpha);
            }

        } else {
            this.model.render(matrixStack, textureVertex, 0xF000F0, OverlayTexture.DEFAULT_UV, 1, 1, 1, alpha);
        }

        matrixStack.pop();
    }
}

