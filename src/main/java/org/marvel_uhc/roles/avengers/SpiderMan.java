package org.marvel_uhc.roles.avengers;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.marvel_uhc.roles.Camp;
import org.marvel_uhc.roles.Role;

import java.util.Arrays;
import java.util.List;

public class SpiderMan extends Role {

    private final String ITEM_POWER_NAME = "§c§lLance-Toile";

    // Cooldowns en secondes
    private final int COOLDOWN_TOILE_JOUEUR = 300; // 5 minutes
    private final int COOLDOWN_TOILE_BLOC = 60;    // 1 minute

    public SpiderMan() {
        super("Spider-Man", Camp.AVENGERS, "§c");
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList(
                "§7Vous êtes " + getColor() + getName() + "§7.",
                "§7Votre objectif est de gagner avec les " + getCamp().getDisplayName() + "§7.",
                "§7",
                "§e§lPassif :",
                "§8- §7Vous possédez l'effet §cForce 1 §7permanent.",
                "§7",
                "§e§lCapacités (Lance-Toile) :",
                "§8- §7Clic droit sur un §eJoueur §7: L'emprisonne dans une toile d'araignée.",
                "  §7(Recharge : 5 minutes)",
                "§8- §7Clic droit dans le §eVide/sur un Bloc §7: Place une toile à distance.",
                "  §7(Recharge : 1 minute)",
                "§7",
                "§e§lÉvolution :",
                "§8- §7Si vous tuez §2Docteur Octopus§7, vous prendrez sa place, ses effets,",
                "  §7ses pouvoir et deviendrez §cSuperior Spider-Man §7(Camp Super-Vilains)."
        );
    }

    @Override
    protected void setupKit() {
        ItemStack webShooter = new ItemStack(Material.STRING, 1);
        ItemMeta meta = webShooter.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ITEM_POWER_NAME);
            // On ajoute un enchantement caché juste pour l'effet visuel brillant !
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            webShooter.setItemMeta(meta);
        }
        this.kit.add(webShooter);
    }

    @Override
    public void onGiveRole(Player player) {
        super.onGiveRole(player);

        // Effet de Force permanent
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0, false, false));

        player.sendMessage("§8§m--------------------------------------------------");
        for (String line : getDescription()) {
            player.sendMessage(line);
        }
        player.sendMessage("§8§m--------------------------------------------------");
    }

    // 1. Clic droit SUR UN JOUEUR
    @Override
    public void onRightClickEntity(Player player, Entity clickedEntity, ItemStack item) {
        if (item.hasItemMeta() && ITEM_POWER_NAME.equals(item.getItemMeta().getDisplayName())) {

            if (clickedEntity instanceof Player victim) {
                if (checkAndApplyCooldown(player, "Toile sur Joueur", COOLDOWN_TOILE_JOUEUR)) {

                    // On place une toile au niveau de la tête (Y + 1)
                    Block headBlock = victim.getLocation().add(0, 1, 0).getBlock();

                    // On s'assure de ne pas remplacer un bloc dur (comme de la pierre)
                    if (headBlock.getType().isAir() || headBlock.getType() == Material.WATER) {
                        headBlock.setType(Material.COBWEB);
                    }

                    player.sendMessage("§c[Spider-Man] §aVous avez entoilé " + victim.getName() + " !");
                    victim.sendMessage("§c[!] §7Vous avez été piégé par la toile de Spider-Man !");
                }
            }
        }
    }

    // 2. Clic droit DANS LE VIDE / SUR UN BLOC
    @Override
    public void onRightClickItem(Player player, ItemStack item) {
        if (item.hasItemMeta() && ITEM_POWER_NAME.equals(item.getItemMeta().getDisplayName())) {

            // 1. On vérifie D'ABORD la distance (Max 10 blocs)
            Block target = player.getTargetBlockExact(10);

            if (target != null) {
                // 2. On vérifie si la place au-dessus est libre
                Block blockAbove = target.getLocation().add(0, 1, 0).getBlock();

                if (blockAbove.getType().isAir() || blockAbove.getType() == Material.WATER) {

                    // 3. SEULEMENT MAINTENANT on vérifie et applique le cooldown !
                    if (checkAndApplyCooldown(player, "Toile sur Bloc", COOLDOWN_TOILE_BLOC)) {
                        blockAbove.setType(Material.COBWEB);
                        player.sendMessage("§c[Spider-Man] §aToile placée !");
                    }

                } else {
                    player.sendMessage("§c[Spider-Man] §cImpossible de placer une toile ici.");
                }
            } else {
                player.sendMessage("§c[Spider-Man] §cCible trop éloignée (Max 10 blocs).");
            }
        }
    }

    // TODO: Dans le onKill, vérifier si la victime est Docteur Octopus.
    // Si oui, supprimer les items, modifier la vie max à 8 cœurs, donner Résistance et changer le camp.
}