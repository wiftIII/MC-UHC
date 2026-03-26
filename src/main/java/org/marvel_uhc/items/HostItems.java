package org.marvel_uhc.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.marvel_uhc.menus.ConfigUi;

public class HostItems
{
    public final ItemStack Item_Config = new ItemStack(Material.NETHER_STAR);
    public final ConfigUi Ui = new ConfigUi();


    public HostItems()
    {
        InitializeItems();
    }

    public void InitializeItems()
    {
        //Item de config
        ItemMeta configM = Item_Config.getItemMeta();
        configM.setDisplayName("§6§oConfiguration");
        Item_Config.setItemMeta(configM);
    }

    public Inventory displayUi(Player player)
    {
        return Ui.onDisplay(player);
    }
}
