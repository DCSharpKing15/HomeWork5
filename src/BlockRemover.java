// a BlockRemover is in charge of removing blocks from the game, as well as keeping count
// of the number of blocks that remain.
public class BlockRemover implements HitListener {
    private Game game;
    private Counter remainingBlocks;
    private final int INITIAL_BLOCKS = 57;

    public BlockRemover(Game game, Counter removedBlocks) {
        this.game = game;

        this.remainingBlocks = new Counter();
        this.remainingBlocks.increase(INITIAL_BLOCKS - removedBlocks.getValue());
    }

    // Blocks that are hit should be removed
    // from the game. Remember to remove this listener from the block
    // that is being removed from the game.
    public void hitEvent(Block beingHit, Ball hitter) {
        beingHit.removeHitListener(this);
        beingHit.removeFromGame(game);

        remainingBlocks.decrease(1);
        this.game.getCounter().decrease(1);
    }
}
