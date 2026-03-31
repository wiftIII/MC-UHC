package org.marvel_uhc.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.marvel_uhc.MarvelUhc;
import org.marvel_uhc.stones.Stone;

public class StoneListener implements Listener {

    // 1. Clic dans le vide ou sur un bloc (Longue distance)
    @EventHandler
    public void onPlayerUseStone(PlayerInteractEvent event) {
        // On évite que l'événement se lance deux fois (main principale + main secondaire)
        if (event.getHand() != EquipmentSlot.HAND) return;

        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        Stone stone = GetStone(item);
        if (stone == null) return;

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            stone.onRightClick(player);
        } else if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            stone.onLeftClick(player);
        }
    }

    // 2. Clic Droit direct sur une entité (Corps-à-corps)
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        Stone stone = GetStone(item);
        if (stone != null) {
            stone.onRightClick(player);
        }
    }

    // 3. Clic Gauche direct sur une entité (Corps-à-corps)
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            ItemStack item = player.getInventory().getItemInMainHand();

            Stone stone = GetStone(item);
            if (stone != null) {
                stone.onLeftClick(player);

                // Optionnel : On annule les dégâts de mêlée normaux (les coups de poing)
                // pour que la pierre ne fasse QUE son pouvoir magique.
                // event.setCancelled(true);
            }
        }
    }

    // Méthode de détection plus robuste basée sur le nom de l'item
    public Stone GetStone(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return null;

        MarvelUhc main = MarvelUhc.instance;
        if (main.items == null) return null;

        String itemName = item.getItemMeta().getDisplayName();

        if (itemName.equals(main.items.mindStone.getItem().getItemMeta().getDisplayName())) return main.items.mindStone;
        if (itemName.equals(main.items.powerStone.getItem().getItemMeta().getDisplayName())) return main.items.powerStone;
        if (itemName.equals(main.items.realityStone.getItem().getItemMeta().getDisplayName())) return main.items.realityStone;
        if (itemName.equals(main.items.soulStone.getItem().getItemMeta().getDisplayName())) return main.items.soulStone;
        if (itemName.equals(main.items.spaceStone.getItem().getItemMeta().getDisplayName())) return main.items.spaceStone;
        if (itemName.equals(main.items.timeStone.getItem().getItemMeta().getDisplayName())) return main.items.timeStone;

        return null;
    }
}