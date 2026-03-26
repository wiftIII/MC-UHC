package org.marvel_uhc.stones;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.inventory.ItemStack;

public class TimeStone extends Stone {

    private static final long COOLDOWN_TIME = 10 * 60 * 1000; // 10 minutes

    public TimeStone(ItemStack item) {
        super(item);
    }

    @Override
    public void onRightClick(Player player) {
        if (isOnCooldown(player, COOLDOWN_TIME)) {
            return;
        }

        // Apply the Speed II effect for 1 minute
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 60, 1));
        player.sendMessage("§aYou used the Time Stone and got Speed II for 1 minute !");

        startCooldown(player, COOLDOWN_TIME);
    }

    @Override
    public void onLeftClick(Player player) {
        Player target = getTargetPlayer(player);
        if (target != null) {
            // Apply the Slowness effect for 1 minute
            target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 60, 1));
            player.sendMessage("§aYou inflicted Slowness on " + target.getName() + " for 1 minute using the Time Stone!");
        }
        else
            player.sendMessage("§cNo target found for the Time Stone!");

        startCooldown(player, COOLDOWN_TIME);
    }

    @Override
    public void onPlayerDeath(PlayerDeathEvent event) {
    }
}
