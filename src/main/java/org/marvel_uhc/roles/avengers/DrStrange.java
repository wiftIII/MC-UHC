package org.marvel_uhc.roles.avengers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.marvel_uhc.MarvelUhc;
import org.marvel_uhc.roles.Camp;
import org.marvel_uhc.roles.Role;

import java.util.Arrays;
import java.util.List;

public class DrStrange extends Role {

    // Variable pour suivre le nombre d'utilisations de son pouvoir
    public int tpRemaining = 2;

    public DrStrange() {
        super("Dr Strange", Camp.AVENGERS, "§6"); // §6 pour l'orange/or
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList(
                "§7Vous êtes " + getColor() + getName() + "§7.",
                "§7Votre objectif est de gagner avec les " + getCamp().getDisplayName() + "§7.",
                "§7",
                "§e§lItems :",
                "§8- §7Vous êtes le gardien de la §aPierre du Temps§7.",
                "§8- §7Vous recevez §d3 Ender Pearls§7.",
                "§7",
                "§e§lCapacité (Portail Magique) :",
                "§8- §7Deux fois par partie, utilisez la commande §e/m tp <Pseudo>",
                "  §7pour téléporter un Avenger à vos côtés."
        );
    }

    @Override
    protected void setupKit() {
        // 1. Les 3 Ender Pearls
        this.kit.add(new ItemStack(Material.ENDER_PEARL, 3));

        // 2. La Pierre du Temps (On la récupère directement de ton ItemManager !)
        if (MarvelUhc.instance.items != null && MarvelUhc.instance.items.timeStone != null) {
            this.kit.add(MarvelUhc.instance.items.timeStone.getItem().clone());
        }
    }

    @Override
    public void onGiveRole(Player player) {
        super.onGiveRole(player);

        player.sendMessage("§8§m--------------------------------------------------");
        for (String line : getDescription()) {
            player.sendMessage(line);
        }
        player.sendMessage("§8§m--------------------------------------------------");
    }

    // --- FUTURES FONCTIONNALITÉS POUR DR STRANGE ---

    // TODO: Créer la commande /m tp (Vérifier que la cible est un Avenger, qu'il reste des tpRemaining, et consommer une utilisation)
}