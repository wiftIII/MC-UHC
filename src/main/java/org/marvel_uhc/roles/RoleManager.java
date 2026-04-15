package org.marvel_uhc.roles;

// Il faudra importer les classes au fur et à mesure qu'on les crée
import org.marvel_uhc.roles.avengers.*;
import org.marvel_uhc.roles.mutants.*;
import org.marvel_uhc.roles.villains.*;
import org.marvel_uhc.roles.anti_heroes.*;

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
            //Avenger
            case IronMan: return new IronMan();
            case CaptainAmerica: return new CaptainAmerica();
            case NickFury: return new NickFury();
            case DrStrange: return new DrStrange();
            case Thor: return new Thor();
            case Hulk: return new Hulk();
            case SpiderMan: return new SpiderMan();
            case BlackPanther: return new BlackPanther();
            case BlackWidow: return new BlackWidow();
            case CaptainMarvel: return new CaptainMarvel();
            case Hawkeye: return new Hawkeye();
            case Vision: return new Vision();
            case Wanda: return new Wanda();


            // Mutants
            case CharlesXavier: return new CharlesXavier();
            case Magneto: return new Magneto();
            case Wolverine: return new Wolverine();
            case X23: return new X23();
            case Quicksilver: return new Quicksilver();
            case TheBeast: return new TheBeast();
            case Colossus: return new Colossus();
            case EmmaFrost: return new EmmaFrost();
            case Iceberg: return new Iceberg();
            case Pyro: return new Pyro();
            case Malicia: return new Malicia();
            case Diablo: return new Diablo();
            case Mystique: return new Mystique();
            case JeanGrey: return new JeanGrey();

            // Super-Villains
            case Ultron: return new Ultron();
            case Thanos: return new Thanos();
            case EbonyMaw: return new EbonyMaw();
            case Ronan: return new Ronan();
            case RedSkull: return new RedSkull();
            case BaronZemo: return new BaronZemo();
            case WinterSoldier: return new WinterSoldier();
            case DoctorFatalis: return new DoctorFatalis();
            case Apocalypse: return new Apocalypse();
            case Kang: return new Kang();
            case DoctorOctopus: return new DoctorOctopus();

            // Anti-Heroes
            case Venom: return new Venom();
            case Loki: return new Loki();
            case Deadpool: return new Deadpool();

            default:
                return null; // Sécurité en attendant de créer toutes les classes
        }
    }
}