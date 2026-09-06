package techguns.client.render.tileentities;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidRegistry;
import org.lwjgl.opengl.GL11;
import techguns.tileentities.BlastFurnaceTileEnt;

public class RenderBlastfurnace extends TileEntitySpecialRenderer<BlastFurnaceTileEnt> {

    private static final float UNIT = 1f / 16f;

    @SuppressWarnings("incomplete-switch")
    @Override
    public void render(BlastFurnaceTileEnt te, double x, double y, double z, float partialTicks, int destroyStage,
                       float alpha) {

        if (te.isWorking()) {
            IBlockState bs = te.getWorld().getBlockState(te.getPos());
            EnumFacing rot = bs.getValue(BlockHorizontal.FACING);

            bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            TextureAtlasSprite tex = Minecraft.getMinecraft().getTextureMapBlocks().getTextureExtry(FluidRegistry.LAVA.getFlowing().toString());

            float nx, ny, nz;
            nx = -rot.getDirectionVec().getX();
            ny = -rot.getDirectionVec().getY();
            nz = -rot.getDirectionVec().getZ();

            BufferBuilder buffer = Tessellator.getInstance().getBuffer();
            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);
            switch (rot) {
                case NORTH:
                    drawGlowPane(buffer, x + UNIT * 3, y + 3 * UNIT, z + 0.06f, tex, UNIT * 10, 0, nx, ny, nz, false);
                    break;
                case EAST:
                    drawGlowPane(buffer, x + UNIT * 15f + 0.0025f, y + 3 * UNIT, z + UNIT * 3, tex, 0, UNIT * 10, nx, ny, nz, true);
                    break;
                case WEST:
                    drawGlowPane(buffer, x + 0.06f, y + 3 * UNIT, z + UNIT * 3, tex, 0, UNIT * 10, nx, ny, nz, false);
                    break;
                case SOUTH:
                    drawGlowPane(buffer, x + UNIT * 3, y + 3 * UNIT, z + 15 * UNIT + 0.0025f, tex, UNIT * 10, 0, nx, ny, nz, true);
                    break;
            }
            Tessellator.getInstance().draw();

        }
    }


    protected void drawGlowPane(BufferBuilder buffer, double x, double y, double z, TextureAtlasSprite icon, double w_x, double w_z, float nx, float ny, float nz, boolean flip) {

        if (flip) {
            buffer.pos(x + 0, y + 0, z + w_z).tex(icon.getMinU(), icon.getMinV()).normal(nx, ny, nz).endVertex();
            buffer.pos((x + w_x), (y + 0), z).tex(icon.getMaxU(), icon.getMinV()).normal(nx, ny, nz).endVertex();
            buffer.pos((x + w_x), (y + 0.375), z).tex(icon.getMaxU(), icon.getInterpolatedV(16)).normal(nx, ny, nz).endVertex();
            buffer.pos((x + 0), (y + 0.375), z + w_z).tex(icon.getMinU(), icon.getInterpolatedV(16)).normal(nx, ny, nz).endVertex();

        } else {
            buffer.pos((x + 0), (y + 0.375), z + w_z).tex(icon.getMinU(), icon.getInterpolatedV(16)).normal(nx, ny, nz).endVertex();
            buffer.pos((x + w_x), (y + 0.375), z).tex(icon.getMaxU(), icon.getInterpolatedV(16)).normal(nx, ny, nz).endVertex();
            buffer.pos((x + w_x), (y + 0), z).tex(icon.getMaxU(), icon.getMinV()).normal(nx, ny, nz).endVertex();
            buffer.pos((x + 0), (y + 0), z + w_z).tex(icon.getMinU(), icon.getMinV()).normal(nx, ny, nz).endVertex();
        }
    }

}
