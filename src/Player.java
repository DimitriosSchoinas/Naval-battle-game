
/**
 * @author Dimitrios Schoinas 65313 and 65830 Miguel Génio Tocantins dos Santos Martins Player class handles the information
 * concerning the player
 */
public class Player {

    /**
     * Player constants
     */
    private static final int INITIAL_VALUE = 0;
    private static final int INITIAL_CANNONS_VALUE = 1;
    private static final int POINT_MULTIPLIER_VALUE = 2;
    private static final int INT_VALUE_OF_A = 65;
    private static final int INT_VALUE_OF_Z = 90;
    private static final int SUNK_BOAT_MULTIPLIER = -30;
    private static final int SHOT_BOAT_MULTIPLIER = 100;

    /**
     * Player instance variables
     */
    private String playerName;
    private int points;
    private char[][] fleetDisposition;
    private char[][] fleetEstate;

    /**
     * Player constructor
     *
     * @param playerName       name of the player
     * @param fleetDisposition disposition of the fleet
     * @param fleetEstate      disposition of the fleet
     * @pre: playerName.length > 0 && playerName.length <= 40 &&  fleetDisposition != null && fleetEstate != null
     */
    public Player(String playerName, char[][] fleetDisposition, char[][] fleetEstate) {

        this.playerName = playerName;
        this.points = INITIAL_VALUE;
        this.fleetDisposition = fleetDisposition;
        this.fleetEstate = fleetEstate;
    }

    /**
     * duplicates the points of the last player alive
     */
    public void duplicatePoints() {

        points = points * POINT_MULTIPLIER_VALUE;
    }

    /**
     * Verifies if a player has any boats
     *
     * @return true if player has any boat and false if not
     */
    public boolean hasBoats() {

        for (int i = 0; i < fleetEstate.length; i++)
            for (int j = 0; j < fleetEstate[0].length; j++)
                if (fleetEstate[i][j] >= INT_VALUE_OF_A && fleetEstate[i][j] <= INT_VALUE_OF_Z)
                    return true;
        return false;
    }

    /**
     * Gives the name of the player
     *
     * @return the name of the player
     */
    public String getName() {
        return this.playerName;
    }

    /**
     * Gives the punctuation of the player
     *
     * @return the punctuation of the player
     */
    public int getPunctuation() {
        return this.points;
    }

    /**
     * Shows the fleet of the player at the moment
     *
     * @return the state of the fleet at the moment
     */
    public String[] getFleetState() {

        String[] fleetState = new String[fleetDisposition.length];


        for (int i = 0; i < fleetState.length; i++) {
            String fleetLine = "";
            for (int j = 0; j < fleetEstate[0].length; j++)
                fleetLine += fleetEstate[i][j];
            fleetState[i] = fleetLine;
        }
        return fleetState;
    }

    /**
     * gives the current fleet disposition
     *
     * @return the current fleet disposition
     */
    public char[][] getFleet() {
        return fleetEstate;
    }

    /**
     * gives the original fleet disposition
     *
     * @return gives the original fleet disposition
     */
    public char[][] getOGFleet() {
        return fleetDisposition;
    }

    /**
     * Gives the number of lines of the grid
     *
     * @return the number of lines of the grid
     */
    public int getGridSizeL() {
        return fleetDisposition.length;
    }

    /**
     * Gives the number of columns of the grid
     *
     * @return the number of columns of the grid
     */
    public int getGridSizeC() {
        return fleetDisposition[0].length;
    }

    /**
     * Changes the fleet state
     *
     * @param positionL line position
     * @param positionC column position
     * @pre: positionL >= 1 && positionL <= gridSizeL && positionC >= 1 && positionC <= gridSizeC
     */
    public void changeFleetState(int positionL, int positionC) {
        char positionChar = fleetEstate[positionL][positionC];
        int i = positionC + 1;
        //changes positions right
        while (i < fleetEstate[0].length && fleetEstate[positionL][i] == positionChar && positionChar != '.') {
            fleetEstate[positionL][i] = '*';
            i++;
        }
        i = positionC;
        //changes positions left
        while (i >= INITIAL_VALUE && fleetEstate[positionL][i] == positionChar && positionChar != '.') {
            fleetEstate[positionL][i] = '*';
            i--;
        }
        i = positionL + 1;
        //changes positions down
        while (i < fleetEstate.length && fleetEstate[i][positionC] == positionChar && positionChar != '.') {
            fleetEstate[i][positionC] = '*';
            i++;
        }
        i = positionL - 1;
        //changes positions up
        while (i >= INITIAL_VALUE && fleetEstate[i][positionC] == positionChar && positionChar != '.') {
            fleetEstate[i][positionC] = '*';
            i--;
        }
    }

    /**
     * calculates the number of boat cannons in right of the shot position
     *
     * @param otherPlayerOriginalFleet the original fleet of the other player
     * @param positionL                line position
     * @param positionC                column position
     * @return the number of boat cannons in right of the shot position
     * @pre: otherPlayerOriginalFleet != null && positionL >= 1 && positionL <= gridSizeL && positionC >= 1 && positionC <= gridSizeC
     */
    private int calculateNumberOfCannonsForward(char[][] otherPlayerOriginalFleet, int positionL, int positionC) {

        int numberOfCannons = INITIAL_VALUE;
        char positionChar = otherPlayerOriginalFleet[positionL][positionC];
        int i = positionC + 1;

        while (i < otherPlayerOriginalFleet[0].length && otherPlayerOriginalFleet[positionL][i] == positionChar) {
            numberOfCannons++;
            i++;
        }
        return numberOfCannons;
    }

    /**
     * calculates the number of boat cannons left the shot position
     *
     * @param otherPlayerOriginalFleet the original fleet of the other player
     * @param positionL                line position
     * @param positionC                column position
     * @return the number of boat cannons in left of the shot position
     * @pre: otherPlayerOriginalFleet != null && positionL >= 1 && positionL <= gridSizeL && positionC >= 1 && positionC <= gridSizeC
     */
    private int calculateNumberOfCannonsBackwards(char[][] otherPlayerOriginalFleet, int positionL, int positionC) {

        int numberOfCannons = INITIAL_VALUE;
        char positionChar = otherPlayerOriginalFleet[positionL][positionC];
        int i = positionC - 1;

        while (i >= INITIAL_VALUE && otherPlayerOriginalFleet[positionL][i] == positionChar) {
            numberOfCannons++;
            i--;
        }
        return numberOfCannons;
    }

    /**
     * calculates the number of boat cannons up the shot position
     *
     * @param otherPlayerOriginalFleet the original fleet of the other player
     * @param positionL                line position
     * @param positionC                column position
     * @return the number of boat cannons up of the shot position
     * @pre: otherPlayerOriginalFleet != null && positionL >= 1 && positionL <= gridSizeL && positionC >= 1 && positionC <= gridSizeC
     */
    private int calculateNumberOfCannonsUp(char[][] otherPlayerOriginalFleet, int positionL, int positionC) {

        int numberOfCannons = INITIAL_VALUE;
        char positionChar = otherPlayerOriginalFleet[positionL][positionC];
        int i = positionL - 1;

        while (i >= INITIAL_VALUE && otherPlayerOriginalFleet[i][positionC] == positionChar) {
            numberOfCannons++;
            i--;
        }
        return numberOfCannons;
    }

    /**
     * calculates the number of boat cannons down the shot position
     *
     * @param otherPlayerOriginalFleet the original fleet of the other player
     * @param positionL                line position
     * @param positionC                column position
     * @return the number of boat cannons down of the shot position
     * @pre: otherPlayerOriginalFleet != null && positionL >= 1 && positionL <= gridSizeL && positionC >= 1 && positionC <= gridSizeC
     */
    private int calculateNumberOfCannonsDown(char[][] otherPlayerOriginalFleet, int positionL, int positionC) {

        int numberOfCannons = INITIAL_VALUE;
        char positionChar = otherPlayerOriginalFleet[positionL][positionC];
        int i = positionL + 1;

        while (i < otherPlayerOriginalFleet.length && otherPlayerOriginalFleet[i][positionC] == positionChar) {
            numberOfCannons++;
            i++;
        }
        return numberOfCannons;
    }

    /**
     * calculates the punctuation of the player when he shoots
     *
     * @param otherPlayerfleetEstate   the other player current fleet disposition
     * @param positionL                line position
     * @param positionC                column position
     * @param otherPlayerOriginalFleet the original fleet of the other player
     * @pre: otherPlayerOriginalFleet != null && positionL >= 1 && positionL <= gridSizeL && positionC >= 1 && positionC <= gridSizeC && otherPlayerfleetEstate != null
     */
    public void calculatePunctuation(char[][] otherPlayerfleetEstate, int positionL, int positionC, char[][] otherPlayerOriginalFleet) {

        char positionChar = otherPlayerfleetEstate[positionL][positionC];
        int boatCannonsNumber = INITIAL_CANNONS_VALUE;
        int numberOfCannonsForward = calculateNumberOfCannonsForward(otherPlayerOriginalFleet, positionL, positionC);
        int numberOfCannonsBackward = calculateNumberOfCannonsBackwards(otherPlayerOriginalFleet, positionL, positionC);
        int numberOfCannonsDown = calculateNumberOfCannonsDown(otherPlayerOriginalFleet, positionL, positionC);
        int numberOfCannonsUp = calculateNumberOfCannonsUp(otherPlayerOriginalFleet, positionL, positionC);

        boatCannonsNumber += (numberOfCannonsForward + numberOfCannonsBackward + numberOfCannonsDown + numberOfCannonsUp);

        if (positionChar == '*')
            this.points += (boatCannonsNumber * SUNK_BOAT_MULTIPLIER);
        else if (positionChar == '.')
            this.points += 0;
        else
            this.points += (boatCannonsNumber * SHOT_BOAT_MULTIPLIER);
    }
}
