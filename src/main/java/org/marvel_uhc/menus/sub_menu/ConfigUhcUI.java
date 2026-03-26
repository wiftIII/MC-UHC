package org.marvel_uhc.menus.sub_menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.marvel_uhc.GameConfiguration;
import org.marvel_uhc.MarvelUhc;
import org.marvel_uhc.menus.AbstractUi;

import java.util.Arrays;

public class ConfigUhcUI extends AbstractUi
{
    private GameConfiguration configuration;

    // UHC
    public ItemStack Item_Pvp = new ItemStack(Material.IRON_SWORD);
    public ItemStack Item_Border = new ItemStack(Material.BARRIER);
    public ItemStack Item_BorderSize;
    public ItemStack Item_BorderSpeed = new ItemStack(Material.SUGAR);
    public ItemStack Item_MaxPlayer;
    public ItemStack Item_RoleTime ;

    public ConfigUhcUI()
    {
        configuration = MarvelUhc.instance.configuration;

        if(MarvelUhc.version >= 16)
        {
            Item_BorderSize = new ItemStack(Material.MAP);
            Item_MaxPlayer = new ItemStack(Material.SKELETON_SKULL);
            Item_RoleTime = new ItemStack(Material.WRITABLE_BOOK);
        }
        else
        {
            Item_BorderSize = new ItemStack(Material.valueOf("EMPTY_MAP"));
            Item_MaxPlayer = new ItemStack(Material.valueOf("SKULL_ITEM"));
            Item_RoleTime = new ItemStack(Material.valueOf("BOOK_AND_QUILL"));
        }

        // PVP
        ItemMeta pvpM = Item_Pvp.getItemMeta();
        pvpM.setDisplayName("§4PVP");
        pvpM.setLore(Arrays.asList("§9Activé à " + configuration.pvp_time + " minute(s)"));
        Item_Pvp.setItemMeta(pvpM);
        // Bordure
        ItemMeta borderM = Item_Border.getItemMeta();
        borderM.setDisplayName("§8Bordure");
        borderM.setLore(Arrays.asList("§9Activée à " + configuration.border_time + " minute(s)"));
        Item_Border.setItemMeta(borderM);
        // Taille de la bordure
        ItemMeta borderSizeM = Item_BorderSize.getItemMeta();
        borderSizeM.setDisplayName("§9Taille de la bordure");
        borderSizeM.setLore(Arrays.asList("§9Actuellement : " + configuration.border_size + "/" + configuration.border_size));
        Item_BorderSize.setItemMeta(borderSizeM);
        // Vitesse de la bordure
        ItemMeta border_speedM = Item_BorderSpeed.getItemMeta();
        border_speedM.setDisplayName("§9Vitesse de la bordure");
        border_speedM.setLore(Arrays.asList("§9" + configuration.border_speed + " bloc(s) par seconde"));
        Item_BorderSpeed.setItemMeta(border_speedM);
        // Nombre de joueur max
        ItemMeta max_playerM = Item_MaxPlayer.getItemMeta();
        max_playerM.setDisplayName("§eNombre de Joueurs Maximum");
        max_playerM.setLore(Arrays.asList("§9Actuellement " + configuration.maxPlayer + " joueur(s) maximum"));
        Item_MaxPlayer.setItemMeta(max_playerM);
        // Rôles
        ItemMeta role_timeM = Item_RoleTime.getItemMeta();
        role_timeM.setDisplayName("§4Rôles");
        role_timeM.setLore(Arrays.asList("§9Assignation à " + configuration.role_time + " minute(s)"));
        Item_RoleTime.setItemMeta(role_timeM);
    }

    //**********************************************************************

    @Override
    public Inventory onDisplay(Player player)
    {

        int size = 45;
        Inventory inv_uhc = Bukkit.createInventory(player, size, "§2§oUHC");
        inv_uhc.setItem((size+1)/2-3, Item_Pvp);
        inv_uhc.setItem((size+1)/2-2, Item_Border);
        inv_uhc.setItem((size+1)/2-1, Item_MaxPlayer);
        inv_uhc.setItem((size+1)/2, Item_RoleTime);

        //************************************
        //Contours
        for(int i = 0; i < 9; i ++)
            inv_uhc.setItem(i, Item_Contours);
        for(int i = 8; i < size; i+=9)
            inv_uhc.setItem(i, Item_Contours);
        for(int i = 9; i < size; i+=9)
            inv_uhc.setItem(i, Item_Contours);
        for(int i = size-9; i < size; i ++)
            inv_uhc.setItem(i, Item_Contours);
        //************************************
        inv_uhc.setItem(size-5, Item_Validator);
        return inv_uhc;
    }
    //**********************************************************************

    public Inventory displayPvp(Player player) {
        int size = 45;
        Inventory inv_uhc = Bukkit.createInventory(player, size, "§4§oPVP");
        inv_uhc.setItem((size+1)/2-4, Item_Minus10);
        inv_uhc.setItem((size+1)/2-3, Item_Minus5);
        inv_uhc.setItem((size+1)/2-2, Item_Minus1);
        inv_uhc.setItem((size+1)/2-1, Item_Pvp);
        inv_uhc.setItem((size+1)/2, Item_Plus1);
        inv_uhc.setItem((size+1)/2+1, Item_Plus5);
        inv_uhc.setItem((size+1)/2+2, Item_Plus10);
        //************************************
        //Contours
        for(int i = 0; i < 9; i ++)
            inv_uhc.setItem(i, Item_Contours);
        for(int i = 8; i < size; i+=9)
            inv_uhc.setItem(i, Item_Contours);
        for(int i = 9; i < size; i+=9)
            inv_uhc.setItem(i, Item_Contours);
        for(int i = size-9; i < size; i ++)
            inv_uhc.setItem(i, Item_Contours);
        //************************************
        inv_uhc.setItem(size-5, Item_Validator);
        return inv_uhc;
    }

    public ItemStack ActualizePVPTime(int i)
    {
        if(configuration.pvp_time + i >= 1)
            configuration.pvp_time += i;
        configuration.pvp_time_s = configuration.pvp_time*60;
        ItemMeta pvpM = Item_Pvp.getItemMeta();
        pvpM.setLore(Arrays.asList("§9Activé à " + configuration.pvp_time + " minute(s)"));
        Item_Pvp.setItemMeta(pvpM);
        return Item_Pvp;
    }

    //**********************************************************************

    public Inventory displayBorder(Player player) {
        int size = 45;
        Inventory inv_border = Bukkit.createInventory(player, size, "§8§oBordure");
        inv_border.setItem((size+1)/2-13, Item_Minus10);
        inv_border.setItem((size+1)/2-12, Item_Minus5);
        inv_border.setItem((size+1)/2-11, Item_Minus1);
        inv_border.setItem((size+1)/2-10, Item_Border);
        inv_border.setItem((size+1)/2-9, Item_Plus1);
        inv_border.setItem((size+1)/2-8, Item_Plus5);
        inv_border.setItem((size+1)/2-7, Item_Plus10);
        inv_border.setItem((size+1)/2-4, Item_Minus100s);
        inv_border.setItem((size+1)/2-3, Item_Minus50s);
        inv_border.setItem((size+1)/2-2, Item_Minus10s);
        inv_border.setItem((size+1)/2-1, Item_BorderSize);
        inv_border.setItem((size+1)/2, Item_Plus10s);
        inv_border.setItem((size+1)/2+1, Item_Plus50s);
        inv_border.setItem((size+1)/2+2, Item_Plus100s);
        inv_border.setItem((size+1)/2+6, Item_Minus);
        inv_border.setItem((size+1)/2+8, Item_BorderSpeed);
        inv_border.setItem((size+1)/2+10, Item_Plus);
        //************************************
        //Contours
        for(int i = 0; i < 9; i ++)
            inv_border.setItem(i, Item_Contours);
        for(int i = 8; i < size; i+=9)
            inv_border.setItem(i, Item_Contours);
        for(int i = 9; i < size; i+=9)
            inv_border.setItem(i, Item_Contours);
        for(int i = size-9; i < size; i ++)
            inv_border.setItem(i, Item_Contours);
        //************************************
        inv_border.setItem(size-5, Item_Validator);
        return inv_border;
    }

    public ItemStack ActualizeBorderTime(int i) {
        if(configuration.border_time + i >= 1)
            configuration.border_time += i;
        configuration.border_time_s = configuration.border_time*60;
        ItemMeta borderM = Item_Border.getItemMeta();
        borderM.setLore(Arrays.asList("§9Activée à " + configuration.border_time + " minute(s)"));
        Item_Border.setItemMeta(borderM);
        return Item_Border;
    }

    public ItemStack ActualizeBorderSize(int i) {
        if(configuration.border_size + i >= 300)
            configuration.border_size += i;
        ItemMeta borderSizeM = Item_BorderSize.getItemMeta();
        borderSizeM.setLore(Arrays.asList("§9Actuellement : " + configuration.border_size + "/" + configuration.border_size));
        Item_BorderSize.setItemMeta(borderSizeM);
        return Item_BorderSize;
    }

    public ItemStack ActualizeBorderSpeed(double i) {
        if(configuration.border_speed + i >= 0.5)
            configuration.border_speed += i;
        ItemMeta border_speedM = Item_BorderSpeed.getItemMeta();
        border_speedM.setLore(Arrays.asList("§9" + configuration.border_speed + " bloc(s) par seconde"));
        Item_BorderSpeed.setItemMeta(border_speedM);
        return Item_BorderSpeed;
    }

    //**********************************************************************

    public Inventory displayMaxPlayer(Player player) {
        int size = 45;
        Inventory inv_max_player = Bukkit.createInventory(player, size, "§e§oJoueurs Maximum");
        inv_max_player.setItem((size+1)/2-3, Item_Minus);
        inv_max_player.setItem((size+1)/2-1, Item_MaxPlayer);
        inv_max_player.setItem((size+1)/2+1, Item_Plus);
        //************************************
        //Contours
        for(int i = 0; i < 9; i ++)
            inv_max_player.setItem(i, Item_Contours);
        for(int i = 8; i < size; i+=9)
            inv_max_player.setItem(i, Item_Contours);
        for(int i = 9; i < size; i+=9)
            inv_max_player.setItem(i, Item_Contours);
        for(int i = size-9; i < size; i ++)
            inv_max_player.setItem(i, Item_Contours);
        //************************************
        inv_max_player.setItem(size-5, Item_Validator);
        return inv_max_player;
    }

    public ItemStack ActualizeMaxPlayer(int i) {
        if(configuration.maxPlayer + i >= 5)
            configuration.maxPlayer += i;
        ItemMeta max_playerM = Item_MaxPlayer.getItemMeta();
        max_playerM.setLore(Arrays.asList("§9Actuellement " + configuration.maxPlayer + " joueur(s) maximum"));
        Item_MaxPlayer.setItemMeta(max_playerM);
        return Item_MaxPlayer;
    }

    //**********************************************************************

    public Inventory displayRoleTime(Player player) {
        int size = 45;
        Inventory inv_uhc = Bukkit.createInventory(player, size, "§4§oTemps des rôles");
        inv_uhc.setItem((size+1)/2-4, Item_Minus10);
        inv_uhc.setItem((size+1)/2-3, Item_Minus5);
        inv_uhc.setItem((size+1)/2-2, Item_Minus1);
        inv_uhc.setItem((size+1)/2-1, Item_RoleTime);
        inv_uhc.setItem((size+1)/2, Item_Plus1);
        inv_uhc.setItem((size+1)/2+1, Item_Plus5);
        inv_uhc.setItem((size+1)/2+2, Item_Plus10);
        //************************************
        //Contours
        for(int i = 0; i < 9; i ++)
            inv_uhc.setItem(i, Item_Contours);
        for(int i = 8; i < size; i+=9)
            inv_uhc.setItem(i, Item_Contours);
        for(int i = 9; i < size; i+=9)
            inv_uhc.setItem(i, Item_Contours);
        for(int i = size-9; i < size; i ++)
            inv_uhc.setItem(i, Item_Contours);
        //************************************
        inv_uhc.setItem(size-5, Item_Validator);
        return inv_uhc;
    }

    public ItemStack ActualizeRolesTime(int i) {
        if(configuration.role_time + i >= 1)
            configuration.role_time += i;
        configuration.role_time_s = configuration.role_time*60;
        ItemMeta role_timeM = Item_RoleTime.getItemMeta();
        role_timeM.setLore(Arrays.asList("§9Assignation à " + configuration.role_time + " minute(s)"));
        Item_RoleTime.setItemMeta(role_timeM);
        return Item_RoleTime;
    }

    //**********************************************************************

    @Override
    public void handleEvent(InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();

        //**********************************************************************
        // UHC
        if(currentItem.isSimilar(Item_Pvp))
        {
            player.closeInventory();
            player.openInventory(displayPvp(player));
        }
        else if(currentItem.isSimilar(Item_Border))
        {
            player.closeInventory();
            player.openInventory(displayBorder(player));
        }
        else if(currentItem.isSimilar(Item_MaxPlayer))
        {
            player.closeInventory();
            player.openInventory(displayMaxPlayer(player));
        }
        else if(currentItem.isSimilar(Item_RoleTime))
        {
            player.closeInventory();
            player.openInventory(displayRoleTime(player));
        }
        //**********************************************************************
        if(event.getCurrentItem().isSimilar(Item_Validator))
        {
            player.closeInventory();
            player.openInventory(MarvelUhc.instance.items.hostItems.displayUi(player));
        }
        event.setCancelled(true);
        //**********************************************************************
    }

    public void handlePvp(InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();

        //**********************************************************************
        // PVP
        if(event.getCurrentItem().isSimilar(Item_Plus1))
            event.getInventory().setItem((event.getInventory().getSize()+1)/2-1, ActualizePVPTime(1));
        else if(event.getCurrentItem().isSimilar(Item_Minus1))
            event.getInventory().setItem((event.getInventory().getSize()+1)/2-1, ActualizePVPTime(-1));
        else if(event.getCurrentItem().isSimilar(Item_Plus5))
            event.getInventory().setItem((event.getInventory().getSize()+1)/2-1, ActualizePVPTime(5));
        else if(event.getCurrentItem().isSimilar(Item_Minus5))
            event.getInventory().setItem((event.getInventory().getSize()+1)/2-1, ActualizePVPTime(-5));
        else if(event.getCurrentItem().isSimilar(Item_Plus10))
            event.getInventory().setItem((event.getInventory().getSize()+1)/2-1, ActualizePVPTime(10));
        else if(event.getCurrentItem().isSimilar(Item_Minus10))
            event.getInventory().setItem((event.getInventory().getSize()+1)/2-1, ActualizePVPTime(-10));
        //**********************************************************************
        if(event.getCurrentItem().isSimilar(Item_Validator)) {
            player.closeInventory();
            player.openInventory(onDisplay(player));
        }
        //main.updateScoreBoard();
        event.setCancelled(true);
        //**********************************************************************
    }

    public void handleBorder(InventoryClickEvent event)
    {

        Player player = (Player) event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();
        //**********************************************************************
        // Bordure
        if(event.getCurrentItem().isSimilar(Item_Plus1))
            event.getInventory().setItem((event.getInventory().getSize()+1)/2-10, ActualizeBorderTime(1));
        else if(event.getCurrentItem().isSimilar(Item_Minus1))
            event.getInventory().setItem((event.getInventory().getSize()+1)/2-10, ActualizeBorderTime(-1));
        else if(event.getCurrentItem().isSimilar(Item_Plus5))
            event.getInventory().setItem((event.getInventory().getSize()+1)/2-10, ActualizeBorderTime(5));
        else if(event.getCurrentItem().isSimilar(Item_Minus5))
            event.getInventory().setItem((event.getInventory().getSize()+1)/2-10, ActualizeBorderTime(-5));
        else if(event.getCurrentItem().isSimilar(Item_Plus10))
            event.getInventory().setItem((event.getInventory().getSize()+1)/2-10, ActualizeBorderTime(10));
        else if(event.getCurrentItem().isSimilar(Item_Minus10))
            event.getInventory().setItem((event.getInventory().getSize()+1)/2-10, ActualizeBorderTime(-10));
        else if(event.getCurrentItem().isSimilar(Item_Plus10s))
            event.getInventory().setItem((event.getInventory().getSize()+1)/2-1, ActualizeBorderSize(10));
        else if(event.getCurrentItem().isSimilar(Item_Minus10s))
            event.getInventory().setItem((event.getInventory().getSize()+1)/2-1, ActualizeBorderSize(-10));
        else if(event.getCurrentItem().isSimilar(Item_Plus50s))
            event.getInventory().setItem((event.getInventory().getSize()+1)/2-1, ActualizeBorderSize(50));
        else if(event.getCurrentItem().isSimilar(Item_Minus50s))
            event.getInventory().setItem((event.getInventory().getSize()+1)/2-1, ActualizeBorderSize(-50));
        else if(event.getCurrentItem().isSimilar(Item_Plus100s))
            event.getInventory().setItem((event.getInventory().getSize()+1)/2-1, ActualizeBorderSize(100));
        else if(event.getCurrentItem().isSimilar(Item_Minus100s))
            event.getInventory().setItem((event.getInventory().getSize()+1)/2-1, ActualizeBorderSize(-100));
        else if(event.getCurrentItem().isSimilar(Item_Plus))
            event.getInventory().setItem((event.getInventory().getSize()+1)/2+8, ActualizeBorderSpeed(0.5));
        else if(event.getCurrentItem().isSimilar(Item_Minus))
            event.getInventory().setItem((event.getInventory().getSize()+1)/2+8, ActualizeBorderSpeed(-0.5));
        //**********************************************************************
        if(event.getCurrentItem().isSimilar(Item_Validator)) {
            player.closeInventory();
            player.openInventory(onDisplay(player));
        }
        //main.border.setSize(main.border_size*2);
        //main.updateScoreBoard();
        event.setCancelled(true);
        //**********************************************************************
    }

    public void handleMaxPlayer(InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();

        if(event.getCurrentItem().isSimilar(Item_Plus))
            event.getInventory().setItem((event.getInventory().getSize()+1)/2-1, ActualizeMaxPlayer(1));
        else if(event.getCurrentItem().isSimilar(Item_Minus))
            event.getInventory().setItem((event.getInventory().getSize()+1)/2-1, ActualizeMaxPlayer(-1));
        //**********************************************************************
        if(event.getCurrentItem().isSimilar(Item_Validator)) {
            player.closeInventory();
            player.openInventory(onDisplay(player));
        }
        //main.updateScoreBoard();
        event.setCancelled(true);
        //**********************************************************************
    }

    public void handleRoleTime(InventoryClickEvent event)
    {

        Player player = (Player) event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();

        //**********************************************************************
        // Temps des rôles
        if(event.getCurrentItem().isSimilar(Item_Plus1))
            event.getInventory().setItem((event.getInventory().getSize()+1)/2-1, ActualizeRolesTime(1));
        else if(event.getCurrentItem().isSimilar(Item_Minus1))
            event.getInventory().setItem((event.getInventory().getSize()+1)/2-1, ActualizeRolesTime(-1));
        else if(event.getCurrentItem().isSimilar(Item_Plus5))
            event.getInventory().setItem((event.getInventory().getSize()+1)/2-1, ActualizeRolesTime(5));
        else if(event.getCurrentItem().isSimilar(Item_Minus5))
            event.getInventory().setItem((event.getInventory().getSize()+1)/2-1, ActualizeRolesTime(-5));
        else if(event.getCurrentItem().isSimilar(Item_Plus10))
            event.getInventory().setItem((event.getInventory().getSize()+1)/2-1, ActualizeRolesTime(10));
        else if(event.getCurrentItem().isSimilar(Item_Minus10))
            event.getInventory().setItem((event.getInventory().getSize()+1)/2-1, ActualizeRolesTime(-10));
        //**********************************************************************
        if(event.getCurrentItem().isSimilar(Item_Validator)) {
            player.closeInventory();
            player.openInventory(onDisplay(player));
        }
        //main.updateScoreBoard();
        event.setCancelled(true);
        //**********************************************************************
    }
}
