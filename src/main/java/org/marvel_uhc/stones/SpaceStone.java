package org.marvel_uhc.stones;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.marvel_uhc.MarvelUhc;
import org.marvel_uhc.PlayerData;

public class SpaceStone extends Stone {

    private final int COOLDOWN = 180; // 3 minutes en secondes
    private final int DISTANCE = 50;  // 50 blocs de portée

    public SpaceStone(ItemStack item) {
        super(item);
    }

    @Override
    public void onRightClick(Player player) {

        // On vérifie le cooldown propre à la pierre
        if (checkAndApplyCooldown(player, COOLDOWN)) {

            // On cherche le bloc regardé par le joueur (jusqu'à 50 blocs maximum)
            Block targetBlock = player.getTargetBlockExact(DISTANCE);
            Location targetLocation;

            if (targetBlock != null) {
                // S'il a regardé un bloc solide, on le téléporte juste au-dessus
                targetLocation = targetBlock.getLocation().add(0.5, 1.0, 0.5);
            } else {
                // S'il a regardé dans le vide (le ciel par exemple), on le téléporte 50 blocs plus loin
                targetLocation = player.getLocation().add(player.getLocation().getDirection().multiply(DISTANCE));
            }

            // On conserve l'orientation de sa tête (pour qu'il regarde toujours dans la même direction après la TP)
            targetLocation.setYaw(player.getLocation().getYaw());
            targetLocation.setPitch(player.getLocation().getPitch());

            // On téléporte le joueur !
            player.teleport(targetLocation);

            // Petit effet sonore stylé (le son de l'Enderman)
            player.getWorld().playSound(targetLocation, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);

            // Sécurité absolue : on lui retire les dégâts de chute pour son atterrissage
            PlayerData data = MarvelUhc.instance.GetData(player);
            if (data != null) {
                data.takeFallDamage = false;
            }

            player.sendMessage("§b[Pierre de l'Espace] §fTéléportation réussie !");
        }
    }
}