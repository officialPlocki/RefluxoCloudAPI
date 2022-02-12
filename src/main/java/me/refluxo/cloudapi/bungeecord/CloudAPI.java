package me.refluxo.cloudapi.bungeecord;

import me.refluxo.cloudapi.bungeecord.events.JoinEvent;
import me.refluxo.cloudapi.bungeecord.util.ServiceManager;
import me.refluxo.cloudapi.common.AuthHandler;
import me.refluxo.cloudapi.common.CloudHandler;
import me.refluxo.cloudapi.common.files.FileBuilder;
import me.refluxo.cloudapi.common.files.YamlConfiguration;
import me.refluxo.cloudapi.common.mysql.MySQLService;
import me.refluxo.cloudapi.common.packet.*;
import me.refluxo.networker.PacketClient;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.sql.SQLException;

public class CloudAPI extends Plugin {

    @Override
    public void onEnable() {
        CloudHandler.packetClient = new PacketClient();
        CloudHandler.packetClient.start(new CloudHandler().getSessionUUID(), new CloudHandler().getInstanceName(), new CloudHandler().getHost(), 6);
        CloudHandler.packetClient.getPacketHandler().registerPacket(StopServicePacket.class);
        CloudHandler.packetClient.getPacketHandler().registerPacket(ServiceDeletePacket.class);
        CloudHandler.packetClient.getPacketHandler().registerPacket(CustomServiceDeletePacket.class);
        CloudHandler.packetClient.getPacketHandler().registerPacket(CustomServiceCreatePacket.class);
        CloudHandler.packetClient.getPacketHandler().registerPacket(ServiceCreatePacket.class);
        FileBuilder fb = new FileBuilder("config/config.yml");
        YamlConfiguration yml = fb.getYaml();
        if(!fb.getFile().exists()) {
            yml.set("mysql.host", "0.0.0.0");
            yml.set("mysql.port", 3306);
            yml.set("mysql.database", "database");
            yml.set("mysql.user", "user");
            yml.set("mysql.password", "password");
            fb.save();
        }
        try {
            MySQLService.connect(yml.getString("mysql.host"), yml.getInt("mysql.port"), yml.getString("mysql.database"), yml.getString("mysql.user"), yml.getString("mysql.password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(!MySQLService.isConnected()) {
            onDisable();
        }
        AuthHandler.init();
        ProxyServer.getInstance().getPluginManager().registerListener(this, new JoinEvent());
        ServiceManager.init();
    }

    @Override
    public void onDisable() {
        MySQLService.disconnect();
        CloudHandler.packetClient.stop();
    }

}
