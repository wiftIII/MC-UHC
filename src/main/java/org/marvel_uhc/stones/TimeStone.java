package org.marvel_uhc.stones;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TimeStone extends Stone {

    private final int COOLDOWN = 600; // 10 minutes en secondes
    private final int DURATION = 60 * 20; // 1 minute en ticks

    public TimeStone(ItemStack item) {
        super(item);
    }

    // Clic Droit : Speed 2 pour l'utilisateur
    @Override
    public void onRightClick(Player player) {
        if (checkAndApplyCooldown(player, COOLDOWN)) {
            // Speed 2 = amplificateur 1
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, DURATION, 1, false, false));
            player.sendMessage("§a[Pierre du Temps] §7Vous accélérez votre propre ligne temporelle (Vitesse 2) !");
        }
    }

    // Clic Gauche : Slowness sur un joueur ciblé
    @Override
    public void onLeftClick(Player player) {
        Player target = getTargetPlayer(player, 20); // Portée de 20 blocs

        if (target != null) {
            if (checkAndApplyCooldown(player, COOLDOWN)) {
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, DURATION, 0, false, false));
                player.sendMessage("§a[Pierre du Temps] §7Vous avez ralenti la ligne temporelle de §a" + target.getName() + " §7!");
                target.sendMessage("§a[!] Le temps ralentit autour de vous...");
            }
        } else {
            player.sendMessage("§a[Pierre du Temps] §cAucun joueur ciblé à portée.");
        }
    }
}