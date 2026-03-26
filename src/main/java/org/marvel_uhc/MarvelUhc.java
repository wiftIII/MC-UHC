package org.marvel_uhc;


import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.marvel_uhc.commands.RoleCommands;
import org.marvel_uhc.items.ItemManager;
import org.marvel_uhc.listeners.*;
import org.marvel_uhc.roles.Role;
import org.marvel_uhc.roles.RoleManager;

import org.bukkit.plugin.PluginManager;
import org.marvel_uhc.commands.Host;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;


public final class MarvelUhc extends JavaPlugin implements Listener
{
    //Debug
    public static final boolean DEBUG_MODE = true;

    //Global
    public static MarvelUhc instance;
    public static int version;

    public HashMap<UUID, PlayerData> playerData = new HashMap<>();


    public ItemManager items;
    public RoleManager roles;

    public GameConfiguration configuration;


    @Override
    public void onEnable()
    {

        // Plugin startup logic
        configuration = new GameConfiguration();
        instance = this;

        String[] v = getServer().getVersion().split("\\.");
        getLogger().log(Level.INFO, v[1]);
        version = Integer.parseInt(v[1]);

        //Managers
        items = new ItemManager();
        roles = new RoleManager();


        Bukkit.getPluginManager().registerEvents(this, this); // Save Listener
        getLogger().log(Level.INFO, "--------------------------------COUAC------------------------------------------------------");

        super.onEnable();

        //Setup
        state = State.CONFIG;

        // Listeners
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new ConnexionListener(), this);
        pm.registerEvents(new ConfigListener(), this);
        pm.registerEvents(new DamageListener(), this);
        pm.registerEvents(new RoleListener(), this);
        pm.registerEvents(new StoneListener(), this);

        // Commandes :
        Host host = new Host();
        getCommand("host").setExecutor(host);
        getCommand("say").setExecutor(host);

        if(DEBUG_MODE)
        {
            InitDebug();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void InitDebug()
    {
        RoleCommands roleCommands = new RoleCommands();

        getCommand("iron_man").setExecutor(roleCommands);
        getCommand("dr_strange").setExecutor(roleCommands);
    }
    //**********************************************************************

    private State state;

    public void setState(State state) {
        this.state = state;
    }
    public boolean isState(State state) {
        return this.state == state;
    }

    private boolean pvp;
    public void setPvp(boolean on_off) {
        pvp = on_off;
    }
    public boolean isPvpOn() {
        return pvp;
    }


    public void AddPlayer(Player p)
    {
        playerData.put(p.getUniqueId(), new PlayerData(p));
    }
    public PlayerData GetData(Player p)
    {
        return playerData.get(p.getUniqueId());
    }

    public void SetRole(Player player, Role role)
    {
        PlayerData data = GetData(player);
        data.role = role;
        data.GiveKit();
    }
}
