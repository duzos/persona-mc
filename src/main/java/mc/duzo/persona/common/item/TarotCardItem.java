package mc.duzo.persona.common.item;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import mc.duzo.persona.common.persona.AbstractPersona;
import mc.duzo.persona.common.persona.PersonaRegistry;
import mc.duzo.persona.common.persona.arcana.Arcana;
import mc.duzo.persona.common.persona.arcana.ArcanaHolder;
import mc.duzo.persona.data.global.client.ClientData;
import mc.duzo.persona.data.global.server.ServerData;
import mc.duzo.persona.data.player.server.ServerPlayerData;

public class TarotCardItem extends Item implements ArcanaHolder {
    private final Arcana arcana; // MAY store these as nbt in future and just have one generic "Tarot Card" but for now we'll do it this way

    protected TarotCardItem(Settings settings, Arcana arcana) {
        super(settings);

        this.arcana = arcana;
    }
    public TarotCardItem(Arcana arcana) {
        this(new Settings().maxCount(1), arcana);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (world.isClient()) {
            boolean success = ClientData.getPlayerState(user).findPersona().isEmpty() && MaskItem.isWearingMask(user);

            if (success)
                MinecraftClient.getInstance().gameRenderer.showFloatingItem(stack); // needs testing, may cause crashes when the mod is loaded on servers !

            return success ? TypedActionResult.success(stack) : TypedActionResult.fail(stack);
        }

        // Must be server-side then
        ServerPlayerEntity player = (ServerPlayerEntity) user;

        boolean success = givePersona(player);

        if (success)
            stack.decrement(1);

        return success ? TypedActionResult.success(stack) : TypedActionResult.fail(stack);
    }

    /**
     * Attempts to give a player a persona
     * @return whether giving a persona was successful
     */
    private boolean givePersona(ServerPlayerEntity player) {
        if (!MaskItem.isWearingMask(player)) return false;

        ServerPlayerData data = ServerData.getPlayerState(player);

        if (data.findPersona().isPresent()) return false;

        AbstractPersona found = PersonaRegistry.findRandom(this.getArcana()); // TODO - not make random persona but instead a "Default" ?
        if (found == null) return false;

        data.awakenPersona(found);

        return true;
    }

    @Override
    public Arcana getArcana() {
        return this.arcana;
    }
}
