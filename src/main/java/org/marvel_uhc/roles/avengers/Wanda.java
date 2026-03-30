package org.marvel_uhc.roles.avengers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
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
import java.util.Random;

public class Wanda extends Role {

    private final String ITEM_POWER_NAME = "§4§lMagie du Chaos";
    private final int COOLDOWN_SORT = 180; // 3 minutes d'attente

    // Variables pour gérer les buffs actifs de Wanda (pour les futurs Listeners)
    public boolean hasArrowImmunity = false;
    public boolean hasDeathEvasion = false;

    // Notre liste de sorts
    private enum Spell {
        MALEDICTION("§2Malédiction", "Poison (10s) sur un joueur"),
        BOUCLIER("§bBouclier Magique", "Résistance et Immunité Flèches (30s) sur vous"),
        REGENERATION("§cRégénération Mortelle", "Survie au prochain coup fatal sur vous"),
        RALENTISSEMENT("§8Ralentissement", "Lenteur et Faiblesse (10s) sur un joueur"),
        NAUSEE("§5Nausée", "Nausée (10s) sur un joueur");

        final String name;
        final String desc;
        Spell(String name, String desc) {
            this.name = name;
            this.desc = desc;
        }
    }

    private final Spell[] spells = Spell.values();
    private int currentSpellIndex = 0;
    private Spell lastUsedSpell = null;

    public Wanda() {
        // Le camp de Wanda est défini aléatoirement à sa création !
        super("Wanda", new Random().nextBoolean() ? Camp.AVENGERS : Camp.MUTANTS, "§4");
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList(
                "§7Vous êtes " + getColor() + getName() + "§7.",
                "§7Votre camp initial est : " + getCamp().getDisplayName() + "§7.",
                "§7",
                "§e§lPouvoirs Passifs :",
                "§8- §7À 50 minutes, vous connaîtrez l'identité de §6Vision§7.",
                "§8- §7Si Vision meurt, vous pouvez le ressusciter s'il est à moins de 20 blocs.",
                "§8- §7Si Vision meurt (même ressuscité), vous devenez §8Anti-Héros§7.",
                "§7",
                "§e§lCapacité (Magie du Chaos) :",
                "§8- §eClic Gauche : §7Fait défiler vos 5 sorts.",
                "§8- §eClic Droit : §7Lance le sort sélectionné. (Recharge : 3 minutes)",
                "  §cAttention : §7Vous ne pouvez pas lancer le même sort deux fois de suite !"
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

        player.sendMessage("§8§m--------------------------------------------------");
        for (String line : getDescription()) {
            player.sendMessage(line);
        }
        player.sendMessage("§8§m--------------------------------------------------");
        player.sendMessage("§4[Wanda] §7Sort actuel : " + spells[currentSpellIndex].name);
    }

    // --- SÉLECTION DU SORT (CLIC GAUCHE) ---
    @Override
    public void onLeftClickItem(Player player, ItemStack item) {
        if (item.hasItemMeta() && ITEM_POWER_NAME.equals(item.getItemMeta().getDisplayName())) {

            // On passe au sort suivant (et on revient à 0 si on dépasse la fin)
            currentSpellIndex = (currentSpellIndex + 1) % spells.length;
            Spell selected = spells[currentSpellIndex];

            player.sendMessage("§4[Wanda] §7Sort sélectionné : " + selected.name + " §8- §o" + selected.desc);
        }
    }

    // --- LANCEMENT DU SORT (CLIC DROIT) ---
    @Override
    public void onRightClickItem(Player player, ItemStack item) {
        if (item.hasItemMeta() && ITEM_POWER_NAME.equals(item.getItemMeta().getDisplayName())) {

            Spell selected = spells[currentSpellIndex];

            // 1. Vérification : Est-ce le même sort que le précédent ?
            if (selected == lastUsedSpell) {
                player.sendMessage("§4[Wanda] §cVous devez utiliser un sort différent du précédent (" + lastUsedSpell.name + "§c) !");
                return;
            }

            // 2. Différenciation : Le sort cible-t-il un joueur ou Wanda elle-même ?
            Player target = null;
            boolean isSelfCast = (selected == Spell.BOUCLIER || selected == Spell.REGENERATION);

            if (!isSelfCast) {
                // On cherche l'entité que Wanda regarde (jusqu'à 20 blocs)
                Entity targetEntity = player.getTargetEntity(20);
                if (targetEntity instanceof Player) {
                    target = (Player) targetEntity;
                } else {
                    player.sendMessage("§4[Wanda] §cAucun joueur ciblé pour ce sort (Max 20 blocs).");
                    return;
                }
            }

            // 3. Application du Cooldown
            if (checkAndApplyCooldown(player, "Magie du Chaos", COOLDOWN_SORT)) {

                lastUsedSpell = selected; // On mémorise ce sort pour le bloquer la prochaine fois
                castSpell(player, target, selected);
            }
        }
    }

    // --- LOGIQUE DES EFFETS DES SORTS ---
    private void castSpell(Player wanda, Player target, Spell spell) {
        switch (spell) {
            case MALEDICTION:
                target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 10 * 20, 0));
                wanda.sendMessage("§4[Wanda] §aVous avez empoisonné " + target.getName() + " !");
                target.sendMessage("§c[!] Vous avez été frappé par une Malédiction...");
                break;

            case BOUCLIER:
                wanda.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 30 * 20, 0));
                hasArrowImmunity = true;
                wanda.sendMessage("§4[Wanda] §aBouclier Magique activé (30s) !");

                // TODO: Dans le DamageListener, annuler les dégâts de flèche si hasArrowImmunity est true

                // On retire l'immunité après 30 secondes
                Bukkit.getScheduler().runTaskLater(MarvelUhc.instance, () -> {
                    hasArrowImmunity = false;
                    if (wanda.isOnline()) wanda.sendMessage("§4[Wanda] §7Votre Bouclier Magique se dissipe.");
                }, 30 * 20);
                break;

            case REGENERATION:
                hasDeathEvasion = true;
                wanda.sendMessage("§4[Wanda] §aRégénération Mortelle active. Votre prochain coup fatal vous sauvera.");
                // TODO: Dans le DamageListener, intercepter la mort si hasDeathEvasion est true
                break;

            case RALENTISSEMENT:
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10 * 20, 0));
                target.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 10 * 20, 0));
                wanda.sendMessage("§4[Wanda] §aVous avez ralenti " + target.getName() + " !");
                break;

            case NAUSEE:
                target.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 10 * 20, 0));
                wanda.sendMessage("§4[Wanda] §aVous avez donné la nausée à " + target.getName() + " !");
                break;
        }
    }
}