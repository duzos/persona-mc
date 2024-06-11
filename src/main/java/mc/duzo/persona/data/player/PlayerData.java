package mc.duzo.persona.data.player;

import mc.duzo.persona.common.persona.AbstractPersona;
import mc.duzo.persona.common.persona.Persona;
import mc.duzo.persona.common.persona.PersonaUser;
import mc.duzo.persona.data.global.server.ServerData;
import mc.duzo.persona.data.player.server.ServerPlayerData;
import mc.duzo.persona.network.PersonaMessages;
import mc.duzo.persona.util.AbsoluteBlockPos;
import mc.duzo.persona.util.DataHelper;
import mc.duzo.persona.util.DeltaTimeManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

/**
 * Player specific nbt data which will be stored in {@link ServerData}
 * Remember to sync and markDirty!!
 *
 * @author duzo
 */
public abstract class PlayerData implements PersonaUser {
    public static final int MAX_SP = 100;

    private AbstractPersona persona;
    private boolean hasTarget;
    private int target;
    private int spiritPoints;
    private boolean personaRevealed;
    private AbsoluteBlockPos.Directed velvetDoorPos; // the position of the velvet door through which the player entered the velvet room
    private final UUID playerId;

    public PlayerData(UUID player) {
        this.playerId = player;
    }
    public PlayerData(NbtCompound data) {
        this(data.getUuid("PlayerId"));

        this.deserialize(data);
    }

    public boolean isClient() {
        return !(this instanceof ServerPlayerData);
    }
    protected abstract Optional<PlayerEntity> getPlayer();
    protected UUID getPlayerId() {
        return this.playerId;
    }

    @Override
    public Optional<AbstractPersona> findPersona() {
        return Optional.ofNullable(this.persona);
    }

    /**
     * Sets the players persona
     */
    public void setPersona(AbstractPersona persona) {
        this.persona = persona;
    }

    /**
     * Sets this players target
     * Calls markdirty if on server
     * @param target the new target
     */
    public void setTarget(@Nullable LivingEntity target) {
        if (target == null) {
            this.target = 0;
            this.hasTarget = false;
            return;
        }

        this.target = target.getId();
        this.hasTarget = true;
    }
    public Optional<LivingEntity> findTarget(World world) {
        if (!this.hasTarget) return Optional.empty();

        Entity target = world.getEntityById(this.target);

        if (!(target instanceof LivingEntity))
            return Optional.empty();

        return Optional.of((LivingEntity) target);
    }

    public boolean hasTarget() {
        return hasTarget;
    }

    public int getTargetId() {
        return target;
    }

    @Override
    public int getSP() {
        return this.spiritPoints;
    }

    @Override
    public int getMaxSP() {
        return MAX_SP;
    }
    @Override
    public void setSP(int amount) {
        this.spiritPoints = Math.min(MAX_SP, amount);
    }
    public boolean isPersonaRevealed() {
        return this.personaRevealed;
    }
    public void revealPersona() {
        if (this.findPersona().isEmpty()) {
            if (this.isPersonaRevealed()) this.hidePersona();
            return;
        }

        this.personaRevealed = true;
    }
    public void hidePersona() {
        this.personaRevealed = false;
    }

    /**
     * @return the position of the velvet door that the player entered the room from. Can be null.
     */
    @Nullable
    public AbsoluteBlockPos.Directed getVelvetDoorPos() {
        return this.velvetDoorPos;
    }

    /**
     * Sets the velvet door position
     * Will not call markdirty, do it yourself.
     */
    public void setVelvetDoorPos(AbsoluteBlockPos.Directed pos) {
        this.velvetDoorPos = pos;
    }

    public NbtCompound serialize() {
        NbtCompound nbt = new NbtCompound();

        if (this.findPersona().isPresent())
            nbt.put("persona", this.persona.toNbt());

        nbt.putInt("SP", this.getSP());
        nbt.putBoolean("PersonaRevealed", this.isPersonaRevealed());
        nbt.putBoolean("hasTarget", this.hasTarget());
        nbt.putInt("targetId", this.getTargetId());
        nbt.putUuid("PlayerId", this.getPlayerId());

        return nbt;
    }

    protected void deserialize(NbtCompound data) {
        if (data.contains("persona"))
            this.persona = new Persona(data.getCompound("persona"));

        if (data.contains("SP"))
            this.spiritPoints = data.getInt("SP");

        if (data.contains("PersonaRevealed"))
            this.personaRevealed = data.getBoolean("PersonaRevealed");

        if(data.contains("hasTarget"))
            this.hasTarget = data.getBoolean("hasTarget");

        if(data.contains("targetId"))
            this.target = data.getInt("targetId");
    }
}
