package org.marvel_uhc.roles.avengers;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.marvel_uhc.roles.Camp;
import org.marvel_uhc.roles.Role;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Thor extends Role {

    private final String WEAPON_NAME = "§e§lStormBreaker";
    private final Random random = new Random();

    public Thor() {
        super("Thor", Camp.AVENGERS, "§e");
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList(
                "§7Vous êtes " + getColor() + getName() + "§7.",
                "§7Votre objectif est de gagner avec les " + getCamp().getDisplayName() + "§7.",
                "§7",
                "§e§lArme (StormBreaker) :",
                "§8- §7Vous possédez une hache en diamant §cTranchant 4§7.",
                "§8- §7Chaque coup porté avec cette arme a §b5% de chance",
                "  §7de foudroyer votre adversaire.",
                "§7",
                "§e§lPassif :",
                "§8- §7À 50 minutes, vous découvrirez l'identité de Loki."
        );
    }

    @Override
    protected void setupKit() {
        ItemStack axe = new ItemStack(Material.DIAMOND_AXE, 1);
        ItemMeta meta = axe.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(WEAPON_NAME);
            axe.setItemMeta(meta);
        }

        // Note pour la 1.21.1 : Si DAMAGE_ALL te fait une erreur, remplace par Enchantment.SHARPNESS
        axe.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 4);

        this.kit.add(axe);
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

    @Override
    public void onAttack(Player attacker, Player victim) {
        // On récupère l'item dans la main principale du joueur
        ItemStack itemInHand = attacker.getInventory().getItemInMainHand();

        // On vérifie s'il tape bien avec StormBreaker
        if (itemInHand.hasItemMeta() && WEAPON_NAME.equals(itemInHand.getItemMeta().getDisplayName())) {

            // Jet de probabilité : 5% de chance (un nombre entre 0 et 99)
            if (random.nextInt(100) < 5) {

                // On génère l'effet de foudre (visuel + son)
                victim.getWorld().strikeLightningEffect(victim.getLocation());

                // On inflige 2.0 points de dégâts supplémentaires (1 cœur plein) car l'effet visuel ne fait pas de dégâts
                victim.damage(2.0, attacker);

                attacker.sendMessage("§e[Thor] §bLa foudre s'abat sur " + victim.getName() + " !");
            }
        }
    }

    // TODO: À 50 minutes, révéler l'identité de Loki
}