package a5.ai;

import static org.junit.jupiter.api.Assertions.*;

import a5.ai.TranspositionTable.StateInfo;
import a5.logic.Position;
import a5.logic.TicTacToe;
import cms.util.maybe.Maybe;
import cms.util.maybe.NoMaybeValue;
import org.junit.jupiter.api.Test;

class TranspositionTableTest {

    @Test
    void testConstructor() {
        // TODO 1: write at least 1 test case
        TranspositionTable<TicTacToe> table = new TranspositionTable<>();
        assertEquals(0, table.size());
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
        // test case 3: state in the table with different depth and value
        TicTacToe state3 = state.applyMove(new Position(2, 1));
        table.add(state3, 1, 1);
        StateInfo info3 = table.getInfo(state3).get();
        assertEquals(1, info3.value());
        assertEquals(1, info3.depth());

        // test case 4: an empty table
        TranspositionTable<TicTacToe> table2 = new TranspositionTable<>();
        assertEquals(Maybe.none(),table2.getInfo(state));

        // test case 5: another state not in the table
        TicTacToe state4 = state.applyMove(new Position(2, 0));
        assertThrows(NoMaybeValue.class, () -> table.getInfo(state4).get());
    }

    @Test
    void add() throws NoMaybeValue {
        TranspositionTable<TicTacToe> table = new TranspositionTable<>();

        // test case 1: add a state and check it is in there
        TicTacToe state = new TicTacToe();
        System.out.println(state.hashCode()%16);
        table.add(state, 0, GameModel.WIN);

        StateInfo info = table.getInfo(state).get();
        assertEquals(GameModel.WIN, info.value());
        assertEquals(0, info.depth());

//        // TODO 3: write at least 3 more test cases
//        // test case 2: add a state with different value and check it is in there
//        TicTacToe state1 = new TicTacToe();
//        table.add(state1, 1,10000);
//        StateInfo info1 = table.getInfo(state1).get();
//        assertEquals(10000, info1.value());
//        assertEquals(1, info1.depth());

        TicTacToe state4 = new TicTacToe();
//        state4.makeMove(new Position(3,2));
        state4.makeMove(new Position(1,2));
        System.out.println(state.equals(state4));
        System.out.println(state4.hashCode()%16);
        table.add(state4, 100,9387);
        StateInfo info4 = table.getInfo(state4).get();
        assertEquals(100, info4.depth());
        assertEquals(9387, info4.value());



//        // test case 3: change the information in the first bucket and check the information
//        table.add(state,2,1);
//        StateInfo info2 = table.getInfo(state).get();
//        assertEquals(1, info2.value());
//        assertEquals(2, info2.depth());

//        // test case 4: add elements with same state but dept less than the original one
//        table.add(state,1,10000);
//        StateInfo info3 = table.getInfo(state).get();
//        assertEquals(1, info3.value());
//        assertEquals(2, info3.depth());
    }
}