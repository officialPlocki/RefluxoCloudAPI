package me.refluxo.cloudapi.common.service;

import java.util.List;

public interface ICustomService {

    boolean isStatic();

    String getInstanceUUID();

    List<ITemplate> getTemplates();

    ITemplate getTemplate();

    int getRam();

    String getOwnerUUID();

    int getPort();

}
