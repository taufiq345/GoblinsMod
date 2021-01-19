package com.goblinsmod.Gobmain.client.render;

import com.goblinsmod.Gobmain.client.entity.model.GoblinsEntityModel;
import com.goblinsmod.Gobmain.common.entities.GoblinsEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.FoxHeldItemLayer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

public class GoblinsEntityRender extends GeoEntityRenderer<GoblinsEntity>
{
	
	
	private IRenderTypeBuffer renderTypeBuffer;
    private GoblinsEntity goblinsEnt;
    private HandSide p_229135_4_;
    @SuppressWarnings("unchecked")
    
   
	public GoblinsEntityRender(EntityRendererManager renderManager)
    {
        super(renderManager, new GoblinsEntityModel());
        this.shadowSize = 0.5F;
        //this.addLayer(new GoblinsItemRenderer(this));
    }

    @Override
    public void renderEarly(GoblinsEntity animatable, MatrixStack stackIn, float ticks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        this.goblinsEnt = animatable;
        this.renderTypeBuffer = renderTypeBuffer;
 
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }
 
    @Override
    public RenderType getRenderType(GoblinsEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.getEntitySmoothCutout(getTextureLocation(animatable));
    }
 
    @Override
    public void renderRecursively(GeoBone bone, MatrixStack matrixStack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (bone.getName().equals("ArmRightBone")) {
            matrixStack.push();
            matrixStack.scale(0.5F, 0.5F, 0.5F);

   	  int i = goblinsEnt.getAttackTimer();
	     if(i > 0) {
	
	    	 matrixStack.translate(0.2F, 0.6F, -0.4F);
	         
	        	matrixStack.rotate(Vector3f.YP.rotationDegrees(200.0F-i));
	        	matrixStack.rotate(Vector3f.ZP.rotationDegrees(200.0F-i));
	        	matrixStack.rotate(Vector3f.XP.rotationDegrees(200.0F-i));
	     }
         else {

        	matrixStack.translate(0.2F, 0.6F, -0.4F); //x kanan -x kiri, z belakang -z depan, y atas -y bawah
        	matrixStack.rotate(Vector3f.XP.rotationDegrees(265.0F));
         }
            ItemStack heldStack = goblinsEnt.getHeldItem(Hand.MAIN_HAND);
            Minecraft.getInstance().getItemRenderer().renderItem(heldStack, ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND, packedLightIn, packedOverlayIn, matrixStack, this.renderTypeBuffer);

            matrixStack.pop();
 
            bufferIn = renderTypeBuffer.getBuffer(RenderType.getEntitySmoothCutout(getTextureLocation(goblinsEnt)));
        }
        
        
        if (bone.getName().equals("ArmLeftBone")) {
            matrixStack.push();
            matrixStack.scale(0.5F, 0.5F, 0.5F);

            int i = goblinsEnt.getAttackTimer();
	     if(i > 0) {
	
	    	 matrixStack.translate(0.2F, 0.6F, -0.4F);
	         
	        	matrixStack.rotate(Vector3f.YP.rotationDegrees(200.0F-i));
	        	matrixStack.rotate(Vector3f.ZP.rotationDegrees(200.0F-i));
	        	matrixStack.rotate(Vector3f.XP.rotationDegrees(200.0F-i));
	     }
         else {

        	matrixStack.translate(0.2F, 0.6F, -0.4F); //x kanan -x kiri, z belakang -z depan, y atas -y bawah
        	matrixStack.rotate(Vector3f.XP.rotationDegrees(265.0F));
         }
            ItemStack heldStack = goblinsEnt.getHeldItem(Hand.OFF_HAND);
            Minecraft.getInstance().getItemRenderer().renderItem(heldStack, ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND, packedLightIn, packedOverlayIn, matrixStack, this.renderTypeBuffer);

            matrixStack.pop();
 
            bufferIn = renderTypeBuffer.getBuffer(RenderType.getEntitySmoothCutout(getTextureLocation(goblinsEnt)));
        }
        super.renderRecursively(bone, matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
    
}