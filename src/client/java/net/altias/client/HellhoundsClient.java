package net.altias.client;

import net.altias.client.renderer.HellhoundRenderer;
import net.altias.entity.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class HellhoundsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

		EntityRendererRegistry.register(ModEntities.HELLHOUND, HellhoundRenderer::new);
	}
}