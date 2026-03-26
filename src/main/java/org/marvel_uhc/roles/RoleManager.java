package org.marvel_uhc.roles;

public class RoleManager
{
    private static final RoleBuilder builder = new RoleBuilder();

    public enum ROLES
    {
        //Avengers
        IronMan,
        CaptainAmerica,
        NickFury,
        DrStrange,
        Thor,
        Hulk,
        SpiderMan,
        Wanda,
        Vision,
        BlackPanther,
        Hawkeye,
        BlackWidow,
        CaptainMarvel,

        //Mutants
        CharlesXavier,
        Magneto,
        Wolverine,
        X23,
        Quicksilver,
        TheBeast,
        Colossus,
        EmmaFrost,
        Iceberg,
        Pyro,
        Malicia,
        Diablo,
        Mystique,
        JeanGrey,

        //Super-Villains
        Ultron,
        Thanos,
        EbonyMaw,
        Ronan,
        RedSkull,
        BaronZemo,
        WinterSoldier,
        DoctorFatalis,
        Apocalypse,
        Kang,
        DoctorOctopus,

        //Anti-Heroes
        Venom,
        Loki,
        Deadpool
    }

    public static Role GetRole(ROLES role)
    {
        Role result = null;
        switch (role)
        {
            //Marvel
            case IronMan -> result = builder.IronMan();
            case CaptainAmerica -> result = builder.CaptainAmerica();
            case NickFury -> result = builder.NickFurr();
            case DrStrange -> result = builder.DrStrange();
            case Thor -> result = builder.Thor();
            case Hulk -> result = builder.Hulk();
            case SpiderMan -> result = builder.SpiderMan();
            case Wanda -> result = builder.Wanda();
            case Vision -> result = builder.Vision();
            case BlackPanther -> result = builder.BlackPanther();
            case Hawkeye -> result = builder.Hawkeye();
            case BlackWidow -> result = builder.BlackWidow();
            case CaptainMarvel -> result = builder.CaptainMarvel();

            //Mutants
            case CharlesXavier -> result = builder.CharlesXavier();
            case Magneto -> result = builder.Magneto();
            case Wolverine -> result = builder.Wolverine();
            case X23 -> result = builder.X23();
            case Quicksilver -> result = builder.Quicksilver();
            case TheBeast -> result = builder.TheBeast();
            case Colossus -> result = builder.Colossus();
            case EmmaFrost -> result = builder.EmmaFrost();
            case Iceberg -> result = builder.Iceberg();
            case Pyro -> result = builder.Pyro();
            case Malicia -> result = builder.Malicia();
            case Diablo -> result = builder.Diablo();
            case Mystique -> result = builder.Mystique();
            case JeanGrey -> result = builder.JeanGrey();

            //Super-Villains
            case Ultron -> result = builder.Ultron();
            case Thanos -> result = builder.Thanos();
            case EbonyMaw -> result = builder.EbonyMaw();
            case Ronan -> result = builder.Ronan();
            case RedSkull -> result = builder.RedSkull();
            case BaronZemo -> result = builder.BaronZemo();
            case WinterSoldier -> result = builder.WinterSoldier();
            case DoctorFatalis -> result = builder.DoctorFatalis();
            case Apocalypse -> result = builder.Apocalypse();
            case Kang -> result = builder.Kang();
            case DoctorOctopus -> result = builder.DoctorOctopus();

            //Anti-Heroes
            case Venom -> result = builder.Venom();
            case Loki -> result = builder.Loki();
            case Deadpool -> result = builder.Deadpool();

        }

        return result;
    }

}
