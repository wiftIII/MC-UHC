package org.marvel_uhc.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.marvel_uhc.MarvelUhc;
import org.marvel_uhc.State;

public class HostCommands implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        MarvelUhc main = MarvelUhc.instance; // Plus propre que de faire une méthode

        if (commandSender instanceof Player player) {

            // Commande : /host
            if (command.getName().equalsIgnoreCase("host")) {
                if (main.isState(State.CONFIG)) {
                    if (player.getInventory().contains(main.items.hostItems.Item_Config)) {
                        player.sendMessage("§c[Erreur] Vous avez déjà reçu votre item de configuration !");
                    } else {
                        player.getInventory().setItem(0, main.items.hostItems.Item_Config);
                        player.sendMessage("§a[UHC] Vous avez reçu votre item de configuration !");
                    }
                } else {
                    player.sendMessage("§c[Erreur] La partie a démarré !");
                }
                return true;
            }

            // Commande : /say
            if (command.getName().equalsIgnoreCase("say")) {
                if (strings.length == 0) {
                    player.sendMessage("§c[Erreur] La commande est §6/say (message) §9!");
                } else {
                    StringBuilder message = new StringBuilder("§6[Host] " + player.getName() + " : §9");
                    for (String m : strings) {
                        message.append(m).append(" ");
                    }
                    Bukkit.broadcastMessage("§9§m----------------------------------------------");
                    Bukkit.broadcastMessage(message.toString());
                    Bukkit.broadcastMessage("§9§m----------------------------------------------");
                }
                return true;
            }
        }
        return false;
    }
}