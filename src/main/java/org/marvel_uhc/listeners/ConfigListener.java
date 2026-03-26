package org.marvel_uhc.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.marvel_uhc.MarvelUhc;

public class ConfigListener implements Listener
{
    // Constructeur
    private MarvelUhc main;

    public ConfigListener() {
        main = MarvelUhc.instance;
    }

    // Accéder au menu de configuration
    @EventHandler(priority = EventPriority.HIGH)
    public void onRightClick (PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if(player.getItemInHand().isSimilar(main.items.hostItems.Item_Config))
            {
                //Lancer le menu de configuration
                player.openInventory(main.items.hostItems.displayUi(player));
            }
        }
    }

    // Différents clics possibles dans le menu de configuration
    @EventHandler
    public void onInventoryClick (InventoryClickEvent event)
    {
        main.items.hostItems.Ui.handleEvent(event);
    }

    // Empécher le host de drop son item de configuration
    @EventHandler
    public void onItemDrop (PlayerDropItemEvent event) {
        if(event.getItemDrop().getItemStack().isSimilar(main.items.hostItems.Item_Config))
            event.setCancelled(true);
    }
}
