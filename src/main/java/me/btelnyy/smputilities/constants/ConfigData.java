package me.btelnyy.smputilities.constants;

import me.btelnyy.smputilities.service.file_manager.Configuration;

public class ConfigData {
    private static ConfigData instance;
    public boolean pluginEnabled = true;

    public void load(Configuration config) {
        instance = this;
        pluginEnabled = config.getBoolean("plugin_enabled");
    }
    public static ConfigData getInstance(){
        return instance;
    }
}
