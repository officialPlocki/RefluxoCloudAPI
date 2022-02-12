package me.refluxo.cloudapi.common.packet;

import me.refluxo.cloudapi.common.CloudHandler;
import me.refluxo.cloudapi.common.service.ServiceType;
import me.refluxo.networker.packet.Packet;
import net.md_5.bungee.api.ProxyServer;
import org.bukkit.Bukkit;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class StopServicePacket implements Packet {

    @Override
    public void read(ObjectInputStream objectInputStream) {
        System.out.println("got");
        if(new CloudHandler().getType().equals(ServiceType.PROXY)) {
            ProxyServer.getInstance().stop();
        } else if(new CloudHandler().getType().equals(ServiceType.SERVER)) {
            Bukkit.getServer().shutdown();
        }
        System.out.println("stop");
    }

    @Override
    public void write(ObjectOutputStream objectOutputStream) {

    }
}
