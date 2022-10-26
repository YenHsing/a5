package a5.logic;

import a5.util.PlayerRole;
import a5.util.GameType;
import a5.util.GameResult;


/**
 * A Pente game, where players take turns to place stones on board.
 * When consecutive two stones are surrounded by the opponent's stones on two ends,
 * these two stones are removed (captured).
 * A player wins by placing 5 consecutive stones or capturing stones 5 times.
 */
public class Pente extends MNKGame {


    /**
     * Create an 8-by-8 Pente game.
     */
    public Pente() {
        super(8, 8, 5);

        // TODO 1
    }

    /**
     * Creates: a copy of the game state.
     */
    public Pente(Pente game) {
        super(game);
        // TODO 2
    }

    @Override
    public boolean makeMove(Position p) {
        // TODO 3
        return true;
    }

    /**
     * Returns: a new game state representing the state of the game after the current player takes a
     * move {@code p}.
     */
    public Pente applyMove(Position p) {
        Pente newGame = new Pente(this);
        newGame.makeMove(p);
        return newGame;
    }

    /**
     * Returns: the number of captured pairs by {@code playerRole}.
     */
    public int capturedPairsNo(PlayerRole playerRole) {
        // TODO 4
        return 0;
    }

    @Override
    public boolean hasEnded() {
        // TODO 5
        return super.hasEnded();
    }

    @Override
    public GameType gameType() {
        return GameType.PENTE;
    }


    @Override
    public String toString() {
        String board = super.toString();
        return board + System.lineSeparator() + "Captured pairs: " +
                "first: " + capturedPairsNo(PlayerRole.FIRST_PLAYER) + ", " +
                "second: " + capturedPairsNo(PlayerRole.SECOND_PLAYER);
    }

    @Override
    public boolean equals(Object o) {
        if (getClass() != o.getClass()) {
            return false;
        }
        Pente p = (Pente) o;
        return stateEqual(p);
    }

    /**
     * Returns: true if the two games have the same state.
     */
    protected boolean stateEqual(Pente p) {
        // TODO 6
        return true;
    }

    @Override
    public int hashCode() {
        // TODO 7
        return super.hashCode();
    }
}
