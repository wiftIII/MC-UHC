package org.marvel_uhc.menus;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.marvel_uhc.MarvelUhc;

public abstract class AbstractUi
{

    protected final ItemStack Item_Contours;
    protected final ItemStack Item_ContoursRole;

    protected final ItemStack Item_Validator = new ItemStack(Material.SLIME_BALL);
    protected final ItemStack Item_Plus = new ItemStack(Material.EMERALD_BLOCK);
    protected final ItemStack Item_Minus = new ItemStack(Material.REDSTONE_BLOCK);
    protected final ItemStack Item_Plus1 = new ItemStack(Material.EMERALD);
    protected final ItemStack Item_Minus1 = new ItemStack(Material.REDSTONE);
    protected final ItemStack Item_Plus5 = new ItemStack(Material.EMERALD_ORE);
    protected final ItemStack Item_Minus5 = new ItemStack(Material.REDSTONE_ORE);
    protected final ItemStack Item_Plus10 = new ItemStack(Material.EMERALD_BLOCK);
    protected final ItemStack Item_Minus10 = new ItemStack(Material.REDSTONE_BLOCK);
    protected final ItemStack Item_Minus10s = new ItemStack(Material.REDSTONE);
    protected final ItemStack Item_Plus10s = new ItemStack(Material.EMERALD);
    protected final ItemStack Item_Minus50s = new ItemStack(Material.REDSTONE_ORE);
    protected final ItemStack Item_Plus50s = new ItemStack(Material.EMERALD_ORE);
    protected final ItemStack Item_Minus100s = new ItemStack(Material.REDSTONE_BLOCK);
    protected final ItemStack Item_Plus100s = new ItemStack(Material.EMERALD_BLOCK);


    public AbstractUi()
    {
        if(MarvelUhc.version >= 16)
        {
            Item_Contours = new ItemStack(Material.BLACK_STAINED_GLASS);
            Item_ContoursRole = new ItemStack(Material.BLACK_STAINED_GLASS);
        }
        else
        {
            Item_Contours = new ItemStack(Material.valueOf("STAINED_GLASS"), 1, (short) 4);
            Item_ContoursRole = new ItemStack(Material.valueOf("STAINED_GLASS"), 1, (short) 4);
        }
        //Vitre de contours d'inventaire
        ItemMeta contoursM = Item_Contours.getItemMeta();
        contoursM.setDisplayName("§e§k42");
        Item_Contours.setItemMeta(contoursM);
        ItemMeta contours_roleM = Item_ContoursRole.getItemMeta();
        contours_roleM.setDisplayName("§e§k24");
        Item_ContoursRole.setItemMeta(contours_roleM);

        // Valider
        ItemMeta validerM = Item_Validator.getItemMeta();
        validerM.setDisplayName("§2Valider");
        validerM.addEnchant(Enchantment.DURABILITY, 1, true);
        validerM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        Item_Validator.setItemMeta(validerM);

        // Plus
        ItemMeta plusM = Item_Plus.getItemMeta();
        plusM.setDisplayName("§a+");
        Item_Plus.setItemMeta(plusM);
        // Moins
        ItemMeta moinsM = Item_Minus.getItemMeta();
        moinsM.setDisplayName("§c-");
        Item_Minus.setItemMeta(moinsM);
        // Plus
        ItemMeta plus1M = Item_Plus1.getItemMeta();
        plus1M.setDisplayName("§a+1");
        Item_Plus1.setItemMeta(plus1M);
        // Moins
        ItemMeta moins1M = Item_Minus1.getItemMeta();
        moins1M.setDisplayName("§c-1");
        Item_Minus1.setItemMeta(moins1M);
        // Plus
        ItemMeta plus5M = Item_Plus5.getItemMeta();
        plus5M.setDisplayName("§a+5");
        Item_Plus5.setItemMeta(plus5M);
        // Moins
        ItemMeta moins5M = Item_Minus5.getItemMeta();
        moins5M.setDisplayName("§c-5");
        Item_Minus5.setItemMeta(moins5M);
        // Plus
        ItemMeta plus10M = Item_Plus10.getItemMeta();
        plus10M.setDisplayName("§a+10");
        Item_Plus10.setItemMeta(plus10M);
        // Moins
        ItemMeta moins10M = Item_Minus10.getItemMeta();
        moins10M.setDisplayName("§c-10");
        Item_Minus10.setItemMeta(moins10M);
        // Plus
        ItemMeta plus10sM = Item_Plus10s.getItemMeta();
        plus10sM.setDisplayName("§a+10");
        Item_Plus10s.setItemMeta(plus10sM);
        // Moins
        ItemMeta moins10sM = Item_Minus10s.getItemMeta();
        moins10sM.setDisplayName("§c-10");
        Item_Minus10s.setItemMeta(moins10sM);
        // Plus
        ItemMeta plus50sM = Item_Plus50s.getItemMeta();
        plus50sM.setDisplayName("§a+50");
        Item_Plus50s.setItemMeta(plus50sM);
        // Moins
        ItemMeta moins50sM = Item_Minus50s.getItemMeta();
        moins50sM.setDisplayName("§c-50");
        Item_Minus50s.setItemMeta(moins50sM);
        // Plus
        ItemMeta plus100sM = Item_Plus100s.getItemMeta();
        plus100sM.setDisplayName("§a+100");
        Item_Plus100s.setItemMeta(plus100sM);
        // Moins
        ItemMeta moins100sM = Item_Minus100s.getItemMeta();
        moins100sM.setDisplayName("§c-100");
        Item_Minus100s.setItemMeta(moins100sM);
    }

    public abstract Inventory onDisplay(Player player);
    public abstract void handleEvent(InventoryClickEvent event);
}
