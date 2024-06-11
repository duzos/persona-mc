package mc.duzo.persona.data.player.server;

import mc.duzo.persona.PersonaMod;
import mc.duzo.persona.common.persona.AbstractPersona;
import mc.duzo.persona.data.player.PlayerData;
import mc.duzo.persona.network.PersonaMessages;
import mc.duzo.persona.util.AbsoluteBlockPos;
import mc.duzo.persona.util.DataHelper;
import mc.duzo.persona.util.DeltaTimeManager;
import mc.duzo.persona.util.WorldUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

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

	public void awakenPersona(AbstractPersona persona) {
		if (this.getPlayer().isEmpty()) return;

		PersonaMessages.sendPersonaAwaken(player);
		player.setHealth(1f);

		DeltaTimeManager.enqueueTask((long) (13.5 * 1000L), () -> this.onFinishAwaken(persona));
	}
	private void onFinishAwaken(AbstractPersona persona) {
		this.setPersona(persona);
		this.revealPersona();
	}
}
