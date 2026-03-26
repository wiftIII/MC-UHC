package org.marvel_uhc.menus.sub_menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.marvel_uhc.GameConfiguration;
import org.marvel_uhc.MarvelUhc;
import org.marvel_uhc.menus.AbstractUi;

import java.util.Arrays;

public class ConfigScenarioUi extends AbstractUi
{

    private GameConfiguration config;

    public ItemStack Item_NoFood = new ItemStack(Material.COOKED_BEEF);
    public ItemStack Item_NoFoodOff = new ItemStack(Material.COOKED_BEEF);
    public ItemStack Item_NoFall = new ItemStack(Material.FEATHER);
    public ItemStack Item_NoFallOff = new ItemStack(Material.FEATHER);
    public ItemStack Item_NoFire = new ItemStack(Material.BLAZE_POWDER);
    public ItemStack Item_NoFireOff = new ItemStack(Material.BLAZE_POWDER);
    public ItemStack Item_DiamondLimit = new ItemStack(Material.DIAMOND);
    public ItemStack Item_NoDiamondLimit = new ItemStack(Material.DIAMOND);
    public ItemStack Item_Diamond = new ItemStack(Material.DIAMOND);

    public ConfigScenarioUi()
    {
        config = MarvelUhc.instance.configuration;
        // NoFood
        ItemMeta noFoodM = Item_NoFood.getItemMeta();
        noFoodM.setDisplayName("§9No food");
        noFoodM.setLore(Arrays.asList("§aActivé", "(Les joueurs n'auront pas", "besoin de nourriture)"));
        noFoodM.addEnchant(Enchantment.DURABILITY, 1, true);
        noFoodM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        Item_NoFood.setItemMeta(noFoodM);
        // NoFood off
        ItemMeta noFoodOffM = Item_NoFoodOff.getItemMeta();
        noFoodOffM.setDisplayName("§9No food");
        noFoodOffM.setLore(Arrays.asList("§cDésactivé"));
        Item_NoFoodOff.setItemMeta(noFoodOffM);

        // NoFall
        ItemMeta noFallM = Item_NoFall.getItemMeta();
        noFallM.setDisplayName("§9No fall");
        noFallM.setLore(Arrays.asList("§aActivé", "(Les joueurs ne prendront pas", "de dégats de chute)"));
        noFallM.addEnchant(Enchantment.DURABILITY, 1, true);
        noFallM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        Item_NoFall.setItemMeta(noFallM);
        // NoFall off
        ItemMeta noFallOffM = Item_NoFallOff.getItemMeta();
        noFallOffM.setDisplayName("§9No fall");
        noFallOffM.setLore(Arrays.asList("§cDésactivé"));
        Item_NoFallOff.setItemMeta(noFallOffM);

        // NoFire
        ItemMeta noFireM = Item_NoFire.getItemMeta();
        noFireM.setDisplayName("§9No fire");
        noFireM.setLore(Arrays.asList("§aActivé", "(Les joueurs ne prendront pas", "de dégats de feu)"));
        noFireM.addEnchant(Enchantment.DURABILITY, 1, true);
        noFireM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        Item_NoFire.setItemMeta(noFireM);
        // NoFire off
        ItemMeta noFireOffM = Item_NoFireOff.getItemMeta();
        noFireOffM.setDisplayName("§9No fire");
        noFireOffM.setLore(Arrays.asList("§cDésactivé"));
        Item_NoFireOff.setItemMeta(noFireOffM);

        // DiamondLimit
        ItemMeta diamondLimitM = Item_DiamondLimit.getItemMeta();
        diamondLimitM.setDisplayName("§9Diamond Limit");
        diamondLimitM.setLore(Arrays.asList("§aActivé", "(Les joueurs ne pourront miner", "que " + config.diamondLimitMax + " diamants)"));
        diamondLimitM.addEnchant(Enchantment.DURABILITY, 1, true);
        diamondLimitM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        Item_DiamondLimit.setItemMeta(diamondLimitM);
        // NoDiamondLimit
        ItemMeta noDiamondLimitM = Item_NoDiamondLimit.getItemMeta();
        noDiamondLimitM.setDisplayName("§9Diamond Limit");
        noDiamondLimitM.setLore(Arrays.asList("§cDésactivé"));
        Item_NoDiamondLimit.setItemMeta(noDiamondLimitM);
        // Diamond
        ItemMeta diamondM = Item_Diamond.getItemMeta();
        diamondM.setDisplayName("§9Diamond Limit");
        diamondM.setLore(Arrays.asList("§aActivé : " + config.diamondLimitMax));
        Item_Diamond.setItemMeta(diamondM);
    }

    @Override
    public Inventory onDisplay(Player player) {
        int size = 45;
        Inventory inv_scenarios = Bukkit.createInventory(player, size, "§9§oScénarios");

        if(config.noFood) inv_scenarios.setItem(10, Item_NoFood); 				else inv_scenarios.setItem(10, Item_NoFoodOff);
        if(config.noFall) inv_scenarios.setItem(11, Item_NoFall);		 		else inv_scenarios.setItem(11, Item_NoFallOff);
        if(config.noFire) inv_scenarios.setItem(12, Item_NoFire); 				else inv_scenarios.setItem(12, Item_NoFireOff);
        if(config.diamondLimit) inv_scenarios.setItem(13, Item_DiamondLimit); 	else inv_scenarios.setItem(13, Item_NoDiamondLimit);
        //************************************
        //Contours
        for(int i = 0; i < 9; i ++)
            inv_scenarios.setItem(i, Item_Contours);
        for(int i = 8; i < size; i+=9)
            inv_scenarios.setItem(i, Item_Contours);
        for(int i = 9; i < size; i+=9)
            inv_scenarios.setItem(i, Item_Contours);
        for(int i = size-9; i < size; i ++)
            inv_scenarios.setItem(i, Item_Contours);
        //************************************
        inv_scenarios.setItem(size-5, Item_Validator);
        return inv_scenarios;
    }

    public Inventory onDisplayDiamond(Player player) {
        int size = 45;
        Inventory inv_diamond_limit = Bukkit.createInventory(player, size, "§9§oDiamond Limit");

        inv_diamond_limit.setItem((size+1)/2-3, Item_Minus);
        inv_diamond_limit.setItem((size+1)/2-1, Item_Diamond);
        inv_diamond_limit.setItem((size+1)/2+1, Item_Plus);
        //************************************
        //Contours
        for(int i = 0; i < 9; i ++)
            inv_diamond_limit.setItem(i, Item_Contours);
        for(int i = 8; i < size; i+=9)
            inv_diamond_limit.setItem(i, Item_Contours);
        for(int i = 9; i < size; i+=9)
            inv_diamond_limit.setItem(i, Item_Contours);
        for(int i = size-9; i < size; i ++)
            inv_diamond_limit.setItem(i, Item_Contours);
        //************************************
        inv_diamond_limit.setItem(size-5, Item_Validator);
        return inv_diamond_limit;
    }

    @Override
    public void handleEvent(InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();

        if(currentItem.isSimilar(Item_NoFood))
        {
            event.getInventory().setItem(10, Item_NoFoodOff);
            config.noFood = false;
        }
        else if(event.getCurrentItem().isSimilar(Item_NoFoodOff))
        {
            event.getInventory().setItem(10, Item_NoFood);
            config.noFood = true;
        }
        else if(event.getCurrentItem().isSimilar(Item_NoFall))
        {
            event.getInventory().setItem(11, Item_NoFallOff);
            config.noFall = false;
        }
        else if(event.getCurrentItem().isSimilar(Item_NoFallOff))
        {
            event.getInventory().setItem(11, Item_NoFall);
            config.noFall = true;
        }
        else if(event.getCurrentItem().isSimilar(Item_NoFire))
        {
            event.getInventory().setItem(12, Item_NoFireOff);
            config.noFire = false;
        }
        else if(event.getCurrentItem().isSimilar(Item_NoFireOff))
        {
            event.getInventory().setItem(12, Item_NoFire);
            config.noFire = true;
        }
        else if(event.getCurrentItem().isSimilar(Item_DiamondLimit))
        {
            event.getInventory().setItem(13, Item_NoDiamondLimit);
            config.diamondLimit = false;
        }
        else if(event.getCurrentItem().isSimilar(Item_NoDiamondLimit))
        {
            player.closeInventory();
            config.diamondLimit = true;
            player.openInventory(onDisplayDiamond(player));
        }
        //**********************************************************************
        if(event.getCurrentItem().isSimilar(Item_Validator)) {
                player.closeInventory();
                player.openInventory(MarvelUhc.instance.items.hostItems.displayUi(player));
        }
        event.setCancelled(true);
        //**********************************************************************
    }

    public void handleDiamondEvent(InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();

        //**********************************************************************
        // Diamond Limit
        if(event.getCurrentItem().isSimilar(Item_Plus))
        {
            event.getInventory().setItem((event.getInventory().getSize() + 1) / 2 - 1, ActualizeDiamondLimit(1));
        }
        else if(event.getCurrentItem().isSimilar(Item_Minus))
        {
            event.getInventory().setItem((event.getInventory().getSize() + 1) / 2 - 1, ActualizeDiamondLimit(-1));
        }
        //**********************************************************************
        else if(event.getCurrentItem().isSimilar(Item_Validator))
        {
            player.closeInventory();
            player.openInventory(onDisplay(player));
        }
        event.setCancelled(true);
    }

    public ItemStack ActualizeDiamondLimit(int i) {
        if(config.diamondLimitMax + i >= 0)
            config.diamondLimitMax += i;
        ItemMeta diamondM = Item_Diamond.getItemMeta();
        diamondM.setDisplayName("§9Diamond Limit");
        diamondM.setLore(Arrays.asList("§aActivé : " + config.diamondLimitMax));
        Item_Diamond.setItemMeta(diamondM);
        ItemMeta diamondLimitM = Item_DiamondLimit.getItemMeta();
        diamondLimitM.setLore(Arrays.asList("§aActivé", "(Les joueurs ne pourront miner", "que " + config.diamondLimitMax + " diamants)"));
        Item_DiamondLimit.setItemMeta(diamondLimitM);
        return Item_Diamond;
    }
}
