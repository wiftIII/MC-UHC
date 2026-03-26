package org.marvel_uhc;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.marvel_uhc.roles.Role;

public class PlayerData
{
    public Player player;
    public Role role;

    public boolean takeFallDamage = true;
    public boolean canGlide = false;

    public PlayerData(Player player)
    {
        this.player = player;
    }

    public void GiveKit()
    {
        for(ItemStack item : role.kit)
        {
            player.getInventory().addItem(item);
        }
    }
}
