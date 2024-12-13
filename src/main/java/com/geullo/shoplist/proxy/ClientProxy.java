package com.geullo.shoplist.proxy;

import com.geullo.shoplist.Packet;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class ClientProxy extends CommonProxy{
    public static SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel("lifeStore3");
    @Override
    public void init() {
        NETWORK.registerMessage(Packet.Handle.class, Packet.class, 0, Side.CLIENT);
    }

}
