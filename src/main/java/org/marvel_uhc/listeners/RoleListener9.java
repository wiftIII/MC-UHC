package org.marvel_uhc.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityToggleGlideEvent;

public class  RoleListener9 extends RoleListener
{

    @EventHandler
    public void onEntityEvent(EntityToggleGlideEvent event)
    {
        if(event.getEntity() instanceof Player player)
        {
            if(!player.isOnGround()) {
                event.setCancelled(true);
            }
        }
    }
}
