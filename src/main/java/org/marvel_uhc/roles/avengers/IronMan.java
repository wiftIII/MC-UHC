package org.marvel_uhc.roles.avengers; // Je te conseille de ranger tes rôles dans des sous-dossiers !

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.marvel_uhc.roles.Camp;
import org.marvel_uhc.roles.Role;

import java.util.Arrays;
import java.util.List;

public class IronMan extends Role {

    public IronMan() {
        // On passe les infos au constructeur de la classe mère (Role)
        super("Iron Man", Camp.AVENGERS, "§c");
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList(
                "§7Vous êtes " + getColor() + getName() + "§7.",
                "§7Votre objectif est de gagner avec les " + getCamp().getDisplayName() + "§7.",
                "§7",
                "§e§lPouvoirs :",
                "§8- §7Vous possédez un §bRépulseur§7 vous permettant de vous propulser en l'air (Clic Droit)."
        );
    }

    @Override
    protected void setupKit() {
        // On prépare l'item de pouvoir spécifique à Iron Man
        ItemStack repulsor = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = repulsor.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§bRépulseur");
            repulsor.setItemMeta(meta);
        }
        this.kit.add(repulsor);
    }

    @Override
    public void onGiveRole(Player player) {
        super.onGiveRole(player); // Garde la ligne de la classe mère qui donne le kit

        // On envoie le message de description au joueur
        player.sendMessage("§8§m--------------------------------------------------");
        for (String line : getDescription()) {
            player.sendMessage(line);
        }
        player.sendMessage("§8§m--------------------------------------------------");
    }

    @Override
    public void onRightClickItem(Player player, ItemStack item) {
        // C'est ici qu'on gère le pouvoir ! Plus besoin de vérifier qui est le joueur dans les Listeners.
        if (item != null && item.hasItemMeta() && item.getItemMeta().getDisplayName().equals("§bRépulseur")) {

            // Logique de propulsion (Vol)
            player.setVelocity(player.getLocation().getDirection().multiply(1.5).setY(1.0));
            player.sendMessage("§b* Propulsion activée ! *");

            // Note : Il faudra gérer les dégâts de chute via ta variable takeFallDamage dans PlayerData
        }
    }
}