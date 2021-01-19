package com.goblinsmod.Gobmain.core.util;

import com.goblinsmod.Gobmain.Maincore_mod;
import com.goblinsmod.Gobmain.client.render.GoblinsEntityRender;
import com.goblinsmod.Gobmain.common.entities.GoblinsEntity;
import com.goblinsmod.Gobmain.common.items.ModSpawnEggItems;
import com.goblinsmod.Gobmain.core.init.EntityInit;

import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Maincore_mod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubcriber {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.GOBLIN.get(), GoblinsEntityRender::new);
    }

    @SubscribeEvent
    public static void onRegisterEntities(final RegistryEvent.Register<EntityType<?>> event) {

    }
}