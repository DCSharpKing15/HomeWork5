// a BlockRemover is in charge of removing blocks from the game, as well as keeping count
// of the number of blocks that remain.
public class BlockRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBlocks;

    public BlockRemover(GameLevel game, Counter removedBlocks) {
        this.game = game;

        this.remainingBlocks = new Counter();
        this.remainingBlocks.increase(game.getInitialBlocks() - removedBlocks.getValue());
    }

    // Blocks that are hit should be removed
    // from the game. Remember to remove this listener from the block
    // that is being removed from the game.
    public void hitEvent(Block beingHit, Ball hitter) {
        beingHit.removeHitListener(this);
        beingHit.removeFromGame(this.game);

        this.remainingBlocks.decrease(1);
        this.game.getBlockCounter().decrease(1);
    }
}
