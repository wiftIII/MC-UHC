package org.marvel_uhc.stones;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PowerStone extends Stone {

    private static final long COOLDOWN_TIME = 3 * 60 * 1000; // 3 minutes

    public PowerStone(ItemStack item) {
        super(item);
    }

    @Override
    public void onRightClick(Player player) {
        Player target = getTargetPlayer(player);

        if (isOnCooldown(player, COOLDOWN_TIME)) {
            return;
        }

        // Inflict 4 hearts of damage on the targeted player
        target.damage(8.0); // 4 hearts = 8 hit points
        player.sendMessage("You have inflicted 4❤ damage to " + target.getName());

        startCooldown(player, COOLDOWN_TIME);
    }

    @Override
    public void onLeftClick(Player player) {
        player.sendMessage("§cNo left-click action for the Mind Stone.");
    }

    @Override
    public void onPlayerDeath(PlayerDeathEvent event) {
    }
}
