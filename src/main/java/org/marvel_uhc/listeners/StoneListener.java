package org.marvel_uhc.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.marvel_uhc.MarvelUhc;
import org.marvel_uhc.stones.Stone;

public class StoneListener implements Listener
{
    @EventHandler
    public void onPlayerUseStone(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null) return;
        Stone stone = GetStone(item);
        if (stone == null) return;

        switch (event.getAction()) {
            case RIGHT_CLICK_BLOCK:
            case RIGHT_CLICK_AIR:
                stone.onRightClick(player);
                break;
            case LEFT_CLICK_BLOCK:
            case LEFT_CLICK_AIR:
                stone.onLeftClick(player);
                break;
        }
    }

    public Stone GetStone(ItemStack item)
    {
        MarvelUhc main = MarvelUhc.instance;
        if (item.isSimilar(main.items.mindStone.getItem())) return main.items.mindStone;
        if (item.isSimilar(main.items.powerStone.getItem())) return main.items.powerStone;
        if (item.isSimilar(main.items.realityStone.getItem())) return main.items.realityStone;
        if (item.isSimilar(main.items.soulStone.getItem())) return main.items.soulStone;
        if (item.isSimilar(main.items.spaceStone.getItem())) return main.items.spaceStone;
        if (item.isSimilar(main.items.timeStone.getItem())) return main.items.timeStone;

        return null;
    }
}
