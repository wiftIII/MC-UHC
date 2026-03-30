package org.marvel_uhc.roles.avengers;
import org.marvel_uhc.roles.Camp;
import org.marvel_uhc.roles.Role;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.marvel_uhc.MarvelUhc;

import java.util.Arrays;
import java.util.List;

public class BlackWidow extends Role {

    // On garde une trace de la tâche pour pouvoir l'arrêter si besoin (mort, fin de partie)
    private BukkitTask timeCheckTask;
    // Permet de savoir si on a déjà appliqué les effets pour ne pas spammer le serveur
    private boolean isNightBuffActive = false;

    public BlackWidow() {
        // Comme pour Nick Fury et Black Panther, je te conseille "§8" (Gris Foncé) au lieu de "§0" (Noir) pour la lisibilité
        super("Black Widow", Camp.AVENGERS, "§8");
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList(
                "§7Vous êtes " + getColor() + getName() + "§7.",
                "§7Votre objectif est de gagner avec les " + getCamp().getDisplayName() + "§7.",
                "§7",
                "§e§lPouvoirs Passifs :",
                "§8- §7Pendant la §9nuit§7, vous obtenez §bVitesse 1 §7et §dVision Nocturne§7.",
                "§8- §7À 50 minutes, vous connaîtrez l'identité de §8Nick Fury§7.",
                "§7",
                "§e§lLien (Hawkeye) :",
                "§8- §7Si vous mourez, Hawkeye pourra choisir de devenir Ronin."
        );
    }

    @Override
    protected void setupKit() {
        // Aucun item spécifique de départ pour Black Widow dans le document !
    }

    @Override
    public void onGiveRole(Player player) {
        super.onGiveRole(player);

        player.sendMessage("§8§m--------------------------------------------------");
        for (String line : getDescription()) {
            player.sendMessage(line);
        }
        player.sendMessage("§8§m--------------------------------------------------");

        // On lance la vérification du temps qui va tourner en boucle
        startTimeCheck(player);
    }

    private void startTimeCheck(Player player) {
        // Cette tâche va s'exécuter toutes les 20 ticks (1 seconde)
        timeCheckTask = Bukkit.getScheduler().runTaskTimer(MarvelUhc.instance, () -> {

            // Sécurité : si le joueur se déconnecte, on met en pause
            if (!player.isOnline()) return;

            // Dans Minecraft, la nuit commence environ à 13000 et finit à 23000
            long time = player.getWorld().getTime();
            boolean isNight = (time >= 13000 && time < 23000);

            // Si c'est la nuit et qu'elle n'a pas encore ses buffs
            if (isNight && !isNightBuffActive) {
                isNightBuffActive = true;

                // On applique les effets en "infini" (Integer.MAX_VALUE) sans particules
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, false, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false));

                player.sendMessage("§8[Black Widow] §9La nuit tombe, vos sens d'espionne s'éveillent...");
            }
            // Si c'est le jour et qu'elle a encore ses buffs de la nuit
            else if (!isNight && isNightBuffActive) {
                isNightBuffActive = false;

                // On lui retire les effets
                player.removePotionEffect(PotionEffectType.SPEED);
                player.removePotionEffect(PotionEffectType.NIGHT_VISION);

                player.sendMessage("§8[Black Widow] §eLe jour se lève, vous perdez vos avantages nocturnes.");
            }

        }, 0L, 20L); // Délai initial 0, répétition toutes les 20 ticks
    }

    // TODO: A 50 minutes, révéler l'identité de Nick Fury
    // TODO: Dans le onDeath, prévenir la classe de Hawkeye pour lui proposer de devenir Ronin
}