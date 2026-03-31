package org.marvel_uhc.stones;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class Stone {

    protected final ItemStack item;
    // Map pour les cooldowns (UUID du joueur -> Temps d'expiration)
    private final Map<UUID, Long> cooldowns = new HashMap<>();

    public Stone(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }

    // Événements à Override dans les classes enfants (pas besoin d'être abstract si une pierre n'utilise qu'un seul clic)
    public void onRightClick(Player player) {}
    public void onLeftClick(Player player) {}

    /**
     * Système de cooldown propre aux pierres (indépendant des rôles)
     */
    protected boolean checkAndApplyCooldown(Player player, int seconds) {
        UUID playerId = player.getUniqueId();
        long currentTime = System.currentTimeMillis();

        if (cooldowns.containsKey(playerId)) {
            long expireTime = cooldowns.get(playerId);
            if (currentTime < expireTime) {
                long timeLeft = (expireTime - currentTime) / 1000;
                player.sendMessage("§c[!] La pierre est en rechargement. Patientez " + timeLeft + "s.");
                return false;
            }
        }

        cooldowns.put(playerId, currentTime + (seconds * 1000L));
        return true;
    }

    /**
     * Récupère le joueur ciblé par le regard (Remplace les 30 lignes de maths !)
     */
    protected Player getTargetPlayer(Player player, int distance) {
        // Fonction native de la 1.21.1 pour faire du RayTracing parfait
        Entity target = player.getTargetEntity(distance);

        if (target instanceof Player) {
            return (Player) target;
        }
        return null;
    }

    /**
     * Permet de réinitialiser le cooldown d'un joueur (très utile pour le debug !)
     */
    public void resetCooldown(Player player) {
        cooldowns.remove(player.getUniqueId());
    }
}