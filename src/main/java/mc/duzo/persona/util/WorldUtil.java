package mc.duzo.persona.util;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.s2c.play.EntityStatusEffectS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import mc.duzo.persona.PersonaMod;

/**
 * Utilities related to the world and the server
 *
 * @author duzo
 * */
public class WorldUtil {
    public static ServerWorld findWorld(RegistryKey<World> key) {
        MinecraftServer server = PersonaMod.getServer().orElse(null);
        if (server == null) return null;

        return server.getWorld(key);
    }

    public static ServerWorld findWorld(Identifier identifier) {
        return WorldUtil.findWorld(RegistryKey.of(RegistryKeys.WORLD, identifier));
    }

    public static ServerWorld findWorld(String identifier) {
        return WorldUtil.findWorld(new Identifier(identifier));
    }

    public static void teleport(LivingEntity entity, ServerWorld target, Vec3d pos, float yaw, float pitch) {
        if (entity instanceof ServerPlayerEntity player) {
            target.getServer().execute(() -> WorldUtil.teleportToWorld(player, target, pos, player.getYaw(), player.getPitch()));
            return;
        }

        if (entity.getWorld().getRegistryKey().equals(target.getRegistryKey())) {
            entity.refreshPositionAndAngles(pos.x, pos.y, pos.z, yaw, pitch);
            return;
        }

        entity.teleport(target, pos.x, pos.y, pos.z, Set.of(), yaw, pitch);
    }

    private static void teleportToWorld(ServerPlayerEntity player, ServerWorld target, Vec3d pos, float yaw, float pitch) {
        player.teleport(target, pos.x, pos.y, pos.z, yaw, pitch);
        player.addExperience(0);

        player.getStatusEffects().forEach(effect -> {
            player.networkHandler.sendPacket(new EntityStatusEffectS2CPacket(player.getId(), effect));
        });
        player.networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(player));
    }

    public static BlockPos findFloor(World world, BlockPos pos) {
        // I shouldn't be allowed to code tbh

        BlockPos.Mutable mutable = new BlockPos.Mutable().set(pos);

        boolean isDown;
        int movementMultiplier = 1;

        int freeCount = 0; // Needs to be 2 to be seen as "safe"

        int MAX_ITERATIONS = 64;
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            freeCount = (isFree(world.getBlockState(mutable))) ? freeCount + 1 : 0;


            if (freeCount >= 2 && !isFree(world.getBlockState(mutable.down(2))) && isFree(world.getBlockState(mutable.down()))) {
                break;
            }

            isDown = i < MAX_ITERATIONS / 2;

            mutable.set(pos);

            movementMultiplier = isDown ? i : i - (MAX_ITERATIONS / 2);
            mutable.move(0, isDown ? -movementMultiplier : movementMultiplier, 0);
        }

        return mutable.down();
    }
    private static boolean isFree(BlockState state) {
        return state.isAir() || state.isReplaceable();
    }

    public static Optional<ServerPlayerEntity> findPlayer(UUID id) {
        MinecraftServer server = PersonaMod.getServer().orElse(null);
        if (server == null) return Optional.empty();

        return Optional.ofNullable(server.getPlayerManager().getPlayer(id));
    }
}
