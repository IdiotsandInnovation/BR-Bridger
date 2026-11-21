package com.brbridger.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

public class BridgeAssistClient implements ClientModInitializer {
	private boolean assistEnabled;

	@Override
	public void onInitializeClient() {
		KeyMapping.Category category = KeyMapping.Category.register(
				Identifier.fromNamespaceAndPath("brbridger", "controls")
		);
		KeyMapping toggleKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
				"key.brbridger.toggle",
				InputConstants.Type.KEYBOARD,
				InputConstants.KEY_G,
				category
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player == null || client.level == null) {
				assistEnabled = false;
				return;
			}

			while (toggleKey.consumeClick()) {
				if (assistEnabled) {
					assistEnabled = false;
					client.player.sendSystemMessage(Component.translatable("message.brbridger.disabled"));
				} else if (isHoldingBlock(client.player.getMainHandItem())) {
					assistEnabled = true;
					client.player.sendSystemMessage(Component.translatable("message.brbridger.enabled"));
				} else {
					client.player.sendSystemMessage(Component.translatable("message.brbridger.select_block"));
				}
			}

			if (!assistEnabled) {
				return;
			}

			if (!isHoldingBlock(client.player.getMainHandItem())) {
				assistEnabled = false;
				client.player.sendSystemMessage(Component.translatable("message.brbridger.no_block"));
				return;
			}

			if (client.gui.screen() == null && !client.player.isSpectator()) {
				KeyMapping.click(KeyMappingHelper.getBoundKeyOf(client.options.keyUse));
			}
		});
	}

	private static boolean isHoldingBlock(ItemStack stack) {
		return !stack.isEmpty() && stack.getItem() instanceof BlockItem;
	}
}