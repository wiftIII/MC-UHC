package org.marvel_uhc.roles.avengers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.marvel_uhc.MarvelUhc;
import org.marvel_uhc.roles.Camp;
import org.marvel_uhc.roles.Role;

import java.util.Arrays;
import java.util.List;

public class Hulk extends Role {

    private final String ITEM_POWER_NAME = "§2§lDéchaînement";
    private final int COOLDOWN_TRANSFORMATION = 300; // 5 minutes (en secondes)
    private final int DUREE_TRANSFORMATION = 600; // 30 secondes (en ticks, 30 * 20 = 600)

    // Variable pour savoir s'il a choisi de devenir Professeur Hulk
    public boolean isProfessorHulk = false;

    public Hulk() {
        super("Hulk", Camp.AVENGERS, "§2");
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList(
                "§7Vous êtes " + getColor() + getName() + "§7.",
                "§7Votre objectif est de gagner avec les " + getCamp().getDisplayName() + "§7.",
                "§7",
                "§e§lPassif :",
                "§8- §7Vous possédez l'effet §8Faiblesse §7en permanence.",
                "§7",
                "§e§lCapacité (Déchaînement) :",
                "§8- §7Avec votre Nether Star (Clic Droit) : Vous vous transformez pendant §a30s§7.",
                "  §7Vous gagnez Force, Résistance, Vitesse, Régénération, Saut et",
                "  §72 cœurs d'absorption. (Recharge : 5 min)",
                "§7",
                "§e§lÉvolution (70 minutes) :",
                "§8- §7Vous pourrez choisir de devenir §aProfesseur Hulk§7.",
                "  §7Vous perdrez votre Faiblesse et obtiendrez 2 effets aléatoires",
                "  §7permanents, mais vous ne pourrez plus vous transformer."
        );
    }

    @Override
    protected void setupKit() {
        ItemStack star = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta meta = star.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ITEM_POWER_NAME);
            star.setItemMeta(meta);
        }
        this.kit.add(star);
    }

    @Override
    public void onGiveRole(Player player) {
        super.onGiveRole(player);

        // On lui donne son malus de base (Faiblesse 1, temps infini)
        applyWeakness(player);

        player.sendMessage("§8§m--------------------------------------------------");
        for (String line : getDescription()) {
            player.sendMessage(line);
        }
        player.sendMessage("§8§m--------------------------------------------------");
    }

    @Override
    public void onRightClickItem(Player player, ItemStack item) {
        if (item.hasItemMeta() && ITEM_POWER_NAME.equals(item.getItemMeta().getDisplayName())) {

            // S'il est devenu Professeur Hulk, le pouvoir ne marche plus !
            if (isProfessorHulk) {
                player.sendMessage("§2[Hulk] §cEn tant que Professeur Hulk, vous avez renoncé à la rage.");
                return;
            }

            if (checkAndApplyCooldown(player, "Déchaînement", COOLDOWN_TRANSFORMATION)) {

                // 1. On lui enlève la Faiblesse
                player.removePotionEffect(PotionEffectType.WEAKNESS);

                // 2. On applique les gros buffs de la transformation
                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, DUREE_TRANSFORMATION, 0, false, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, DUREE_TRANSFORMATION, 0, false, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, DUREE_TRANSFORMATION, 0, false, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, DUREE_TRANSFORMATION, 0, false, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, DUREE_TRANSFORMATION, 0, false, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, DUREE_TRANSFORMATION, 0, false, false));

                player.sendMessage("§2[Hulk] §a§lHULK SMASH !!! §7(Transformation : 30s)");

                // 3. On programme le retour à la normale dans 30 secondes
                Bukkit.getScheduler().runTaskLater(MarvelUhc.instance, () -> {
                    // On vérifie que le joueur est toujours connecté
                    if (player.isOnline()) {
                        player.sendMessage("§2[Hulk] §7La rage retombe... Vous reprenez votre forme normale.");

                        // S'il n'est pas devenu Professeur Hulk entre temps, on lui remet sa faiblesse
                        if (!isProfessorHulk) {
                            applyWeakness(player);
                        }
                    }
                }, DUREE_TRANSFORMATION);
            }
        }
    }

    /**
     * Petite méthode pour lui appliquer sa faiblesse sans spammer le code
     */
    public void applyWeakness(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE, 0, false, false));
    }

    // TODO: A 70 minutes de jeu, proposer au joueur de devenir Professeur Hulk via un menu interactif ou un message cliquable
}