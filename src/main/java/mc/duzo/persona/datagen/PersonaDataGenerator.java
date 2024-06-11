package mc.duzo.persona.datagen;

import mc.duzo.persona.Register;
import mc.duzo.persona.common.PersonaSounds;
import mc.duzo.persona.common.item.TarotCardItem;
import mc.duzo.persona.common.persona.arcana.Arcana;
import mc.duzo.persona.common.skill.Skill;
import mc.duzo.persona.common.skill.SkillRegistry;
import mc.duzo.persona.datagen.provider.PersonaModelProvider;
import mc.duzo.persona.datagen.provider.PersonaSoundProvider;
import mc.duzo.persona.datagen.provider.lang.LanguageProvider;
import mc.duzo.persona.datagen.provider.lang.LanguageType;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.MinecraftVersion;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.Identifier;

public class PersonaDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator gen) {
		FabricDataGenerator.Pack pack = gen.createPack();

		genSounds(pack);
		genLang(pack);
		genModels(pack);
	}

	private void genSounds(FabricDataGenerator.Pack pack) {
		pack.addProvider((((output, registriesFuture) -> {
			PersonaSoundProvider provider = new PersonaSoundProvider(output);

			// Music
			provider.addSound("velvet_room", PersonaSounds.MUSIC_VELVET);
			provider.addSound("reach_out_to_the_truth", PersonaSounds.MUSIC_REACH_OUT);
			provider.addSound("going_down", PersonaSounds.MUSIC_GOING_DOWN);
			provider.addSound("make_history", PersonaSounds.MUSIC_MAKE_HISTORY);
			provider.addSound("take_over", PersonaSounds.MUSIC_TAKE_OVER);
			provider.addSound("awakening", PersonaSounds.MUSIC_AWAKENING);

			// Skills
			provider.addSound("attack", PersonaSounds.ATTACK);
			provider.addSound("dia", PersonaSounds.DIA);
			provider.addSound("weak_phys", PersonaSounds.WEAK_PHYS);

			// PERSONA!!
			provider.addSound("persona_shout", PersonaSounds.PERSONA_SHOUT);
			provider.addSound("arsene", PersonaSounds.ARSENE_SHOUT);
			provider.addSound("orpheus", PersonaSounds.ORPHEUS_SHOUT);

			// Other
			provider.addSound("welcome_velvet", PersonaSounds.WELCOME_VELVET);
			provider.addSound("fail", PersonaSounds.FAIL);
			provider.addSound("skill_switch", PersonaSounds.SKILL_SWITCH);

			return provider;
		})));
	}

	private void genLang(FabricDataGenerator.Pack pack) {
		genEnglish(pack);
	}

	private void genEnglish(FabricDataGenerator.Pack pack) {
		pack.addProvider((((output, registriesFuture) -> {
			LanguageProvider provider = new LanguageProvider(output, LanguageType.EN_US);

			for (Skill skill : SkillRegistry.REGISTRY) {
				Identifier id = skill.id();

				provider.addTranslation(skill.getTranslationKey(), id.getPath());
			}

			for (Arcana arcana : Arcana.values()) {
				provider.addTranslation(arcana.getTranslationKey(), convertToName(arcana.name()));
			}

			for (TarotCardItem card : Register.TAROT_CARDS.values()) {
				String arcanaName = provider.translations.get(card.getArcana().getTranslationKey());

				provider.addTranslation(card, arcanaName + " Tarot Card");
			}

			provider.addTranslation(Register.EVOKER, "SEES Evoker");

			return provider;
		})));
	}

	private static String convertToName(String str) {
		String[] split = str.split("_");

		for (int i = 0; i < split.length; i++) {
			split[i] = split[i].substring(0, 1).toUpperCase() + split[i].substring(1).toLowerCase();
		}

		return String.join(" ", split);
	}

	private void genModels(FabricDataGenerator.Pack pack) {
		pack.addProvider(((output, registriesFuture) -> new PersonaModelProvider(output)));
	}
}
