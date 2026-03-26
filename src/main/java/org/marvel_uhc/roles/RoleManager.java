package org.marvel_uhc.roles;

// Il faudra importer les classes au fur et à mesure qu'on les crée
import org.marvel_uhc.roles.avengers.IronMan;

public class RoleManager {

    // On garde ton enum EXACTEMENT comme il était pour ne pas casser la configuration et les menus
    public enum ROLES {
        // Avengers
        IronMan, CaptainAmerica, NickFury, DrStrange, Thor, Hulk, SpiderMan, Wanda, Vision, BlackPanther, Hawkeye, BlackWidow, CaptainMarvel,
        // Mutants
        CharlesXavier, Magneto, Wolverine, X23, Quicksilver, TheBeast, Colossus, EmmaFrost, Iceberg, Pyro, Malicia, Diablo, Mystique, JeanGrey,
        // Super-Villains
        Ultron, Thanos, EbonyMaw, Ronan, RedSkull, BaronZemo, WinterSoldier, DoctorFatalis, Apocalypse, Kang, DoctorOctopus,
        // Anti-Heroes
        Venom, Loki, Deadpool
    }

    // On remplace GetRole() pour qu'elle renvoie une nouvelle instance de notre classe objet
    public static Role getRoleInstance(ROLES role) {
        switch (role) {
            case IronMan:
                return new IronMan(); // On crée le "vrai" Iron Man avec ses pouvoirs

            // Pour l'instant, on laisse les autres commentés ou on renvoie null
            // car les classes n'existent pas encore. On les ajoutera ici à l'Étape 2.

            /*
            case CaptainAmerica: return new CaptainAmerica();
            case SpiderMan: return new SpiderMan();
            */

            default:
                return null; // Sécurité en attendant de créer toutes les classes
        }
    }
}