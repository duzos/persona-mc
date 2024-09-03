package mc.duzo.persona.util;

import java.util.Optional;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import mc.duzo.persona.PersonaMod;
import mc.duzo.persona.Register;
import mc.duzo.persona.common.PersonaDimensions;
import mc.duzo.persona.common.PersonaSounds;
import mc.duzo.persona.common.entity.door.VelvetDoorEntity;
import mc.duzo.persona.data.global.server.ServerData;
import mc.duzo.persona.network.PersonaMessages;

/**
 * Welcome to the Velvet Room.
 * Utilities for the Room.
 *
 * @author duzo
 */
public class VelvetUtil {
    private static BlockPos VELVET_CENTRE;

    public static boolean isInVelvetRoom(LivingEntity entity) {
        return isVelvetRoom(entity.getWorld());
    }
    public static boolean isVelvetRoom(World world) {
        return PersonaDimensions.VELVET_DIM_WORLD.equals(world.getRegistryKey());
    }

    public static ServerWorld getVelvetDimension() {
        MinecraftServer server = PersonaMod.getServer().orElse(null);
        if (server == null) return null;

        return server.getWorld(PersonaDimensions.VELVET_DIM_WORLD);
    }

    public static void sendToRoom(LivingEntity entity) {
        BlockPos pos = getRoomCentre();
        Vec3d vec = pos.toCenterPos();

        WorldUtil.teleport(entity, getVelvetDimension(), vec, entity.getYaw(), entity.getPitch());
    }

    public static void onEnter(LivingEntity entity) {
        if (entity instanceof ServerPlayerEntity player) {
            PersonaMessages.sendVelvetChange(player, true);
            player.playSound(PersonaSounds.WELCOME_VELVET, SoundCategory.AMBIENT, 1.0f, 1.0f);
        }
    }
    public static void onExit(LivingEntity entity) {
        if (entity instanceof ServerPlayerEntity player) {
            PersonaMessages.sendVelvetChange(player, false);
        }
    }

    public static boolean hasVelvetRoom() {
        MinecraftServer server = PersonaMod.getServer().orElse(null);
        if (server == null) return false;

        return ServerData.getServerState(server).hasVelvetRoom;
    }

    private static Identifier getRoomStructureLocation() {
        return new Identifier(PersonaMod.MOD_ID, "velvet_room");
    }
    private static Optional<StructureTemplate> findRoomStructure() {
        MinecraftServer server = PersonaMod.getServer().orElse(null);
        if (server == null) return Optional.empty();

        return server.getStructureTemplateManager().getTemplate(getRoomStructureLocation());
    }

    /**
     * Places the velvet room around point 0,0,0 in the velvet dimension
     */
    private static void placeRoom() {
        if (hasVelvetRoom()) {
            PersonaMod.LOGGER.warn("Placing Velvet Room, despite being told one already exists");
        }

        Optional<StructureTemplate> found = findRoomStructure();

        if (found.isEmpty()) {
            PersonaMod.LOGGER.error("Failed to place Velvet Room - Structure not found");
            return;
        }

        StructureTemplate template = found.get();

        MinecraftServer server = PersonaMod.getServer().orElse(null);
        if (server == null) {
            PersonaMod.LOGGER.error("Failed to place - Server not found");
            return;
        }

        ServerWorld dimension = server.getWorld(PersonaDimensions.VELVET_DIM_WORLD);

        if (dimension == null) {
            PersonaMod.LOGGER.error("Velvet Room dimension does not exist - Failed to place");
            return;
        }

        Vec3i size = template.getSize();
        BlockPos offset = new BlockPos(-size.getX() / 2, 0, -size.getZ() / 2);

        template.place(
                dimension,
                offset,
                offset,
                new StructurePlacementData(),
                dimension.random,
                Block.NO_REDRAW
        );

        ServerData data = ServerData.getServerState();
        data.hasVelvetRoom = true;
        data.markDirty();

        getRoomCentre();

        summonDoor();
    }

    private static VelvetDoorEntity summonDoor() {
        BlockPos centre = getRoomCentre();

        // todo improve code - maybe when creating the structure i can place one down and save w entities?
        BlockPos doorPos = centre.west(3);

        VelvetDoorEntity entity = Register.VELVET_DOOR_ENTITY.spawn(getVelvetDimension(), doorPos, SpawnReason.STRUCTURE);

        return entity;
    }

    public static BlockPos getRoomCentre() {
        if (VELVET_CENTRE != null) return VELVET_CENTRE;

        if (!hasVelvetRoom()) {
            placeRoom();
        }

        Optional<StructureTemplate> found = findRoomStructure();

        if (found.isEmpty()) {
            PersonaMod.LOGGER.warn("Velvet Room not found! - Returning 0 1 0");
            return new BlockPos(0, 1, 0);
        }

        StructureTemplate template = found.get();

        Vec3i size = template.getSize();

        VELVET_CENTRE = new BlockPos(0, size.getY() / 4, 0);

        return VELVET_CENTRE;
    }
}
