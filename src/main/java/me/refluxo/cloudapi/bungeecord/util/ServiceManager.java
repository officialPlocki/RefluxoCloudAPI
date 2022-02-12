package me.refluxo.cloudapi.bungeecord.util;

import me.refluxo.cloudapi.common.mysql.MySQLService;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ServiceManager {

    public static void init() {
        MySQLService mysql = new MySQLService();
        ProxyServer.getInstance().getScheduler().schedule(ProxyServer.getInstance().getPluginManager().getPlugin("CloudService"), () -> {
            new Thread(() -> {
                ResultSet rs = mysql.getResult("SELECT * FROM bungeeList;");
                try {
                    List<IMySQLInfo> mySQLInfos = new ArrayList<>();
                    List<ServerInfo> currentServers = new ArrayList<>();
                    while (rs.next()) {
                        String name = rs.getString("serviceName");
                        String host = rs.getString("serviceIP");
                        int port = rs.getInt("servicePort");
                        mySQLInfos.add(new IMySQLInfo() {
                            @Override
                            public String getServiceName() {
                                return name;
                            }

                            @Override
                            public String getServiceHost() {
                                return host;
                            }

                            @Override
                            public int getServicePort() {
                                return port;
                            }
                        });
                    }
                    mySQLInfos.forEach(info -> {
                        currentServers.add(ProxyServer.getInstance().constructServerInfo(info.getServiceName(), (SocketAddress) new InetSocketAddress(info.getServiceHost(), info.getServicePort()), "", false));
                        ProxyServer.getInstance().getPlayers().forEach(proxiedPlayer -> {
                            if(proxiedPlayer.hasPermission("cloud.notify")) {
                                proxiedPlayer.sendMessage(TextComponent.fromLegacyText("§b§lRefluxo§f§lCloud §8» §7Der Service §e" + info.getServiceName() + " §7ist nun §aonline§7."));
                            }
                        });
                    });
                    ProxyServer.getInstance().getServers().clear();
                    currentServers.forEach(serverInfo -> {
                        ProxyServer.getInstance().getServers().put(serverInfo.getName(), serverInfo);
                    });
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }).start();
        }, 0, 500, TimeUnit.MILLISECONDS);
    }

}
