package org.marvel_uhc.stones;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class RealityStone extends Stone {

    private static final long INVISIBILITY_DURATION = 10 * 60 * 20; // 10 minutes
    private boolean powerUsed = false;

    public RealityStone(ItemStack item) {
        super(item);
    }

    @Override
    public void onRightClick(Player player) {
        if (powerUsed) {
            player.sendMessage("The power of the Reality Stone has already been used.");
            return;
        }

        // Apply the invisibility effect for 10 minutes
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, (int) INVISIBILITY_DURATION, 0));
        player.sendMessage("You are now invisible for 10 minutes thanks to the Reality Stone.");
        powerUsed = true;

        // Define a task to notify when the effect is complete
        new BukkitRunnable() {
            @Override
            public void run() {
                player.sendMessage("Your invisibility thanks to the Reality Stone has expired.");
            }
        }.runTaskLater(Bukkit.getPluginManager().getPlugin("MarvelUhc"), INVISIBILITY_DURATION);
    }

    @Override
    public void onLeftClick(Player player) {
        player.sendMessage("§cNo left-click action for the Mind Stone.");
    }

    @Override
    public void onPlayerDeath(PlayerDeathEvent event) {
    }

}
