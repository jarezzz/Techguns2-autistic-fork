package techguns.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import techguns.client.ClientProxy;

public class TGParticleStreak extends TGParticle {

    protected TGParticleStreak prev;
    protected TGParticleStreak next;

    protected Vec3d pos1; //This streak segment's vertices
    protected Vec3d pos2;

    public TGParticleStreak(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn,
                            double ySpeedIn, double zSpeedIn, TGParticleSystem particleSystem) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, particleSystem);
    }


    /**
     * Renders the particle
     */
    @Override
    public void renderParticle(@NotNull BufferBuilder buffer, @NotNull Entity entityIn, float partialTickTime, float rotX, float rotZ, float rotYZ, float rotXY, float rotXZ) {
        float progress = ((float) this.particleAge + partialTickTime) / (float) this.particleMaxAge;
        preRenderStep(progress);

        if (this.next == null) {
            this.particleAlpha = 0.0f;
        }

        /*-------------------
         * ANIMATION
         */
        int currentFrame;
        if (type.hasVariations) {
            currentFrame = variationFrame;
        } else {
            currentFrame = ((int) ((float) type.frames * (progress * this.animationSpeed))) % type.frames;
        }

        /* -------------
         * RENDER PARTICLE
         */
        this.particleScale = sizePrev + (size - sizePrev) * partialTickTime;


        Minecraft.getMinecraft().getTextureManager().bindTexture(type.texture);
        float fscale = 0.1F * this.particleScale;

        float fPosX = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) partialTickTime - TGParticleManager.interpPosX);
        float fPosY = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) partialTickTime - TGParticleManager.interpPosY);
        float fPosZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) partialTickTime - TGParticleManager.interpPosZ);

        int col = currentFrame % type.columns;
        int row = (currentFrame / type.columns);

        float u = 1.f / type.columns;
        float v = 1.f / type.columns;

        float U1 = col * u;
        float V1 = row * v;
        float U2 = (col + 1) * u;
        float V2 = (row + 1) * v;

        float ua, va, ub, vb, uc, vc, ud, vd;
        ua = U2;
        va = V2;
        ub = U2;
        vb = V1;
        uc = U1;
        vc = V1;
        ud = U1;
        vd = V2;

        enableBlendMode();

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        buffer.begin(7, VERTEX_FORMAT);
        Vec3d p1, p2, p3, p4;

        if (prev == null) {
            this.pos1 = null;
            this.pos2 = null;
        } else {
            Vec3d v_view = ClientProxy.get().getPlayerClient().getLook(partialTickTime);
            Vec3d v_prev = new Vec3d(prev.posX, prev.posY, prev.posZ).subtract(ClientProxy.get().getPlayerClient().getPositionVector());
            Vec3d v_dir = v_prev.subtract(fPosX, fPosY, fPosZ).normalize();
            Vec3d v_cross = v_view.crossProduct(v_dir).normalize();

            p1 = new Vec3d(v_cross.x * fscale + fPosX, v_cross.y * fscale + fPosY, v_cross.z * fscale + fPosZ);
            p2 = new Vec3d(v_cross.x * -fscale + fPosX, v_cross.y * -fscale + fPosY, v_cross.z * -fscale + fPosZ);

            this.pos1 = p1;
            this.pos2 = p2;


            float fscaleP = prev.particleScale * 0.1f;


            if (prev.pos1 != null && prev.pos2 != null) {
                p3 = prev.pos2;
                p4 = prev.pos1;
            } else {
                p4 = new Vec3d(v_cross.x * fscaleP + v_prev.x, v_cross.y * fscaleP + v_prev.y, v_cross.z * fscaleP + v_prev.z);
                p3 = new Vec3d(v_cross.x * -fscaleP + v_prev.x, v_cross.y * -fscaleP + v_prev.y, v_cross.z * -fscaleP + v_prev.z);

                prev.pos1 = p4;
                prev.pos2 = p3;
            }

            buffer.pos(p1.x, p1.y, p1.z).tex(ua, va).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(0, 240).normal(0.0f, 1.0f, 0.0f).endVertex();
            buffer.pos(p2.x, p2.y, p2.z).tex(ub, vb).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(0, 240).normal(0.0f, 1.0f, 0.0f).endVertex();
            buffer.pos(p3.x, p3.y, p3.z).tex(uc, vc).color(prev.particleRed, prev.particleGreen, prev.particleBlue, prev.particleAlpha).lightmap(0, 240).normal(0.0f, 1.0f, 0.0f).endVertex();
            buffer.pos(p4.x, p4.y, p4.z).tex(ud, vd).color(prev.particleRed, prev.particleGreen, prev.particleBlue, prev.particleAlpha).lightmap(0, 240).normal(0.0f, 1.0f, 0.0f).endVertex();

        }
        Tessellator.getInstance().draw();
        disableBlendMode();
    }

    public void setPrev(TGParticleStreak prev) {
        this.prev = prev;
    }

    public TGParticleStreak getNext() {
        return next;
    }

    public void setNext(TGParticleStreak next) {
        this.next = next;
    }

    @Override
    public boolean doNotSort() {
        return true;
    }

}
