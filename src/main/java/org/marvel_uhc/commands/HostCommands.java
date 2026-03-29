package org.marvel_uhc.commands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.GreedyStringArgument;
import org.bukkit.Bukkit;
import org.marvel_uhc.MarvelUhc;
import org.marvel_uhc.State;

public class HostCommands {

    public HostCommands() {
        MarvelUhc main = MarvelUhc.instance;

        // Commande : /host
        new CommandAPICommand("host")
                .executesPlayer((player, args) -> {
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
                })
                .register();

        // Commande : /say <message>
        new CommandAPICommand("say")
                // On demande obligatoirement un texte (GreedyString capte toute la phrase)
                .withArguments(new GreedyStringArgument("message"))
                .executesPlayer((player, args) -> {
                    String messageText = (String) args.get("message");

                    String message = "§6[Host] " + player.getName() + " : §9" + messageText;
                    Bukkit.broadcastMessage("§9§m----------------------------------------------");
                    Bukkit.broadcastMessage(message);
                    Bukkit.broadcastMessage("§9§m----------------------------------------------");
                })
                .register();
    }
}