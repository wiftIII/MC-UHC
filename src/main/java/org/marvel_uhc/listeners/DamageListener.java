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
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.Sound;
import org.marvel_uhc.stones.SoulStone;

public class DamageListener implements Listener {

    private final MarvelUhc main;

    public DamageListener() {
        main = MarvelUhc.instance;
    }

    // ==========================================
    // 1. INTERCEPTION DE LA MORT (RÉSURRECTION)
    // ==========================================
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFatalDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {

            // Si les dégâts que le joueur va prendre sont supérieurs ou égaux à sa vie restante
            if (event.getFinalDamage() >= player.getHealth()) {

                // Vérification de la Pierre de l'Âme
                if (main.items != null && main.items.soulStone instanceof SoulStone soulStone) {

                    // Si l'UUID du joueur qui va mourir est celui sauvegardé dans la pierre
                    if (soulStone.savedPlayerId != null && soulStone.savedPlayerId.equals(player.getUniqueId())) {

                        // 1. ON ANNULE LE COUP FATAL !
                        event.setCancelled(true);

                        // 2. On le soigne (il revient à 10 cœurs / 20 HP)
                        player.setHealth(20.0);

                        // 3. On vide la pierre (usage unique)
                        soulStone.savedPlayerId = null;

                        // 4. Effets visuels et sonores (comme un Totem)
                        player.sendMessage("§6[Pierre de l'Âme] §aL'énergie de la pierre a refusé votre mort ! Vous êtes ressuscité.");
                        player.getWorld().playSound(player.getLocation(), Sound.ITEM_TOTEM_USE, 1.0f, 1.0f);

                        // On "return" pour arrêter l'exécution ici : le joueur est sauvé !
                        return;
                    }
                }

                // (Plus tard, on ajoutera la survie de Wanda et Deadpool ici)
            }
        }
    }


    // ==========================================
    // 2. LA VRAIE MORT (Conséquences)
    // ==========================================
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();

        // Plus besoin de la pierre de l'âme ici, car si le joueur arrive à cette étape,
        // c'est que son âme n'a pas été sauvée par l'événement au-dessus !

        // 1. Délégation au Rôle du joueur mort (ex: Prévenir Hawkeye si Black Widow meurt)
        PlayerData victimData = main.GetData(player);
        if (victimData != null && victimData.role != null) {
            victimData.role.onDeath(player, killer);
        }

        // 2. Délégation au Rôle du tueur (ex: Spiderman qui tue Docteur Octopus)
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

        // 1. Si la VICTIME est un joueur
        if (event.getEntity() instanceof Player victim) {

            // On vérifie si la victime doit perdre son invisibilité
            if (main.items != null && main.items.realityStone instanceof org.marvel_uhc.stones.RealityStone realityStone) {
                realityStone.breakInvisibility(victim);
            }

            // 2. Si l'ATTAQUANT est aussi un joueur
            if (event.getDamager() instanceof Player attacker) {

                // On vérifie si l'attaquant doit perdre son invisibilité
                if (main.items != null && main.items.realityStone instanceof org.marvel_uhc.stones.RealityStone rStone) {
                    rStone.breakInvisibility(attacker);
                }

                // ... (Garde ton code existant en dessous pour prévenir les rôles avec onAttack et onDamageReceived)
                PlayerData attackerData = main.GetData(attacker);
                if (attackerData != null && attackerData.role != null) {
                    attackerData.role.onAttack(attacker, victim);
                }

                PlayerData victimData = main.GetData(victim);
                if (victimData != null && victimData.role != null) {
                    victimData.role.onDamageReceived(victim, attacker, event.getDamage(), event);
                }
            }
        }
    }
}