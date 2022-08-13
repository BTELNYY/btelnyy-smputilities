package me.btelnyy.smputilities.listener;

import org.bukkit.entity.Player;
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
        if(event.getEntity().getKiller() == null){
            return;
        }
        Player killer = event.getEntity().getKiller();
        Utils.sendActionBarMessage(killer, language.getString("killed_player").replace("{player}", event.getEntity().getName()));
    }

}
