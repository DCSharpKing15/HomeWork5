public class Ass5Game {
    public static void main(String[] args) {

        //LevelInformation level = new LevelOne();
        //LevelInformation level = new LevelTwo();
        //LevelInformation level = new LevelThree();
        LevelInformation level = new LevelFour();

        GameLevel game = new GameLevel(level);
        game.initialize3();
        game.run();
    }
}
