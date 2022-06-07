// a BlockRemover is in charge of removing blocks from the game, as well as keeping count
// of the number of blocks that remain.
public class BallRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBalls;

    public BallRemover(GameLevel game, Counter removedBalls) {
        this.game = game;
        this.remainingBalls = new Counter();
        this.remainingBalls.increase(game.getInitialBalls() - removedBalls.getValue());
    }

    // Balls that hit the deathRegion will be removed
    // from the game. Remember to remove this listener from the block
    // that is being removed from the game.
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.game);

        this.remainingBalls.decrease(1);
        this.game.getBallCounter().decrease(1);
    }
}
