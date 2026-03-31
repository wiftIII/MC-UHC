package org.marvel_uhc.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.marvel_uhc.stones.*;

public class ItemManager {

    public final HostItems hostItems;

    // Pierres d'Infinité
    public final Stone spaceStone;
    public final Stone timeStone;
    public final Stone mindStone;
    public final Stone powerStone;
    public final Stone soulStone;
    public final Stone realityStone;

    public ItemManager() {
        hostItems = new HostItems();

        // Initialisation des pierres avec leur apparence
        // Attention : Ces classes (SpaceStone, TimeStone, etc.) vont afficher des erreurs
        // dans IntelliJ tant qu'on ne les aura pas recréées correctement !
        spaceStone = new SpaceStone(createItem(Material.LAPIS_LAZULI, "§bPierre de l'Espace"));
        timeStone = new TimeStone(createItem(Material.EMERALD, "§aPierre du Temps"));
        mindStone = new MindStone(createItem(Material.GLOWSTONE_DUST, "§ePierre de l'Esprit"));
        powerStone = new PowerStone(createItem(Material.AMETHYST_SHARD, "§5Pierre du Pouvoir"));
        soulStone = new SoulStone(createItem(Material.MAGMA_CREAM, "§6Pierre de l'Âme"));
        realityStone = new RealityStone(createItem(Material.REDSTONE, "§cPierre de la Réalité"));
    }

    private ItemStack createItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            item.setItemMeta(meta);
        }
        return item;
    }

    /**
     * Récupère l'objet Stone correspondant à un ItemStack
     */
    public Stone getStoneByItem(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return null;

        String itemName = item.getItemMeta().getDisplayName();

        if (itemName.equals(spaceStone.getItem().getItemMeta().getDisplayName())) return spaceStone;
        if (itemName.equals(timeStone.getItem().getItemMeta().getDisplayName())) return timeStone;
        if (itemName.equals(mindStone.getItem().getItemMeta().getDisplayName())) return mindStone;
        if (itemName.equals(powerStone.getItem().getItemMeta().getDisplayName())) return powerStone;
        if (itemName.equals(soulStone.getItem().getItemMeta().getDisplayName())) return soulStone;
        if (itemName.equals(realityStone.getItem().getItemMeta().getDisplayName())) return realityStone;

        return null;
    }
}