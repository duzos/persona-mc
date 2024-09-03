package mc.duzo.persona.common.persona.affinities;

import net.minecraft.util.Identifier;

import mc.duzo.persona.PersonaMod;
import mc.duzo.persona.util.Identifiable;

public enum Affinity implements Identifiable {
    PHYS("phys"),
    FIRE("fire"),
    ICE("ice"),
    ELEC("elec"),
    WIND("wind"),
    PSY("psy"),
    NUKE("nuke"),
    BLESS("bless"),
    CURSE("curse"),
    ALMIGHTY("almighty"),
    HEAL("heal"),
    SUPPORT("support"),
    PASSIVE("passive");

    private final Identifier id;

    Affinity(Identifier id) {
        this.id = id;
    }
    Affinity(String name) {
        this(new Identifier(PersonaMod.MOD_ID, name));
    }
    Affinity() {
        this.id = new Identifier(PersonaMod.MOD_ID, this.name().toLowerCase());
    }

    @Override
    public Identifier id() {
        return this.id;
    }

    public enum Type {
        WEAK,
        NONE,
        STRONG,
        NULL
    }
}
