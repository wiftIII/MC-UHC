package org.marvel_uhc.roles.avengers;
import org.marvel_uhc.roles.Camp;
import org.marvel_uhc.roles.Role;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BlackPanther extends Role {

    // On stocke le nombre de coups reçus par chaque attaquant (UUID du joueur -> Nombre de coups)
    private final Map<UUID, Integer> hitCounters = new HashMap<>();

    public BlackPanther() {
        // Le Noir (§0) est parfois illisible dans le chat de Minecraft, tu peux mettre "§8" (Gris Foncé) si tu préfères !
        super("Black Panther", Camp.AVENGERS, "§8");
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList(
                "§7Vous êtes " + getColor() + getName() + "§7.",
                "§7Votre objectif est de gagner avec les " + getCamp().getDisplayName() + "§7.",
                "§7",
                "§e§lItems :",
                "§8- §7Vous recevez un livre enchanté §bProtection 3 §7(Pièce en Diamant).",
                "§7",
                "§e§lPouvoirs Passifs (Vibranium) :",
                "§8- §7Vous possédez l'effet §bVitesse 1 §7en permanence.",
                "§8- §7Tous les §c4 coups reçus§7, votre adversaire subit les dégâts",
                "  §7exacts du 4ème coup qu'il vient de vous infliger."
        );
    }

    @Override
    protected void setupKit() {
        // Le Livre Protection 3 (Même technique que pour Iron Man !)
        ItemStack protBook = new ItemStack(Material.ENCHANTED_BOOK, 1);
        EnchantmentStorageMeta protMeta = (EnchantmentStorageMeta) protBook.getItemMeta();
        if (protMeta != null) {
            protMeta.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
            protBook.setItemMeta(protMeta);
        }
        this.kit.add(protBook);
    }

    @Override
    public void onGiveRole(Player player) {
        super.onGiveRole(player);

        // Effet permanent de Vitesse (Vitesse 1 = amplificateur 0)
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, false, false));

        player.sendMessage("§8§m--------------------------------------------------");
        for (String line : getDescription()) {
            player.sendMessage(line);
        }
        player.sendMessage("§8§m--------------------------------------------------");
    }

    @Override
    public void onDamageReceived(Player victim, Player attacker, double damage, EntityDamageByEntityEvent event) {
        UUID attackerId = attacker.getUniqueId();

        // On récupère le nombre de coups actuels (ou 0 s'il n'a jamais tapé)
        int currentHits = hitCounters.getOrDefault(attackerId, 0);

        // On ajoute le nouveau coup
        currentHits++;

        if (currentHits >= 4) {
            // C'est le 4ème coup ! On renvoie les dégâts
            attacker.damage(damage, victim);

            // Petits effets visuels/sonores optionnels pour que ce soit cool
            victim.sendMessage(getColor() + "[Black Panther] §aVotre armure renvoie l'attaque de " + attacker.getName() + " !");
            attacker.sendMessage("§c[!] L'armure en vibranium de Black Panther renvoie votre coup !");

            // On remet le compteur à zéro
            hitCounters.put(attackerId, 0);
        } else {
            // Ce n'est pas encore le 4ème coup, on sauvegarde le nouveau compte
            hitCounters.put(attackerId, currentHits);
        }
    }
}