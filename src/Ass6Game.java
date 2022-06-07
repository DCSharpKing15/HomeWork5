import biuoop.GUI;

import java.util.ArrayList;
import java.util.List;

public class Ass6Game {
    public static void main(String[] args) {

        LevelInformation level1 = new LevelOne();
        LevelInformation level2 = new LevelTwo();
        LevelInformation level3 = new LevelThree();
        LevelInformation level4 = new LevelFour();

        List<LevelInformation> lIL = new ArrayList<LevelInformation>();

        for (String i : args) {
            if (i.equals("1")) {
                lIL.add(level1);
            } else if (i.equals("2")) {
                lIL.add(level2);
            } else if (i.equals("3")) {
                lIL.add(level3);
            } else if (i.equals("4")) {
                lIL.add(level4);
            }
        }

        GUI gui = new GUI("Arkanoid", 800, 600);
        GameFlow gF = new GameFlow(gui);

        gF.runLevels(lIL);
    }
}
