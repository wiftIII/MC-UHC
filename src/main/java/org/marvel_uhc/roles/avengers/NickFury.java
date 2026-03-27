package org.marvel_uhc.roles.avengers;

import org.bukkit.entity.Player;
import org.marvel_uhc.roles.Camp;
import org.marvel_uhc.roles.Role;

import java.util.Arrays;
import java.util.List;

public class NickFury extends Role {

    public NickFury() {
        // Dans ton ancien RoleBuilder tu avais mis '0' (Noir).
        // Le noir est parfois illisible dans le chat, tu peux mettre "§8" (Gris foncé) si tu préfères !
        super("Nick Fury", Camp.AVENGERS, "§0");
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList(
                "§7Vous êtes " + getColor() + getName() + "§7.",
                "§7Votre objectif est de gagner avec les " + getCamp().getDisplayName() + "§7.",
                "§7",
                "§e§lPouvoirs Passifs :",
                "§8- §7Vous connaissez l'identité de §cIron Man §7et §9Captain America§7.",
                "§8- §7Vous connaissez l'emplacement des §dPierres d'Infinité §71 minute avant Thanos.",
                "§7",
                "§e§lCapacité (Communication) :",
                "§8- §7À partir de 70 minutes (et toutes les 10 min), utilisez §e/m say <message>",
                "  §7pour contacter tous les Avengers secrètement.",
                "  §cAttention : §7Ultron interceptera ce message et pourra le modifier."
        );
    }

    @Override
    protected void setupKit() {
        // Le document ne mentionne aucun item spécifique pour Nick Fury à l'annonce des rôles.
        // On laisse donc son kit vide. Il devra se stuffer comme un grand !
    }

    @Override
    public void onGiveRole(Player player) {
        super.onGiveRole(player);

        player.sendMessage("§8§m--------------------------------------------------");
        for (String line : getDescription()) {
            player.sendMessage(line);
        }

        // TODO: Envoyer l'identité de Iron Man et Captain America dans le chat du joueur

        player.sendMessage("§8§m--------------------------------------------------");
    }

    // --- FUTURES FONCTIONNALITÉS POUR NICK FURY ---

    // TODO: Dans le Timer UHC, à l'apparition d'une pierre, envoyer les coordonnées à Nick Fury (1 min avant Thanos)

    /**
     * Méthode qui sera appelée par la future commande /m say
     */
    public void sendAvengersMessage(Player sender, String message) {
        // TODO: Vérifier si on est à + de 70 minutes de jeu
        // TODO: Vérifier si le cooldown de 10 minutes est respecté
        // TODO: Envoyer le message à tous les joueurs ayant un rôle du Camp.AVENGERS
        // TODO: Envoyer secrètement le message à Ultron
    }
}