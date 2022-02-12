package me.refluxo.cloudapi.common.service;

public interface IService {

    String getInstanceUUID();

    int getTaskID();

    IGroup getInstanceGroup();

    ITemplate getTemplate();

    int getPort();

}
