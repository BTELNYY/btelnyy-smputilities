package me.btelnyy.smputilities.constants;

import me.btelnyy.smputilities.service.file_manager.Configuration;

public class ConfigData {
    private static ConfigData instance;

    public boolean pluginEnabled = true;
    public boolean showKillsInActionBar = true;
    public boolean allowEggInContainers = false;
    public boolean placeEggOnDisconnectingPlayer = true;
    public boolean placeEggWhenDropped = true;

    public void load(Configuration config) {
        instance = this;
        pluginEnabled = config.getBoolean("plugin_enabled");
        showKillsInActionBar = config.getBoolean("show_kills_in_actionbar");
        allowEggInContainers = config.getBoolean("allow_dragon_egg_in_containers");
        placeEggOnDisconnectingPlayer = config.getBoolean("place_dragon_egg_where_player_left");
        placeEggWhenDropped = config.getBoolean("place_dragon_egg_when_dropped");
    }
    public static ConfigData getInstance(){
        return instance;
    }
}
