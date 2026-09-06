package techguns.client.render.entities.projectiles;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import techguns.*;
import techguns.entities.projectiles.FlamethrowerProjectile;

public class RenderFlameThrowerProjectile extends RenderTextureProjectile<FlamethrowerProjectile>{

	public RenderFlameThrowerProjectile(RenderManager renderManager) {
		super(renderManager);
		textureLoc = new ResourceLocation(Tags.MOD_ID,"textures/fx/fireball.png");
    	scale=1.0f;
    	baseSize=0.15f;
	}
	
	@Override
	public void doRender(FlamethrowerProjectile entity, double x, double y, double z, float entityYaw,
			float partialTicks) {
	}
}
