package com.goblinsmod.Gobmain.client.render;

import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.HandSide;

public class GoblinsHeldLayer<T extends LivingEntity, M extends EntityModel<T> & IHasArm> extends HeldItemLayer<T,M> {

	public GoblinsHeldLayer(IEntityRenderer<T,M> livingEntityRendererIn) {
		super(livingEntityRendererIn);
	}

//	protected void translateToHand(HandSide handSide) {
//		if (this.livingEntityRenderer.getMainModel() instanceof ModelBiped) {
//			ModelBiped model = (ModelBiped) this.livingEntityRenderer.getMainModel();
//			ModelRenderer armRenderer;
//			if (handSide == HandSide.RIGHT) {
//				armRenderer = model.bipedRightArm;
//			} else {
//				armRenderer = model.bipedLeftArm;
//			}
//			if (!armRenderer.cubeList.isEmpty()) {
//				ModelBox armBox = armRenderer.cubeList.get(0);
//				float x = 0.125F - 0.03125F * (armBox.posX2 - armBox.posX1);
//				if (handSide == EnumHandSide.LEFT) {
//					x *= -1.0F;
//				}
//				float y = 0.0625F * (armBox.posY2 - armBox.posY1 - 12.0F);
//				GlStateManager.translate(x, y, 0.0F);
//			}
//		}
//	}

}