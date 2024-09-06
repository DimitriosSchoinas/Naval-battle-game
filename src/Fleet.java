/**
 * @author Dimitrios Schoinas 65313 and 65830 Miguel Génio Tocantins dos Santos Martins Fleet class handles the information
 * concerning the fleet
 */
public class Fleet {

    /**
     * Fleet instance variable
     */
    private char[][] fleet;

    /**
     * Fleet constructor
     *
     * @pre: fleet != null
     */
    public Fleet(char[][] fleet) {
        this.fleet = fleet;
    }

    /**
     * gives the fleet matrix
     *
     * @return the fleet matrix
     */
    public char[][] getFleet() {
        return fleet;
    }
}
