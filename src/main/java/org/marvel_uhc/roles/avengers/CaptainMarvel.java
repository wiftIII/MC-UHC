package org.marvel_uhc.roles.avengers;
import org.marvel_uhc.roles.Camp;
import org.marvel_uhc.roles.Role;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.marvel_uhc.MarvelUhc;
import org.marvel_uhc.PlayerData;

import java.util.Arrays;
import java.util.List;

public class CaptainMarvel extends Role {

    private final String ITEM_POWER_NAME = "§f§lÉnergie Cosmique";

    // Cooldowns en secondes
    private final int COOLDOWN_REGEN = 180; // 3 minutes
    private final int COOLDOWN_RAYON = 180; // 3 minutes
    private final int COOLDOWN_SAUT = 180;   // 15 secondes pour le saut (pour éviter le spam)

    public CaptainMarvel() {
        super("Captain Marvel", Camp.AVENGERS, "§f"); // Blanc
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList(
                "§7Vous êtes " + getColor() + getName() + "§7.",
                "§7Votre objectif est de gagner avec les " + getCamp().getDisplayName() + "§7.",
                "§7",
                "§e§lPassifs :",
                "§8- §7Vous possédez §cForce 1 §7et §bVitesse 1 §7en permanence.",
                "§8- §7Au premier coup reçu, vous obtenez §dRégénération 1 §7pendant 3s.",
                "  §7(Recharge : 3 minutes)",
                "§8- §7Utilisez §e/mc planer §7pour activer/désactiver votre vol plané.",
                "§7",
                "§e§lCapacité Cosmique :",
                "§8- §eClic Droit : §7Bond de 20 blocs sans dégâts de chute.",
                "§8- §eClic Gauche : §7Tire un rayon cosmique. S'il touche un joueur : -4❤.",
                "  §7S'il touche un bloc : Explosion infligeant -2❤ en zone. (Recharge : 3 min)"
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

        // Effets permanents
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, false, false));

        player.sendMessage("§8§m--------------------------------------------------");
        for (String line : getDescription()) {
            player.sendMessage(line);
        }
        player.sendMessage("§8§m--------------------------------------------------");
    }

    // --- PASSIF : REGEN AU PREMIER COUP ---
    @Override
    public void onDamageReceived(Player victim, Player attacker, double damage, EntityDamageByEntityEvent event) {
        if (checkAndApplyCooldown(victim, "Régénération Cosmique", COOLDOWN_REGEN)) {
            // 60 ticks = 3 secondes
            victim.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 60, 0, false, false));
            victim.sendMessage("§f[Captain Marvel] §dVotre énergie cosmique réagit et vous régénère !");
        }
    }

    // --- CAPACITÉ : BOND (CLIC DROIT) ---
    @Override
    public void onRightClickItem(Player player, ItemStack item) {
        if (item.hasItemMeta() && ITEM_POWER_NAME.equals(item.getItemMeta().getDisplayName())) {

            if (checkAndApplyCooldown(player, "Bond Cosmique", COOLDOWN_SAUT)) {
                Vector direction = player.getLocation().getDirection();
                direction.multiply(2.5).setY(1.5);
                player.setVelocity(direction);

                PlayerData data = MarvelUhc.instance.GetData(player);
                if (data != null) data.takeFallDamage = false;

                player.sendMessage("§f[Captain Marvel] §eVers l'infini !");
            }
        }
    }

    // --- CAPACITÉ : RAYON D'ÉNERGIE (CLIC GAUCHE) ---
    @Override
    public void onLeftClickItem(Player player, ItemStack item) {
        if (item.hasItemMeta() && ITEM_POWER_NAME.equals(item.getItemMeta().getDisplayName())) {

            if (checkAndApplyCooldown(player, "Rayon Cosmique", COOLDOWN_RAYON)) {
                player.sendMessage("§f[Captain Marvel] §cTir d'énergie cosmique !");
                player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1.0f, 2.0f);

                Location loc = player.getEyeLocation();
                Vector direction = loc.getDirection().normalize().multiply(0.5); // On avance de 0.5 bloc à chaque étape
                boolean hitPlayer = false;

                // On fait avancer le rayon sur 30 blocs (60 étapes de 0.5)
                for (int i = 0; i < 60; i++) {
                    loc.add(direction);

                    // Particules Rouges (Dust en 1.21)
                    Particle.DustOptions dustOptions = new Particle.DustOptions(Color.RED, 1.5f);
                    loc.getWorld().spawnParticle(Particle.ASH, loc, 1, dustOptions);

                    // On cherche les entités proches du point actuel du rayon
                    for (Entity entity : loc.getWorld().getNearbyEntities(loc, 0.5, 0.5, 0.5)) {
                        if (entity instanceof Player target && target != player) {

                            // Un joueur a été touché ! 4 coeurs = 8.0 points de dégâts
                            target.damage(8.0, player);
                            target.sendMessage("§c[!] Vous avez été foudroyé par le rayon de Captain Marvel !");
                            hitPlayer = true;
                            break;
                        }
                    }

                    // Si on a touché un joueur OU qu'on a percuté un bloc solide, on arrête le rayon
                    if (hitPlayer || !loc.getBlock().isPassable()) {
                        break;
                    }
                }

                // S'il n'a touché aucun joueur (donc a fini dans un bloc ou dans le vide à 30 blocs)
                if (!hitPlayer) {
                    loc.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, loc, 1);
                    loc.getWorld().playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);

                    // Explosion en zone : 2 coeurs = 4.0 dégâts aux joueurs dans un rayon de 3 blocs
                    for (Entity entity : loc.getWorld().getNearbyEntities(loc, 3.0, 3.0, 3.0)) {
                        if (entity instanceof Player target && target != player) {
                            target.damage(4.0, player);
                        }
                    }
                }
            }
        }
    }

    /**
     * Méthode pour la commande /mc planer (Identique à Vision)
     */
    public void toggleGlide(Player player) {
        PlayerData data = MarvelUhc.instance.GetData(player);
        if (data != null) {
            data.canGlide = !data.canGlide;
            if (data.canGlide) {
                player.sendMessage("§f[Captain Marvel] §aMode planage activé.");
            } else {
                player.sendMessage("§f[Captain Marvel] §cMode planage désactivé.");
                player.setGliding(false);
            }
        }
    }
}