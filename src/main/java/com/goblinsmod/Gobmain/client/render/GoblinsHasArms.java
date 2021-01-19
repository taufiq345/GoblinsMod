package com.goblinsmod.Gobmain.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.util.HandSide;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
@OnlyIn(Dist.CLIENT)
public interface GoblinsHasArms {
	 void translateHand(HandSide sideIn, MatrixStack matrixStackIn);
}
