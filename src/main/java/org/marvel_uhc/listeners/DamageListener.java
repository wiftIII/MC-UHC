package org.marvel_uhc.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.marvel_uhc.MarvelUhc;
import org.marvel_uhc.PlayerData;
import org.marvel_uhc.State;

import java.util.logging.Level;

public class DamageListener implements Listener
{

    private final MarvelUhc main;

    public DamageListener()
    {
        main = MarvelUhc.instance;
    }


    //Mort d'un joueur
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        main.items.soulStone.onPlayerDeath(event);
    }

    // N'importe quel dégat
    @EventHandler(priority = EventPriority.HIGH)
    public void OnDamage(EntityDamageEvent event) {
        Entity victim = event.getEntity();
        DamageCause cause = event.getCause();

        if (victim instanceof Player player)
        {
            PlayerData playerData = main.GetData(player);
            if ((main.isState(State.CONFIG) || main.isState(State.STARTING) || main.isState(State.INVINCIBILITY)) && !MarvelUhc.DEBUG_MODE)
            {
                event.setCancelled(true); // Pas de dégats pris dans la phase de préparation ou de lancement ou d'invincibilité
            }
            else {

                //Fire
                if ((((cause.equals(DamageCause.FIRE) || cause.equals(DamageCause.FIRE_TICK) || cause.equals(DamageCause.LAVA)) && main.configuration.noFire)))
                {
                    event.setCancelled(true); // Explicite
                }
                //Fall
                if((cause.equals(DamageCause.FALL)) && (main.configuration.noFall || !playerData.takeFallDamage ))
                {
                    main.getLogger().log(Level.INFO, "Fall damage : " + (playerData.takeFallDamage ? "true" : "false"));
                    playerData.takeFallDamage = true;
                    event.setCancelled(true); // Explicite
                }
                /*if (!cause.equals(DamageCause.ENTITY_ATTACK) && !cause.equals(DamageCause.PROJECTILE)) {
                    if (player.getHealth() <= event.getDamage()) {
                        // Check si le player est mort
                        if (!cause.equals(DamageCause.VOID)) {
                            main.findPlayer(player).setDying(true);
                            main.findPlayer(player).death_location = player.getLocation();
                            DyingTimer task = new DyingTimer(main, main.findPlayer(player));
                            task.runTaskTimer(main, 0, 20);
                            event.setDamage(0);
                            player.setHealth(20);
                            player.setGameMode(GameMode.SPECTATOR);
                        }
                    }
                }*/
            }
        }
    }
}
