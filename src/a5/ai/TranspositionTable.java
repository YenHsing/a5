package a5.ai;

import cms.util.maybe.Maybe;

/**
 * A transposition table for an arbitrary game. It maps a game state
 * to a search depth and a heuristic evaluation of that state to the
 * recorded depth. Unlike a conventional map abstraction, a state is
 * associated with a depth, so that clients can look for states whose
 * entry has at least the desired depth.
 *
 * @param <GameState> A type representing the state of a game.
 */
public class TranspositionTable<GameState> {

    /**
     * Information about a game state, for use by clients.
     */
    public interface StateInfo {

        /**
         * The heuristic value of this game state.
         */
        int value();

        /**
         * The depth to which the game tree was searched to determine the value.
         */
        int depth();
    }

    /**
     * A Node is a node in a linked list of nodes for a chaining-based implementation of a hash
     * table.
     *
     * @param <GameState>
     */
    static private class Node<GameState> implements StateInfo {
        /**
         * The state
         */
        GameState state;
        /**
         * The depth of this entry. >= 0
         */
        int depth;
        /**
         * The value of this entry.
         */
        int value;
        /**
         * The next node in the list. May be null.
         */
        Node<GameState> next;

        Node(GameState state, int depth, int value, Node<GameState> next) {
            this.state = state;
            this.depth = depth;
            this.value = value;
            this.next = next;
        }

        public int value() {
            return value;
        }

        public int depth() {
            return depth;
        }
    }

    /**
     * The number of entries in the transposition table.
     */
    private int size;

    /**
     * The buckets array may contain null elements.
     * Class invariant:
     * All transposition table entries are found in the linked list of the
     * bucket to which they hash, and the load factor is no more than 1.
     */
    private Node<GameState>[] buckets;

    // TODO 1: implement the classInv() method. You may also
    // strengthen the class invariant. The classInv()
    // method is likely to be expensive, so you may want to turn
    // off assertions in this file, but only after you have the transposition
    // table fully tested and working.
    boolean classInv() {
           return false;
    }

    @SuppressWarnings("unchecked")
    /** Creates: a new, empty transposition table. */
    TranspositionTable() {
        size = 0;
        // TODO 2
        buckets = new Node[5]; // initial space = 5
    }

    /** The number of entries in the transposition table. */
    public int size() {
        return size;
    }

    /**
     * Returns: the information in the transposition table for a given
     * game state, package in an Optional. If there is no information in
     * the table for this state, returns an empty Optional.
     */
    public Maybe<StateInfo> getInfo(GameState state) {
        // TODO 3

        for(int i = 0; i < buckets.length; i++){
            if(buckets[i] != null && buckets[i].state.equals(state)){
                return Maybe.some(buckets[i]);
            }
        }
        return Maybe.none();
    }

    /**
     * Effect: Add a new entry in the transposition table for a given
     * state and depth, or overwrite the existing entry for this state
     * with the new state and depth. Requires: if overwriting an
     * existing entry, the new depth must be greater than the old one.
     */
    public void add(GameState state, int depth, int value) {
        // TODO 4
        //if load factor > 0.8, resize array to double size
        if(size > buckets.length * 0.8) resize(buckets.length * 2);
        // search in the array to find the existing entry
        for(int i = 0; i < buckets.length; i++){
            if(buckets[i]==null){
                buckets[i] = new Node<>(state, depth, value, null);
                return;
            }
            else if(state.equals(buckets[i].state)) {
                // only overwrite if depth is larger than the existing node's depth
                if (depth > buckets[i].depth) {
                    Node<GameState> temp = buckets[i];
                    buckets[i] = new Node<>(state, depth, value, temp.next);
                    size++;
                    return;
                }
            }
        }
    }

    /**
     * Effect: Make sure the hash table has at least {@code target} buckets.
     * Returns true if the hash table actually resized.
     */
    private boolean grow(int target) {
        // TODO 5
        //resize if load factor > 0.8
        if(target > buckets.length){
            resize(target);
            return true;
        }
        return false;
    }

    // You may want to write some additional helper methods.
    private void resize(int target){
        Node<GameState>[] new_buckets = new Node[target];
        for(int i = 0; i < buckets.length; i++){
            new_buckets[i] = buckets[i];
        }
        buckets = new_buckets;
    }


    /**
     * Estimate clustering. With a good hash function, clustering
     * should be around 1.0. Higher values of clustering lead to worse
     * performance.
     */
    double estimateClustering() {
        final int N = 500;
        int m = buckets.length, n = size;
        double sum2 = 0;
        for (int i = 0; i < N; i++) {
            int j = Math.abs((i * 82728353) % buckets.length);
            int count = 0;
            Node<GameState> node = buckets[j];
            while (node != null) {
                count++;
                node = node.next;
            }
            sum2 += count*count;
        }
        double alpha = (double)n/m;
        return sum2/(N * alpha * (1 - 1.0/m + alpha));
    }
}
