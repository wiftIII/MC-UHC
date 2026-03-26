package org.marvel_uhc.menus.sub_menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.marvel_uhc.GameConfiguration;
import org.marvel_uhc.MarvelUhc;
import org.marvel_uhc.menus.AbstractUi;
import org.marvel_uhc.roles.Role;
import org.marvel_uhc.roles.RoleManager;

import java.util.ArrayList;

public class ConfigRoleUi extends AbstractUi
{
    @Override
    public Inventory onDisplay(Player player)
    {
        GameConfiguration config = MarvelUhc.instance.configuration;
        //reloadRoles("");
        int size =72;
        Inventory inv_roles = Bukkit.createInventory(player, size, "§4§oRôles");
        int start = 10;
        int width = 7;
        int padding = 2;

        RoleManager.ROLES[] roles = RoleManager.ROLES.values();
        for(int i=0; i < roles.length; ++i )
        {
            Role role = RoleManager.GetRole(roles[i]);
            int p = i/width;
            inv_roles.setItem(i + start +p * padding, role.icon);
        }

        //************************************
        //Contours
        for(int i = 0; i < 9; i ++)
            inv_roles.setItem(i, Item_Contours);
        for(int i = 8; i < size; i+=9)
            inv_roles.setItem(i, Item_Contours);
        for(int i = 9; i < size; i+=9)
            inv_roles.setItem(i, Item_Contours);
        for(int i = size-9; i < size; i ++)
            inv_roles.setItem(i, Item_Contours);
        //************************************
        inv_roles.setItem(size-5, Item_Validator);
        return inv_roles;
    }

    @Override
    public void handleEvent(InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();

        if (event.getCurrentItem().isSimilar(Item_Validator))
        {
            player.closeInventory();
            player.openInventory(MarvelUhc.instance.items.hostItems.displayUi(player));
        }
        else if(!event.getCurrentItem().isSimilar(Item_Contours))
        {
            player.closeInventory();
            //player.openInventory(items_inv.InvRole(player, event.getCurrentItem()));
        }

    }
}
