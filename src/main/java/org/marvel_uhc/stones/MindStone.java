package org.marvel_uhc.stones;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.marvel_uhc.MarvelUhc;
import org.marvel_uhc.PlayerData;

public class MindStone extends Stone {

    public MindStone(ItemStack item) {
        super(item);
    }

    @Override
    public void onRightClick(Player player) {
        Player target = getTargetPlayer(player, 20);

        if (target != null) {
            // Petit anti-spam de 3 secondes
            if (checkAndApplyCooldown(player, 3)) {
                PlayerData targetData = MarvelUhc.instance.GetData(target);

                if (targetData != null && targetData.role != null) {
                    player.sendMessage("§e[Pierre de l'Esprit] §7Vous lisez dans l'esprit de " + target.getName() + "...");
                    player.sendMessage("§e> Son camp est : " + targetData.role.getCamp().getDisplayName());
                } else {
                    player.sendMessage("§e[Pierre de l'Esprit] §cCe joueur n'a pas de rôle mentalement identifiable.");
                }
            }
        } else {
            player.sendMessage("§e[Pierre de l'Esprit] §cAucun esprit ciblé à portée.");
        }
    }
}