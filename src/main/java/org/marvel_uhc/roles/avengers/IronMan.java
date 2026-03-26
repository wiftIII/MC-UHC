package org.marvel_uhc.roles.avengers;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;
import org.marvel_uhc.MarvelUhc;
import org.marvel_uhc.PlayerData;
import org.marvel_uhc.roles.Camp;
import org.marvel_uhc.roles.Role;

import java.util.Arrays;
import java.util.List;

public class IronMan extends Role {

    private final String ITEM_POWER_NAME = "§c§lPropulseurs";

    public IronMan() {
        super("Iron Man", Camp.AVENGERS, "§6");
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList(
                "§7Vous êtes " + getColor() + getName() + "§7.",
                "§7Votre objectif est de gagner avec les " + getCamp().getDisplayName() + "§7.",
                "§7",
                "§e§lPouvoirs :",
                "§8- §7Vous connaissez un tiers de l'équipe des Avengers.",
                "§8- §7Avec votre Nether Star :",
                "  §8▶ §eClic Droit : §7Vous fait faire un bond de 20 blocs sans dégâts de chute.",
                "  §8▶ §eClic Gauche : §7Active/Désactive le vol plané."
        );
    }

    @Override
    protected void setupKit() {
        // 1. Les 3 Fils
        this.kit.add(new ItemStack(Material.STRING, 3));

        // 2. Les 2 Livres Protection 3
        ItemStack protBook = new ItemStack(Material.ENCHANTED_BOOK, 2);
        EnchantmentStorageMeta protMeta = (EnchantmentStorageMeta) protBook.getItemMeta();
        if (protMeta != null) {
            protMeta.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
            protBook.setItemMeta(protMeta);
        }
        this.kit.add(protBook);

        // 3. Le Livre Flame 1
        ItemStack flameBook = new ItemStack(Material.ENCHANTED_BOOK, 1);
        EnchantmentStorageMeta flameMeta = (EnchantmentStorageMeta) flameBook.getItemMeta();
        if (flameMeta != null) {
            flameMeta.addStoredEnchant(Enchantment.ARROW_FIRE, 1, true);
            flameBook.setItemMeta(flameMeta);
        }
        this.kit.add(flameBook);

        // 4. La Nether Star (Pouvoir)
        ItemStack star = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta starMeta = star.getItemMeta();
        if (starMeta != null) {
            starMeta.setDisplayName(ITEM_POWER_NAME);
            star.setItemMeta(starMeta);
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
        // TODO : Envoyer la liste générée du tiers des Avengers
        player.sendMessage("§8§m--------------------------------------------------");
    }

    @Override
    public void onRightClickItem(Player player, ItemStack item) {
        if (item.hasItemMeta() && item.getItemMeta().getDisplayName().equals(ITEM_POWER_NAME)) {

            // Logique du bond de 20 blocs
            Vector direction = player.getLocation().getDirection();
            direction.multiply(2.5).setY(1.5); // Ajuste ces valeurs si le saut est trop fort ou trop faible
            player.setVelocity(direction);

            // Annulation des dégâts de chute
            PlayerData data = MarvelUhc.instance.GetData(player);
            if (data != null) {
                data.takeFallDamage = false;
            }

            player.sendMessage("§6[Iron Man] §ePropulsion !");
        }
    }

    @Override
    public void onLeftClickItem(Player player, ItemStack item) {
        if (item.hasItemMeta() && item.getItemMeta().getDisplayName().equals(ITEM_POWER_NAME)) {

            PlayerData data = MarvelUhc.instance.GetData(player);
            if (data != null) {
                // On inverse la valeur de canGlide (Si true devient false, si false devient true)
                data.canGlide = !data.canGlide;

                if (data.canGlide) {
                    player.sendMessage("§6[Iron Man] §aMode planage activé. (Tombez pour planer)");
                } else {
                    player.sendMessage("§6[Iron Man] §cMode planage désactivé.");
                    player.setGliding(false); // Force l'arrêt immédiat si on est en l'air
                }
            }
        }
    }
}