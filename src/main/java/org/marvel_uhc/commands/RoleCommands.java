package org.marvel_uhc.commands;

import dev.jorel.commandapi.CommandAPICommand;
import org.marvel_uhc.MarvelUhc;
import org.marvel_uhc.roles.Role;
import org.marvel_uhc.roles.RoleManager;

public class RoleCommands {

    public RoleCommands() {

        // ========================================================================
        // 1. COMMANDE DE DEBUG : /mc_role_debug <role>
        // ========================================================================
        CommandAPICommand debugCommand = new CommandAPICommand("mc_role_debug")
                .withPermission("op") // Seuls les opérateurs peuvent voir/utiliser cette commande
                .withShortDescription("Permet de se donner un rôle pour les tests");

        // On ajoute nos sous-commandes (ex: /mc_role_debug iron_man)
        debugCommand.withSubcommand(createDebugSubCommand("iron_man", RoleManager.ROLES.IronMan));
        debugCommand.withSubcommand(createDebugSubCommand("captain_america", RoleManager.ROLES.CaptainAmerica));
        debugCommand.withSubcommand(createDebugSubCommand("nick_fury", RoleManager.ROLES.NickFury));
        debugCommand.withSubcommand(createDebugSubCommand("dr_strange", RoleManager.ROLES.DrStrange));
        debugCommand.withSubcommand(createDebugSubCommand("thor", RoleManager.ROLES.Thor));

        // On enregistre la commande principale et toutes ses sous-commandes d'un coup !
        debugCommand.register();


        // ========================================================================
        // 2. COMMANDE DES JOUEURS : /mc <pouvoir>
        // ========================================================================
        CommandAPICommand mcCommand = new CommandAPICommand("mc")
                .withShortDescription("Commandes liées à votre rôle Marvel UHC")
                .executesPlayer((player, args) -> {
                    // Si le joueur tape juste /mc sans rien derrière
                    player.sendMessage("§cUtilisation: /mc <pouvoir>");
                });

        // Plus tard, on viendra ajouter ici :
        // mcCommand.withSubcommand(new CommandAPICommand("say")...);
        // mcCommand.withSubcommand(new CommandAPICommand("tp")...);

        mcCommand.register();
    }

    /**
     * Méthode utilitaire pour générer les sous-commandes de debug proprement
     */
    private CommandAPICommand createDebugSubCommand(String name, RoleManager.ROLES roleEnum) {
        return new CommandAPICommand(name)
                .executesPlayer((player, args) -> {

                    // Sécurité : ne fonctionne qu'en DEBUG_MODE
                    if (!MarvelUhc.DEBUG_MODE) {
                        player.sendMessage("§cLe mode debug est désactivé.");
                        return;
                    }

                    player.getInventory().clear();
                    Role role = RoleManager.getRoleInstance(roleEnum);

                    if (role != null) {
                        MarvelUhc.instance.SetRole(player, role);
                        player.sendMessage("§a[Debug] Rôle " + role.getName() + " forcé.");
                    } else {
                        player.sendMessage("§c[Erreur] Ce rôle n'est pas encore développé !");
                    }
                });
    }
}