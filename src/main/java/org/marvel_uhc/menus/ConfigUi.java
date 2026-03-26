package org.marvel_uhc.menus;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.marvel_uhc.MarvelUhc;
import org.marvel_uhc.State;
import org.marvel_uhc.items.HostItems;
import org.marvel_uhc.menus.sub_menu.ConfigRoleUi;
import org.marvel_uhc.menus.sub_menu.ConfigScenarioUi;
import org.marvel_uhc.menus.sub_menu.ConfigUhcUI;
import org.marvel_uhc.roles.Role;

public class ConfigUi extends AbstractUi
{
    public final MarvelUhc main;

    public final ConfigScenarioUi scenarioUi;
    public final ConfigUhcUI uhcUi;
    public final ConfigRoleUi roleUi;

    public final ItemStack Item_Scenarios = new ItemStack(Material.BOOK);
    public final ItemStack Item_Uhc = new ItemStack(Material.GOLDEN_APPLE);
    public final ItemStack Item_Roles;

    // Démarrer/Stopper
    public final ItemStack Item_Start;
    public final ItemStack Item_Starting;


    public ConfigUi()
    {
        main =  MarvelUhc.instance;

        scenarioUi = new ConfigScenarioUi();
        uhcUi = new ConfigUhcUI();
        roleUi = new ConfigRoleUi();

        if(MarvelUhc.version >= 16)
        {
            Item_Roles = new ItemStack(Material.WRITABLE_BOOK);
            Item_Start = new ItemStack(Material.GREEN_STAINED_GLASS);
            Item_Starting = new ItemStack(Material.RED_STAINED_GLASS);
        }
        else
        {
            Item_Roles = new ItemStack(Material.valueOf("BOOK_AND_QUILL"));
            Item_Start = new ItemStack(Material.valueOf("STAINED_GLASS"), 1, (short) 4);
            Item_Starting = new ItemStack(Material.valueOf("STAINED_GLASS"), 1, (short) 4);
        }

        //Scénarios
        ItemMeta scenariosM = Item_Scenarios.getItemMeta();
        scenariosM.setDisplayName("§9Scénarios");
        Item_Scenarios.setItemMeta(scenariosM);

        //UHC
        ItemMeta uhcM = Item_Uhc.getItemMeta();
        uhcM.setDisplayName("§2UHC");
        Item_Uhc.setItemMeta(uhcM);

        //Rôles
        ItemMeta rolesM = Item_Roles.getItemMeta();
        rolesM.setDisplayName("§4Rôles");
        Item_Roles.setItemMeta(rolesM);
    }

    @Override
    public Inventory onDisplay(Player player)
    {
        int size = 45;
        Inventory inv_config = Bukkit.createInventory(player, size, "§6§oConfiguration");
        //************************************
        inv_config.setItem((size+1)/2-3, Item_Scenarios);
        inv_config.setItem((size+1)/2-1, Item_Uhc);
        inv_config.setItem((size+1)/2+1, Item_Roles);
        //************************************
        //Contours
        for(int i = 0; i < 9; i ++)
            inv_config.setItem(i, Item_Contours);
        for(int i = 8; i < size; i+=9)
            inv_config.setItem(i, Item_Contours);
        for(int i = 9; i < size; i+=9)
            inv_config.setItem(i, Item_Contours);
        for(int i = size-9; i < size; i ++)
            inv_config.setItem(i, Item_Contours);
        //************************************
        if(MarvelUhc.instance.isState(State.STARTING))
            inv_config.setItem(size-5, Item_Starting);
        else
            inv_config.setItem(size-5, Item_Start);
        return inv_config;
    }

    @Override
    public void handleEvent(InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();

        if(event.getView().getTitle().equalsIgnoreCase("§6§oConfiguration"))
        {
            if(currentItem.isSimilar(Item_Start))
            {
                handleStart(player);
            }
            else if(currentItem.isSimilar(Item_Starting))
            {
                handleStarting(player);
            }
            else if(currentItem.isSimilar(Item_Scenarios))
            {
                player.closeInventory();
                player.openInventory(scenarioUi.onDisplay(player));
            }
            else if(currentItem.isSimilar(Item_Uhc))
            {
                player.closeInventory();
                player.openInventory(uhcUi.onDisplay(player));
            }
            else if(currentItem.isSimilar(Item_Roles)) {
                player.closeInventory();
                player.openInventory(roleUi.onDisplay(player));
            }
            event.setCancelled(true);
        }
        else if(event.getView().getTitle().equalsIgnoreCase("§9§oScénarios"))
        {
            scenarioUi.handleEvent(event);
        }
        else if(event.getView().getTitle().equalsIgnoreCase("§9§oDiamond Limit"))
        {
            scenarioUi.handleDiamondEvent(event);
        }
        else if(event.getView().getTitle().equalsIgnoreCase("§2§oUHC"))
        {
            uhcUi.handleEvent(event);
        }
        else if(event.getView().getTitle().equalsIgnoreCase("§4§oPVP"))
        {
            uhcUi.handlePvp(event);
        }
        else if(event.getView().getTitle().equalsIgnoreCase("§8§oBordure"))
        {
            uhcUi.handleBorder(event);
        }
        else if(event.getView().getTitle().equalsIgnoreCase("§e§oJoueurs Maximum"))
        {
            uhcUi.handleMaxPlayer(event);
        }
        else if(event.getView().getTitle().equalsIgnoreCase("§4§oTemps des rôles"))
        {
            uhcUi.handleRoleTime(event);
        }
        else if(event.getView().getTitle().equalsIgnoreCase("§4§oRôles"))
        {
            event.setCancelled(true);
            //**********************************************************************
        }
        else if(event.getInventory().getItem(4) != null && event.getInventory().getItem(4).isSimilar(Item_ContoursRole))
        {

            /*if(event.getCurrentItem().isSimilar(items_inv.valider)) {
                player.closeInventory();
                player.openInventory(items_inv.InvAllRoles(player));
            }else if(event.getCurrentItem().isSimilar(items_inv.plus)) {
                if(main.countRoles() < main.maxPlayer)
                    main.plusMoinsRoles(1, event.getInventory().getName());
                event.getInventory().setItem((event.getInventory().getSize()+1)/2-1, items_inv.reloadRoles(event.getInventory().getName()));
            }else if(event.getCurrentItem().isSimilar(items_inv.moins)) {
                main.plusMoinsRoles(-1, event.getInventory().getName());
                event.getInventory().setItem((event.getInventory().getSize()+1)/2-1, items_inv.reloadRoles(event.getInventory().getName()));
            }*/
            event.setCancelled(true);
            //**********************************************************************
        }
    }

    private void handleStart(Player player)
    {
        /*if(main.countRoles() == main.Connected.size()) {
            main.setState(State.STARTING);
            //Démarrer la partie
            player.closeInventory();
            player.openInventory(main.items.hostItems.displayUi(player));
            StartingTimer task = new StartingTimer(main, "La téléportation des joueurs");
            task.runTaskTimer(main, 0, 20);
        }else {
            player.sendMessage("§c[Erreur] Le nombre de rôles ne correspond pas au nombre de joueurs !");
        }*/
    }

    private void handleStarting(Player player)
    {
        main.setState(State.CONFIG);
        //Arréter de démarrer la partie
        player.closeInventory();
        player.openInventory( main.items.hostItems.displayUi(player));
    }
}
