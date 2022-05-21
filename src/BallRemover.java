// a BlockRemover is in charge of removing blocks from the game, as well as keeping count
// of the number of blocks that remain.
public class BallRemover implements HitListener {
    private Game game;
    private Counter remainingBalls;

    public BallRemover(Game game, Counter removedBalls) {
        this.game = game;

        this.remainingBalls = new Counter();
        this.remainingBalls.increase(game.getINITIAL_BALLS() - removedBalls.getValue());
    }

    // Balls that hit the deathRegion will be removed
    // from the game. Remember to remove this listener from the block
    // that is being removed from the game.
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(game);

        remainingBalls.decrease(1);
        this.game.getBallCounter().decrease(1);
    }
}
