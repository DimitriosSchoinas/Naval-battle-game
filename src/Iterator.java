/**
 * @author Dimitrios Schoinas 65313 and 65830 Miguel Génio Tocantins dos Santos Martins Iterator class handles the information
 * concerning the player iterator
 */
public class Iterator {

    /**
     * Iterator instance variables
     */
    private Player[] players;
    private int size;
    private int nextIndex;

    /**
     * Iterator constructor
     *
     * @pre: players != null && size > 0
     */
    public Iterator(Player[] players, int size) {
        this.size = size;
        this.players = players;
        nextIndex = 0;
    }

    /**
     * verifies if there is any next element in the array
     *
     * @return true if there is any next element in the array
     */
    public boolean hasNext() {
        return size > nextIndex;
    }

    /**
     * gives the next player to return
     *
     * @return the next player in the array
     */
    public Player next() {
        return players[nextIndex++];
    }


}