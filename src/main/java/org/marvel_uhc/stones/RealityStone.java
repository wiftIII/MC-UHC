package org.marvel_uhc.stones;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RealityStone extends Stone {

    private final int COOLDOWN = 600; // 10 minutes en secondes
    private final int DURATION = 10 * 60 * 20; // 10 minutes en ticks (20 ticks = 1 seconde)

    public RealityStone(ItemStack item) {
        super(item);
    }

    @Override
    public void onRightClick(Player player) {

        // On vérifie le cooldown propre à la pierre
        if (checkAndApplyCooldown(player, COOLDOWN)) {

            // Invisibilité (sans particules : false, false)
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, DURATION, 0, false, false));

            // Absorption (4 cœurs = niveau 1, car niveau 0 = 2 cœurs. Sans particules)
            player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, DURATION, 1, false, false));

            player.sendMessage("§c[Pierre de la Réalité] §aLa réalité s'altère... Vous êtes invisible et renforcé pour 10 minutes.");
        }
    }
}