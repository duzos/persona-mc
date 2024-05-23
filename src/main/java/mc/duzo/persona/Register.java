package mc.duzo.persona;

import mc.duzo.persona.common.entity.door.VelvetDoorEntity;
import mc.duzo.persona.common.item.TarotCardItem;
import mc.duzo.persona.common.item.WearableItem;
import mc.duzo.persona.common.persona.arcana.Arcana;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This is where all things are registered
 * Based off how Bug registered things
 *
 * @author bug
 * @author duzo
 */
public class Register {
    // Items
    public static final WearableItem JOKER_MASK = register(Registries.ITEM, "joker_mask", new WearableItem(EquipmentSlot.HEAD, true, new FabricItemSettings()));


    public static final List<TarotCardItem> TAROT_CARDS = registerTarotCards();
    public static Optional<TarotCardItem> findTarotCard(Arcana arcana) {
        for (TarotCardItem card : TAROT_CARDS) {
            if (card.getArcana() == arcana) return Optional.of(card); // should this be optional or just return null
        }
        return Optional.empty();
    }

    private static List<TarotCardItem> registerTarotCards() {
        List<TarotCardItem> cards = new ArrayList<>();

        for (Arcana arcana : Arcana.values()) {
            TarotCardItem created = new TarotCardItem(arcana);

            Registry.register(Registries.ITEM, new Identifier(PersonaMod.MOD_ID, arcana.name().toLowerCase() + "_tarot_card"), created);
            cards.add(created);
        }

        return cards;
    }

    // Entities

    public static final EntityType<VelvetDoorEntity> VELVET_DOOR_ENTITY = register(Registries.ENTITY_TYPE, "velvet_door", FabricEntityTypeBuilder.create(
            SpawnGroup.MISC,
            (EntityType.EntityFactory<VelvetDoorEntity>) VelvetDoorEntity::new
    ).fireImmune().dimensions(EntityDimensions.fixed(1f, 2f)).build());

    // Initialising & Registering

    public static void initialize() {}

    public static <V, T extends V> T register(Registry<V> registry, String name, T entry) {
        return Registry.register(registry, new Identifier(PersonaMod.MOD_ID, name), entry);
    }

    public static <T extends Block> T registerBlockAndItem(String name, T entry) {
        T output = Register.register(Registries.BLOCK, name, entry);
        Registry.register(Registries.ITEM, new Identifier(PersonaMod.MOD_ID, name), new BlockItem(output, new FabricItemSettings()));
        return output;
    }
}
