package org.marvel_uhc.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.marvel_uhc.MarvelUhc;
import org.marvel_uhc.PlayerData;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.entity.Arrow;

public class RoleListener implements Listener {

    // 1. Le "Facteur" pour les pouvoirs (Clic Gauche ET Droit)
    @EventHandler(priority = EventPriority.HIGH)
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();

        if (item != null) {
            PlayerData data = MarvelUhc.instance.GetData(player);
            if (data != null && data.role != null) {

                // Clic Droit
                if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                    data.role.onRightClickItem(player, item);
                }
                // Clic Gauche
                else if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
                    data.role.onLeftClickItem(player, item);
                }
            }
        }
    }

    // 2. Gestion du planage (Gliding) pour des pouvoirs comme Iron Man
    @EventHandler
    public void onMovement(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        PlayerData data = MarvelUhc.instance.GetData(player);

        // Si le joueur est en train de tomber et qu'il est autorisé à planer
        if (data != null && data.canGlide && event.getTo().getY() < event.getFrom().getY()) {
            player.setGliding(true);
            data.canGlide = false; // On désactive pour ne pas spammer
        }
    }

    // 3. Empêcher le planage si on touche le sol (ex-RoleListener9)
    @EventHandler
    public void onEntityToggleGlide(EntityToggleGlideEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (!player.isOnGround() && !event.isGliding()) {
                // Permet de forcer l'arrêt du planage ou le gérer custom
                // (Garde cette logique si tu l'avais mise pour éviter les bugs visuels Elytra)
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        // On évite que l'événement se déclenche deux fois (une fois pour chaque main)
        if (event.getHand() != EquipmentSlot.HAND) return;

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item != null && item.getType() != org.bukkit.Material.AIR) {
            PlayerData data = MarvelUhc.instance.GetData(player);
            if (data != null && data.role != null) {
                // On transmet l'info au rôle
                data.role.onRightClickEntity(player, event.getRightClicked(), item);
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBowShoot(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player player && event.getProjectile() instanceof Arrow arrow) {
            PlayerData data = MarvelUhc.instance.GetData(player);
            if (data != null && data.role != null) {
                data.role.onBowShoot(player, arrow);
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow arrow && arrow.getShooter() instanceof Player player) {
            PlayerData data = MarvelUhc.instance.GetData(player);
            if (data != null && data.role != null) {
                data.role.onArrowHit(player, arrow);
            }
        }
    }
}