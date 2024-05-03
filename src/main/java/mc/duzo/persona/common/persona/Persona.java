package mc.duzo.persona.common.persona;

import mc.duzo.persona.common.PersonaSounds;
import mc.duzo.persona.common.skill.SkillSet;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class Persona extends AbstractPersona {
    private final Identifier texture;
    private final SkillSet skills;
    private final SoundEvent summonSound;
    private int level;

    public Persona(Identifier id, SkillSet skills, Identifier texture, SoundEvent sound) {
        super(id);

        this.skills = skills;
        this.texture = texture;
        this.summonSound = sound;
    }
    public Persona(Identifier id, SkillSet skills, Identifier texture) {
        this(id, skills, texture, PersonaSounds.PERSONA_SHOUT);
    }
    public Persona(NbtCompound nbt) {
        super(new Identifier(nbt.getString("id")));

        this.skills = SkillSet.fromNbt(nbt.getCompound("SkillSet"));
        this.texture = new Identifier(nbt.getString("Texture"));
        this.summonSound = SoundEvent.of(new Identifier(nbt.getString("Sound")));

        this.loadNbt(nbt);
    }
    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id() +
                ", skills=" + skills +
                ", level=" + level +
                '}';
    }

    @Override
    public SkillSet getSkillSet() {
        return this.skills;
    }
    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public SoundEvent getSummonSound() {
        return this.summonSound;
    }

    @Override
    public Identifier texture() {
        return this.texture;
    }
}
