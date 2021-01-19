package com.goblinsmod.Gobmain.common.AI.AIlist;



import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import com.google.common.base.Stopwatch;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.server.ServerWorld;

public class BreakWoodGoal extends Aifindandbreaktrees {
   private final Block block;
   private final MobEntity entity;
   private int breakingTime;
   //variable for head looking block
   

   
   public BreakWoodGoal(Block blockIn, CreatureEntity creature, double speed, int yMax) {
      super(creature, speed, 24, yMax);
      this.block = blockIn;
      this.entity = creature;
   }

   /**
    * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
    * method as well.
    */
   public boolean shouldExecute() {
      if (!net.minecraftforge.common.ForgeHooks.canEntityDestroy(this.entity.world, this.destinationBlock, this.entity)) {
         return false;
      } else if (this.runDelay > 0) {
         --this.runDelay;
         return false;
      } else if (this.func_220729_m()  ) {
         this.runDelay = 1;
         
         return true;
      } else {
         this.runDelay = this.getRunDelay(this.creature);
         return false;
      }
   }

   private boolean func_220729_m() {
      return this.destinationBlock != null && this.shouldMoveTo(this.creature.world, this.destinationBlock) ? true : this.searchForDestination();
   }

   /**
    * Reset the task's internal state. Called when this task is interrupted by another one
    */
   public void resetTask() {
      super.resetTask();
      this.entity.fallDistance = 0.0F;
   }

   /**
    * Execute a one shot task or start executing a continuous task
    */
   public void startExecuting() {
      super.startExecuting();
      this.breakingTime = 0;
   }

   public void playBreakingSound(IWorld worldIn, BlockPos pos) {
   }

   public void playBrokenSound(World worldIn, BlockPos pos) {
   }
   
   

 
   
   /**
    * Keep ticking a continuous task that has already been started
    */
   public void tick() {
      super.tick();
      World world = this.entity.world;
      BlockPos blockpos = this.entity.getPosition();
      BlockPos blockpos1 = this.findTarget(blockpos, world);

      Random random = this.entity.getRNG();
    
     
      
      BlockPos blocklok = new BlockPos(this.creature.getPosX()+1, this.creature.getPosY()-2,  this.creature.getPosZ());// posisi entity jika dibawah posisi x=1 y=-2 z=0 tidak ada block
      BlockPos blocknotair = new BlockPos(this.creature.getPosX()+2, this.creature.getPosY()-1,  this.creature.getPosZ());
 
      if (/*this.getIsAboveDestination() && */blockpos1 != null) {

         if (this.breakingTime > 0) {
        	
  
            Vector3d vector3d = this.entity.getMotion();
            
            Vector3d vector3d1 = new Vector3d(blockpos.getX() - this.creature.getPosX(), 0.0D, blockpos.getZ() - this.creature.getPosZ());// ini untuk fungsi mengtahui jarak antar entity dengan block
            
            this.entity.setMotion(vector3d.x, vector3d.y, vector3d.z);//ketika task ini dijalankan entity akan bergerak sesuai vektor x,y,z
            this.creature.getNavigator().clearPath();
            this.creature.getLookController().setLookPosition((double)((float)blockpos1.getX()), (double)((float)blockpos1.getY()), (double)((float)blockpos1.getZ()));//entity mmelihat target block
             vector3d1 = vector3d1.normalize().scale(0D).add(vector3d.scale(0D)); // vector3d1.normalize().scale adalah menormalisasi dan mengubah skala pathnya,    vector3d.scale(0.3D) mengedit x,y,z vektor
              this.creature.setMotion(vector3d1.x,  -0.4D, vector3d1.z);//entity loncat karena nilai setmotion(y)nya 0.4F
           
            
            if (!world.isRemote) {
               //double d0 = 0.08D;
               ((ServerWorld)world).spawnParticle(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(Items.OAK_LOG)), (double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.7D, (double)blockpos1.getZ() + 0.5D, 3, ((double)random.nextFloat() - 0.5D) * 0.08D, ((double)random.nextFloat() - 0.5D) * 0.08D, ((double)random.nextFloat() - 0.5D) * 0.08D, (double)0.15F);
           	 
            }
         }

         if (this.breakingTime % 2 == 0) {
        	 
            Vector3d vector3d1 = this.entity.getMotion();
            
            Vector3d vector3d2 = new Vector3d(blockpos.getX() - this.creature.getPosX(), 0.0D, blockpos.getZ() - this.creature.getPosZ());// ini untuk fungsi mengtahui jarak antar entity dengan block
            vector3d2 = vector3d2.normalize().scale(0D).add(vector3d2.scale(0D)); // vector3d1.normalize().scale adalah menormalisasi dan mengubah skala pathnya,    vector3d.scale(0.3D) seberapa jauh loncat entity
             this.creature.setMotion(vector3d2.x, -0.4D, vector3d2.z);//entity loncat turun karena nilai setmotion(-y)nya -0.4F
             this.creature.getNavigator().clearPath();
             
             
            // this.creature.getNavigator().tryMoveToXYZ((double)((float)blockpos.getX()), (float)blockpos.getY(), (double)((float)blockpos.getZ()) , 0);//entity bergerak ketempat yang diinginkan kali ini blocknya
        
            this.creature.getLookController().setLookPosition((double)((float)blockpos1.getX()), (double)((float)blockpos1.getY()), (double)((float)blockpos1.getZ()));
            
            
           
            if (this.breakingTime % 6 == 0) {
               this.playBreakingSound(world, this.destinationBlock);
            }
         }

         if (this.breakingTime > 60) {
        	
            world.removeBlock(blockpos1, false);
            if (!world.isRemote) {
               for(int i = 0; i < 20; ++i) {
                  double d3 = random.nextGaussian() * 0.02D;
                  double d1 = random.nextGaussian() * 0.02D;
                  double d2 = random.nextGaussian() * 0.02D;
                  ((ServerWorld)world).spawnParticle(ParticleTypes.POOF, (double)blockpos1.getX() + 0.5D, (double)blockpos1.getY(), (double)blockpos1.getZ() + 0.5D, 1, d3, d1, d2, (double)0.15F);
               }

               this.playBrokenSound(world, blockpos1);
            }
         }
        

         ++this.breakingTime;
      }
    

   }

   @Nullable
   private BlockPos findTarget(BlockPos pos, IBlockReader worldIn) {
      if (worldIn.getBlockState(pos).isIn(this.block)) {
         return pos;
      } else {
         BlockPos[] ablockpos = new BlockPos[]{pos.down(),pos.west(), pos.east(), pos.north(), pos.south(),pos.up()};

         for(BlockPos blockpos : ablockpos) {
            if (worldIn.getBlockState(blockpos).isIn(this.block)) {
                

               return blockpos;
            }
         }

         return null;
      }
   }

   /**
    * Return true to set given position as destination
    */
   protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
      IChunk ichunk = worldIn.getChunk(pos.getX()>>4 , pos.getZ()>>4 , ChunkStatus.FULL, false);
      if (ichunk == null) {
         return false;
      } else {
    	  
         return ichunk.getBlockState(pos).isIn(this.block); //&& ichunk.getBlockState(pos.up(2)).isAir(); pos.up/north/down/ dan lain lain adalah untuk mengecek tiap sisi block
      }
   }
}
