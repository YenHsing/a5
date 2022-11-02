package a5.logic;

import a5.util.PlayerRole;
import a5.util.GameType;
import a5.util.GameResult;
import java.util.Objects;
import java.util.Arrays;


/**
 * A Pente game, where players take turns to place stones on board. When consecutive two stones are
 * surrounded by the opponent's stones on two ends, these two stones are removed (captured). A
 * player wins by placing 5 consecutive stones or capturing stones 5 times.
 */
public class Pente extends MNKGame {

    private int first_player_captured_times;
    private int second_player_captured_times;

    /**
     * Create an 8-by-8 Pente game.
     */
    public Pente() {
        super(8, 8, 5);
        first_player_captured_times = 0;
        second_player_captured_times = 0;
        // TODO 1
    }
    
    public int first_time_getter(){
        return first_player_captured_times;
    }
    
    public int second_time_getter(){
        return second_player_captured_times;
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
        if (!board().validPos(p)) {
            return false;
        }
        board().place(p, currentPlayer());

        Position top_left = new Position(p.row() - 3, p.col() - 3);
        Position top_right = new Position(p.row() - 3, p.col() + 3);
        Position top = new Position(p.row() - 3, p.col());
        Position bottom = new Position(p.row() + 3, p.col());
        Position mid_left = new Position(p.row(), p.col() - 3);
        Position mid_right = new Position(p.row(), p.col() + 3);
        Position bottom_left = new Position(p.row() + 3, p.col() - 3);
        Position bottom_right = new Position(p.row() + 3, p.col() + 3);

        // if there's a stone on one of the previous position(top_left, top_right...) and the 2 stones between them are rivals' stones, then capture)
        if (board().onBoard(top_left)
                && board().get(new Position(p.row() - 1, p.col() - 1)) == currentPlayer().nextPlayer().boardValue()
                && board().get(new Position(p.row() - 2, p.col() - 2)) == currentPlayer().nextPlayer().boardValue()
                && board().get(top_left) == currentPlayer().boardValue()) {
            //capture 2 stones
            board().erase(new Position(p.row() - 1, p.col() - 1));
            board().erase(new Position(p.row() - 2, p.col() - 2));
            //if boardvalue = 1 => first player, else it is second player
            if (currentPlayer().boardValue() == 1) {
                first_player_captured_times++;
            } else {
                second_player_captured_times++;
            }
        }
        if (board().onBoard(top_right)
                && board().get(new Position(p.row() - 1, p.col() + 1)) == currentPlayer().nextPlayer().boardValue()
                && board().get(new Position(p.row() - 2, p.col() + 2)) == currentPlayer().nextPlayer().boardValue()
                && board().get(top_right) == currentPlayer().boardValue()) {
            board().erase(new Position(p.row() - 1, p.col() + 1));
            board().erase(new Position(p.row() - 2, p.col() + 2));
            if (currentPlayer().boardValue() == 1) {
                first_player_captured_times++;
            } else {
                second_player_captured_times++;
            }
        }
        if (board().onBoard(mid_left)
                && board().get(new Position(p.row(), p.col() - 1)) == currentPlayer().nextPlayer().boardValue()
                && board().get(new Position(p.row(), p.col() - 2)) == currentPlayer().nextPlayer().boardValue()
                && board().get(mid_left) == currentPlayer().boardValue()) {
            board().erase(new Position(p.row(), p.col() - 1));
            board().erase(new Position(p.row(), p.col() - 2));
            if (currentPlayer().boardValue() == 1) {
                first_player_captured_times++;
            } else {
                second_player_captured_times++;
            }
        }
        if (board().onBoard(mid_right)
                && board().get(new Position(p.row(), p.col() + 1)) == currentPlayer().nextPlayer().boardValue()
                && board().get(new Position(p.row(), p.col() + 2)) == currentPlayer().nextPlayer().boardValue()
                && board().get(mid_right) == currentPlayer().boardValue()) {
            board().erase(new Position(p.row(), p.col() + 1));
            board().erase(new Position(p.row(), p.col() + 2));
            if (currentPlayer().boardValue() == 1) {
                first_player_captured_times++;
            } else {
                second_player_captured_times++;
            }
        }
        if (board().onBoard(bottom_left)
                && board().get(new Position(p.row() + 1, p.col() - 1)) == currentPlayer().nextPlayer().boardValue()
                && board().get(new Position(p.row() + 2, p.col() - 2)) == currentPlayer().nextPlayer().boardValue()
                && board().get(bottom_left) == currentPlayer().boardValue()) {
            board().erase(new Position(p.row() + 1, p.col() - 1));
            board().erase(new Position(p.row() + 2, p.col() - 2));
            if (currentPlayer().boardValue() == 1) {
                first_player_captured_times++;
            } else {
                second_player_captured_times++;
            }
        }
        if (board().onBoard(bottom_right)
                && board().get(new Position(p.row() + 1, p.col() + 1)) == currentPlayer().nextPlayer().boardValue()
                && board().get(new Position(p.row() + 2, p.col() + 2)) == currentPlayer().nextPlayer().boardValue()
                && board().get(bottom_right) == currentPlayer().boardValue()) {
            board().erase(new Position(p.row() + 1, p.col() + 1));
            board().erase(new Position(p.row() + 2, p.col() + 2));
            if (currentPlayer().boardValue() == 1) {
                first_player_captured_times++;
            } else {
                second_player_captured_times++;
            }
        }
        if (board().onBoard(top)
                && board().get(new Position(p.row() - 1, p.col())) == currentPlayer().nextPlayer().boardValue()
                && board().get(new Position(p.row() - 2, p.col())) == currentPlayer().nextPlayer().boardValue()
                && board().get(top) == currentPlayer().boardValue()) {
            board().erase(new Position(p.row() - 1, p.col()));
            board().erase(new Position(p.row() - 2, p.col()));
            if (currentPlayer().boardValue() == 1) {
                first_player_captured_times++;
            } else {
                second_player_captured_times++;
            }
        }
        if (board().onBoard(bottom)
                && board().get(new Position(p.row() + 1, p.col())) == currentPlayer().nextPlayer().boardValue()
                && board().get(new Position(p.row() + 2, p.col())) == currentPlayer().nextPlayer().boardValue()
                && board().get(bottom) == currentPlayer().boardValue()) {
            board().erase(new Position(p.row() + 1, p.col()));
            board().erase(new Position(p.row() + 2, p.col()));
            if (currentPlayer().boardValue() == 1) {
                first_player_captured_times++;
            } else {
                second_player_captured_times++;
            }
        }
        changePlayer();
        advanceTurn();
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
        return playerRole.boardValue() == 1 ?
                first_player_captured_times : second_player_captured_times;
    }

    @Override
    public boolean hasEnded() {
        // TODO 5
        return super.hasEnded()
                || first_player_captured_times >= 5
                || second_player_captured_times >= 5;
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
        if (this == p) {
            return true;
        }
        if (p == null || getClass() != p.getClass()) {
            return false;
        }
        Pente p_ = (Pente) p;
        return p_.first_player_captured_times == p.first_player_captured_times
                && p_.second_player_captured_times == p.second_player_captured_times
                && super.stateEqual(p_);
    }

    @Override
    public int hashCode() {
        // TODO 7
        int prime = 31;
        int catched_times_hashcode =
                prime * (first_player_captured_times + second_player_captured_times);
        return Arrays.hashCode(new int[]{
                super.hashCode(),
                catched_times_hashcode,
        });
    }

