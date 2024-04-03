package mc.duzo.persona.common.skill;

import mc.duzo.persona.common.PersonaSounds;
import mc.duzo.persona.common.affinities.Affinity;
import mc.duzo.persona.common.battle.BattleHandler;
import mc.duzo.persona.common.battle.data.ServerBattleData;
import mc.duzo.persona.common.battle.turn.BattleTurn;
import mc.duzo.persona.common.persona.Persona;
import mc.duzo.persona.data.PlayerData;
import mc.duzo.persona.data.ServerData;
import mc.duzo.persona.network.PersonaMessages;
import mc.duzo.persona.util.Identifiable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class Skill implements Identifiable {
    private final Identifier id;

    protected Skill(Identifier id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                '}';
    }

    public boolean run(LivingEntity source, Persona persona, LivingEntity target) {
        Optional<ServerBattleData> battle = BattleHandler.findBattle(source);
        if (battle.isPresent()) {
            BattleTurn turn = battle.get().getTurn();

            if (!turn.isCurrent(source)) {
                source.sendMessage(Text.literal("Not your turn!"));
                return false;
            }

            turn.next();
            turn.getCurrent().sendMessage(Text.literal("It's your turn!"));
        }

        if (source instanceof ServerPlayerEntity player) {
            PlayerData data = ServerData.getPlayerState(player);

            if (this.usesHealth()) {
                source.damage(source.getDamageSources().generic(), source.getMaxHealth() * (this.getCost() / 100f));
                return true;
            }

            data.removeSP(this.getCost());

            ServerData.getServerState(player.getServer()).markDirty();
            PersonaMessages.syncData(player, player);
        }
        return true;
    }

    public Text getName() {
        return Text.translatable("skill." + id.getNamespace() + "." + id.getPath());
    }
    public Text getDescription() {
        return Text.translatable("skill." + id.getNamespace() + "." + id.getPath());
    }
    public abstract boolean usesHealth();
    public abstract int getCost();
    public abstract double getCooldown();
    public abstract Affinity getAffinity();
    public SoundEvent getUseSound() {
        return PersonaSounds.ATTACK;
    }

    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();

        nbt.putString("id", this.id.toString());

        return nbt;
    }

    public Skill loadNbt(NbtCompound nbt) {
        if (!(this.id.equals(new Identifier(nbt.getString("id"))))) {
            throw new RuntimeException("Attempted to load skill with mismatched id");
        }

        return this;
    }
    public static Skill fromNbt(NbtCompound nbt) {
        return SkillRegistry.get(new Identifier(nbt.getString("id")));
    }

    public static Skill create(Identifier id, Affinity affinity, RunSkill onRun, boolean usesHealth, int cost, double cooldown, @Nullable SoundEvent sound) {
        return new Skill(id) {
            @Override
            public boolean run(LivingEntity source, Persona persona, LivingEntity target) {
                boolean success = super.run(source, persona, target);

                if (!success) return false;

                onRun.run(source, persona, target);

                return true;
            }

            @Override
            public boolean usesHealth() {
                return usesHealth;
            }

            @Override
            public int getCost() {
                return cost;
            }

            @Override
            public double getCooldown() {
                return cooldown;
            }

            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public SoundEvent getUseSound() {
                if (sound == null) return super.getUseSound();
                return sound;
            }

            @Override
            public Affinity getAffinity() {
                return affinity;
            }
        };
    }
    public static Skill create(Identifier id, Affinity affinity, RunSkill onRun, boolean usesHealth, int cost, double cooldown) {
        return create(id, affinity, onRun, usesHealth, cost, cooldown, null);
    }
    public interface RunSkill {
        void run(LivingEntity source, Persona persona, LivingEntity target);
    }
}
