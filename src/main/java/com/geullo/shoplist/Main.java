package com.geullo.shoplist;

import com.geullo.shoplist.proxy.CommonProxy;
import com.geullo.shoplist.util.Reference;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.Logger;

import java.io.IOException;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME)
public class Main {
    @Instance
    public static Main instacne;

    @SidedProxy(clientSide = Reference.CLIENTPROXY,serverSide = Reference.COMMONPROXY)
    public static CommonProxy proxy;
    public Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) throws IOException {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }


}
