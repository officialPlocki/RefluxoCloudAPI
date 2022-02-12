package me.refluxo.cloudapi.common.packet;

import me.refluxo.cloudapi.common.service.ITemplate;
import me.refluxo.networker.packet.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class CustomServiceCreatePacket implements Packet {

    private final boolean isStatic;
    private final List<ITemplate> templates;
    private final ITemplate template;
    private final int ram;
    private final String owner;

    public CustomServiceCreatePacket(boolean isStatic, List<ITemplate> templates, ITemplate template, int ram, String ownerUUID) {
        this.isStatic = isStatic;
        this.templates = templates;
        this.template = template;
        this.ram = ram;
        this.owner = ownerUUID;
    }

    @Override
    public void read(ObjectInputStream objectInputStream) {

    }

    @Override
    public void write(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeBoolean(isStatic);
        String ts;
        List<String> tn = new ArrayList<>();
        templates.forEach(t -> tn.add(t.getTemplateName()));
        ts = String.join(",", tn);
        objectOutputStream.writeUTF(ts);
        objectOutputStream.writeUTF(template.getTemplateName());
        objectOutputStream.writeInt(ram);
        objectOutputStream.writeUTF(owner);
    }
}
