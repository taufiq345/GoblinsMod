package com.goblinsmod.Gobmain.common.AI.AIlist;




import com.goblinsmod.Gobmain.common.entities.GoblinsEntity;

import java.util.EnumSet;


import java.util.Random;


import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

import net.minecraft.entity.ai.goal.Goal;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import net.minecraft.world.World;


public class findandBreakWoodAI extends Goal {
	
	
//		 protected BlockPos blckpos = BlockPos.ZERO;
		//private  PlayerEntity blckpos;
		
       // BlockState blockstate = this.creature.world.getBlockState(this.blckpos);

	
		protected GoblinsEntity  creature;
		private final double speedIn ;
		private World world;
		protected double movex;
		protected double movey;
		protected double movez;
	   public findandBreakWoodAI(GoblinsEntity creature, double speedIn) {
	      
	   
		 this.creature = creature;
		 this.world = this.creature.world;
		 this.speedIn = speedIn;
	      this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
	   }

	  
	

//	   public boolean shouldExecute() {
//	
//	        if (this.world.isDaytime()) {
//	           //Vector3d vec3d = this.findBlock();
//	        	//this.startExecuting();
////	            if (vec3d == null) {
////	                return false;
////	            } else {
//////	                this.blckpos = BlockPos.ZERO;
//////	               
//	                return true;
//	            }
//	         else {
//	            return false;
//	        }
//	    }

	   
	    public boolean shouldContinueExecuting() {
	        return !this.creature.getNavigator().noPath();
	    }


	    public void startExecuting() {
       // BlockState blockstate = this.creature.world.getBlockState(this.blckpos);
        //this.bk = Blocks.OAK_LOG;
		//if(!(this.creature.getDistanceSq((double)this.blckpos.getPosX(), this.creature.getPosY(), (double)this.blckpos.getPosZ()) > 2.25D) ){
			System.out.println("THE AI IS EXECUTING");
	       this.creature.getNavigator().tryMoveToXYZ(this.movex, this.movey, this.movez, this.speedIn);
		//}
	    }
		
		
	    @Nullable
	    private Vector3d findBlock() {
	        Random random = this.creature.getRNG();
	        BlockPos blockpos = new BlockPos(this.creature.getPosX(), this.creature.getBoundingBox().minY, this.creature.getPosZ());
	        
	        BlockState blockstate = this.creature.world.getBlockState(blockpos);
	        for (int i = 0; i < 10; ++i) {
	            BlockPos blockpos1 = blockpos.add(random.nextInt(20) - 10, random.nextInt(6) - 3, random.nextInt(20) - 10);
	            				

	            if (!this.world.canSeeSky(blockpos1)&& this.creature.getBlockPathWeight(blockpos1) < 40.0F) {
	                return new Vector3d((double) blockpos1.getX(), (double) blockpos1.getY(), (double) blockpos1.getZ());
	            }
	            
	        }
			return null;
			
	    }

//	        return null;
//	    } 
//	   @Nullable
//	   protected Vector3d getPosition() {
//	      if (this.creature.isInWaterOrBubbleColumn()) {
//	         Vector3d vector3d = RandomPositionGenerator.getLandPos(this.creature, 15, 7);
//	         	this.getPosition().
//	         return vector3d == null ? super.getPosition() : vector3d;
//	      } else {
//	         return this.creature.getRNG().nextFloat() >= this.probability ? RandomPositionGenerator.getLandPos(this.creature, 10, 7) : super.getPosition();
//	      }
//	   }


	

		@Override
		public boolean shouldExecute() {
			 if (!this.creature.world.isDaytime()) {
				 return false;
			 }else if (!this.world.canSeeSky(new BlockPos(this.creature.getPosX(), this.creature.getBoundingBox().minY, this.creature.getPosZ()))) {
		            return false;
		        }
			 else {
				 Vector3d Vector3d = this.findBlock();
				 if (!(Vector3d == null)) {
		       
		                this.movex = Vector3d.x;
		                this.movey = Vector3d.y;
		                this.movez = Vector3d.z;
		                return true;
		            } else {
		           
		                return false;
		            }
				 
			 }
		}



//	@Override
//	public boolean shouldExecute() {
//		// TODO Auto-generated method stub
//		return false;
//	}
}
