package org.marvel_uhc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.marvel_uhc.MarvelUhc;
import org.marvel_uhc.roles.RoleManager;
import org.marvel_uhc.roles.Role;

public class RoleCommands implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        MarvelUhc main = MarvelUhc.instance;

        if (commandSender instanceof Player player) {

            // --- COMMANDES DE DEBUG (Actives uniquement en DEBUG_MODE) ---
            if (MarvelUhc.DEBUG_MODE) {

                if (command.getName().equalsIgnoreCase("iron_man")) {
                    player.getInventory().clear();
                    // On utilise la nouvelle méthode getRoleInstance !
                    Role ironManRole = RoleManager.getRoleInstance(RoleManager.ROLES.IronMan);
                    if (ironManRole != null) {
                        main.SetRole(player, ironManRole);
                        player.sendMessage("§a[Debug] Rôle Iron Man forcé.");
                    }
                    return true;
                }

                if (command.getName().equalsIgnoreCase("captain_america")) {
                    player.getInventory().clear();
                    Role capRole = RoleManager.getRoleInstance(RoleManager.ROLES.CaptainAmerica);
                    if (capRole != null) {
                        main.SetRole(player, capRole);
                        player.sendMessage("§a[Debug] Rôle Captain America forcé.");
                    }
                    return true;
                }

                if (command.getName().equalsIgnoreCase("nick_fury")) {
                    player.getInventory().clear();
                    Role furyRole = RoleManager.getRoleInstance(RoleManager.ROLES.NickFury);
                    if (furyRole != null) {
                        main.SetRole(player, furyRole);
                        player.sendMessage("§a[Debug] Rôle Nick Fury forcé.");
                    }
                    return true;
                }

                if (command.getName().equalsIgnoreCase("dr_strange")) {
                    player.getInventory().clear();
                    Role strangeRole = RoleManager.getRoleInstance(RoleManager.ROLES.DrStrange);
                    if (strangeRole != null) {
                        main.SetRole(player, strangeRole);
                        player.sendMessage("§a[Debug] Rôle Dr Strange forcé.");
                    }
                    return true;
                }

            }

            // --- FUTURES COMMANDES DE POUVOIRS ICI (Ex: /shield, /web) ---

        }
        return true;
    }
}