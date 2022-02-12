package me.refluxo.cloudapi.common.files;

import java.util.Map;

public interface ConfigurationSerializable {

    Map<String, Object> serialize();
}