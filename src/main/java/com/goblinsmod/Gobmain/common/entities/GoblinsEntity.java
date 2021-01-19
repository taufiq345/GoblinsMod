package com.goblinsmod.Gobmain.common.entities;

import java.util.List;

import javax.annotation.Nullable;

import com.goblinsmod.Gobmain.common.AI.AIlist.BreakWoodGoal;
import com.goblinsmod.Gobmain.common.AI.AIlist.NearestAttackEntity;
import com.goblinsmod.Gobmain.common.AI.AIlist.findandBreakWoodAI;

import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;


public class GoblinsEntity extends CreatureEntity implements IAnimatable
{
	private AnimationFactory factory = new AnimationFactory(this);

	 private final Inventory inventory = new Inventory(1);
	 private int attackTimer;
	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
	{
		
//		if(event.isMoving()== true){
//			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.goblinmod.walking_animation", true));
//			return PlayState.CONTINUE;
//		}
		if(event.isMoving()== false) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.goblinmod.idle_animation", true));
			//event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.goblinmod.size",true));
			return PlayState.CONTINUE;
		}
		int i = this.getAttackTimer();
//		if(i>0) {
//			
//			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.goblinmod.attackanim", true));
//		}
		
		return  PlayState.CONTINUE;
		
	
	}
	

    
	  //func_233666_p_ ---> registerAttributes()
    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 10.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D)
        		.createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.0D);
    }
    
    
    
    
    
    
    
    
    
    
  
	public GoblinsEntity(EntityType<? extends CreatureEntity> type, World worldIn)
	{
		super(type, worldIn);
  	  
		 GoblinsEntity.this.setCanPickUpLoot(true);
		this.ignoreFrustumCheck = true;
	}
	


     @Override
	 public boolean canPickUpItem(ItemStack itemstackIn) {
	      EquipmentSlotType equipmentslottype = MobEntity.getSlotForItemStack(itemstackIn);
	      if (!this.getItemStackFromSlot(equipmentslottype).isEmpty()) {
	         return equipmentslottype == EquipmentSlotType.OFFHAND && super.canPickUpItem(itemstackIn)|| equipmentslottype == EquipmentSlotType.MAINHAND&& super.canPickUpItem(itemstackIn);
	      } else {
	         return equipmentslottype == EquipmentSlotType.MAINHAND && super.canPickUpItem(itemstackIn);
	      }
	   }
	
     /**
      * Tests if this entity should pickup a weapon or an armor. Entity drops current weapon or armor if the new one is
      * better.
      */
     @Override
     protected void updateEquipmentIfNeeded(ItemEntity itemEntity) {
        ItemStack itemstack = itemEntity.getItem();
        Item item = itemstack.getItem();
        if (this.canEquipItem(itemstack)&&item != Items.SHIELD ) {
           int i = itemstack.getCount();
           if (i > 1) {
              this.spawnItem(itemstack.split(i - 1));
           }
           ItemStack itemstackmain = GoblinsEntity.this.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
  
           
           if(itemstackmain.isEmpty()) {
        	   this.spitOutItem(this.getItemStackFromSlot(EquipmentSlotType.MAINHAND));
               this.triggerItemPickupTrigger(itemEntity);
           this.setItemStackToSlot(EquipmentSlotType.MAINHAND, itemstack.split(1));
           this.inventoryHandsDropChances[EquipmentSlotType.MAINHAND.getIndex()] = 2.0F;
           }else {
        	   this.spitOutItem(this.getItemStackFromSlot(EquipmentSlotType.OFFHAND));
               this.triggerItemPickupTrigger(itemEntity);
        	   this.setItemStackToSlot(EquipmentSlotType.OFFHAND, itemstack.split(1));
        	   this.inventoryHandsDropChances[EquipmentSlotType.OFFHAND.getIndex()] = 2.0F;
           }
           
           
           this.onItemPickup(itemEntity, itemstack.getCount());
           itemEntity.remove();
           
        }

     }
	
     private void spawnItem(ItemStack stackIn) {
         ItemEntity itementity = new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), stackIn);
         this.world.addEntity(itementity);
      }

	private void spitOutItem(ItemStack stackIn) {
	      if (!stackIn.isEmpty() && !this.world.isRemote) {
	         ItemEntity itementity = new ItemEntity(this.world, this.getPosX() + this.getLookVec().x, this.getPosY() + 1.0D, this.getPosZ() + this.getLookVec().z, stackIn);
	         itementity.setPickupDelay(40);
	         itementity.setThrowerId(this.getUniqueID());
	        // this.playSound(SoundEvents.ENTITY_FOX_SPIT, 1.0F, 1.0F);
	         this.world.addEntity(itementity);
	      }
	   }
	 
	@Override
	public void registerControllers(AnimationData data)
	{
		data.addAnimationController(new AnimationController<GoblinsEntity>(this, "gobcontroller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory()
	{
		return this.factory;
	}

	@Override
	protected void registerGoals()
	{
		//this.goalSelector.addGoal(0, new SwimGoal(this));
	   // this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));

			this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
			if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(GoblinsEntity.this.world, GoblinsEntity.this)) {
				 this.goalSelector.addGoal(1, new GoblinsEntity.AttackWoodGoal(this,1.0D,7));
		 }
		    //  this.targetSelector.addGoal(2, new NearestAttackEntity<>(this, ChickenEntity.class,true, true));
			this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
		    this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, MobEntity.class, 5, false, false, (p_234199_0_) -> {
		         return p_234199_0_ instanceof IMob && !(p_234199_0_ instanceof CreeperEntity);
		      }));

		//GoblinsEntity.AttackWoodGoal(this,1.0D(ini kecepatan entity dapat berlari/jalan),4 (seberapa tingginya entity dapat mencari block))
		
		
		this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.applyAI();
		super.registerGoals();
	}
   
	
	   protected boolean canBreakWood() {
	      return true;
	   }

	   
	   public void writeAdditional(CompoundNBT compound) {
		      super.writeAdditional(compound);
		      ListNBT listnbt = new ListNBT();

		      for(int i = 0; i < this.inventory.getSizeInventory(); ++i) {
	         ItemStack itemstack = this.inventory.getStackInSlot(i);
		         		         if (!itemstack.isEmpty()) {		           
		         		        	 listnbt.add(itemstack.write(new CompoundNBT()));
		         		         }
		         }
		      

		      compound.put("Inventory", listnbt);
		   }

		   /**
		    * (abstract) Protected helper method to read subclass entity data from NBT.
		    */
		   public void readAdditional(CompoundNBT compound) {
		      super.readAdditional(compound);
		
		      
		      
		      ListNBT listnbt = compound.getList("Inventory", 10);

		      for(int i = 0; i < listnbt.size(); ++i) {
		         ItemStack itemstack = ItemStack.read(listnbt.getCompound(i));
		         if (!itemstack.isEmpty()) {
		            this.inventory.addItem(itemstack);
		           
		         }
		      }

		      //this.setCanPickUpLoot(true);
		   
		   }
		   
		   
		   @Nullable
		   public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		      spawnDataIn = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		
		      return spawnDataIn;
		   }
		   
		   
		   
		   protected void collideWithEntity(Entity entityIn) {
			      if (entityIn instanceof IMob && !(entityIn instanceof CreeperEntity) && this.getRNG().nextInt(20) == 0) {
			         this.setAttackTarget((LivingEntity)entityIn);
			      }

			      super.collideWithEntity(entityIn);
			   }
		

		   
		   /**
		    * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
		    * use this to react to sunlight and start to burn.
		    */
		   public void livingTick() {
		      super.livingTick();
		      this.world.getProfiler().startSection("looting");
		      if (!this.world.isRemote && this.canPickUpLoot() && this.isAlive() && !this.dead && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this)) {
		         for(ItemEntity itementity : this.world.getEntitiesWithinAABB(ItemEntity.class, this.getBoundingBox().grow(1.0D, 0.0D, 1.0D))) {
		            if (!itementity.removed && !itementity.getItem().isEmpty() && !itementity.cannotPickup() && this.func_230293_i_(itementity.getItem())) {
		               this.updateEquipmentIfNeeded(itementity);
		            }
		         }
		      }
		      if (this.attackTimer > 0) {
		          --this.attackTimer;
		       }

		      this.world.getProfiler().endSection();
		   }
		   
			@OnlyIn(Dist.CLIENT)
			   public int getAttackTimer() {
			      return this.attackTimer;
			   }

		   /**
		    * Handler for {@link World#setEntityState}
		    */
		   @OnlyIn(Dist.CLIENT)
		   public void handleStatusUpdate(byte id) {
		      if (id == 4) {
		         this.attackTimer = 10;
		         this.playSound(SoundEvents.ENTITY_IRON_GOLEM_ATTACK, 1.0F, 1.0F);
		      } else {
		         super.handleStatusUpdate(id);
		      }

		   }
		   
		   
		   
		   private float attackDAMAGE() {
			      return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
			   }
		   
		   
		   
		   
		   
		   public boolean attackEntityAsMob(Entity entityIn) {
			      this.attackTimer = 10;
			      this.world.setEntityState(this, (byte)4);
			      float f = this.attackDAMAGE();
			      float f1 = (int)f > 0 ? f / 2.0F + (float)this.rand.nextInt((int)f) : f;
			      boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), f1);
			      if (flag) {
			         entityIn.setMotion(entityIn.getMotion().add(0.0D, (double)0.4F, 0.0D));
			         this.applyEnchantments(this, entityIn);
			      }

			      this.playSound(SoundEvents.ENTITY_IRON_GOLEM_ATTACK, 1.0F, 1.0F);
			      return flag;
			   }

		   /**
		       * Keep ticking a continuous task that has already been started
		       */
		      public void tick() {
//		    	  ItemStack itemstack = GoblinsEntity.this.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
//		            if (!itemstack.isEmpty()) {
//		            	
//		            		GoblinsEntity.this.offhandequip();
//		               
//		            }else if(!GoblinsEntity.this.isAlive()) {		    		
//		           	GoblinsEntity.this.gobdeath();
//		           	
//		           }
//		    	  
		         super.tick();
		      }
		      
		
		      
		      @Nullable
		      public ItemEntity entityDropItem(ItemStack stack) {
		         return this.entityDropItem(stack, 0.0F);
		      }


		      
		      
		 
		     
		      
	
	   
	   @Override
	protected void updateAITasks() {
      

	}


		   class AttackWoodGoal extends BreakWoodGoal {
		

			AttackWoodGoal(CreatureEntity creatureIn, double speed, int yMax) {
			         super(Blocks.OAK_LOG, creatureIn, speed, yMax);
			   
			      }
			

			     public void playBreakingSound(IWorld worldIn, BlockPos pos) {
			         worldIn.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_WOOD_HIT, SoundCategory.HOSTILE, 0.5F, 0.9F + GoblinsEntity.this.rand.nextFloat() * 0.2F);
			      }

			      public void playBrokenSound(World worldIn, BlockPos pos) {
			         worldIn.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_WOOD_BREAK, SoundCategory.BLOCKS, 0.7F, 0.9F + worldIn.rand.nextFloat() * 0.2F);
			      }

		   }


	private void applyAI() {
		// TODO Auto-generated method stub
		
	}
	
}