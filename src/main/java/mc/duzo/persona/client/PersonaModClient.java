package mc.duzo.persona.client;

import mc.duzo.persona.Register;
import mc.duzo.persona.client.battle.ClientBattleCache;
import mc.duzo.persona.client.render.animation.PersonaAnimationRegistry;
import mc.duzo.persona.client.render.animation.player.PersonaPlayerAnimations;
import mc.duzo.persona.data.global.client.ClientData;
import mc.duzo.persona.client.hud.SPHudOverlay;
import mc.duzo.persona.client.network.PersonaClientMessages;
import mc.duzo.persona.client.render.VelvetDoorRenderer;
import mc.duzo.persona.client.sound.SoundsManager;
import mc.duzo.persona.client.sound.persona.SoundSetRegistry;
import mc.duzo.persona.client.util.Keybinds;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;

public class PersonaModClient implements ClientModInitializer {
    public static final SoundsManager sounds = SoundsManager.create();

    @Override
    public void onInitializeClient() {
        PersonaClientMessages.initialise();
        Keybinds.initialise();
        SoundSetRegistry.initialise();
        PersonaAnimationRegistry.init();

        HudRenderCallback.EVENT.register(new SPHudOverlay());

        ClientTickEvents.END_CLIENT_TICK.register(this::tick);

        ClientEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(entity instanceof AbstractClientPlayerEntity player)) return;

            PersonaClientMessages.askForPlayerData(player.getUuid());
        });

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> ClientData.clearBattles());

        // Entity Renderers
        EntityRendererRegistry.register(Register.VELVET_DOOR_ENTITY, VelvetDoorRenderer::new);
    }

    private void tick(MinecraftClient client) {
        if (client.player == null) return;
        ClientData.getInstance().tick(client);

        tickBattleMusic(client);
    }

    private void tickBattleMusic(MinecraftClient client) {
        if (!SoundSetRegistry.isPlayingBattleMusic() && ClientBattleCache.findCurrentBattle().isPresent()) {
            SoundSetRegistry.playRandomBattleMusic();
            return;
        }
        if (SoundSetRegistry.isPlayingBattleMusic() && ClientBattleCache.findCurrentBattle().isEmpty()) {
            SoundSetRegistry.stopBattleMusic();
            return;
        }
    }
}
