package org.marvel_uhc.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.marvel_uhc.MarvelUhc;
import org.marvel_uhc.State;

public class ConnexionListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent event) {
        MarvelUhc main = MarvelUhc.instance;
        Player p = event.getPlayer();

        // 1. On crée les données du joueur (PlayerData)
        main.AddPlayer(p);

        // 2. Comportement selon l'état de la partie
        if (main.isState(State.CONFIG)) {
            // Avant le lancement : on remet le joueur à zéro
            event.setJoinMessage("§a[UHC] §b" + p.getName() + " vient de se connecter ! §a(" + Bukkit.getOnlinePlayers().size() + "/" + main.configuration.maxPlayer + ")");

            p.setGameMode(GameMode.ADVENTURE); // Ou SURVIVAL selon ton lobby
            p.setHealth(20.0);
            p.setFoodLevel(20);
            p.setLevel(0);
            p.setExp(0);

            // Nettoyage de l'inventaire
            p.getInventory().clear();
            p.getInventory().setArmorContents(null);

            // Optionnel : donner l'item de configuration si le joueur est OP (Host)
            if (p.isOp()) {
                p.getInventory().setItem(4, main.items.hostItems.Item_Config);
            }

        } else {
            // Si la partie a déjà commencé (reconnexion en cours de jeu)
            event.setJoinMessage("§a[UHC] §b" + p.getName() + " vient de se reconnecter !");
        }

        /* * Note : La gestion du Tab et du Scoreboard a été retirée pour éviter les erreurs.
         * On pourra recréer des classes propres pour ça plus tard !
         */
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        MarvelUhc main = MarvelUhc.instance; // Il manquait la déclaration de 'main' ici !
        Player p = event.getPlayer();

        // On gère le message de déconnexion selon l'état
        if (main.isState(State.CONFIG)) {
            // Note: on fait -1 car le joueur est encore compté dans getOnlinePlayers() au moment où l'event se déclenche
            event.setQuitMessage("§a[UHC] §b" + p.getName() + " vient de se déconnecter ! §a(" + (Bukkit.getOnlinePlayers().size() - 1) + "/" + main.configuration.maxPlayer + ")");
        } else {
            event.setQuitMessage("§a[UHC] §b" + p.getName() + " vient de se déconnecter ! Il peut encore se reconnecter.");
        }
    }
}