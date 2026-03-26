package org.marvel_uhc;

import org.marvel_uhc.roles.RoleManager;

import java.util.HashMap;

public class GameConfiguration
{
    public int maxPlayer = 25;

    // Scénarios
    public boolean noFood = false;
    public boolean noFall = false;
    public boolean noFire = false;
    public boolean diamondLimit = true;
    public int diamondLimitMax = 17;

    // UHC
    public int pvp_time = 21;
    public int pvp_time_s = 1260;
    public int border_time = 90;
    public int border_time_s = 5400;
    public int border_size = 1000;
    public double border_speed = 1.0;
    public int role_time = 1;			// 20
    public int role_time_s = 60;		// 1200
    public int cycle_jn = 5;
    public int global_timer = 0;

    //Roles
    public HashMap<RoleManager.ROLES, Boolean> roles = new HashMap<RoleManager.ROLES, Boolean>();
    public HashMap<RoleManager.ROLES, Integer> roleCount = new HashMap<RoleManager.ROLES, Integer>();

    public GameConfiguration()
    {
        for(RoleManager.ROLES r : RoleManager.ROLES.values())
        {
            roles.put(r, false);
            roleCount.put(r, 0);
        }
    }
}
