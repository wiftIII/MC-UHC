package org.marvel_uhc.stones;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.UUID;

public class SoulStone extends Stone {

    private HashSet<UUID> imprisonedSouls = new HashSet<>();
    private boolean powerUsed = false;

    public SoulStone(ItemStack item) {
        super(item);
    }

    @Override
    public void onRightClick(Player player) {
        Player target = getTargetPlayer(player);
        if (powerUsed) {
            player.sendMessage("The power of the Soul Stone has already been used.");
            return;
        }

        // Imprison the target player's soul
        imprisonedSouls.add(target.getUniqueId());
        powerUsed = true;
        player.sendMessage(target.getName() + "'s soul imprisoned in the Soul Stone.");
        target.sendMessage("Your soul is imprisoned in the Soul Stone. You will be resurrected in the event of death.");
    }

    @Override
    public void onLeftClick(Player player) {
        player.sendMessage("§cNo left-click action for the Mind Stone.");
    }

    @Override
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        UUID playerUUID = player.getUniqueId();

        if (imprisonedSouls.contains(playerUUID)) {
            event.setCancelled(true);  // Cancel death event
            player.setHealth(player.getMaxHealth());  // Restoring player health
            // TODO : player.teleport(player.getWorld().getSpawnLocation());
            player.sendMessage("You've been resurrected by the Soul Stone !");
            imprisonedSouls.remove(playerUUID);  // Removing the player from imprisoned souls
        }
    }
}
