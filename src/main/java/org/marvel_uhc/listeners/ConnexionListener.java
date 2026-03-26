package org.marvel_uhc.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.marvel_uhc.MarvelUhc;

import java.util.logging.Level;

public class ConnexionListener implements Listener
{

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent event)
    {
        MarvelUhc main = MarvelUhc.instance;
        Player p = event.getPlayer();

        main.AddPlayer(p);
        /*for (org.bukkit.entity.Player op : Bukkit.getOnlinePlayers()) {
            Tab.sendTablist(op, main, false);
        }

        if(main.contains(main.Ingame, p))
            main.Connected.add(main.findPlayer(p));
        else {
            Player player = new Player(p, main);
            main.Connected.add(player);
        }

        // Check du moment de la connexion
        if(main.isState(State.CONFIG)) {
            event.setJoinMessage("§a[UHC] §b"+ p.getName()+" vient de se connecter ! §a(" + main.Connected.size() + "/" + main.maxPlayer + ")");
            p.setGameMode(GameMode.ADVENTURE);
            p.setMaxHealth(20); // Cas du LGB
            p.setHealth(20); // 20 = full life
            p.setFoodLevel(20); // 20 = full food
            p.setLevel(0); p.setExp(0);
            p.getInventory().clear();
            items_inv.clearArmor(p);
        }else if(main.contains(main.Ingame, p))
            event.setJoinMessage("§a[UHC] §b"+ p.getName()+" vient de se reconnecter !");
        else
            event.setJoinMessage("");

        //**********************************************************************
        // Scoreboard

        Scoreboard sb = new Scoreboard(p);
        sb.sendLine();
        sb.set();
        main.updateScoreBoard();*/

    }

    //***********************************************************************

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        org.bukkit.entity.Player p = event.getPlayer();
        String name = p.getName();
        /*for(int i = 0; i < main.Connected.size(); i++) {
            if(main.Connected.get(i).getName() == name)
                main.Connected.remove(i);
        }
        if(!main.game_started)
            event.setQuitMessage("§a[UHC] §b"+ p.getName()+" vient de se déconnecter ! §a(" + main.Connected.size() + "/" + main.maxPlayer + ")");
        else if(main.contains(main.Ingame, p))
            event.setQuitMessage("§a[UHC] §b"+ p.getName()+" vient de se déconnecter ! Il peut encore se reconnecter.");
        else
            event.setQuitMessage("");

        //Scoreboard
        main.updateScoreBoard();*/
    }
}
