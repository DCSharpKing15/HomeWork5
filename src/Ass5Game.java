public class Ass5Game {
    public static void main(String[] args) {

        LevelInformation level1 = new LevelOne();

        GameLevel game = new GameLevel(level1);
        game.initialize3();
        game.run();
    }
}
