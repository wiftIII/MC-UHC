package org.marvel_uhc.roles.avengers;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.marvel_uhc.MarvelUhc;
import org.marvel_uhc.PlayerData;
import org.marvel_uhc.roles.Camp;
import org.marvel_uhc.roles.Role;

import java.util.Arrays;
import java.util.List;

public class Vision extends Role {

    // Durée du pouvoir de la Pierre de la Réalité en ticks (10 minutes * 60 secondes * 20 ticks)
    private final int REALITY_STONE_DURATION = 10 * 60 * 20;
    private final int REALITY_STONE_COOLDOWN = 600; // 10 minutes en secondes

    public Vision() {
        super("Vision", Camp.AVENGERS, "§6");
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList(
                "§7Vous êtes " + getColor() + getName() + "§7.",
                "§7Votre objectif est de gagner en duo avec §4Wanda§7.",
                "§7",
                "§e§lPassif :",
                "§8- §7À 50 minutes, vous connaîtrez l'identité de §4Wanda§7.",
                "§8- §7Si Wanda meurt, vous devez gagner avec les " + Camp.AVENGERS.getDisplayName() + "§7.",
                "§8- §7Si vous mourez près de Wanda (20 blocs), elle peut vous ressusciter.",
                "§7",
                "§e§lCapacités :",
                "§8- §7Vous possédez la §cPierre de la Réalité§7. Clic Droit pour devenir",
                "  §7invisible (sans particules), obtenir Saut et 4 cœurs d'absorption",
                "  §7pendant 10 minutes. (Recharge : 10 minutes)",
                "§8- §7Utilisez §e/mc planer §7pour activer/désactiver votre vol plané."
        );
    }

    @Override
    protected void setupKit() {
        // On récupère la Pierre de la Réalité depuis l'ItemManager
        if (MarvelUhc.instance.items != null && MarvelUhc.instance.items.realityStone != null) {
            this.kit.add(MarvelUhc.instance.items.realityStone.getItem());
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

    @Override
    public void onRightClickItem(Player player, ItemStack item) {
        // On vérifie si l'item utilisé est bien la Pierre de la Réalité
        if (MarvelUhc.instance.items != null && MarvelUhc.instance.items.realityStone != null) {
            ItemStack realityStone = MarvelUhc.instance.items.realityStone.getItem();

            if (item.isSimilar(realityStone)) {
                if (checkAndApplyCooldown(player, "Pierre de la Réalité", REALITY_STONE_COOLDOWN)) {

                    // Invisibilité (10 min, sans particules : false, false)
                    player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, REALITY_STONE_DURATION, 0, false, false));

                    // Jump Boost (10 min, sans particules)
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, REALITY_STONE_DURATION, 1, false, false));

                    // Absorption (4 cœurs = niveau 1, car niveau 0 = 2 cœurs. Sans particules)
                    player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, REALITY_STONE_DURATION, 1, false, false));

                    player.sendMessage("§6[Vision] §aVous avez altéré la réalité. Vous êtes invisible et renforcé pour 10 minutes.");
                }
            }
        }
    }

    /**
     * Cette méthode sera appelée plus tard par la commande /mc planer
     */
    public void toggleGlide(Player player) {
        PlayerData data = MarvelUhc.instance.GetData(player);
        if (data != null) {
            data.canGlide = !data.canGlide;

            if (data.canGlide) {
                player.sendMessage("§6[Vision] §aMode planage activé. (Tombez pour planer)");
            } else {
                player.sendMessage("§6[Vision] §cMode planage désactivé.");
                player.setGliding(false);
            }
        }
    }
}