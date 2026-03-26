package org.marvel_uhc.stones;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class Stone {

    // Map pour les cooldowns
    protected Map<UUID, Long> cooldowns = new HashMap<>();
    protected ItemStack item;

    public Stone(ItemStack item) {
        this.item = item;
    }

    public abstract void onRightClick(Player player);

    public abstract void onLeftClick(Player player);

    public abstract void onPlayerDeath(PlayerDeathEvent event);

    public boolean isOnCooldown(Player player, long cooldownTime) {
        UUID playerId = player.getUniqueId();
        if (cooldowns.containsKey(playerId) && cooldowns.get(playerId) > System.currentTimeMillis()) {
            long timeLeft = (cooldowns.get(playerId) - System.currentTimeMillis()) / 1000;
            player.sendMessage("§cThis stone is on cooldown! Time left: " + timeLeft + " seconds.");
            return true;
        }
        return false;
    }

    public void startCooldown(Player player, long cooldownTime) {
        UUID playerId = player.getUniqueId();
        cooldowns.put(playerId, System.currentTimeMillis() + cooldownTime);
    }

    public ItemStack getItem() {
        return item;
    }

    // Targeted player retrieval function (ray tracing ?)
    protected Player getTargetPlayer(Player player) {
        double maxDistance = 20;

        // Retrieve entities in the player's line of sight
        List<Entity> nearbyEntities = player.getNearbyEntities(maxDistance, maxDistance, maxDistance);

        Vector playerDirection = player.getLocation().getDirection();
        Vector playerPosition = player.getLocation().toVector();

        Player targetPlayer = null;
        double closestDistance = maxDistance;

        // Browse nearby entities
        for (Entity entity : nearbyEntities) {
            if (entity instanceof Player potentialTarget) {
                Vector targetPosition = potentialTarget.getLocation().toVector();

                //Vector between player and potential player
                Vector directionToTarget = targetPosition.subtract(playerPosition).normalize();

                // Check whether the entity is in the line of sight (via the angle between vectors)
                if (playerDirection.dot(directionToTarget) > 0.99) {
                    // Distance to potential player
                    double distance = player.getLocation().distance(potentialTarget.getLocation());

                    // If it's the nearest entity
                    if (distance < closestDistance) {
                        closestDistance = distance;
                        targetPlayer = potentialTarget;
                    }
                }
            }
        }

        return targetPlayer;
    }
}
