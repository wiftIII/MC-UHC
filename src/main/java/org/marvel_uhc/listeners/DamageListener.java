package org.marvel_uhc.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.marvel_uhc.MarvelUhc;
import org.marvel_uhc.PlayerData;
import org.marvel_uhc.State;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageListener implements Listener {

    private final MarvelUhc main;

    public DamageListener() {
        main = MarvelUhc.instance;
    }

    // Mort d'un joueur
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();

        // 1. Délégation à la Pierre de l'Âme (ton code existant)
        main.items.soulStone.onPlayerDeath(event);

        // 2. NOUVEAU : Délégation au Rôle du joueur mort
        PlayerData victimData = main.GetData(player);
        if (victimData != null && victimData.role != null) {
            victimData.role.onDeath(player, killer);
        }

        // 3. NOUVEAU : Délégation au Rôle du tueur (s'il y en a un)
        if (killer != null) {
            PlayerData killerData = main.GetData(killer);
            if (killerData != null && killerData.role != null) {
                killerData.role.onKill(killer, player);
            }
        }
    }

    // Dégâts subis
    @EventHandler(priority = EventPriority.HIGH)
    public void OnDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        PlayerData playerData = main.GetData(player);

        // Annulation des dégâts avant le jeu
        if ((main.isState(State.CONFIG) || main.isState(State.STARTING) || main.isState(State.INVINCIBILITY)) && !MarvelUhc.DEBUG_MODE) {
            event.setCancelled(true);
            return;
        }

        DamageCause cause = event.getCause();

        // Scénario : NoFire
        if ((cause == DamageCause.FIRE || cause == DamageCause.FIRE_TICK || cause == DamageCause.LAVA) && main.configuration.noFire) {
            event.setCancelled(true);
            return;
        }

        // Dégâts de chute custom (ex: atterrissage d'Iron Man)
        if (cause == DamageCause.FALL && (main.configuration.noFall || !playerData.takeFallDamage)) {
            playerData.takeFallDamage = true; // On reset après la chute
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player attacker && event.getEntity() instanceof Player victim) {

            // 1. On prévient l'attaquant (Pour Thor, etc.)
            PlayerData attackerData = main.GetData(attacker);
            if (attackerData != null && attackerData.role != null) {
                attackerData.role.onAttack(attacker, victim);
            }

            // 2. On prévient la victime (Pour Black Panther, etc.)
            PlayerData victimData = main.GetData(victim);
            if (victimData != null && victimData.role != null) {
                // On lui passe l'événement complet au cas où un rôle aurait besoin d'annuler le coup !
                victimData.role.onDamageReceived(victim, attacker, event.getDamage(), event);
            }
        }
    }
}