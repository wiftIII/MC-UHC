package org.marvel_uhc.stones;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.UUID;

public class SoulStone extends Stone {

    // On stocke l'UUID du joueur sauvé
    public UUID savedPlayerId = null;
    private boolean isUsed = false;

    public SoulStone(ItemStack item) {
        super(item);
    }

    @Override
    public void onRightClick(Player player) {
        if (isUsed) {
            player.sendMessage("§6[Pierre de l'Âme] §cLe pouvoir de cette pierre a déjà été consommé.");
            return;
        }

        Player target = getTargetPlayer(player, 20);

        if (target != null) {
            // On sauvegarde l'âme
            savedPlayerId = target.getUniqueId();
            isUsed = true; // Pouvoir consommé définitivement

            player.sendMessage("§6[Pierre de l'Âme] §aVous avez emprisonné et protégé l'âme de " + target.getName() + " !");
        } else {
            player.sendMessage("§6[Pierre de l'Âme] §cAucun joueur ciblé à portée.");
        }
    }
}