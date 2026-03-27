package org.marvel_uhc.roles.avengers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.marvel_uhc.roles.Camp;
import org.marvel_uhc.roles.Role;

import java.util.Arrays;
import java.util.List;

public class CaptainAmerica extends Role {

    // Temps d'effets en "ticks" (20 ticks = 1 seconde)
    private final int BLOCK_EFFECT_DURATION = 60; // 3 secondes
    private final int BLOCK_COOLDOWN = 60; // 60 secondes d'attente pour éviter le spam

    public CaptainAmerica() {
        super("Captain America", Camp.AVENGERS, "§9");
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList(
                "§7Vous êtes " + getColor() + getName() + "§7.",
                "§7Votre objectif est de gagner avec les " + getCamp().getDisplayName() + "§7.",
                "§7",
                "§e§lPouvoirs Passifs :",
                "§8- §7Vous possédez l'effet §cForce 1 §7permanent.",
                "§8- §7Vous connaissez un tiers de l'équipe des Avengers.",
                "§8- §7À 50 minutes, vous découvrirez l'identité du Soldat de l'Hiver.",
                "§7",
                "§e§lCapacité (Parade) :",
                "§8- §7En faisant un Clic Droit avec une épée ou un bouclier, vous obtenez",
                "  §7§8Résistance 2 et Faiblesse pendant " + (BLOCK_EFFECT_DURATION/20) + " secondes. (Recharge: " + BLOCK_COOLDOWN + "s)"
        );
    }

    @Override
    protected void setupKit() {
        // En 1.21.1, on ne peut plus parer à l'épée, je lui donne donc un bouclier !
        ItemStack shield = new ItemStack(Material.SHIELD, 1);
        ItemMeta meta = shield.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§9Bouclier en Vibranium");
            shield.setItemMeta(meta);
        }
        this.kit.add(shield);
    }

    @Override
    public void onGiveRole(Player player) {
        super.onGiveRole(player);

        // Effet permanent de Force (Durée infinie = Integer.MAX_VALUE, Amplificateur 0 = Force 1)
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0, false, false));

        player.sendMessage("§8§m--------------------------------------------------");
        for (String line : getDescription()) {
            player.sendMessage(line);
        }

        // TODO: Envoyer la liste générée du tiers des Avengers à l'annonce des rôles

        player.sendMessage("§8§m--------------------------------------------------");
    }

    @Override
    public void onRightClickItem(Player player, ItemStack item) {
        // On vérifie si l'item est une épée (n'importe laquelle) ou son bouclier
        if (item.getType().toString().contains("SWORD") || item.getType() == Material.SHIELD) {

            if (checkAndApplyCooldown(player, "Parade", BLOCK_COOLDOWN)) {

                // Ajout des effets : Résistance 2 (Amplificateur 1) et Weakness 1 (Amplificateur 0)
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, BLOCK_EFFECT_DURATION, 1, false, true));
                player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, BLOCK_EFFECT_DURATION, 0, false, true));

                player.sendMessage("§9[Captain America] §bPosition défensive adoptée !");
            }
        }
    }

    // TODO: À 50 minutes, révéler l'identité du Soldat de l'Hiver

    // TODO: Mécanique de proximité (15 blocs) avec le Soldat de l'Hiver pour le convertir avec une BossBar
}