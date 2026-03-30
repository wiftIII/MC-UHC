package org.marvel_uhc.roles;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public abstract class Role {
    private final String name;
    private final Camp camp;
    private final String color; // Ex: "§c" pour le rouge
    protected List<ItemStack> kit;

    public Role(String name, Camp camp, String color) {
        this.name = name;
        this.camp = camp;
        this.color = color;
        this.kit = new ArrayList<>();
        setupKit();
    }

    // --- Getters ---
    public String getName() { return name; }
    public Camp getCamp() { return camp; }
    public String getColor() { return color; }
    public List<ItemStack> getKit() { return kit; }

    // --- Méthodes abstraites (Obligatoires pour chaque rôle) ---
    public abstract List<String> getDescription();
    protected abstract void setupKit();

    // --- Événements (Hooks) ---
    // Les rôles peuvent "Override" (écraser) ces méthodes uniquement s'ils en ont besoin.

    public void onGiveRole(Player player) {
        //suppression de tout les effets
        Iterator<PotionEffect> iterator = player.getActivePotionEffects().iterator();

        while (iterator.hasNext()) {
            PotionEffect effect = iterator.next();
            player.removePotionEffect(effect.getType());
        }
        // Par défaut, on donne juste le kit au joueur
        for (ItemStack item : kit) {
            player.getInventory().addItem(item);
        }
    }

    // Événements liés au temps
    public void onDayCycle(Player player) {}
    public void onNightCycle(Player player) {}

    // Événements liés au combat
    public void onKill(Player killer, Player victim) {}
    public void onDeath(Player player, Player killer) {}
    // Déclenché quand le joueur attaque un autre joueur
    public void onAttack(Player attacker, Player victim) {}
    // Déclenché quand le joueur subit des dégâts d'un autre joueur
    public void onDamageReceived(Player victim, Player attacker, double damage, org.bukkit.event.entity.EntityDamageByEntityEvent event) {}

    // Déclenché quand le joueur tire à l'arc
    public void onBowShoot(Player player, org.bukkit.entity.Arrow arrow) {}

    // Déclenché quand une de ses flèches touche le sol ou une entité
    public void onArrowHit(Player player, org.bukkit.entity.Arrow arrow) {}

    // Événement lié à l'utilisation des pouvoirs
    public void onRightClickItem(Player player, ItemStack item) {}
    public void onLeftClickItem(Player player, ItemStack item) {}

    // Déclenché quand le joueur fait un clic droit sur une entité (un autre joueur)
    public void onRightClickEntity(Player player, org.bukkit.entity.Entity clickedEntity, ItemStack item) {}

    // --- Système de Cooldowns ---
    private final Map<String, Long> cooldowns = new HashMap<>();

    /**
     * Vérifie si le pouvoir est prêt, et si oui, applique le cooldown.
     * @param player Le joueur qui utilise le pouvoir
     * @param powerName Le nom du pouvoir (pour le différencier s'il en a plusieurs)
     * @param seconds Le temps d'attente en secondes
     * @return true si le pouvoir peut être lancé, false s'il est en rechargement
     */
    protected boolean checkAndApplyCooldown(Player player, String powerName, int seconds) {
        long currentTime = System.currentTimeMillis();

        if (cooldowns.containsKey(powerName)) {
            long expireTime = cooldowns.get(powerName);
            if (currentTime < expireTime) {
                // Le cooldown n'est pas terminé
                long timeLeft = (expireTime - currentTime) / 1000;
                player.sendMessage("§c[!] §7Le pouvoir §e" + powerName + " §7est en rechargement. (Patientez §c" + timeLeft + "s§7)");
                return false;
            }
        }

        // Le pouvoir est prêt, on enregistre le nouveau temps d'attente
        cooldowns.put(powerName, currentTime + (seconds * 1000L));
        return true;
    }
}