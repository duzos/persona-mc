package mc.duzo.persona.common.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class MaskItem extends WearableItem {
	private final Identifier texture;

	public MaskItem(EquipmentSlot slot, Identifier texture) {
		super(slot, true, new FabricItemSettings().maxCount(1));

		this.texture = texture;
	}

	public Identifier getTexture() {
		return this.texture;
	}

	public static boolean isWearingMask(LivingEntity entity) {
		return entity.getEquippedStack(EquipmentSlot.HEAD).getItem() instanceof MaskItem;
	}
}
