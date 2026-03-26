package org.marvel_uhc.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.marvel_uhc.MarvelUhc;
import org.marvel_uhc.stones.*;

public class ItemManager
{
    public final HostItems hostItems;
    public final RoleItems roleItems;

    //Stones
    public final Stone spaceStone;
    public final Stone timeStone;
    public final Stone mindStone;
    public final Stone powerStone;
    public final Stone soulStone;
    public final Stone realityStone;

    public ItemManager()
    {
        hostItems = new HostItems();
        roleItems = new RoleItems();

        // Init Stone objects
        spaceStone = new SpaceStone(createItem(Material.NETHER_STAR, "§bSpace Stone"));
        timeStone = new TimeStone(createItem(Material.EMERALD, "§aTime Stone"));
        mindStone = new MindStone(createItem(Material.DIRT, "§eMind Stone"));
        powerStone = new PowerStone(createItem(Material.DIAMOND, "§5Power Stone"));
        soulStone = new SoulStone(createItem(Material.COAL, "§6Soul Stone"));
        realityStone = new RealityStone(createItem(Material.GOLD_INGOT, "§cReality Stone"));
    }

    private ItemStack createItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }
}
