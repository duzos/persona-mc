package mc.duzo.persona.common.skill;

import mc.duzo.persona.PersonaMod;
import mc.duzo.persona.common.PersonaSounds;
import mc.duzo.persona.common.affinities.Affinity;
import mc.duzo.persona.common.battle.BattleHandler;
import mc.duzo.persona.common.battle.data.ServerBattleData;
import mc.duzo.persona.util.PersonaUtil;
import mc.duzo.persona.util.VelvetUtil;
import mc.duzo.persona.util.WorldUtil;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Optional;

public class SkillRegistry {
    public static final SimpleRegistry<Skill> REGISTRY = FabricRegistryBuilder.createSimple(RegistryKey.<Skill>ofRegistry(new Identifier(PersonaMod.MOD_ID, "skill"))).buildAndRegister();

    public static Skill register(Skill skill) {
        return Registry.register(REGISTRY, skill.id(), skill);
    }

    public static Skill get(Identifier id) {
        return REGISTRY.get(id);
    }

    public static Skill DIA = register(Skill.create(
            new Identifier(PersonaMod.MOD_ID, "dia"),
            Affinity.HEAL,
            (source, persona, target) -> target.setHealth(target.getHealth() + 4),
            false,
            3,
            1,
            PersonaSounds.DIA
    ));
    public static Skill DIARAMA = register(Skill.create(
            new Identifier(PersonaMod.MOD_ID, "diarama"),
            Affinity.HEAL,
            (source, persona, target) -> {
                target.setHealth(target.getHealth() + 10);
            },
            false,
            6,
            1,
            PersonaSounds.DIA
    ));
    public static Skill DIARAHAN = register(Skill.create(
            new Identifier(PersonaMod.MOD_ID, "diarahan"),
            Affinity.HEAL,
            (source, persona, target) -> {
                target.setHealth(target.getHealth() + 10);
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 60, 3));
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 10, 1));
            },
            false,
            18,
            1,
            PersonaSounds.DIA
    ));
    public static Skill MEDIA = register(Skill.create(
            new Identifier(PersonaMod.MOD_ID, "media"),
            Affinity.HEAL,
            (source, persona, target) -> {
                Optional<ServerBattleData> battle = BattleHandler.findBattle(source);

                if (battle.isEmpty()) {
                    target.setHealth(target.getHealth() + 4);
                    return;
                }

                for (LivingEntity entity : battle.get().getPlayers()) {
                    PersonaUtil.createSkillParticles(entity, ParticleTypes.FIREWORK);
                    entity.setHealth(entity.getHealth() + 4);
                }
            },
            false,
            18,
            1,
            PersonaSounds.DIA
    ));
    public static Skill CLEAVE = register(Skill.create(
            new Identifier(PersonaMod.MOD_ID, "cleave"),
            Affinity.PHYS,
            (source, persona, target) -> target.damage(target.getDamageSources().generic(), 4),
            true,
            5,
            1,
            PersonaSounds.WEAK_PHYS
    ));
    public static Skill ZIO = register(Skill.create(
            new Identifier(PersonaMod.MOD_ID, "zio"),
            Affinity.ELEC,
            (source, persona, target) -> {
                EntityType.LIGHTNING_BOLT.spawn((ServerWorld) target.getWorld(), target.getBlockPos(), SpawnReason.TRIGGERED);
            },
            false,
            4,
            2
    ));

    public static Skill MAZIO = register(Skill.create(
            new Identifier(PersonaMod.MOD_ID, "mazio"),
            Affinity.ELEC,
            (source, persona, target) -> {
                Optional<ServerBattleData> battle = BattleHandler.findBattle(source);
                if (battle.isEmpty()) {
                    ZIO.run(source, persona, target);
                    return;
                }

                if (source instanceof ServerPlayerEntity) {
                    for (LivingEntity entity : battle.get().getTargets()) {
                        PersonaUtil.createSkillParticles(entity, ParticleTypes.FIREWORK);
                        EntityType.LIGHTNING_BOLT.spawn((ServerWorld) entity.getWorld(), entity.getBlockPos(), SpawnReason.TRIGGERED);
                    }
                    return;
                }

                for (LivingEntity player : battle.get().getPlayers()) {
                    PersonaUtil.createSkillParticles(player, ParticleTypes.FIREWORK);
                    EntityType.LIGHTNING_BOLT.spawn((ServerWorld) player.getWorld(), player.getBlockPos(), SpawnReason.TRIGGERED);
                }
            },
            false,
            10,
            2
    ));

    public static Skill TRAFURI = register(Skill.create(
            new Identifier(PersonaMod.MOD_ID, "trafuri"),
            Affinity.SUPPORT,
            (source, persona, target) -> {
                if (!PersonaMod.hasServer()) return;

                if (!(source instanceof ServerPlayerEntity sauce)) return;

                ServerWorld world = WorldUtil.findWorld(sauce.getSpawnPointDimension());
                BlockPos pos = sauce.getSpawnPointPosition();

                if (world == null || pos == null) return;

                Optional<Vec3d> respawnPos = PlayerEntity.findRespawnPosition(world, pos, sauce.getSpawnAngle(), true, true);

                if (respawnPos.isEmpty()) return;

                WorldUtil.teleport(source, world, respawnPos.get(), sauce.getSpawnAngle(), 0);
            },
            false,
            25,
            4
    ));



    public static void init() {

    }
}
