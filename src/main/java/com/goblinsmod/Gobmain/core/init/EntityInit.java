package com.goblinsmod.Gobmain.core.init;

import com.goblinsmod.Gobmain.Maincore_mod;
import com.goblinsmod.Gobmain.common.entities.GoblinsEntity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityInit {

	
	
	 public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES,Maincore_mod.MOD_ID);

	 // Entity Types
	    public static final RegistryObject<EntityType<GoblinsEntity>> GOBLIN = ENTITY_TYPES.register("goblin",
	            () -> EntityType.Builder.create(GoblinsEntity::new, EntityClassification.MONSTER)
	                    .size(0.6f, 1.3f) // Hitbox Size ()
	                    .build(new ResourceLocation(Maincore_mod.MOD_ID,"goblin").toString()));
}
