package minefantasy.client;

import minefantasy.block.tileentity.TileEntityTanningRack;
import minefantasy.client.entityrender.ItemRendererMF;
import minefantasy.system.data_minefantasy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 *
 * @author Anonymous Productions
 * 
 *         Sources are provided for educational reasons. though small bits of
 *         code, or methods can be used in your own creations.
 * 
 *         Custom renderers based off render tutorial by MC_DucksAreBest
 */
public class TileEntityTanningRackRenderer extends TileEntitySpecialRenderer {

	public TileEntityTanningRackRenderer() {
		model = new ModelTanningRack();
	}

	public TileEntityTanningRackRenderer(TileEntityRenderer render) {
		model = new ModelTanningRack();
		this.setTileEntityRenderer(render);
	}

	public void renderAModelAt(TileEntityTanningRack tile, double d, double d1, double d2, float f) {
		if (tile != null)
			;
		int i = 0;
		if (tile.worldObj != null) {
			i = tile.direction; // this is for rotation
		}

		int j = 90 * i;

		if (i == 0) {
			j = 0;
		}

		if (i == 1) {
			j = 270;
		}

		if (i == 2) {
			j = 180;
		}

		if (i == 3) {
			j = 90;
		}
		if (i == 4) {
			j = 90;
		}
		bindTextureByName(data_minefantasy.image("/item/tanner.png")); // texture
		GL11.glPushMatrix(); // start
		GL11.glTranslatef((float) d + 0.5F, (float) d1 + 1.45F, (float) d2 + 0.5F); // size
		GL11.glRotatef(j, 0.0F, 1.0F, 0.0F); // rotate based on metadata
		GL11.glScalef(1.0F, -1F, -1F); // if you read this comment out this line
										// and you can see what happens
		model.renderModel(0.0625F); // renders and yes 0.0625 is a random number
		renderHungItem((TileEntityTanningRack) tile, d, d1, d2, f);
		GL11.glPopMatrix(); // end

	}

	private void bindTextureByName(String image) {
		bindTexture(TextureHelperMF.getResource(image));
	}

	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		renderAModelAt((TileEntityTanningRack) tileentity, d, d1, d2, f); // where
																			// to
																			// render
	}

	private void renderHungItem(TileEntityTanningRack tile, double d, double d1, double d2, float f) {
		Minecraft mc = Minecraft.getMinecraft();
		ItemStack itemstack = tile.getHung();
		if (itemstack != null) {
			Item item = itemstack.getItem();
			mc.renderEngine.bindTexture(TextureMap.locationItemsTexture);

			Tessellator image = Tessellator.instance;
			Icon index = item.getIconFromDamage(itemstack.getItemDamage());
			if (index != null) {
				float x1 = index.getMinU();
				float x2 = index.getMaxU();
				float y1 = index.getMinV();
				float y2 = index.getMaxV();
				float xPos = 0.5F;
				float yPos = -0.5F;
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				GL11.glTranslatef(-xPos, -yPos, 0.0F);
				float var13 = 1F;
				GL11.glScalef(var13, var13, var13);
				// GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(-1F, -1F, 0.0F);
				ItemRenderer.renderItemIn2D(image, x2, y1, x1, y2, index.getIconWidth(), index.getIconHeight(),
						0.0625F);
			}
		}

	}

	private ModelTanningRack model;
}