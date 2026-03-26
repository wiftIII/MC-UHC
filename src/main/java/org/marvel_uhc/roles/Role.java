package org.marvel_uhc.roles;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class Role {
    private final String name;
    private final Camp camp;
    private final String color; // Ex: "§c" pour le rouge
    protected List<ItemStack> kit;

    public Role(String name, Camp camp, String color) {
        this.name = name;
        this.camp = camp;
        this.color = color;
        this.kit = new ArrayList<>();
        setupKit();
    }

    // --- Getters ---
    public String getName() { return name; }
    public Camp getCamp() { return camp; }
    public String getColor() { return color; }
    public List<ItemStack> getKit() { return kit; }

    // --- Méthodes abstraites (Obligatoires pour chaque rôle) ---
    public abstract List<String> getDescription();
    protected abstract void setupKit();

    // --- Événements (Hooks) ---
    // Les rôles peuvent "Override" (écraser) ces méthodes uniquement s'ils en ont besoin.

    public void onGiveRole(Player player) {
        // Par défaut, on donne juste le kit au joueur
        for (ItemStack item : kit) {
            player.getInventory().addItem(item);
        }
    }

    // Événements liés au temps
    public void onDayCycle(Player player) {}
    public void onNightCycle(Player player) {}

    // Événements liés au combat
    public void onKill(Player killer, Player victim) {}
    public void onDeath(Player player, Player killer) {}

    // Événement lié à l'utilisation des pouvoirs
    public void onRightClickItem(Player player, ItemStack item) {}
    public void onLeftClickItem(Player player, ItemStack item) {}
}