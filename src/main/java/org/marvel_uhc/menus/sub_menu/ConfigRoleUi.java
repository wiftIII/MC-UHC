package org.marvel_uhc.menus.sub_menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.marvel_uhc.MarvelUhc;
import org.marvel_uhc.menus.AbstractUi;
import org.marvel_uhc.roles.Role;
import org.marvel_uhc.roles.RoleManager;

public class ConfigRoleUi extends AbstractUi {

    @Override
    public Inventory onDisplay(Player player) {
        // 1. Taille maximale autorisée par Bukkit : 54 (6 lignes de 9)
        int size = 54;
        Inventory inv_roles = Bukkit.createInventory(player, size, "§4§oRôles");

        RoleManager.ROLES[] roles = RoleManager.ROLES.values();

        // 2. On place les rôles un par un (ils prendront les slots 0 à 40)
        for (int i = 0; i < roles.length; i++) {
            // Attention : on utilise notre nouvelle méthode getRoleInstance !
            Role role = RoleManager.getRoleInstance(roles[i]);
            ItemStack icon;

            // 3. Sécurité anti-crash pour les rôles qu'on n'a pas encore créés
            if (role != null) {
                // Le rôle existe (ex: Iron Man). On lui crée son icône.
                // (On pourra ajouter un getIcon() dans la classe Role plus tard)
                icon = new ItemStack(Material.BOOK);
                ItemMeta meta = icon.getItemMeta();
                if (meta != null) {
                    meta.setDisplayName(role.getColor() + role.getName());
                    icon.setItemMeta(meta);
                }
            } else {
                // Le rôle n'existe pas encore : on met un papier gris
                icon = new ItemStack(Material.PAPER);
                ItemMeta meta = icon.getItemMeta();
                if (meta != null) {
                    meta.setDisplayName("§7" + roles[i].name() + " (En dev)");
                    icon.setItemMeta(meta);
                }
            }

            inv_roles.setItem(i, icon);
        }

        // 4. On crée une barre de navigation sur la toute dernière ligne (slots 45 à 53)
        for (int i = 45; i < size; i++) {
            inv_roles.setItem(i, Item_Contours);
        }
        // On place le validateur au milieu de cette dernière ligne
        inv_roles.setItem(49, Item_Validator);

        return inv_roles;
    }

    @Override
    public void handleEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        // Sécurité si on clique dans le vide
        if (event.getCurrentItem() == null) return;

        if (event.getCurrentItem().isSimilar(Item_Validator)) {
            player.closeInventory();
            player.openInventory(MarvelUhc.instance.items.hostItems.displayUi(player));
        } else if (!event.getCurrentItem().isSimilar(Item_Contours)) {
            // N'oublie pas d'annuler l'event pour que les joueurs ne puissent pas
            // voler les items du menu et les mettre dans leur inventaire !
            event.setCancelled(true);

            // player.closeInventory();
            // player.openInventory(items_inv.InvRole(player, event.getCurrentItem()));
        } else {
            // On annule aussi le clic sur les contours
            event.setCancelled(true);
        }
    }
}