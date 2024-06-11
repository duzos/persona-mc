package mc.duzo.persona.common.item;

import mc.duzo.persona.data.global.server.ServerData;
import mc.duzo.persona.data.player.server.ServerPlayerData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class EvokerItem extends Item {
	public EvokerItem() {
		super(new Settings().maxCount(1));
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (!world.isClient()) {
			ServerPlayerData data = ServerData.getPlayerState(user);
			if (data.isPersonaRevealed()) {
				data.hidePersona();
			} else {
				data.revealPersona();
			}
		}

		return TypedActionResult.pass(user.getStackInHand(hand));
	}
}
