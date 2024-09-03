package mc.duzo.persona.data.player.server;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import mc.duzo.animation.DuzoAnimationMod;
import mc.duzo.animation.player.PlayerAnimationTracker;
import mc.duzo.animation.player.holder.PlayerAnimationHolder;
import org.jetbrains.annotations.Nullable;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;

import mc.duzo.persona.PersonaMod;
import mc.duzo.persona.client.render.animation.PersonaAnimationRegistry;
import mc.duzo.persona.common.PersonaSounds;
import mc.duzo.persona.common.battle.BattleHandler;
import mc.duzo.persona.common.persona.AbstractPersona;
import mc.duzo.persona.data.player.PlayerData;
import mc.duzo.persona.util.AbsoluteBlockPos;
import mc.duzo.persona.util.DataHelper;
import mc.duzo.persona.util.DeltaTimeManager;
import mc.duzo.persona.util.WorldUtil;

public class ServerPlayerData extends PlayerData {
    private ServerPlayerEntity player;

    public ServerPlayerData(UUID player) {
        super(player);
    }

    public ServerPlayerData(NbtCompound data) {
        super(data);
    }

    @Override
    protected Optional<PlayerEntity> getPlayer() {
        if (this.player == null) {
            this.findAndSetPlayer();
        }

        return Optional.ofNullable(this.player);
    }

    private ServerPlayerEntity getServerPlayer() {
        return (ServerPlayerEntity) this.getPlayer().orElse(null);
    }

    private void findAndSetPlayer() {
        if (this.player != null) {
            PersonaMod.LOGGER.warn("Setting player twice!");
        }

        this.player = WorldUtil.findPlayer(this.getPlayerId()).orElse(null);
    }

    private void markDirty() {
        ServerPlayerEntity player = this.getServerPlayer();
        if (player != null) {
            DataHelper.markDirty(player);
        }
    }
    @Override
    public void setPersona(AbstractPersona persona) {
        super.setPersona(persona);

        this.markDirty();
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        super.setTarget(target);

        this.markDirty();
    }

    @Override
    public void setSP(int amount) {
        super.setSP(amount);

        this.markDirty();
    }

    @Override
    public void setVelvetDoorPos(AbsoluteBlockPos.Directed pos) {
        super.setVelvetDoorPos(pos);

        this.markDirty();
    }

    @Override
    public void hidePersona() {
        super.hidePersona();

        this.playMaskTouchAnimation();
        this.markDirty();
    }

    @Override
    public void revealPersona() {
        super.revealPersona();

        if (this.findPersona().isEmpty()) return;
        if (this.getPlayer().isEmpty()) return;

        player.getServerWorld().playSound(null, player.getBlockPos(), this.findPersona().get().getSummonSound(), SoundCategory.PLAYERS, 1.0f, 1.0f);

        this.markDirty();
    }
    public void revealPersona(boolean animate) {
        this.revealPersona();

        if (animate) this.playMaskTouchAnimation();
    }
    private void playMaskTouchAnimation() {
        Supplier<PlayerAnimationHolder> anim = (BattleHandler.findPlayersBattle(player, true).isPresent()) ? PersonaAnimationRegistry.Players.TOUCH_MASK_BATTLE : PersonaAnimationRegistry.Players.TOUCH_MASK;
        DuzoAnimationMod.play(player, PlayerAnimationTracker.getInstance(), anim.get());
    }

    public void awakenPersona(AbstractPersona persona) {
        if (this.getPlayer().isEmpty()) return;

        player.setHealth(1f);

        DuzoAnimationMod.play(player, PlayerAnimationTracker.getInstance(), PersonaAnimationRegistry.Players.AWAKENING.get());
        player.getServerWorld().playSound(null, player.getBlockPos(), PersonaSounds.MUSIC_AWAKENING, SoundCategory.PLAYERS, 1.0f, 1.0f);

        DeltaTimeManager.enqueueTask((long) (13.5 * 1000L), () -> this.onFinishAwaken(persona));
    }
    private void onFinishAwaken(AbstractPersona persona) {
        this.setPersona(persona);
        this.revealPersona(false);
    }
}
