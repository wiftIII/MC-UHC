package org.marvel_uhc.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.marvel_uhc.MarvelUhc;
import org.marvel_uhc.State;
import org.marvel_uhc.roles.Role;
import org.marvel_uhc.roles.RoleManager;

public class RoleCommands implements CommandExecutor
{

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        MarvelUhc main = MarvelUhc.instance;

        if(commandSender instanceof Player player)
        {
            if (command.getName().equalsIgnoreCase("iron_man") && MarvelUhc.DEBUG_MODE)
            {
                player.getInventory().clear();
                main.SetRole(player, RoleManager.GetRole(RoleManager.ROLES.IronMan));
            }
            if (command.getName().equalsIgnoreCase("dr_strange") && MarvelUhc.DEBUG_MODE)
            {
                player.getInventory().clear();
                main.SetRole(player, RoleManager.GetRole(RoleManager.ROLES.DrStrange));
            }
        }
        return true;
    }
}
