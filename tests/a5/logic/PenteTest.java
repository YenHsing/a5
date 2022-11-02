package a5.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import a5.util.PlayerRole;
import org.junit.jupiter.api.Test;

class PenteTest {
    @Test
    void testConstructor() {
        // TODO 1: write at least 1 test case
        Pente game1 = new Pente();
        assertEquals(game1.board(),new Board(8,8));
        assertEquals(game1.countToWin(),5);
        assertEquals(game1.first_time_getter(),0);
        assertEquals(game1.second_time_getter(),0);
    }


    @Test
    void testCopyConstructor() {
        // test case 1: can a board state be copied to an equal state
        Pente game1 = new Pente();
        game1.makeMove(new Position(2, 2));
        Pente game2 = new Pente(game1);
        assertTrue(game1.stateEqual(game2));

        // TODO 2: write at least 3 test cases
        // test case 2: two copies are equal
        Pente game3 = new Pente(game1);
        assertTrue(game3.stateEqual(game2));
        // test case 3: copy of a state is equal to its equal-state
        Pente game4 = new Pente();
        game4.makeMove(new Position(2, 2));
        assertTrue(game4.stateEqual(game2));
        // test case 4: a copy of a copy is equal to the original state
        Pente game5 = new Pente(game1);
        assertTrue(game5.stateEqual(game1));

    }

    @Test
    void testHashCode() {
        Pente game1 = new Pente();
        Pente game2 = new Pente();
        Pente game3 = new Pente();

        // test case 1: do two equal nonempty board states have the same hash code
        game1.makeMove(new Position(3, 3));
        game2.makeMove(new Position(3, 3));
        assertEquals(game1.hashCode(), game2.hashCode());

        // test case 2: non-equal board states should be very unlikely to have the
        // same hash code.
        game3.makeMove(new Position(0, 0));
        assertNotEquals(game1.hashCode(), game3.hashCode());

        // TODO 3: write at least 3 test cases
        // test 3: games with equal first_player_captured_times and second_player_captured_times
        // and board state should have the same hash code
        game1.makeMove(new Position(4, 4));
        game1.makeMove(new Position(6, 6));
        game1.makeMove(new Position(5, 5));
        game2.makeMove(new Position(4, 4));
        game2.makeMove(new Position(6, 6));
        game2.makeMove(new Position(5, 5));
        assertEquals(game1.hashCode(), game2.hashCode());
        // test 4: games with different first_player_captured_times and second_player_captured_times
        // the same board state should not have the same hash code
        game1.makeMove(new Position(3, 4));
        game1.makeMove(new Position(2, 3));
        game1.makeMove(new Position(4, 3));
        game1.makeMove(new Position(5, 3));
        Pente game4 = new Pente();
        game4.makeMove(new Position(3, 4));
        game4.makeMove(new Position(2, 3));
        game4.makeMove(new Position(6, 6));
        game4.makeMove(new Position(5, 3));
        assertNotEquals(game1.hashCode(), game4.hashCode());
        // tast 5: games with equal capture times but different board states should not have the same
        // hash code
        game2.makeMove(new Position(3, 2));
        game2.makeMove(new Position(3, 1));
        game2.makeMove(new Position(4, 3));
        game2.makeMove(new Position(3, 4));
        assertNotEquals(game1.hashCode(), game2.hashCode());
    }

    @Test
    void makeMove() {
        // test case 1: a simple move
        Pente game = new Pente();
        Position p = new Position(2, 2);
        game.makeMove(p); // a move by the first player
        assertEquals(PlayerRole.SECOND_PLAYER, game.currentPlayer());
        assertEquals(2, game.currentTurn());
        assertFalse(game.hasEnded());
        assertEquals(0, game.capturedPairsNo(PlayerRole.FIRST_PLAYER));
        assertEquals(0, game.capturedPairsNo(PlayerRole.SECOND_PLAYER));
        assertEquals(PlayerRole.FIRST_PLAYER.boardValue(), game.board().get(p));

        // test case 2: try a capture
        game.makeMove(new Position(2, 3)); // a move by the second player
        game.makeMove(new Position(3, 2)); // a move by the first player
        assertEquals(1, game.board().get(new Position(3, 2)));
        game.makeMove(new Position(2, 4)); // a move by the second player
        game.makeMove(new Position(2, 5)); // a move by the first player, which should capture the pair [(2, 3), (2, 4)]
        assertEquals(1, game.capturedPairsNo(PlayerRole.FIRST_PLAYER));
        assertEquals(0, game.board().get(new Position(2, 3))); // the stone should be removed
        assertEquals(0, game.board().get(new Position(2, 4))); // the stone should be removed

        // TODO 4: write at least 3 test cases
        // test case 3: capture upright pair
        Pente game2= new Pente();
        game2.makeMove(new Position(0, 1)); // a move by the first player
        game2.makeMove(new Position(0, 0)); // a move by the second player
        game2.makeMove(new Position(0, 2)); // a move by the first player
        game2.makeMove(new Position(0, 3)); // a move by the second player, and captures the pair [(0,1),(0,2)]
        assertEquals(1, game2.capturedPairsNo(PlayerRole.SECOND_PLAYER));
        assertEquals(0, game2.board().get(new Position(0, 1))); // the stone should be removed
        assertEquals(0, game2.board().get(new Position(0, 2))); // the stone should be removed

        // test case 3: capture slanted pair
        assertEquals(2, game2.board().get(new Position(0, 0)));
        game2.makeMove(new Position(1, 1)); // a move by the first player
        game2.makeMove(new Position(5, 5)); // a move by the second player
        game2.makeMove(new Position(2, 2)); // a move by the first player
        game2.makeMove(new Position(3, 3)); // a move by the second player, and captures the pair [(1,1),(2,2)]
        assertEquals(2, game2.capturedPairsNo(PlayerRole.SECOND_PLAYER));
        assertEquals(0, game2.board().get(new Position(1, 1))); // the stone should be removed
        assertEquals(0, game2.board().get(new Position(1, 2))); // the stone should be removed

        // test case 4: capture can only done by opponent
        Pente game3= new Pente();
        game3.makeMove(new Position(0, 0)); // a move by the first player
        game3.makeMove(new Position(0, 1)); // a move by the second player
        game3.makeMove(new Position(0, 3)); // a move by the first player
        game3.makeMove(new Position(0, 2)); // a move by the second player
        assertEquals(0, game3.capturedPairsNo(PlayerRole.FIRST_PLAYER));
        assertEquals(2, game3.board().get(new Position(0, 1))); // the stone should be removed
        assertEquals(2, game3.board().get(new Position(0, 2))); // the stone should be removed
    }


    @Test
    void capturedPairsNo() {
        // test case 1: are captured pairs registered?
        Pente game = new Pente();
        game.makeMove(new Position(3, 2)); // a move by the first player
        game.makeMove(new Position(3, 3)); // a move by the second player
        game.makeMove(new Position(4, 2)); // a move by the first player
        game.makeMove(new Position(3, 4)); // a move by the second player
        game.makeMove(new Position(3, 5)); // a move by the first player, which should capture the pair [(3, 3), (3, 4)]
        assertEquals(1, game.capturedPairsNo(PlayerRole.FIRST_PLAYER));

        assertEquals(0, game.board().get(new Position(3, 3))); // the stone should be removed
        assertEquals(0, game.board().get(new Position(3, 4))); // the stone should be removed

        // TODO 5: write at least 3 test cases
        // test case 2: are captured pairs by second player registered?
        Pente game4 = new Pente();
        game4.makeMove(new Position(0, 0));
        game4.makeMove(new Position(3, 2)); // a move by the first player
        game4.makeMove(new Position(3, 3)); // a move by the second player
        game4.makeMove(new Position(4, 2)); // a move by the first player
        game4.makeMove(new Position(3, 4)); // a move by the second player
        game4.makeMove(new Position(3, 5)); // a move by the first player, and captures
        assertEquals(1, game4.capturedPairsNo(PlayerRole.SECOND_PLAYER));
        game4.makeMove(new Position(4, 3)); // a move by the second player
        game4.makeMove(new Position(5, 2)); // a move by the first player
        game4.makeMove(new Position(4, 4)); // a move by the second player
        game4.makeMove(new Position(4, 5)); // a move by the first player,and captures
        assertEquals(2, game4.capturedPairsNo(PlayerRole.SECOND_PLAYER));

        //test case 3: can 5 captured pairs end a game?
        game4.makeMove(new Position(5, 3)); // a move by the second player
        game4.makeMove(new Position(7, 2)); // a move by the first player
        game4.makeMove(new Position(5, 4)); // a move by the second player
        game4.makeMove(new Position(5, 5)); // a move by the first player, and captures
        assertEquals(3, game4.capturedPairsNo(PlayerRole.SECOND_PLAYER));
        game4.makeMove(new Position(7, 3)); // a move by the second player
        game4.makeMove(new Position(0, 2)); // a move by the first player
        game4.makeMove(new Position(7, 4)); // a move by the second player
        game4.makeMove(new Position(7, 5)); // a move by the first player, and captures,
        assertEquals(4, game4.capturedPairsNo(PlayerRole.SECOND_PLAYER));
        game4.makeMove(new Position(0, 3)); // a move by the second player
        game4.makeMove(new Position(0, 7)); // a move by the first player
        game4.makeMove(new Position(0, 4)); // a move by the second player
        game4.makeMove(new Position(0, 5)); // a move by the first player, and captures, which end the game
        assertEquals(5, game4.capturedPairsNo(PlayerRole.SECOND_PLAYER));
        assertTrue(game4.hasEnded());

        // test case 4: capture 2 pairs at a time, capturedPrirNo should plus 2
        Pente game2 = new Pente();
        game2.makeMove(new Position(0, 3)); // a move by the first player
        game2.makeMove(new Position(0, 1)); // a move by the second player
        game2.makeMove(new Position(1, 3)); // a move by the first player
        game2.makeMove(new Position(0, 2)); // a move by the second player
        game2.makeMove(new Position(3, 0)); // a move by the first player
        game2.makeMove(new Position(2, 0)); // a move by the second player
        game2.makeMove(new Position(3, 1)); // a move by the first player
        game2.makeMove(new Position(1, 0)); // a move by the second player
        game2.makeMove(new Position(0, 0)); // a move by the first player, and captures 2 pairs
        assertEquals(2, game2.capturedPairsNo(PlayerRole.FIRST_PLAYER));
    }

    @Test
    void hasEnded() {
        // test case 1: is a board with 5 in a row an ended game?
        Pente game = new Pente();
        assertFalse(game.hasEnded());
        game.makeMove(new Position(1, 1)); // a move by the first player
        game.makeMove(new Position(2, 1)); // a move by the second player
        game.makeMove(new Position(1, 2)); // a move by the first player
        game.makeMove(new Position(2, 2)); // a move by the second player
        game.makeMove(new Position(1, 3)); // a move by the first player
        game.makeMove(new Position(2, 3)); // a move by the second player
        game.makeMove(new Position(1, 4)); // a move by the first player
        game.makeMove(new Position(2, 4)); // a move by the second player
        game.makeMove(new Position(1, 5)); // a move by the first player
        assertTrue(game.hasEnded());

        // TODO 6: write at least 3 test cases
        // test case 3: game end by connecting 5 horizontally
        Pente game2 = new Pente();
        Position p = new Position(2, 2);
        game2.makeMove(p);
        game2.makeMove(new Position(2, 3)); // a move by the second player
        game2.makeMove(new Position(3, 2)); // a move by the first player
        game2.makeMove(new Position(2, 4)); // a move by the second player
        game2.makeMove(new Position(2, 5)); // a move by the first player, which should capture
        game2.makeMove(new Position(3, 3)); // a move by the second player
        game2.makeMove(new Position(1, 2)); // a move by the first player
        game2.makeMove(new Position(3, 4)); // a move by the second player
        game2.makeMove(new Position(4, 2)); // a move by the first player
        game2.makeMove(new Position(2, 4)); // a move by the second player
        game2.makeMove(new Position(5, 2)); // a move by the first player, which should end the game
        assertTrue(game2.hasEnded());

        // test case 3: game end by 5 captures by first player
        Pente game3 = new Pente();
        assertEquals(PlayerRole.SECOND_PLAYER, game.currentPlayer());
        game3.makeMove(new Position(3, 2)); // a move by the first player
        game3.makeMove(new Position(3, 3)); // a move by the second player
        game3.makeMove(new Position(4, 2)); // a move by the first player
        game3.makeMove(new Position(3, 4)); // a move by the second player
        game3.makeMove(new Position(3, 5)); // a move by the first player, and captures
        assertEquals(1, game3.capturedPairsNo(PlayerRole.FIRST_PLAYER));
        game3.makeMove(new Position(4, 3)); // a move by the second player
        game3.makeMove(new Position(5, 2)); // a move by the first player
        game3.makeMove(new Position(4, 4)); // a move by the second player
        game3.makeMove(new Position(4, 5)); // a move by the first player,and captures
        assertEquals(2, game3.capturedPairsNo(PlayerRole.FIRST_PLAYER));
        game3.makeMove(new Position(5, 3)); // a move by the second player
        game3.makeMove(new Position(7, 2)); // a move by the first player
        game3.makeMove(new Position(5, 4)); // a move by the second player
        game3.makeMove(new Position(5, 5)); // a move by the first player, and captures
        assertEquals(3, game3.capturedPairsNo(PlayerRole.FIRST_PLAYER));
        game3.makeMove(new Position(7, 3)); // a move by the second player
        game3.makeMove(new Position(0, 2)); // a move by the first player
        game3.makeMove(new Position(7, 4)); // a move by the second player
        game3.makeMove(new Position(7, 5)); // a move by the first player, and captures,
        assertEquals(4, game3.capturedPairsNo(PlayerRole.FIRST_PLAYER));
        game3.makeMove(new Position(0, 3)); // a move by the second player
        game3.makeMove(new Position(0, 7)); // a move by the first player
        game3.makeMove(new Position(0, 4)); // a move by the second player
        game3.makeMove(new Position(0, 5)); // a move by the first player, and captures, which end the game
        assertEquals(5, game3.capturedPairsNo(PlayerRole.FIRST_PLAYER));
        assertTrue(game3.hasEnded());

        // test case 4: game end by 5 captures by second player
        Pente game4 = new Pente();
        game4.makeMove(new Position(0, 0));
        game4.makeMove(new Position(3, 2)); // a move by the first player
        game4.makeMove(new Position(3, 3)); // a move by the second player
        game4.makeMove(new Position(4, 2)); // a move by the first player
        game4.makeMove(new Position(3, 4)); // a move by the second player
        game4.makeMove(new Position(3, 5)); // a move by the first player, and captures
        assertEquals(1, game4.capturedPairsNo(PlayerRole.SECOND_PLAYER));
        game4.makeMove(new Position(4, 3)); // a move by the second player
        game4.makeMove(new Position(5, 2)); // a move by the first player
        game4.makeMove(new Position(4, 4)); // a move by the second player
        game4.makeMove(new Position(4, 5)); // a move by the first player,and captures
        assertEquals(2, game4.capturedPairsNo(PlayerRole.SECOND_PLAYER));
        game4.makeMove(new Position(5, 3)); // a move by the second player
        game4.makeMove(new Position(7, 2)); // a move by the first player
        game4.makeMove(new Position(5, 4)); // a move by the second player
        game4.makeMove(new Position(5, 5)); // a move by the first player, and captures
        assertEquals(3, game4.capturedPairsNo(PlayerRole.SECOND_PLAYER));
        game4.makeMove(new Position(7, 3)); // a move by the second player
        game4.makeMove(new Position(0, 2)); // a move by the first player
        game4.makeMove(new Position(7, 4)); // a move by the second player
        game4.makeMove(new Position(7, 5)); // a move by the first player, and captures,
        assertEquals(4, game4.capturedPairsNo(PlayerRole.SECOND_PLAYER));
        game4.makeMove(new Position(0, 3)); // a move by the second player
        game4.makeMove(new Position(0, 7)); // a move by the first player
        game4.makeMove(new Position(0, 4)); // a move by the second player
        game4.makeMove(new Position(0, 5)); // a move by the first player, and captures, which end the game
        assertEquals(5, game4.capturedPairsNo(PlayerRole.SECOND_PLAYER));
        assertTrue(game4.hasEnded());
    }

    @Test
    void stateEqual() {
        Pente game1 = new Pente();
        Pente game2 = new Pente();
        Pente game3 = new Pente();

        // test 1: games with equal board states should be stateEqual()
        game1.makeMove(new Position(3, 3));
        game2.makeMove(new Position(3, 3));
        assertTrue(game1.stateEqual(game2));
        assertTrue(game2.stateEqual(game1));

        // test 2: games with unequal board states should not be stateEqual()
        game3.makeMove(new Position(0, 0));
        assertFalse(game1.stateEqual(game3));
        // TODO 7: write at least 3 test cases
        // test 3: games with equal first_player_captured_times and second_player_captured_times
        game1.makeMove(new Position(4, 4));
        game1.makeMove(new Position(6, 6));
        game1.makeMove(new Position(5, 5));
        game2.makeMove(new Position(4, 4));
        game2.makeMove(new Position(6, 6));
        game2.makeMove(new Position(5, 5));
        assertTrue(game1.stateEqual(game2));
        assertTrue(game2.stateEqual(game1));
        // test 4: games with different first_player_captured_times and second_player_captured_times
        game1.makeMove(new Position(3, 4));
        game1.makeMove(new Position(2, 3));
        game1.makeMove(new Position(4, 3));
        game1.makeMove(new Position(5, 3));
        Pente game4 = new Pente();
        game4.makeMove(new Position(3, 4));
        game4.makeMove(new Position(2, 3));
        game4.makeMove(new Position(6, 6));
        game4.makeMove(new Position(5, 3));
        assertFalse(game1.stateEqual(game4));
        // tast 5: games with equal capture times but different board
        game2.makeMove(new Position(3, 2));
        game2.makeMove(new Position(3, 1));
        game2.makeMove(new Position(4, 3));
        game2.makeMove(new Position(3, 4));
        assertFalse(game1.stateEqual(game2));
    }
}