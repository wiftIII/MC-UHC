package org.marvel_uhc.stones;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PowerStone extends Stone {

    private final int COOLDOWN = 300; // 5 minutes en secondes

    public PowerStone(ItemStack item) {
        super(item);
    }

    @Override
    public void onRightClick(Player player) {
        Player target = getTargetPlayer(player, 20);

        if (target != null) {
            if (checkAndApplyCooldown(player, COOLDOWN)) {
                // 8.0 dégâts = 4 cœurs
                target.damage(8.0, player);

                player.sendMessage("§5[Pierre du Pouvoir] §7Vous avez foudroyé " + target.getName() + " !");
                target.sendMessage("§5[!] Une force écrasante s'abat sur vous !");
            }
        } else {
            player.sendMessage("§5[Pierre du Pouvoir] §cAucun joueur ciblé à portée.");
        }
    }
}