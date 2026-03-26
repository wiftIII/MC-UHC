package org.marvel_uhc.roles;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Role
{
    public String name;
    public int colorIndex;
    public ItemStack icon;

    public ArrayList<ItemStack> kit;

    public Role(String name, char colorIndex, Material icon)
    {
        this.name = name;
        this.colorIndex = colorIndex;
        this.icon = new ItemStack(icon, 1);

        ItemMeta itemM = this.icon.getItemMeta();
        itemM.setDisplayName("§" + colorIndex + name);
        this.icon.setItemMeta(itemM);

        kit = new ArrayList<>();
    }
}
