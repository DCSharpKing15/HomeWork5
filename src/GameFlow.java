import biuoop.GUI;

import java.util.List;

public class GameFlow {
    private GUI gui;

    public GameFlow(GUI gui) {
        this.gui = gui;
    }

    public void runLevels(List<LevelInformation> levels) {
        for (LevelInformation levelInfo : levels) {
            GameLevel level = new GameLevel(levelInfo, this.gui);
            level.initialize();
            level.run();
        }
        this.gui.close();
    }
}
