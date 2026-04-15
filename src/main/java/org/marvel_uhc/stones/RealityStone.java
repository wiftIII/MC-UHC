package org.marvel_uhc.stones;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class RealityStone extends Stone {

    private final int COOLDOWN = 600; // 10 minutes en secondes
    private final int DURATION = 10 * 60 * 20; // 10 minutes en ticks

    // Liste pour mémoriser qui est actuellement invisible grâce à la pierre
    private final Set<UUID> invisibleUsers = new HashSet<>();

    public RealityStone(ItemStack item) {
        super(item);
    }

    @Override
    public void onRightClick(Player player) {
        if (checkAndApplyCooldown(player, COOLDOWN)) {

            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, DURATION, 0, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, DURATION, 1, false, false));

            // On ajoute le joueur à la liste des invisibles
            invisibleUsers.add(player.getUniqueId());

            player.sendMessage("§c[Pierre de la Réalité] §aLa réalité s'altère... Vous êtes invisible et renforcé pour 10 minutes.");
        }
    }

    /**
     * Méthode appelée quand le joueur donne ou reçoit un coup
     */
    public void breakInvisibility(Player player) {
        if (invisibleUsers.contains(player.getUniqueId())) {
            // On lui retire uniquement l'invisibilité
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
            invisibleUsers.remove(player.getUniqueId());

            player.sendMessage("§c[Pierre de la Réalité] §7Votre concentration est brisée par le combat, vous redevenez visible !");
        }
    }
}