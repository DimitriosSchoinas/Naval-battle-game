import java.util.Scanner;
import java.io.*;

/**
 * @author Dimitrios Schoinas 65313 and 65830 Miguel Génio Tocantins dos Santos Martins Main class handles the information
 * concerning the user input and output
 */
public class Main {

    /**
     * Command constants
     */
    private static final String QUIT = "quit";
    private static final String PLAYER = "player";
    private static final String SCORE = "score";
    private static final String FLEET = "fleet";
    private static final String SHOOT = "shoot";
    private static final String SCORES = "scores";
    private static final String PLAYERS = "players";

    /**
     * Output constants
     */
    private static final int MIN_POS_VALUE = 1;
    private static final String INVALID_COMMAND = "Invalid command\n";
    private static final String GAME_ENDED_MESSAGE = "The game is over\n";
    private static final String GAME_NOT_ENDED_MESSAGE = "The game was not over yet...\n";
    private static final String PLAYER_MESSAGE = "Next player: %s\n";
    private static final String NONEXISTENT_PLAYER_MESSAGE = "Nonexistent player\n";
    private static final String SCORE_MESSAGE = "%s has %d points\n";
    private static final String SELF_INFLICT_MESSAGE = "Self-inflicted shot\n";
    private static final String ELIMINATED_MESSAGE = "Eliminated player\n";
    private static final String INVALID_SHOT_MESSAGE = "Invalid shot\n";
    private static final String WINNER_MESSAGE = "%s won the game!\n";
    private static final String SCORES_MESSAGE = "%s has %d points\n";
    private static final String PLAYERS_MESSAGE = "%s\n";

    private static final String FILE_NAME = "fleets.txt";

    /**
     * Main program. Creates the game and invokes the command interpreter
     *
     * @param args - arguments for running the application. Not used in this
     *             program.
     */
    public static void main(String[] args) throws FileNotFoundException {

        Scanner in = new Scanner(System.in);
        String fileName = FILE_NAME;
        Scanner input = new Scanner(new FileReader(fileName));
        int n = in.nextInt();
        in.nextLine();
        Game game = readFrom(input, n);
        createGame(in, game, n);
    }

    /**
     * Creates the game
     *
     * @param in   scanner input
     * @param game current game
     * @param n    number of players
     * @pre: in != null && game != null && n >= 2
     */
    private static void createGame(Scanner in, Game game, int n) {

        for (int i = 0; i < n; i++) {
            String playerName = in.nextLine();
            int fleetNumber = in.nextInt();
            in.nextLine();
            game.addPlayer(playerName, fleetNumber);
        }
        executeCommands(in, game);
    }

    /**
     * command interpreter
     */
    private static void executeCommands(Scanner in, Game game) {
        String option;

        do {
            option = in.next();
            switch (option) {
                case PLAYER:
                    playerCommand(game);
                    break;
                case SCORE:
                    scoreCommand(game, in);
                    break;
                case FLEET:
                    fleetCommand(game, in);
                    break;
                case SHOOT:
                    shootCommand(game, in);
                    break;
                case SCORES:
                    scoresCommand(game);
                    break;
                case PLAYERS:
                    playersCommand(game, in);
                    break;
                case QUIT:
                    quitCommand(game);
                    break;
                default:
                    in.nextLine();
                    System.out.printf(INVALID_COMMAND);
                    break;
            }
        } while (!option.equals(QUIT));
        in.close();
    }

    /**
     * Shows the next player to play
     *
     * @param game current game
     * @pre:game != null
     */
    private static void playerCommand(Game game) {

        String nextToShoot;

        if (game.hasEnded())
            System.out.printf(GAME_ENDED_MESSAGE);
        else {
            nextToShoot = game.getNextPlayerToShot().getName();
            System.out.printf(PLAYER_MESSAGE, nextToShoot);
        }
    }

    /**
     * Shows the score of a player
     *
     * @param game current game
     * @param in   scanner input
     * @pre: game != null && playerName.length > 0 && playerName.length <= 40
     */
    private static void scoreCommand(Game game, Scanner in) {

        String playerName;
        int score;

        playerName = in.nextLine().trim();

        if (!game.doesNameExist(playerName))
            System.out.printf(NONEXISTENT_PLAYER_MESSAGE);
        else {
            score = game.getPontuation(playerName);
            System.out.printf(SCORE_MESSAGE, playerName, score);
        }
    }

    /**
     * Shows the fleet of a player
     *
     * @param game current game
     * @param in   scanner input
     * @pre: game != null && playerName.length > 0 && playerName.length <= 40
     */
    private static void fleetCommand(Game game, Scanner in) {

        String playerName;
        String[] fleetState;

        playerName = in.nextLine().trim();

        if (!game.doesNameExist(playerName))
            System.out.printf(NONEXISTENT_PLAYER_MESSAGE);
        else {
            fleetState = game.getFleetState(playerName);
            for (int i = 0; i < fleetState.length; i++)
                System.out.println(fleetState[i]);
        }
    }

    private static void scoresCommand(Game game) {

        Iterator it = game.scoresIterator();
        while (it.hasNext()) {
            Player player = it.next();
            System.out.printf(SCORES_MESSAGE, player.getName(), player.getPunctuation());
        }
    }

    private static void playersCommand(Game game, Scanner in) {

        Iterator it = game.playersIterator();
        while (it.hasNext()) {
            Player player = it.next();
            System.out.printf(PLAYERS_MESSAGE, player.getName());
        }
    }

    /**
     * The next player shoots at the selected position
     *
     * @param game current game
     * @param in   scanner input
     * @pre: game != null && position.isInt()
     */
    private static void shootCommand(Game game, Scanner in) {

        int positionL, positionC;
        positionL = in.nextInt();
        positionC = in.nextInt();
        String otherPlayerName = in.nextLine().stripLeading();

        if (game.hasEnded())
            System.out.printf(GAME_ENDED_MESSAGE);
        else if (otherPlayerName.equals(game.getNextPlayerToShot().getName()))
            System.out.printf(SELF_INFLICT_MESSAGE);
        else if (!game.doesNameExist(otherPlayerName))
            System.out.printf(NONEXISTENT_PLAYER_MESSAGE);
        else if (game.isEliminated(otherPlayerName))
            System.out.printf(ELIMINATED_MESSAGE);
        else if (positionL < MIN_POS_VALUE || positionL > game.getGridSizeL(otherPlayerName) || positionC < MIN_POS_VALUE || positionC > game.getGridSizeC(otherPlayerName))
            System.out.printf(INVALID_SHOT_MESSAGE);
        else
            game.makeShot(positionL, positionC, otherPlayerName);
    }

    /**
     * This command runs when the program ends
     *
     * @param game current game
     * @pre: game != null
     */
    private static void quitCommand(Game game) {

        String winnerName;

        if (!game.hasEnded())
            System.out.printf(GAME_NOT_ENDED_MESSAGE);
        else {
            winnerName = game.getWinnerName();
            System.out.printf(WINNER_MESSAGE, winnerName);
        }
    }

    /**
     * reads the file and creates a game with all the fleets stored there
     *
     * @param in scanner input
     * @param n  number of players
     * @return the new Game
     * @throws FileNotFoundException
     * @pre: n >= 2
     */
    private static Game readFrom(Scanner in, int n) throws FileNotFoundException {

        Game game = new Game(n);

        while (in.hasNextLine()) {
            int l = in.nextInt();
            int c = in.nextInt();
            in.nextLine();
            char[][] fleetDispositon = new char[l][c];
            for (int i = 0; i < l; i++) {
                String line = in.nextLine().trim();
                char[] lineDisposition = line.toCharArray();
                for (int j = 0; j < c; j++)
                    fleetDispositon[i][j] = lineDisposition[j];
            }
            game.addFleet(fleetDispositon);
        }
        return game;
    }
}