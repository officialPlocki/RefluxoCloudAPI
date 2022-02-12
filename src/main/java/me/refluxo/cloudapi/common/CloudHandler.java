package me.refluxo.cloudapi.common;

import me.refluxo.cloudapi.common.files.FileBuilder;
import me.refluxo.cloudapi.common.mysql.MySQLService;
import me.refluxo.cloudapi.common.packet.CustomServiceCreatePacket;
import me.refluxo.cloudapi.common.packet.CustomServiceDeletePacket;
import me.refluxo.cloudapi.common.packet.ServiceCreatePacket;
import me.refluxo.cloudapi.common.packet.ServiceDeletePacket;
import me.refluxo.cloudapi.common.service.ICustomService;
import me.refluxo.cloudapi.common.service.IService;
import me.refluxo.cloudapi.common.service.ServiceType;
import me.refluxo.networker.PacketClient;

public class CloudHandler {

    public static PacketClient packetClient;

    public String getInstanceUUID() {
        FileBuilder builder = new FileBuilder("config/config.yml");
        return builder.getYaml().getString("info.uuid");
    }

    public String getInstanceName() {
        FileBuilder builder = new FileBuilder("config/config.yml");
        return builder.getYaml().getString("info.name");
    }

    public void setInstanceOnline(boolean online) {
        MySQLService service = new MySQLService();
        service.executeUpdate("UPDATE serviceStatus SET isOnline = " + online + " WHERE serviceUUID = '" + getInstanceUUID() + "';");
    }

    public String getSessionUUID() {
        FileBuilder builder = new FileBuilder("config/config.yml");
        return builder.getYaml().getString("info.sessionUUID");
    }

    public ServiceType getType() {
        FileBuilder builder = new FileBuilder("config/config.yml");
        return ServiceType.valueOf(builder.getYaml().getString("info.type"));
    }

    public String getHost() {
        FileBuilder builder = new FileBuilder("config/config.yml");
        return builder.getYaml().getString("info.host");
    }

    public void deleteCustomService(ICustomService service) {
        packetClient.sendPacket(new CustomServiceDeletePacket(service.getInstanceUUID()));
    }

    public void createCustomService(ICustomService service) {
        packetClient.sendPacket(new CustomServiceCreatePacket(service.isStatic(), service.getTemplates(), service.getTemplate(), service.getRam(), service.getOwnerUUID()));
    }

    public void deleteService(IService service) {
        packetClient.sendPacket(new ServiceDeletePacket( service.getInstanceGroup() + "@" + service.getTaskID()));
    }

    public void createService(IService service) {
        packetClient.sendPacket(new ServiceCreatePacket(service.getInstanceGroup(), service.getTemplate()));
    }

}
