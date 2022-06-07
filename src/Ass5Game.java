import biuoop.GUI;

import java.util.ArrayList;
import java.util.List;

public class Ass5Game {
    public static void main(String[] args) {

        LevelInformation level1 = new LevelOne();
        LevelInformation level2 = new LevelTwo();
        LevelInformation level3 = new LevelThree();
        LevelInformation level4 = new LevelFour();

        List<LevelInformation> lIL = new ArrayList<LevelInformation>();
        lIL.add(level1);
        lIL.add(level2);
        lIL.add(level3);
        lIL.add(level4);

        GUI gui = new GUI("Arkanoid", 800, 600);
        GameFlow gF = new GameFlow(gui);

        gF.runLevels(lIL);
    }
}
