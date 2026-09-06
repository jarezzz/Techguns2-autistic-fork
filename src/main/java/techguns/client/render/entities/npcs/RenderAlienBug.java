package techguns.client.render.entities.npcs;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import techguns.*;
import techguns.client.models.npcs.ModelAlienBug;
import techguns.entities.npcs.AlienBug;

public class RenderAlienBug<T extends AlienBug> extends RenderLiving<T>{

	private static final ResourceLocation TEXTURE = new ResourceLocation(Tags.MOD_ID,"textures/entity/alienbug.png");

	public RenderAlienBug(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelAlienBug(), 0.8f);
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return TEXTURE;
	}

}
