package org.marvel_uhc.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import org.marvel_uhc.MarvelUhc;
import org.marvel_uhc.PlayerData;

import java.util.logging.Level;

public class RoleListener implements Listener
{
    // Constructeur
    private MarvelUhc main;

    public RoleListener() {
        main = MarvelUhc.instance;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onRightClick (PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if(player.getItemInHand().isSimilar(main.items.roleItems.Item_IronMan_Buff)){
                //Lancer le menu de configuration
                player.setVelocity(new Vector(0,20,0));
                main.GetData(player).canGlide = true;
                main.GetData(player).takeFallDamage = false;
            }
        }
    }

    @EventHandler
    public void onMovement(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        PlayerData data = MarvelUhc.instance.GetData(player);
        if(MarvelUhc.version >=9 && event.getTo().getY() < event.getFrom().getY() && data.canGlide)
        {
            player.setGliding(true);
            data.canGlide = false;
        }
    }
}
