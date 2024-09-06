/**
 * @author Dimitrios Schoinas 65313 and 65830 Miguel Génio Tocantins dos Santos Martins Game class handles the information
 * concerning the game
 */
public class Game {

    /**
     * Game constants
     */
    private static final int NUMBER_OF_FLEETS = 0;
    private static final int NUMBER_OF_PLAYERS = 0;
    private static final int PLAYER_ONE_INDEX = 0;
    private static final int ERROR_VALUE = -1;

    private static final int GROWTH_FACTOR = 1;
    private static final int INITIAL_FLEETS_NUMBER = 1;


    /**
     * Game instance variables
     */
    private Player[] players;
    private int nextToPlayIndex;
    private boolean hasGameEnded;
    private Player[] playersOriginal;
    private int numberOfPlayers;
    private int numberOfFleets;
    private Fleet[] fleets;


    /**
     * Game constructor
     *
     * @pre: numberOfPlayers >= 2
     */
    public Game(int numberOfPlayers) {
        numberOfFleets = NUMBER_OF_FLEETS;
        fleets = new Fleet[INITIAL_FLEETS_NUMBER];
        this.numberOfPlayers = NUMBER_OF_PLAYERS;
        players = new Player[numberOfPlayers];
        playersOriginal = new Player[numberOfPlayers];
        nextToPlayIndex = PLAYER_ONE_INDEX;
        hasGameEnded = false;
    }

    /**
     * adds a player to the game
     *
     * @param playerName  name of the player
     * @param fleetNumber number of the fleet that he chooses
     * @pre: playerName.length > 0 && playerName.length <= 40 &&    fleetNumber >=1 && fleetNumber <= numberOfFleets;
     */
    public void addPlayer(String playerName, int fleetNumber) {

        char[][] fleetToCopy = fleets[fleetNumber - 1].getFleet();
        char[][] fleetToAdd = new char[fleetToCopy.length][fleetToCopy[0].length];
        char[][] fleetEstate = new char[fleetToCopy.length][fleetToCopy[0].length];
        for (int i = 0; i < fleetToAdd.length; i++)
            for (int j = 0; j < fleetToAdd[0].length; j++) {
                fleetToAdd[i][j] = fleetToCopy[i][j];
                fleetEstate[i][j] = fleetToCopy[i][j];
            }
        Player player = new Player(playerName, fleetToAdd, fleetEstate);
        players[numberOfPlayers++] = player;
        playersOriginal[numberOfPlayers - 1] = player;
    }

    /**
     * adds a fleet to the game
     *
     * @param fleet fleet to add
     * @pre: fleet != null
     */
    public void addFleet(char[][] fleet) {
        if (isFleetsFull())
            growFleets();
        fleets[numberOfFleets++] = new Fleet(fleet);
    }


    /**
     * increases the size of the fleets array
     */
    private void growFleets() {
        Fleet[] tmp = new Fleet[fleets.length + GROWTH_FACTOR];

        for (int i = 0; i < numberOfFleets; i++)
            tmp[i] = fleets[i];

        fleets = tmp;
    }

    /**
     * verifies if the fleets array is full
     *
     * @return true if the fleets array is full and false if not
     */
    private boolean isFleetsFull() {
        return fleets.length == numberOfFleets;
    }


    /**
     * finds the index of an active player
     *
     * @param playerName name of the active player
     * @return the index of the player
     * @pre: playerName.length > 0 && playerName.length <= 40
     */
    private int findPlayerIndex(String playerName) {
        int i = 0;

        while (i < playersOriginal.length && !playersOriginal[i].getName().equals(playerName))
            i++;

        if (i == playersOriginal.length)
            return ERROR_VALUE;
        return i;
    }

    /**
     * verifies if the game has ended
     *
     * @return true if the game has ended and false if not
     */
    public boolean hasEnded() {
        return hasGameEnded;
    }

    /**
     * ends the game if any player has no boats
     *
     * @pre: playerName.length > 0 && playerName.length <= 40
     */
    private void isPlayerWithoutBoats(String playerName) {
        if (!getPlayer(playerName).hasBoats())
            Eliminate(playerName);
    }

    /**
     * Gives the next player to shot
     *
     * @return the next player to shot
     */
    public Player getNextPlayerToShot() {
        return players[nextToPlayIndex];
    }

    /**
     * Verifies if the player name exists
     *
     * @param playerName the name of the active player
     * @return true if the active player name exists and false if not
     * @pre: playerName.length > 0 && playerName.length <= 40
     */
    public boolean doesNameExist(String playerName) {

        if (this.findPlayerIndex(playerName) != ERROR_VALUE)
            return true;
        return false;
    }

    /**
     * Gives the punctuation of the player
     *
     * @param playerName name of the player
     * @return the punctuation of the player
     * @pre: playerName.length > 0 && playerName.length <= 40
     */
    public int getPontuation(String playerName) {

        Player player = getOriginalPlayer(playerName);
        int pontuation = player.getPunctuation();
        return pontuation;
    }

    /**
     * Gives the current fleet of the player
     *
     * @param playerName name of the player
     * @return the current fleet of the player
     * @pre: playerName.length > 0 && playerName.length <= 40
     */
    public String[] getFleetState(String playerName) {

        Player player = getOriginalPlayer(playerName);
        return player.getFleetState();
    }

    /**
     * gives the Player with the given name
     *
     * @param playerName name of the player
     * @return the Player with the given name
     * @pre: playerName.length > 0 && playerName.length <= 40
     */
    private Player getOriginalPlayer(String playerName) {

        return playersOriginal[findPlayerIndex(playerName)];
    }

    /**
     * gives the active Player with the given name
     *
     * @param playerName name of the active player
     * @return the active Player with the given name
     * @pre: playerName.length > 0 && playerName.length <= 40
     */
    private Player getPlayer(String playerName) {

        return players[findActivePlayersIndex(playerName)];
    }

    /**
     * Gives the number of lines of the fleet
     *
     * @return the number of lines of the fleet
     * @pre: playerName.length > 0 && playerName.length <= 40
     */
    public int getGridSizeL(String playerName) {
        return getPlayer(playerName).getGridSizeL();
    }

    /**
     * Gives the number of columns of the fleet
     *
     * @return the number of columns of the fleet
     * @pre: playerName.length > 0 && playerName.length <= 40
     */
    public int getGridSizeC(String playerName) {
        return getPlayer(playerName).getGridSizeC();
    }

    /**
     * updates the turn of the next player to play
     */
    private void updateNextToPlay() {
        if (nextToPlayIndex >= numberOfPlayers - 1)
            nextToPlayIndex = PLAYER_ONE_INDEX;
        else
            nextToPlayIndex++;

    }

    /**
     * The next player to shoot makes a shot in the specified position shooting the target player
     *
     * @param positionLine line to shot
     * @param positionCol  column to shot
     * @param target       name of the player affected by the shot
     * @pre: positionLine >= 1 && positionLine <= gridSizeL(target) && positionCol >= 1 && positionCol <= gridSizeC(target) && target.length > 0 && target.length <= 40
     */
    public void makeShot(int positionLine, int positionCol, String target) {

        Player player = this.getNextPlayerToShot();
        int positionL = positionLine - 1;
        int positionC = positionCol - 1;

        char[][] otherPlayerfleetEstate = getPlayer(target).getFleet();
        char[][] otherPlayerOriginalFleet = getPlayer(target).getOGFleet();
        player.calculatePunctuation(otherPlayerfleetEstate, positionL, positionC, otherPlayerOriginalFleet);
        getPlayer(target).changeFleetState(positionL, positionC);

        this.isPlayerWithoutBoats(target);

        updateNextToPlay();
    }

    /**
     * Gives the name of the winner of the game
     *
     * @return the name of the winner of the game
     */
    public String getWinnerName() {
        Player winner = players[PLAYER_ONE_INDEX];

        int playerScore = playersOriginal[PLAYER_ONE_INDEX].getPunctuation();
        int highestScoreindex = 0;

        for (int i = 1; i < playersOriginal.length; i++)
            if (playersOriginal[i].getPunctuation() > playerScore) {
                playerScore = playersOriginal[i].getPunctuation();
                highestScoreindex = i;
            }

        int count = 0;

        for (int i = 0; i < playersOriginal.length; i++)
            if (playersOriginal[i].getPunctuation() == playerScore)
                count++;

        if (count == 1)
            winner = playersOriginal[highestScoreindex];

        return winner.getName();
    }

    /**
     * sorts the array by the highest score to the lowest and if 2 values have the same score then sorts by alphabetical order
     *
     * @param tmp  array that we want to sort
     * @param size size of the array that we want to sort
     * @pre: tmp != null && size > 0
     */
    private void sort(Player[] tmp, int size) {

        for (int i = 0; i < size - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < size; j++) {
                if (tmp[j].getPunctuation() > tmp[minIndex].getPunctuation())
                    minIndex = j;
                if (tmp[j].getPunctuation() == tmp[minIndex].getPunctuation())
                    if (tmp[j].getName().compareTo(tmp[minIndex].getName()) < 0)
                        minIndex = j;
            }
            Player tmpPlayer = tmp[i];
            tmp[i] = tmp[minIndex];
            tmp[minIndex] = tmpPlayer;
        }
    }

    /**
     * gives the iterator of the player scores
     *
     * @return the iterator of the player scores
     */
    public Iterator scoresIterator() {
        Player[] tmp = playersOriginal;
        sort(tmp, playersOriginal.length);
        return new Iterator(tmp, tmp.length);
    }

    /**
     * gives the iterator of the active players
     *
     * @return the iterator of the active players
     */
    public Iterator playersIterator() {

        return new Iterator(this.players, numberOfPlayers);
    }

    /**
     * gives the index of the active player
     *
     * @param playerName name of the active player
     * @return the index of the active player
     * playerName.length > 0 && playerName.length <= 40
     */
    private int findActivePlayersIndex(String playerName) {
        int i = 0;

        while (i < numberOfPlayers && !players[i].getName().equals(playerName))
            i++;

        if (i == numberOfPlayers)
            return ERROR_VALUE;
        return i;
    }

    /**
     * verifies if the player is eliminated or not
     *
     * @param otherPlayerName name of the player that we want to verify if is eliminated
     * @return true if the otherPlayerName is eliminated and false if not
     * @pre: otherPlayerName.length > 0 && otherPlayerName.length <= 40
     */
    public boolean isEliminated(String otherPlayerName) {
        if (this.findActivePlayersIndex(otherPlayerName) == ERROR_VALUE)
            return true;
        return false;
    }

    /**
     * eliminates the player with the given name
     *
     * @param playerName name of the player that we want to eliminate
     * @pre: playerName.length > 0 && playerName.length <= 40
     */
    private void Eliminate(String playerName) {

        int index = findActivePlayersIndex(playerName);
        for (int i = index; i < numberOfPlayers - 1; i++)
            players[i] = players[i + 1];

        numberOfPlayers--;
        if (index < nextToPlayIndex)
            nextToPlayIndex--;
        if (numberOfPlayers == 1) {
            hasGameEnded = true;
            players[0].duplicatePoints();
        }
    }
}

