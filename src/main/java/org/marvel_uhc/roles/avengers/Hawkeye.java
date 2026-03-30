package org.marvel_uhc.roles.avengers;
import org.marvel_uhc.roles.Camp;
import org.marvel_uhc.roles.Role;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.marvel_uhc.MarvelUhc;

import java.util.Arrays;
import java.util.List;

public class Hawkeye extends Role {

    private final String ITEM_POWER_NAME = "§8§lCarquois Spécial";

    // Cooldowns
    private final int COOLDOWN_FLAME = 300; // 5 minutes
    private final int COOLDOWN_GRAPPLE = 600; // 10 minutes
    private final int DUREE_FLAME = 60 * 20; // 1 minute (en ticks)

    // États actifs
    public boolean isFlameActive = false;
    public boolean isGrappleReady = false;
    private Arrow grappleArrow = null; // Pour mémoriser la flèche en vol

    private enum ArrowType {
        CLASSIQUE("§7Flèches Classiques"),
        ENFLAMMEES("§cFlèches Enflammées (1 min)"),
        GRAPPIN("§dFlèche Grappin (1 tir)");

        final String name;
        ArrowType(String name) { this.name = name; }
    }

    private final ArrowType[] arrowTypes = ArrowType.values();
    private int currentTypeIndex = 0;

    public Hawkeye() {
        super("Hawkeye", Camp.AVENGERS, "§8"); // Gris foncé
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList(
                "§7Vous êtes " + getColor() + getName() + "§7.",
                "§7Votre objectif est de gagner avec les " + getCamp().getDisplayName() + "§7.",
                "§7",
                "§e§lItems :",
                "§8- §73 Fils, 64 Flèches.",
                "§8- §7Livres : Chute Amortie 4 et Puissance 3.",
                "§8- §7Vous pouvez crafter un arc Puissance 4.",
                "§7",
                "§e§lCapacité (Carquois) :",
                "§8- §eClic Gauche : §7Sélectionne le type de flèche.",
                "§8- §eClic Droit : §7Active le pouvoir sélectionné.",
                "  §cFeu : §7Recharge 5 min. §dGrappin : §7Recharge 10 min.",
                "§7",
                "§e§lÉvolution :",
                "§8- §7Si §0Black Widow §7meurt, vous pouvez devenir §8Ronin§7."
        );
    }

    @Override
    protected void setupKit() {
        this.kit.add(new ItemStack(Material.STRING, 3));
        this.kit.add(new ItemStack(Material.ARROW, 64));

        // Livre Feather Falling 4
        ItemStack ffBook = new ItemStack(Material.ENCHANTED_BOOK, 1);
        EnchantmentStorageMeta ffMeta = (EnchantmentStorageMeta) ffBook.getItemMeta();
        if (ffMeta != null) {
            ffMeta.addStoredEnchant(Enchantment.PROTECTION_FALL, 4, true);
            ffBook.setItemMeta(ffMeta);
        }
        this.kit.add(ffBook);

        // Livre Power 3
        ItemStack powerBook = new ItemStack(Material.ENCHANTED_BOOK, 1);
        EnchantmentStorageMeta powerMeta = (EnchantmentStorageMeta) powerBook.getItemMeta();
        if (powerMeta != null) {
            powerMeta.addStoredEnchant(Enchantment.ARROW_DAMAGE, 3, true);
            powerBook.setItemMeta(powerMeta);
        }
        this.kit.add(powerBook);

        // L'item de pouvoir
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

        player.sendMessage("§8§m--------------------------------------------------");
        for (String line : getDescription()) {
            player.sendMessage(line);
        }
        player.sendMessage("§8§m--------------------------------------------------");
        player.sendMessage("§8[Hawkeye] §7Type de flèche actuel : " + arrowTypes[currentTypeIndex].name);
    }

    @Override
    public void onLeftClickItem(Player player, ItemStack item) {
        if (item.hasItemMeta() && ITEM_POWER_NAME.equals(item.getItemMeta().getDisplayName())) {
            currentTypeIndex = (currentTypeIndex + 1) % arrowTypes.length;
            player.sendMessage("§8[Hawkeye] §7Sélection : " + arrowTypes[currentTypeIndex].name);
        }
    }

    @Override
    public void onRightClickItem(Player player, ItemStack item) {
        if (item.hasItemMeta() && ITEM_POWER_NAME.equals(item.getItemMeta().getDisplayName())) {

            ArrowType selected = arrowTypes[currentTypeIndex];

            if (selected == ArrowType.CLASSIQUE) {
                player.sendMessage("§8[Hawkeye] §7Vous utilisez des flèches normales.");

            } else if (selected == ArrowType.ENFLAMMEES) {
                if (checkAndApplyCooldown(player, "Flèches Enflammées", COOLDOWN_FLAME)) {
                    isFlameActive = true;
                    player.sendMessage("§8[Hawkeye] §cFlèches enflammées activées pour 1 minute !");

                    Bukkit.getScheduler().runTaskLater(MarvelUhc.instance, () -> {
                        isFlameActive = false;
                        if (player.isOnline()) player.sendMessage("§8[Hawkeye] §7Vos flèches ne sont plus enflammées.");
                    }, DUREE_FLAME);
                }

            } else if (selected == ArrowType.GRAPPIN) {
                if (checkAndApplyCooldown(player, "Flèche Grappin", COOLDOWN_GRAPPLE)) {
                    isGrappleReady = true;
                    player.sendMessage("§8[Hawkeye] §dFlèche Grappin prête ! Votre prochain tir vous téléportera.");
                }
            }
        }
    }

    @Override
    public void onBowShoot(Player player, Arrow arrow) {
        // Si le mode Feu est actif, on enflamme la flèche (2000 ticks = 100 secondes, large pour le vol)
        if (isFlameActive) {
            arrow.setFireTicks(2000);
        }

        // Si le grappin est prêt, cette flèche devient la flèche grappin !
        if (isGrappleReady) {
            grappleArrow = arrow;
            isGrappleReady = false; // On consomme l'effet
            player.sendMessage("§8[Hawkeye] §dGrappin lancé !");
        }
    }

    @Override
    public void onArrowHit(Player player, Arrow arrow) {
        // Si la flèche qui atterrit est notre flèche grappin
        if (grappleArrow != null && arrow.equals(grappleArrow)) {

            // On téléporte le joueur à l'emplacement exact de la flèche
            // On ajoute +1 en Y pour éviter qu'il ne spawn à moitié dans le sol
            player.teleport(arrow.getLocation().add(0, 1, 0));

            // On annule les dégâts de chute pour son atterrissage
            MarvelUhc.instance.GetData(player).takeFallDamage = false;

            player.sendMessage("§8[Hawkeye] §dZioooop !");

            grappleArrow = null; // On nettoie
            arrow.remove(); // On supprime la flèche pour faire propre
        }
    }

    // TODO: Si Black Widow meurt, proposer à Hawkeye de devenir Ronin (Camp Anti-Héros, arc Power 3 fixe, épée S4, Force perm).
    // TODO: Autoriser le craft de l'arc Power 4 dans le Game Manager spécifiquement pour lui.
}