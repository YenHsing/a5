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

    private int capacity;

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
        // TODO 2
        size = 0;
        capacity = 500;
        buckets = new Node[capacity]; // initial space = 20
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
        int idx = gethashcode(state,capacity);
        if(buckets[idx] != null){
            Node n = buckets[idx];
            while(n!=null) {
                if (n.state.equals(state))return Maybe.some(buckets[idx]);
                n = n.next;
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
        int hashcode = gethashcode(state, capacity);
        if(size > capacity * 0.8) resize(capacity * 2);
        if(buckets[hashcode] == null){
            buckets[hashcode] = new Node<GameState>(state, depth, value, null);
            size++;
        }
        else {
            Node n = buckets[hashcode];
            Node head = n;
            while(n!=null){
                if(state.equals(n.state) && depth > n.depth){
                    n.value = value;
                    n.depth = depth;
                    buckets[hashcode] = head;
                    return;
                }
                n = n.next;
            }
            n = new Node<GameState>(state, depth, value, null);
            return;
        }

    }

    /**
     * Effect: Make sure the hash table has at least {@code target} buckets.
     * Returns true if the hash table actually resized.
     */
    private boolean grow(int target) {
        // TODO 5
        if(target > capacity){
            resize(target);
            return true;
        }
        return false;
    }

    // You may want to write some additional helper methods.
    private void resize(int target){

        Node<GameState>[] new_buckets = new Node[target];
        for(int i = 0; i < buckets.length; i++){
            if(buckets[i]!= null){
                int newhashcode = gethashcode(buckets[i].state, target);
                new_buckets[newhashcode] = buckets[i];
            }
        }
        buckets = new_buckets;
        this.capacity = target;
    }


    private int gethashcode(GameState state, int size){
        return Math.abs(state.hashCode()) % size;
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

//    public static final int EXACT_CUTOFF = 500;
//    double estimateClustering(boolean exact) {
//        int m = buckets.length, n = size;
//        if (buckets.length < EXACT_CUTOFF) exact = true;
//        final int N = exact ? m : EXACT_CUTOFF;
//        double sum2 = 0;
//        for (int i = 0; i < N; i++) {
//            int j = exact ? i : Math.abs((i * 82728353) % buckets.length);
//            int count = 0;
//            Node<GameState> node = buckets[j];
//            while (node != null) {
//                count++;
//                node = node.next;
//            }
//            sum2 += count*count;
//        }
//        double alpha = (double)n/m;
//        return sum2/(N * alpha * (1 - 1.0/m + alpha));
//    }
}
