package mc.duzo.persona.data;

import mc.duzo.persona.common.persona.AbstractPersona;
import mc.duzo.persona.common.persona.Persona;
import mc.duzo.persona.common.persona.PersonaUser;
import mc.duzo.persona.util.AbsoluteBlockPos;
import mc.duzo.persona.util.DataHelper;
import mc.duzo.persona.util.PersonaUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * Player specific nbt data which will be stored in {@link ServerData}
 * Remember to sync and markDirty!!
 *
 * @author duzo
 */
public class PlayerData implements PersonaUser {
    public static final int MAX_SP = 100;

    private AbstractPersona persona;
    private boolean hasTarget;
    private int target;
    private int spiritPoints;
    private boolean personaRevealed;
    private AbsoluteBlockPos.Directed velvetDoorPos; // the position of the velvet door through which the player entered the velvet room

    @Override
    public Optional<AbstractPersona> findPersona() {
        return Optional.ofNullable(this.persona);
    }

    /**
     * Sets the players persona
     * Will not call markdirty
     */
    public void setPersona(AbstractPersona persona) {
        this.persona = persona;
    }
    public void setPersona(AbstractPersona persona, ServerPlayerEntity player) {
        this.setPersona(persona);

        DataHelper.markDirty(player);
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

        if (!target.getWorld().isClient()) {
            ServerData.getServerState(target.getServer()).markDirty();
        }
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
    private void revealPersona() {
        if (this.findPersona().isEmpty()) {
            if (this.isPersonaRevealed()) this.hidePersona();
            return;
        }

        this.personaRevealed = true;
    }
    public void revealPersona(ServerPlayerEntity player) {
        this.revealPersona();

        if (this.findPersona().isEmpty()) return;

        player.getServerWorld().playSound(null, player.getBlockPos(), this.findPersona().get().getSummonSound(), SoundCategory.PLAYERS, 1.0f, 1.0f);

        DataHelper.markDirty(player);
    }

    private void hidePersona() {
        this.personaRevealed = false;
    }
    public void hidePersona(ServerPlayerEntity player) {
        this.hidePersona();

        DataHelper.markDirty(player);
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

    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();

        if (this.findPersona().isPresent())
            nbt.put("persona", this.persona.toNbt());

        nbt.putInt("SP", this.getSP());
        nbt.putBoolean("PersonaRevealed", this.isPersonaRevealed());
        nbt.putBoolean("hasTarget", this.hasTarget());
        nbt.putInt("targetId", this.getTargetId());

        return nbt;
    }

    public static PlayerData createFromNbt(NbtCompound nbt) {
        PlayerData data = new PlayerData();

        if (nbt.contains("persona"))
            data.persona = new Persona(nbt.getCompound("persona"));

        if (nbt.contains("SP"))
            data.spiritPoints = nbt.getInt("SP");

        if (nbt.contains("PersonaRevealed"))
            data.personaRevealed = nbt.getBoolean("PersonaRevealed");

        if(nbt.contains("hasTarget"))
            data.hasTarget = nbt.getBoolean("hasTarget");

        if(nbt.contains("targetId"))
            data.target = nbt.getInt("targetId");

        return data;
    }

    public static PlayerData get(ServerPlayerEntity player) {
        return ServerData.getPlayerState(player);
    }
}
