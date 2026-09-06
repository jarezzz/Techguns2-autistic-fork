package techguns.client.render.entities.projectiles;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import techguns.*;
import techguns.entities.projectiles.StoneBulletProjectile;

public class RenderStoneBulletProjectile extends RenderTextureProjectile<StoneBulletProjectile> {
	
    public RenderStoneBulletProjectile(RenderManager renderManager)
    {	
    	super(renderManager);
    	textureLoc = new ResourceLocation(Tags.MOD_ID,"textures/entity/handgunbullet.png");
    	scale=1.0f;
    	baseSize=0.1f;
    }
    
}
