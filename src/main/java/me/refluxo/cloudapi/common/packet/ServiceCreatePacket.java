package me.refluxo.cloudapi.common.packet;

import me.refluxo.cloudapi.common.service.IGroup;
import me.refluxo.cloudapi.common.service.ITemplate;
import me.refluxo.networker.packet.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ServiceCreatePacket implements Packet {

    private final IGroup group;
    private final ITemplate template;

    public ServiceCreatePacket(IGroup group, ITemplate template) {
        this.group = group;
        this.template = template;
    }

    @Override
    public void read(ObjectInputStream objectInputStream) {

    }

    @Override
    public void write(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeUTF(group.getGroupName());
        objectOutputStream.writeUTF(template.getTemplateName());
    }
}
