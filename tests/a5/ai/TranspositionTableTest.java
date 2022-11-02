package a5.ai;

import static org.junit.jupiter.api.Assertions.*;

import a5.ai.TranspositionTable.StateInfo;
import a5.logic.Position;
import a5.logic.TicTacToe;
import cms.util.maybe.NoMaybeValue;
//import javax.swing.text.Position;
import org.junit.jupiter.api.Test;


class TranspositionTableTest {

    @Test
    void testConstructor() {
        // TODO 1: write at least 1 test case
    }


    @Test
    void getInfo() throws NoMaybeValue {
        // test case 1: look for a state that is in the table
        TranspositionTable<TicTacToe> table = new TranspositionTable<>();
        TicTacToe state = new TicTacToe();
        table.add(state, 0, GameModel.WIN);

        StateInfo info = table.getInfo(state).get();
        assertEquals(GameModel.WIN, info.value());
        assertEquals(0, info.depth());

        // test case 2: look for a state not in the table
        TicTacToe state2 = state.applyMove(new Position(0, 0));
        assertThrows(NoMaybeValue.class, () -> table.getInfo(state2).get());

        // TODO 2: write at least 3 more test cases
        TicTacToe state3 = state.applyMove(new Position(2,1));
//        table.add(state3, 0, 10000);
        table.add(state3, 1, 10000);
        StateInfo info3 = table.getInfo(state3).get();
        assertEquals(10000, info3.value());
        assertEquals(1, info3.depth());
    }

    @Test
    void add() throws NoMaybeValue {
        TranspositionTable<TicTacToe> table = new TranspositionTable<>();

        // test case 1: add a state and check it is in there
        TicTacToe state = new TicTacToe();
        table.add(state, 1, GameModel.WIN);

        StateInfo info = table.getInfo(state).get();
        assertEquals(GameModel.WIN, info.value());
        assertEquals(1, info.depth());

        // TODO 3: write at least 3 more test cases
    }
}