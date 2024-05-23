package mc.duzo.persona.datagen.provider;

import mc.duzo.persona.PersonaMod;
import mc.duzo.persona.Register;
import mc.duzo.persona.common.item.TarotCardItem;
import mc.duzo.persona.common.persona.arcana.Arcana;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PersonaModelProvider extends FabricModelProvider {
	private final FabricDataOutput output;

	public PersonaModelProvider(FabricDataOutput output) {
		super(output);

		this.output = output;
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator generator) {

	}

	@Override
	public void generateItemModels(ItemModelGenerator generator) {
		for (TarotCardItem card : Register.TAROT_CARDS.values()) {
			registerTarotCard(generator, card);
		}
	}

	private static Model item(String modid, String parent, TextureKey... requiredTextureKeys) {
		return new Model(Optional.of(new Identifier(modid, "item/" + parent)), Optional.empty(), requiredTextureKeys);
	}
	private static Model item(String parent, TextureKey... requiredTextureKeys) {
		return item("persona", parent, requiredTextureKeys);
	}
	private static Model item(TextureKey... requiredTextureKeys) {
		return item("minecraft", "generated", requiredTextureKeys);
	}
	private Model createTarotCardModel(@Nullable Identifier possibleTexture) {
		boolean hasExisting = false;

		if (possibleTexture != null) {
			hasExisting = doesTextureExist(possibleTexture);
		}

		if (!hasExisting)
			return item("tarot_card");

		return item("tarot_card", TextureKey.LAYER0);
	}
	private void registerTarotCard(ItemModelGenerator generator, TarotCardItem card, String modid) {
		Identifier possibleTexture = new Identifier(modid, "item/tarot/" + card.getArcana().name().toLowerCase());
		Model model = createTarotCardModel(possibleTexture);
		model.upload(ModelIds.getItemModelId(card), createTarotCardTextureMap(card.getArcana()), generator.writer);
	}
	private void registerTarotCard(ItemModelGenerator generator, TarotCardItem card) {
		registerTarotCard(generator, card, PersonaMod.MOD_ID);
	}
	private static TextureMap createTarotCardTextureMap(Arcana arcana) {
		return new TextureMap().put(TextureKey.LAYER0, new Identifier(PersonaMod.MOD_ID, "item/tarot/" + arcana.name().toLowerCase()));
	}


	public boolean doesTextureExist(Identifier texture) {
		return this.output.getModContainer().findPath("assets/" + texture.getNamespace() + "/textures/" + texture.getPath() + ".png").isPresent();
	}
}
