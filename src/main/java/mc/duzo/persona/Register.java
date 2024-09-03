package mc.duzo.persona;

import java.util.HashMap;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import mc.duzo.persona.common.entity.door.VelvetDoorEntity;
import mc.duzo.persona.common.item.EvokerItem;
import mc.duzo.persona.common.item.MaskItem;
import mc.duzo.persona.common.item.TarotCardItem;
import mc.duzo.persona.common.persona.arcana.Arcana;

/**
 * This is where all things are registered
 * Based off how Bug registered things
 *
 * @author bug
 * @author duzo
 */
public class Register {
    // Items
    public static HashMap<String, MaskItem> MASKS = new HashMap<>();
    private static void registerMasks() {
        registerMask("joker", new Identifier(PersonaMod.MOD_ID, "textures/mask/joker.png"));
    }
    private static MaskItem registerMask(String name, Identifier texture) {
        MaskItem created = new MaskItem(EquipmentSlot.HEAD, texture);

        register(Registries.ITEM, name + "_mask", created);
        MASKS.put(name, created);

        return created;
    }
    public static final HashMap<Arcana, TarotCardItem> TAROT_CARDS = registerTarotCards();
    private static HashMap<Arcana, TarotCardItem> registerTarotCards() {
        HashMap<Arcana, TarotCardItem> map = new HashMap<>();

        for (Arcana arcana : Arcana.values()) {
            TarotCardItem created = new TarotCardItem(arcana);

            Registry.register(Registries.ITEM, new Identifier(PersonaMod.MOD_ID,  "tarot_card_" + arcana.name().toLowerCase()), created);
            map.put(arcana, created);
        }

        return map;
    }

    public static final EvokerItem EVOKER = register(Registries.ITEM, "evoker", new EvokerItem());

    // Entities

    public static final EntityType<VelvetDoorEntity> VELVET_DOOR_ENTITY = register(Registries.ENTITY_TYPE, "velvet_door", FabricEntityTypeBuilder.create(
            SpawnGroup.MISC,
            (EntityType.EntityFactory<VelvetDoorEntity>) VelvetDoorEntity::new
    ).fireImmune().dimensions(EntityDimensions.fixed(1f, 2f)).build());

    // Initialising & Registering

    public static void initialize() {
        registerMasks();
    }

    public static <V, T extends V> T register(Registry<V> registry, String name, T entry) {
        return Registry.register(registry, new Identifier(PersonaMod.MOD_ID, name), entry);
    }

    public static <T extends Block> T registerBlockAndItem(String name, T entry) {
        T output = Register.register(Registries.BLOCK, name, entry);
        Registry.register(Registries.ITEM, new Identifier(PersonaMod.MOD_ID, name), new BlockItem(output, new FabricItemSettings()));
        return output;
    }
}
