package org.marvel_uhc.stones;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class MindStone extends Stone {

    public MindStone(ItemStack item) {
        super(item);
    }

    @Override
    public void onRightClick(Player player) {
        Player target = getTargetPlayer(player);

        if (target != null)
            player.sendMessage("The player " + target.getName() + " is on the team " + getTeam(target) + ".");
        else
            player.sendMessage("§cNo target found for the Time Stone!");
    }

    @Override
    public void onLeftClick(Player player) {
        player.sendMessage("§cNo left-click action for the Mind Stone.");
    }

    private String getTeam(Player player) {
        // return player.getTeam() ?
        return "Team";
    }

    @Override
    public void onPlayerDeath(PlayerDeathEvent event) {
    }
}
