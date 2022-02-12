package me.refluxo.cloudapi.common.packet;

import me.refluxo.networker.packet.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ServiceDeletePacket implements Packet {

    private final String serviceName;

    public ServiceDeletePacket(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public void read(ObjectInputStream objectInputStream) {

    }

    @Override
    public void write(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeUTF(serviceName);
    }
}

