package org.marvel_uhc.stones;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.util.Vector;
import org.bukkit.inventory.ItemStack;

public class SpaceStone extends Stone {

    private static final long COOLDOWN_TIME = 3 * 60 * 1000; // 3 minutes

    public SpaceStone(ItemStack item) {
        super(item);
    }

    @Override
    public void onRightClick(Player player) {
        if (isOnCooldown(player, COOLDOWN_TIME))
            return;

        // Teleport 50 blocks
        Vector direction = player.getLocation().getDirection();
        player.teleport(player.getLocation().add(direction.multiply(50)));
        player.sendMessage("§bYou used the Space Stone and teleported 50 blocks forward !");

        startCooldown(player, COOLDOWN_TIME);
    }

    @Override
    public void onLeftClick(Player player) {
        player.sendMessage("§cNo left-click action for the Space Stone.");
    }

    @Override
    public void onPlayerDeath(PlayerDeathEvent event) {
    }
}