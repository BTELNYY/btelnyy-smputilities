package me.btelnyy.smputilities.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.btelnyy.smputilities.SmpUtilities;
import me.btelnyy.smputilities.service.Utils;
import me.btelnyy.smputilities.service.file_manager.Configuration;
import me.btelnyy.smputilities.service.file_manager.FileID;

public class EventListener implements Listener {
    static final Configuration language = SmpUtilities.getInstance().getFileManager().getFile(FileID.LANGUAGE).getConfiguration();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        Utils.sendActionBarMessage(event.getEntity().getKiller(), language.getString("killed_player").replace("{player}", event.getEntity().getName()));
    }

}
