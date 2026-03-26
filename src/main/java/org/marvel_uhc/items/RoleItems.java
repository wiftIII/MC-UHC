package org.marvel_uhc.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class RoleItems
{
    //IronMan
    public final ItemStack Item_Protect3 = new ItemStack(Material.ENCHANTED_BOOK);
    public final ItemStack Item_Flame1 = new ItemStack(Material.ENCHANTED_BOOK);
    public final ItemStack Item_String = new ItemStack(Material.STRING,3);
    public final ItemStack Item_IronMan_Buff = new ItemStack(Material.NETHER_STAR);

    //DrStrange
    public final ItemStack Item_EnderPearl = new ItemStack(Material.ENDER_PEARL,3);

    public RoleItems()
    {
        InitializeItems();
    }

    public void InitializeItems()
    {
        //IronMan
        createBookEnchantment(Item_Protect3, Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        createBookEnchantment(Item_Flame1, Enchantment.ARROW_FIRE, 1);
        ItemMeta configM = Item_IronMan_Buff.getItemMeta();
        configM.setDisplayName("§6§oDécollage");
        Item_IronMan_Buff.setItemMeta(configM);
    }

    public void createBookEnchantment(ItemStack item, Enchantment enchant, int level){

        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
        meta.addStoredEnchant(enchant, level, true);
        item.setItemMeta(meta);
    }
}
