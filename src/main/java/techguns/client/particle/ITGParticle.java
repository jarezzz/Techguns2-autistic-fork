package techguns.client.particle;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public interface ITGParticle {

	Vec3d getPos();
	
	boolean shouldRemove();
	void updateTick();
	
	void doRender(BufferBuilder buffer, Entity entityIn, float partialTickTime, float rotX, float rotZ, float rotYZ, float rotXY, float rotXZ);
	   
	AxisAlignedBB getRenderBoundingBox(float ptt, Entity viewEnt);
	
	default boolean doNotSort() {
		return false;
	}
	
	double getDepth();
	
	void setDepth(double depth);
	void setItemAttached();
}
