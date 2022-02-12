package me.refluxo.cloudapi.spigot;

import me.refluxo.cloudapi.common.AuthHandler;
import me.refluxo.cloudapi.common.CloudHandler;
import me.refluxo.cloudapi.common.files.FileBuilder;
import me.refluxo.cloudapi.common.files.YamlConfiguration;
import me.refluxo.cloudapi.common.mysql.MySQLService;
import me.refluxo.cloudapi.common.packet.*;
import me.refluxo.cloudapi.spigot.commands.CloudCommand;
import me.refluxo.cloudapi.spigot.events.JoinEvent;
import me.refluxo.networker.PacketClient;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Objects;

public class CloudAPI extends JavaPlugin {

    @Override
    public void onEnable() {
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
        CloudHandler.packetClient = new PacketClient();
        CloudHandler.packetClient.start(new CloudHandler().getSessionUUID(), new CloudHandler().getInstanceName(), new CloudHandler().getHost(), 6);
        CloudHandler.packetClient.getPacketHandler().registerPacket(StopServicePacket.class);
        CloudHandler.packetClient.getPacketHandler().registerPacket(ServiceDeletePacket.class);
        CloudHandler.packetClient.getPacketHandler().registerPacket(CustomServiceDeletePacket.class);
        CloudHandler.packetClient.getPacketHandler().registerPacket(CustomServiceCreatePacket.class);
        CloudHandler.packetClient.getPacketHandler().registerPacket(ServiceCreatePacket.class);
        new CloudHandler().setInstanceOnline(true);
        if(!MySQLService.isConnected()) {
            Bukkit.getPluginManager().disablePlugin(this);
        }
        AuthHandler.init();
        Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);
        Objects.requireNonNull(getCommand("cloud")).setExecutor(new CloudCommand());
        Objects.requireNonNull(getCommand("refluxocloud")).setExecutor(new CloudCommand());
        Objects.requireNonNull(getCommand("rcloud")).setExecutor(new CloudCommand());
        Objects.requireNonNull(getCommand("cv")).setExecutor(new CloudCommand());
        Objects.requireNonNull(getCommand("cloudversion")).setExecutor(new CloudCommand());
    }

    @Override
    public void onDisable() {
        new CloudHandler().setInstanceOnline(false);
        MySQLService.disconnect();
        CloudHandler.packetClient.stop();
    }

}
