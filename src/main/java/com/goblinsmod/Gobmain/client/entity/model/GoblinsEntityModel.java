package com.goblinsmod.Gobmain.client.entity.model;

import java.util.List;

import javax.annotation.Nullable;

import com.goblinsmod.Gobmain.Maincore_mod;
import com.goblinsmod.Gobmain.common.entities.GoblinsEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;


public class GoblinsEntityModel<T extends GoblinsEntity> extends AnimatedGeoModel implements IHasArm, IHasHead  {

    @Override
    public ResourceLocation getModelLocation(Object object)
    {
        return new ResourceLocation(Maincore_mod.MOD_ID, "geo/model/goblinsmodel.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Object object)
    {
        return new ResourceLocation(Maincore_mod.MOD_ID, "textures/entitytextures/goblins_texture.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Object object)
    {
        return new ResourceLocation(Maincore_mod.MOD_ID, "animations/goblins.animation.json");
    }
    
    
//    public Iterable<IBone> getParts() {
//        return ImmutableList.of(this.getAnimationProcessor().getBone("HeadBone"), this.getAnimationProcessor().getBone("LeftLegBone"), this.getAnimationProcessor().getBone("RightLegBone"), this.getAnimationProcessor().getBone("ArmRightBone"), this.getAnimationProcessor().getBone("ArmLeftBone"), this.getAnimationProcessor().getBone("BodyBone"));
//     }

    

	@Override
    public void setLivingAnimations(IAnimatable entity, Integer uniqueID,@Nullable AnimationEvent customPredicate)
	{
    	super.setLivingAnimations(entity, uniqueID, customPredicate);
  
		IBone head = this.getAnimationProcessor().getBone("HeadBone");
		IBone handright = this.getAnimationProcessor().getBone("ArmRightBone");
		IBone handleft = this.getAnimationProcessor().getBone("ArmLeftBone");
		IBone legsright = this.getAnimationProcessor().getBone("RightLegBone");
		IBone legsleft = this.getAnimationProcessor().getBone("LeftLegBone");

		float RotateHeadYaw = customPredicate.getPartialTick();
		
		LivingEntity entityIn = (LivingEntity) entity;
		GoblinsEntity entgob = (GoblinsEntity) entity;
		ItemStack mainhand = entityIn.getHeldItemMainhand();
		ItemStack offhand = entityIn.getHeldItemOffhand();
		//customPredicate
		legsleft.setRotationX(MathHelper.cos(customPredicate.getLimbSwing() * 0.6662F) * 1.4F * customPredicate.getLimbSwingAmount());
		legsright.setRotationX(MathHelper.cos(customPredicate.getLimbSwing() * 0.6662F + (float)Math.PI) * 1.4F * customPredicate.getLimbSwingAmount());
	
		int i = entgob.getAttackTimer();
		
		//goblins entity attacking animation
	      if (i > 0 && !mainhand.isEmpty() && !offhand.isEmpty()) {
//	    	  this.ironGolemRightArm.rotateAngleX = -2.0F + 1.5F * MathHelper.func_233021_e_((float)i - partialTick, 10.0F);
//	          this.ironGolemLeftArm.rotateAngleX = -2.0F + 1.5F * MathHelper.func_233021_e_((float)i - partialTick, 10.0F);
	    	  handright.setRotationX( MathHelper.func_233021_e_((float)i - customPredicate.getPartialTick(), 50.0F));
	    	  handleft.setRotationX(MathHelper.func_233021_e_((float)i - customPredicate.getPartialTick(), 50.0F));
	      }
	      else if(i > 0 && !mainhand.isEmpty()) {
	    	
	    	  handright.setRotationX( MathHelper.func_233021_e_((float)i - customPredicate.getPartialTick(), 50.0F));
	    	  handleft.setRotationX(MathHelper.func_233021_e_((float)i - customPredicate.getPartialTick(), 50.0F));
	      }
	      else if (i > 0 && !offhand.isEmpty()){
	    	  handright.setRotationX(MathHelper.func_233021_e_((float)i - customPredicate.getPartialTick(), 50.0F));
	    	  handleft.setRotationX(MathHelper.func_233021_e_((float)i - customPredicate.getPartialTick(), 50.0F));
	      }
	      else {
	    	  handright.getRotationX();
	    	  handleft.getRotationX();
	  		}
		
		if(i == 0 && !mainhand.isEmpty() && !offhand.isEmpty()) {
  			this.getAnimationProcessor().getBone("ArmRightBone").setRotationX(0.5F);
  			this.getAnimationProcessor().getBone("ArmLeftBone").setRotationX(0.5F);
  		}else if(i == 0 &&!mainhand.isEmpty()) {
  			this.getAnimationProcessor().getBone("ArmRightBone").setRotationX(0.5F);
  		}else if(i == 0 &&!offhand.isEmpty()) {
  			this.getAnimationProcessor().getBone("ArmLeftBone").setRotationX(0.5F);
  		}
		else {
  			this.getAnimationProcessor().getBone("ArmRightBone").getRotationX();
  			this.getAnimationProcessor().getBone("ArmLeftBone").getRotationX();
  		}
		
		this.getAnimationProcessor().getBone("BodyBone").setScaleX(1.9F);// skala besar X seberapa besar entity dalam arah X
		this.getAnimationProcessor().getBone("BodyBone").setScaleY(1.9F);// skala besar Y seberapa besar entity dalam arah Y
		this.getAnimationProcessor().getBone("BodyBone").setScaleZ(2.3F);// skala besar Z seberapa besar entity dalam arah Z
		
		this.getAnimationProcessor().getBone("BodyBone").setPositionY(6F);
		
	

		
		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX(extraData.headPitch * ((float)Math.PI / 180F));
		head.setRotationY(extraData.netHeadYaw * ((float)Math.PI / 180F));
		
		extraData.netHeadYaw = entityIn.getEyeHeight();
		
		
		
	}



@Override
public ModelRenderer getModelHead() {
	// TODO Auto-generated method stub
	IBone head = this.getAnimationProcessor().getBone("HeadBone");
	return (ModelRenderer) head;
}

@Override
public void translateHand(HandSide sideIn, MatrixStack matrixStackIn) {
	this.getArmForSide(sideIn).translateRotate(matrixStackIn);
	
}
protected ModelRenderer getArmForSide(HandSide side) {
	IBone handright = this.getAnimationProcessor().getBone("ArmRightBone");
	IBone handleft = 	this.getAnimationProcessor().getBone("ArmLeftBone");
    return side == HandSide.LEFT ? (ModelRenderer) handleft: (ModelRenderer) handright;
 }

}
