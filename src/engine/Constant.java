package engine;

import java.awt.*;

public class Constant {

    public static final Color OpaqueColor = new Color(75, 76, 77, 0);
    public static final Color BackColor = new Color(75, 76, 77);
    public static final Color ForeColor = new Color(42, 44, 46);
    public static final Color TextColor = Color.WHITE;
    public static final Font TextFont1 = new Font(Font.DIALOG, Font.PLAIN, 15);
    public static final Font TextFont2 = new Font(Font.DIALOG, Font.BOLD, 50);

    public static final String InitialMassage = "Press F1 To Display Key Events";

    public static final String HelpMassage = "F1~Help,a~Attack,e~End Turn,q~Cure,s~Special Action,x~Choose Hero,h~AI Action,Esc~End Game";

    public static final String HeroesFile = "resources/Heroes.csv";

    public static final String AboutFile = "resources/about.txt";

    public static final String ImagesDirectory = "resources/images/";

    public static final String SoundsDirectory = "resources/sounds/";

    public static final int MapSize = 15,
            CollectableN = 5,
            ZombieN = 10;

    public static final String Explorer = "explorer.png",
            Fighter = "fighter.png",
            Medic = "medic.png",
            ExplorerTrap = "explorerTrap.png",
            FighterTrap = "fighterTrap.png",
            MedicTrap = "medicTrap.png",
            Supply = "supply.png",
            Vaccine = "vaccine.png",
            Zombie = "orc.png",
            Cup = "cup.png",
            Setting = "options.png",
            Question = "question.png",
            Home = "home.png",
            GoBack = "goBack.png";

    public static final String GameOverSound = "over.wav",
            WinSound = "win.wav",
            SupplySound = "supply.wav",
            VaccineSound = "vaccine.wav",
            AttackSound = "attack.wav",
            CureSound = "cure.wav",
            ZombieSound = "zombie_att.wav",
            HpSound = "resetHp.wav",
            NavSound = "nav.wav",
            MoveSound = "move.wav",
            MoveExSound = "move_exp.wav",
            TrapSound = "trap.wav",
            HeroDeathSound = "heroDeath.wav",
            ErrorSound = "error.wav";

}
