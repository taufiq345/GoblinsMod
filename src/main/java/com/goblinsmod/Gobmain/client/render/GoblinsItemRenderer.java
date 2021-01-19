package com.goblinsmod.Gobmain.client.render;

import com.goblinsmod.Gobmain.client.entity.model.GoblinsEntityModel;
import com.goblinsmod.Gobmain.common.entities.GoblinsEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.FoxModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.core.util.MathUtil;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

@OnlyIn(Dist.CLIENT)

//<T extends LivingEntity, M extends EntityModel<T> & IHasArm> extends LayerRenderer<T, M>
public class GoblinsItemRenderer <T extends GoblinsEntity & GoblinsHasArms> extends GeoLayerRenderer<T> {
	 public GoblinsItemRenderer(IGeoRenderer<T> func_Igeorender) {
		
	      super(func_Igeorender);
	   }


		
	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn,
			GoblinsEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks,
			float ageInTicks, float netHeadYaw, float headPitch) {
	
		    boolean flag = entitylivingbaseIn.getPrimaryHand() == HandSide.RIGHT;
		   
		      ItemStack itemstack =  entitylivingbaseIn.getHeldItemOffhand();
		      ItemStack itemstack1 = entitylivingbaseIn.getHeldItemMainhand();
		      
		      if (!itemstack.isEmpty() || !itemstack1.isEmpty()) {
		         matrixStackIn.push();
		      
		    	  GoblinsEntity entgob = (GoblinsEntity) entitylivingbaseIn;
		    	  int i = entgob.getAttackTimer();
		     if(i > 0) {
		    		 // -2.0F + 1.5F * MathHelper.func_233021_e_((float)i - customPredicate.getPartialTick(), 10.0F

//		    		  for(float k = 0.0F; k<200.0F; k++) {
//		    			  float rightx = ((AnimatedGeoModel) this.getEntityModel()).getAnimationProcessor().getBone("ArmRightBone").getRotationX();
//		    			  float righty = ((AnimatedGeoModel) this.getEntityModel()).getAnimationProcessor().getBone("ArmRightBone").getRotationY();
//		    			  float rightz = ((AnimatedGeoModel) this.getEntityModel()).getAnimationProcessor().getBone("ArmRightBone").getRotationX();
//		    	    	  float leftx = ((AnimatedGeoModel) this.getEntityModel()).getAnimationProcessor().getBone("ArmLeftBone").getRotationX();
//			    	// matrixStackIn.rotate(Vector3f.XP.rotationDegrees(MathHelper.func_233021_e_((float)i - partialTicks, -300.0F)));
//			    	//matrixStackIn.rotate(Vector3f.XP.rotationDegrees(rightx));
//			    	 //matrixStackIn.rotate(Vector3f.YP.rotationDegrees(righty ));
//			    	 //matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(rightz ));
////			    	  float f1 =  entitylivingbaseIn.func_213475_v(partialTicks);
////			          matrixStackIn.rotate(Vector3f.ZP.rotation(f1));
////			          matrixStackIn.rotate(Vector3f.YP.rotationDegrees(netHeadYaw));
////			          matrixStackIn.rotate(Vector3f.XP.rotationDegrees(headPitch));
//		
//			    			    	  
//
//		    		  }
		    	  }
//		     else {
//		    	 matrixStackIn.rotate(Vector3f.XP.rotationDegrees(265.0F));
//		      	}
		    	

		         this.renderheldItem(entitylivingbaseIn, itemstack1, ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND, HandSide.RIGHT, matrixStackIn, bufferIn, packedLightIn);
		         this.renderheldItem(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, HandSide.LEFT, matrixStackIn, bufferIn, packedLightIn);
		         matrixStackIn.pop();
		      }
		 
		     
	        
		   
		   
	
	}
	
	

	
	private void renderheldItem(LivingEntity p_229135_1_, ItemStack p_229135_2_, ItemCameraTransforms.TransformType p_229135_3_, HandSide p_229135_4_, MatrixStack matrixStackIn, IRenderTypeBuffer p_229135_6_, int p_229135_7_) {
	      if (!p_229135_2_.isEmpty()) {
	    	  matrixStackIn.push();
	    
	    	
	    	  IBone handright =  ((AnimatedGeoModel)this.getEntityModel()).getAnimationProcessor().getBone("ArmRightBone");
	  		IBone handleft = ((AnimatedGeoModel)this.getEntityModel()).getAnimationProcessor().getBone("ArmLeftBone");
	  		
	    	  if (p_229135_4_ == HandSide.RIGHT) {
	    		  IBone hr = handright;
	    		
				} else {
					IBone hl = handleft;
				}
	    	 
	    	  float rightx = ((AnimatedGeoModel) this.getEntityModel()).getAnimationProcessor().getBone("ArmRightBone").getPivotX();
	    	  float leftx = ((AnimatedGeoModel) this.getEntityModel()).getAnimationProcessor().getBone("ArmLeftBone").getPivotX();
	    	  
	    	//  float righty = ((AnimatedGeoModel) this.getEntityModel()).getAnimationProcessor().getBone("ArmRightBone").getPivotY();
	    	 // float lefty = ((AnimatedGeoModel) this.getEntityModel()).getAnimationProcessor().getBone("ArmLeftBone").getPivotY();
	    	 GoblinsEntityModel model =(GoblinsEntityModel) this.getEntityModel();
	    	
	         boolean flag = p_229135_4_ == HandSide.LEFT;
	         GoblinsEntity entgob = (GoblinsEntity) p_229135_1_;
	        // matrixStackIn.translate(((double)((AnimatedGeoModel) this.getEntityModel()).getAnimationProcessor().getBone("ArmRightBone").getPivotX()/5.0F),( (double)((AnimatedGeoModel) this.getEntityModel()).getAnimationProcessor().getBone("ArmRightBone").getPivotY()/14.0F), ((double)((AnimatedGeoModel) this.getEntityModel()).getAnimationProcessor().getBone("ArmRightBone").getPivotZ()/7.0F) );
	   	  int i = entgob.getAttackTimer();
		     if(i > 0) {
		    	 double y = 0.660D;
		        	double z = -0.0128D;
		        	float rightx1 = ((AnimatedGeoModel) this.getEntityModel()).getAnimationProcessor().getBone("ArmRightBone").getRotationX() - 0.5F;
		         matrixStackIn.translate((double)((float)(flag ? leftx : rightx) / 9.8F ) * 1D, -1.1D, y);// (float)(flag ? leftx : rightx) maksudnya jarak item tangan kiri dengan item tangan kanan dengan arah x
		         matrixStackIn.translate((double)(float)0.02F, 1.8D, -1.0D);
		         
		        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(200.0F-i));
		        
		         ///final Quaternion ZERO = new Quaternion(1F, 200.0F-i, 1F, 1F);
		         ///matrixStackIn.rotate(ZERO);
		     }
	         else {
	         double y = 0.660D;
	        	double z = -0.0128D;
	         matrixStackIn.translate((double)((float)(flag ? leftx : rightx) / 9.8F ) * 1D, z, y);// (float)(flag ? leftx : rightx) maksudnya jarak item tangan kiri dengan item tangan kanan dengan arah x
	         matrixStackIn.translate((double)(float)-0.03F, 0.5D, -1.0D);
	         matrixStackIn.rotate(Vector3f.XP.rotationDegrees(265.0F));
	         }

	         Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(p_229135_1_, p_229135_2_, p_229135_3_, flag , matrixStackIn, p_229135_6_, p_229135_7_);
	         matrixStackIn.pop();
	      }
	   }

	
	

	
}
