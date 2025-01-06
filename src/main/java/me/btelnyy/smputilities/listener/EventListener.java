package me.btelnyy.smputilities.listener;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import me.btelnyy.smputilities.SmpUtilities;
import me.btelnyy.smputilities.constants.ConfigData;
import me.btelnyy.smputilities.service.Utils;
import me.btelnyy.smputilities.service.file_manager.Configuration;
import me.btelnyy.smputilities.service.file_manager.FileID;

public class EventListener implements Listener {
    static final Configuration language = SmpUtilities.getInstance().getFileManager().getFile(FileID.LANGUAGE).getConfiguration();

    private static HashMap<UUID, Boolean> hasInvOpen = new HashMap<UUID, Boolean>();

    @EventHandler
    public void onPlayerOpenInv(InventoryOpenEvent event)
    {
        hasInvOpen.put(event.getPlayer().getUniqueId(), true);
    }

    @EventHandler
    public void onPlayerCloseInv(InventoryCloseEvent event)
    {
        hasInvOpen.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        if(event.getEntity().getKiller() == null){
            return;
        }
        Player killer = event.getEntity().getKiller();
        Utils.sendActionBarMessage(killer, language.getString("killed_player").replace("{player}", event.getEntity().getName()));
    }

    @EventHandler
    public void onPlayerDropEvent(PlayerDropItemEvent event)
    {
        if(!ConfigData.getInstance().placeEggWhenDropped)
        {
            return;
        }
        if(event.getItemDrop().getItemStack().getType() != Material.DRAGON_EGG)
        {
            return;
        }
        for (ItemStack item : event.getPlayer().getInventory().getContents())
        {
            if(item == null)
            {
                continue;
            }
            if(item.getType() == Material.DRAGON_EGG)
            {
                event.getPlayer().getInventory().remove(item);
            }
        }
        Item droppedItem = event.getItemDrop();
        droppedItem.remove();
        //event.setCancelled(true);
        Location logoffPlace = event.getPlayer().getLocation();
        logoffPlace.getBlock().setType(Material.DRAGON_EGG);
    }

    @EventHandler 
    public void onItemPickupEvent(InventoryPickupItemEvent event)
    {
        if(ConfigData.getInstance().allowEggInContainers)
        {
            return;
        }
        if(event.getInventory().getType() == InventoryType.PLAYER)
        {
            PlayerInventory playerInv = (PlayerInventory)event.getInventory();
            if(hasInvOpen.containsKey(playerInv.getHolder().getUniqueId()))
            {
                event.setCancelled(true);
            }
            return;
        }
        if(event.getItem().getItemStack().getType() != Material.DRAGON_EGG)
        {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event)
    {
        if(!ConfigData.getInstance().placeEggOnDisconnectingPlayer)
        {
            return;
        }
        boolean hasDragonEgg = false;
        ItemStack egg = null;
        for (ItemStack item : event.getPlayer().getInventory().getContents())
        {
            if(item == null)
            {
                continue;
            }
            if(item.getType() == Material.DRAGON_EGG)
            {
                hasDragonEgg = true;
                egg = item;
            }
        }
        if(!hasDragonEgg)
        {
            return;
        }
        event.getPlayer().getInventory().remove(egg);
        Location logoffPlace = event.getPlayer().getLocation();
        logoffPlace.getBlock().setType(Material.DRAGON_EGG);
    }

    @EventHandler
    public void onPlayerKicked(PlayerKickEvent event)
    {
        if(!ConfigData.getInstance().placeEggOnDisconnectingPlayer)
        {
            return;
        }
        boolean hasDragonEgg = false;
        ItemStack egg = null;
        for (ItemStack item : event.getPlayer().getInventory().getContents())
        {
            if(item == null)
            {
                continue;
            }
            if(item.getType() == Material.DRAGON_EGG)
            {
                hasDragonEgg = true;
                egg = item;
            }
        }
        if(!hasDragonEgg)
        {
            return;
        }
        //Remove the egg.
        event.getPlayer().getInventory().remove(egg);
        Location logoffPlace = event.getPlayer().getLocation();
        logoffPlace.getBlock().setType(Material.DRAGON_EGG);
    }

    @EventHandler
    public void onPlayerOpenInventory(InventoryOpenEvent event)
    {
        if(ConfigData.getInstance().allowEggInContainers)
        {
            return;
        }
        if(event.getInventory().getType() == InventoryType.PLAYER)
        {
            return;
        }
        boolean hasDragonEgg = false;
        for (ItemStack item : event.getPlayer().getInventory().getContents())
        {
            if(item == null)
            {
                continue;
            }
            if(item.getType() == Material.DRAGON_EGG)
            {
                hasDragonEgg = true;
            }
        }
        if(!hasDragonEgg)
        {
            return;
        }
        if(event.getPlayer().getGameMode() == GameMode.CREATIVE || event.getPlayer().getGameMode() == GameMode.SPECTATOR)
        {
            return;
        }
        event.setCancelled(true);
    }
}
