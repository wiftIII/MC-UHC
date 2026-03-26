package org.marvel_uhc;

import org.bukkit.entity.Player;
import org.marvel_uhc.roles.Role;

public class PlayerData {
    public Player player;
    public Role role;

    public boolean takeFallDamage = true;
    public boolean canGlide = false;

    // Ajout très utile pour la suite (savoir si le joueur est mort ou spectateur)
    public boolean isAlive = true;

    public PlayerData(Player player) {
        this.player = player;
    }

    public void GiveKit() {
        if (role != null) {
            // C'est magique : on délègue la distribution au rôle directement !
            // La méthode onGiveRole() gère l'ajout des items ET le message de description.
            role.onGiveRole(player);
        }
    }
}