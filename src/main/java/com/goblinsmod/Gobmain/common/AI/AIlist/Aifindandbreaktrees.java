package com.goblinsmod.Gobmain.common.AI.AIlist;

import java.util.EnumSet;
import java.util.Random;

import javax.annotation.Nullable;

import com.goblinsmod.Gobmain.common.entities.GoblinsEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;



public abstract class Aifindandbreaktrees extends Goal {
   protected final CreatureEntity creature;
   public final double movementSpeed;
   /** Controls task execution delay */
   protected int runDelay;
   
 
   
   protected int timeoutCounter;
   private int maxStayTicks;
   /** Block to move to */
   protected BlockPos destinationBlock = BlockPos.ZERO;
   private boolean isAboveDestination;
   private final int searchLength;
   private final int field_203113_j;
   protected int field_203112_e;

   public Aifindandbreaktrees(CreatureEntity creature, double speedIn, int length) {
      this(creature, speedIn, length, 1);
   }

   public Aifindandbreaktrees(CreatureEntity creatureIn, double speed, int length, int p_i48796_5_) {
      this.creature = creatureIn;
      this.movementSpeed = speed;
      this.searchLength = length;
      this.field_203112_e = 0;
      this.field_203113_j = p_i48796_5_;
      this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE,Goal.Flag.LOOK,Goal.Flag.JUMP));
  
   }

   /**
    * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
    * method as well.
    */
   public boolean shouldExecute() {
	   BlockPos blockpos = this.creature.getPosition();
	    BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
	    blockpos$mutable.setAndOffset(blockpos, blockpos.getX(), blockpos.getY(), blockpos.getZ());
      if (this.runDelay > 0) {
         --this.runDelay;
         return false;
      } else {
    	
         this.runDelay = this.getRunDelay(this.creature);
         return this.searchForDestination();
      }
   }

   protected int getRunDelay(CreatureEntity creatureIn) {
      return 10 + creatureIn.getRNG().nextInt(10);
   }

   /**
    * Returns whether an in-progress EntityAIBase should continue executing
    */
   public boolean shouldContinueExecuting() {
      return this.timeoutCounter >= -this.maxStayTicks && this.timeoutCounter <= 120 && this.shouldMoveTo(this.creature.world, this.destinationBlock) ;
   }

   /**
    * Execute a one shot task or start executing a continuous task
    */
   public void startExecuting() {
      this.func_220725_g();
      this.timeoutCounter = 0;
      this.maxStayTicks = this.creature.getRNG().nextInt(this.creature.getRNG().nextInt(120) + 120) + 120;
   

  
   }

   protected void func_220725_g() {
      this.creature.getNavigator().tryMoveToXYZ((double)((float)this.destinationBlock.getX()) , (float)this.destinationBlock.getY(), (double)((float)this.destinationBlock.getZ()) , this.movementSpeed);
   }

   public double getTargetDistanceSq() { // seberapa jauh entity sudah dekat block
      return 1.0D;
   }

   protected BlockPos func_DestinationBlock() {
      return this.destinationBlock.down();
   }

   protected IWorldReader worldIn;
   /**
    * Keep ticking a continuous task that has already been started
    */
  
public void tick() {
      BlockPos blockpos = this.func_DestinationBlock();
    
      if (!blockpos.withinDistance(this.creature.getPositionVec(), this.getTargetDistanceSq())) {
       this.isAboveDestination = false;
       
         ++this.timeoutCounter;
         if (this.shouldMove()&& this.isAboveDestination == false) {
        	
        	 
             
            this.creature.getNavigator().tryMoveToXYZ((double)((float)blockpos.getX()), (float)blockpos.getY(), (double)((float)blockpos.getZ()) , this.movementSpeed);// entity atau mobnya bergerak ke target
            
        
            Vector3d vector3d = this.creature.getMotion();
            BlockPos blockfrontandbot = new BlockPos(this.creature.getPosX()+1, this.creature.getPosY()-2,  this.creature.getPosZ() );// posisi entity jika dibawah posisi x=1 y=-2 z=0 tidak ada block
            BlockPos blocknotair = new BlockPos(this.creature.getPosX()+2 - this.creature.getLookVec().x, this.creature.getPosY()-1,  this.creature.getPosZ()- this.creature.getLookVec().z);
            Vector3d vector3d1 = new Vector3d(blockpos.getX() - this.creature.getPosX(), 0.0D, blockpos.getZ() - this.creature.getPosZ());


           // this.creature.getLookVec().x
            
            //1.0E-1D  Enya ada scientific notasi yang mengdoublekan nilai 1.0nya
            //!(this.creature.world.getBlockState(this.creature.getPosition().add(blocknotair)) != Blocks.AIR.getDefaultState())
            //this.creature.world.getBlockState(this.creature.getPosition().add(blockfrontandbot))== Blocks.AIR.getDefaultState() &&!this.creature.getNavigator().canEntityStandOnPos(blockfrontandbot)&&
            if (vector3d1.lengthSquared() > 1.0E-1D && !this.creature.world.getBlockState(blocknotair).isAir()&& this.creature.world.getBlockState(blockfrontandbot).isAir()) {
               vector3d1 = vector3d1.normalize().scale(0.3D).add(vector3d.scale(0.3D)); // vector3d1.normalize().scale adalah menormalisasi dan mengubah skala pathnya,    vector3d.scale(0.3D) seberapa jauh loncat entity
               this.creature.setMotion(vector3d1.x, (double)0.5F, vector3d1.z);//entity loncat karena nilai setmotion(y)nya 0.4F
             
            }else {
            	
            }
       
          
         }
      }
      else  {
    	    
          
    	  
        this.isAboveDestination = true;
         --this.timeoutCounter;
      }

   }

   public boolean shouldMove() {
      return this.timeoutCounter % 40 == 0;
   }

   protected boolean getIsAboveDestination() {
      return this.isAboveDestination;
   }

   /**
    * Searches and sets new destination block and returns true if a suitable block (specified in {@link
    * net.minecraft.entity.ai.EntityAIMoveToBlock#shouldMoveTo(World, BlockPos) EntityAIMoveToBlock#shouldMoveTo(World,
    * BlockPos)}) can be found.
    */
   protected boolean searchForDestination() {
      int i = this.searchLength;
      int j = this.field_203113_j;
      BlockPos blockpos = this.creature.getPosition();
      BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
     
      //matematika Quaternion k itu atas, -k itu bawah , i1 itu arah x, -i1 itu arah -x, j1 itu arah z,  -j1 itu arah -z dan l adalah seberapa jauh pencarian AInya dapat mendeteksi block
      for(int k = this.field_203112_e; k <= j; k = k > 0 ? -k : 1 - k) {
         for(int l = 0; l < i; ++l) {
            for(int i1 = 0; i1 <= l; i1 = i1 > 0 ? -i1 : 1 - i1) {
               for(int j1 = i1 < l && i1 > -l ? l : 0; j1 <= l; j1 = j1 > 0 ? -j1 : 1 - j1) {
            	
                  blockpos$mutable.setAndOffset(blockpos, i1, k, j1);
                  if (this.creature.isWithinHomeDistanceFromPosition(blockpos$mutable) && this.shouldMoveTo(this.creature.world, blockpos$mutable)) {
                     this.destinationBlock = blockpos$mutable;
                     return true;
                  }
            	
               }
            }
         }
      }

      return false;
   }
   
  
   /**
    * Return true to set given position as destination
    */
   protected abstract boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos);
}
