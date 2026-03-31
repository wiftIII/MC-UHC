package org.marvel_uhc.commands;

import dev.jorel.commandapi.CommandAPICommand;
import org.marvel_uhc.MarvelUhc;
import org.marvel_uhc.roles.Role;
import org.marvel_uhc.roles.RoleManager;
import org.marvel_uhc.stones.Stone;

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
        debugCommand.withSubcommand(createDebugSubCommand("hulk", RoleManager.ROLES.Hulk));
        debugCommand.withSubcommand(createDebugSubCommand("spider_man", RoleManager.ROLES.SpiderMan));
        debugCommand.withSubcommand(createDebugSubCommand("black_panther", RoleManager.ROLES.BlackPanther));
        debugCommand.withSubcommand(createDebugSubCommand("black_widow", RoleManager.ROLES.BlackWidow));
        debugCommand.withSubcommand(createDebugSubCommand("captain_marvel", RoleManager.ROLES.CaptainMarvel));
        debugCommand.withSubcommand(createDebugSubCommand("hawkeye", RoleManager.ROLES.Hawkeye));
        debugCommand.withSubcommand(createDebugSubCommand("vision", RoleManager.ROLES.Vision));
        debugCommand.withSubcommand(createDebugSubCommand("wanda", RoleManager.ROLES.Wanda));

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

        // ========================================================================
        // 3. COMMANDE DE DEBUG DES PIERRES : /mc_debug_stone <pierre>
        // ========================================================================
        CommandAPICommand stoneDebugCommand = new CommandAPICommand("mc_debug_stone")
                .withPermission("op")
                .withShortDescription("Permet de se donner une Pierre d'Infinité pour les tests");

        stoneDebugCommand.withSubcommand(createStoneDebugSubCommand("space"));
        stoneDebugCommand.withSubcommand(createStoneDebugSubCommand("time"));
        stoneDebugCommand.withSubcommand(createStoneDebugSubCommand("mind"));
        stoneDebugCommand.withSubcommand(createStoneDebugSubCommand("power"));
        stoneDebugCommand.withSubcommand(createStoneDebugSubCommand("soul"));
        stoneDebugCommand.withSubcommand(createStoneDebugSubCommand("reality"));

        stoneDebugCommand.register();
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

    /**
     * Méthode utilitaire pour générer les sous-commandes des pierres
     */
    private CommandAPICommand createStoneDebugSubCommand(String stoneId) {
        return new CommandAPICommand(stoneId)
                .executesPlayer((player, args) -> {

                    // Sécurité : ne fonctionne qu'en DEBUG_MODE
                    if (!MarvelUhc.DEBUG_MODE) {
                        player.sendMessage("§cLe mode debug est désactivé.");
                        return;
                    }

                    org.bukkit.inventory.ItemStack stoneItem = null;

                    // On récupère la bonne pierre en fonction de l'argument tapé
                    switch(stoneId) {
                        case "space": stoneItem = MarvelUhc.instance.items.spaceStone.getItem(); break;
                        case "time": stoneItem = MarvelUhc.instance.items.timeStone.getItem(); break;
                        case "mind": stoneItem = MarvelUhc.instance.items.mindStone.getItem(); break;
                        case "power": stoneItem = MarvelUhc.instance.items.powerStone.getItem(); break;
                        case "soul": stoneItem = MarvelUhc.instance.items.soulStone.getItem(); break;
                        case "reality": stoneItem = MarvelUhc.instance.items.realityStone.getItem(); break;
                    }

                    if (stoneItem != null) {

                        // NOUVEAU : On réinitialise le cooldown de cette pierre pour ce joueur !
                        Stone stone = MarvelUhc.instance.items.getStoneByItem(stoneItem); // Ou utilise ton switch pour récupérer l'objet Stone
                        if (stone != null) stone.resetCooldown(player);

                        // On donne l'item au joueur
                        player.getInventory().addItem(stoneItem);
                        String displayName = stoneItem.hasItemMeta() ? stoneItem.getItemMeta().getDisplayName() : stoneId;
                        player.sendMessage("§a[Debug] Vous avez reçu : " + displayName + " §a!");
                    } else {
                        player.sendMessage("§c[Erreur] Impossible de trouver cette pierre. L'ItemManager est-il bien initialisé ?");
                    }
                });
    }
}