import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//board class that initializes the game (initial state) and keeps the board updates as the game progress
public class Board implements StateSpaceModel {

    Reversi[][] board;
    int size;
    static ArrayList<Character> convert = new ArrayList<>();
    Character[] alphabet = { ' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };
    Map<String, Reversi[][]> darkMap = new HashMap<>();
    Map<String, Reversi[][]> lightMap = new HashMap<>();
    int countDark, countLight;

    //representation of a state
    public Board(int size) {
        this.size = size;
        this.board = new Reversi[size + 1][size + 1];
    }

    //initial state
    public void initiate() {
        convert.addAll(Arrays.asList(alphabet));
        // index to know where to put the initial state pieces
        int index = size / 2;
        // default value for all pieces is an empty space " "
        for (int i = 0; i <= size; i++) {
            for (int j = 0; j <= size; j++) {
                if ((i == index && j == index) || (i == index + 1 && j == index + 1)) {
                    board[i][j] = new Reversi("o", i, j);
                } else if ((i == index && j == index + 1) || (i == index + 1 && j == index)) {
                    board[i][j] = new Reversi("x", i, j);
                } else
                    board[i][j] = new Reversi(" ", i, j);
            }
        }
    }

    Reversi[][] copy(Reversi[][] orig) {
        Reversi[][] copy = new Reversi[size + 1][size + 1];
        for (int i = 0; i <= size; i++) {
            for (int j = 0; j <= size; j++) {
                if (orig[i][j] != null) {
                    copy[i][j] = new Reversi(orig[i][j].color, i, j);
                }
            }
        }
        return copy;
    }

    void retrieve(Reversi[][] orig) {
        for (int i = 0; i <= size; i++) {
            for (int j = 0; j <= size; j++) {
                if (orig[i][j] != null) {
                    board[i][j] = new Reversi(orig[i][j].color, i, j);
                }
            }
        }
    }

    boolean isOnBoard(int i, int j) {
        if (i >= 1 && i <= size && j >= 1 && j <= size) {
            return true;
        } else
            return false;
    }

    boolean isEmpty(int i, int j, Reversi[][] copy) {
        if (!(isOnBoard(i, j))) {
            return false;
        }
        if (copy[i][j] == null || copy[i][j].color.equals(" ")) {
            return true;
        } else
            return false;
    }

    //transition model
    Map<String, Reversi[][]> possibleMove(Reversi[][] board, String player, String opp) {
        Map<String, Reversi[][]> movesMap = new HashMap<>();
        int x, y, x1, y1;
        boolean canFlipUpLeftDiag, canFlipDownRightDiag, canFlipDownLeftDiag, canFlipUpRightDiag, canFlipVertUp,
                canFlipVertDown, canFlipHorLeft, canFlipHorRight;
        for (int i = 0; i <= size; i++) {
            for (int j = 0; j <= size; j++) {
                canFlipUpLeftDiag = false;
                canFlipDownRightDiag = false;
                canFlipDownLeftDiag = false;
                canFlipUpRightDiag = false;
                canFlipVertUp = false;
                canFlipVertDown = false;
                canFlipHorLeft = false;
                canFlipHorRight = false;
                if (isEmpty(i, j, board)) {
                    Reversi[][] tmp = copy(board);
                    /** up left diagonal **/
                    if (isOnBoard(i - 1, j - 1) && board[i - 1][j - 1].color.equals(opp)) {
                        x = i - 1;
                        y = j - 1;
                        while (isOnBoard(x, y) && !canFlipUpLeftDiag) {
                            if (board[x][y].color.equals(player))
                                canFlipUpLeftDiag = true;
                            else {
                                x--;
                                y--;
                            }
                        }
                        if (canFlipUpLeftDiag) {
                            tmp[i][j].color = player;
                            x1 = i - 1;
                            y1 = j - 1;
                            while (x1 > x && y1 > y) {
                                tmp[x1][y1].color = player;
                                x1--;
                                y1--;
                            }
                        }
                    }
                    /** down right diagonal **/
                    if (isOnBoard(i + 1, j + 1) && board[i + 1][j + 1].color.equals(opp)) {
                        x = i + 1;
                        y = j + 1;
                        while (isOnBoard(x, y) && !canFlipDownRightDiag) {
                            if (board[x][y].color.equals(player))
                                canFlipDownRightDiag = true;
                            else {
                                x++;
                                y++;
                            }
                        }
                        if (canFlipDownRightDiag) {
                            tmp[i][j].color = player;
                            x1 = i + 1;
                            y1 = j + 1;
                            while (x1 < x && y1 < y) {
                                tmp[x1][y1].color = player;
                                x1++;
                                y1++;
                            }
                        }
                    }
                    /** up right diagonal **/
                    if (isOnBoard(i - 1, j + 1) && board[i - 1][j + 1].color.equals(opp)) {
                        x = i - 1;
                        y = j + 1;
                        while (isOnBoard(x, y) && !canFlipUpRightDiag) {
                            if (board[x][y].color.equals(player))
                                canFlipUpRightDiag = true;
                            else {
                                x--;
                                y++;
                            }
                        }
                        if (canFlipUpRightDiag) {
                            tmp[i][j].color = player;
                            x1 = i - 1;
                            y1 = j + 1;
                            while (x1 > x && y1 < y) {
                                tmp[x1][y1].color = player;
                                x1--;
                                y1++;
                            }
                        }
                    }
                    /** down left diagonal */
                    if (isOnBoard(i + 1, j - 1) && board[i + 1][j - 1].color.equals(opp)) {
                        x = i + 1;
                        y = j - 1;
                        while (isOnBoard(x, y) && !canFlipDownLeftDiag) {
                            if (board[x][y].color.equals(player))
                                canFlipDownLeftDiag = true;
                            else {
                                x++;
                                y--;
                            }
                        }
                        if (canFlipDownLeftDiag) {
                            tmp[i][j].color = player;
                            x1 = i + 1;
                            y1 = j - 1;
                            while (x1 < x && y1 > y) {
                                tmp[x1][y1].color = player;
                                x1++;
                                y1--;
                            }
                        }
                    }
                    /** vertical up */
                    if (isOnBoard(i - 1, j) && board[i - 1][j].color.equals(opp)) {
                        x = i - 1;
                        y = j;
                        while (isOnBoard(x, y) && !canFlipVertUp) {
                            if (board[x][y].color.equals(player))
                                canFlipVertUp = true;
                            else {
                                x--;
                            }
                        }
                        if (canFlipVertUp) {
                            tmp[i][j].color = player;
                            x1 = i - 1;
                            y1 = j;
                            while (x1 > x) {
                                tmp[x1][y1].color = player;
                                x1--;
                            }
                        }
                    }
                    /** vertical down */
                    if (isOnBoard(i + 1, j) && board[i + 1][j].color.equals(opp)) {
                        x = i + 1;
                        y = j;
                        while (isOnBoard(x, y) && !canFlipVertDown) {
                            if (board[x][y].color.equals(player))
                                canFlipVertDown = true;
                            else {
                                x++;
                            }
                        }
                        if (canFlipVertDown) {
                            tmp[i][j].color = player;
                            x1 = i + 1;
                            y1 = j;
                            while (x1 < x) {
                                tmp[x1][y1].color = player;
                                x1++;
                            }
                        }
                    }
                    /** horizontal left */
                    if (isOnBoard(i, j - 1) && board[i][j - 1].color.equals(opp)) {
                        x = i;
                        y = j - 1;
                        while (isOnBoard(x, y) && !canFlipHorLeft) {
                            if (board[x][y].color.equals(player))
                                canFlipHorLeft = true;
                            else {
                                y--;
                            }
                        }
                        if (canFlipHorLeft) {
                            tmp[i][j].color = player;
                            x1 = i;
                            y1 = j - 1;
                            while (y1 > y) {
                                tmp[x1][y1].color = player;
                                y1--;
                            }
                        }
                    }
                    /** horizontal right */
                    if (isOnBoard(i, j + 1) && board[i][j + 1].color.equals(opp)) {
                        x = i;
                        y = j + 1;
                        while (isOnBoard(x, y) && !canFlipHorRight) {
                            if (board[x][y].color.equals(player))
                                canFlipHorRight = true;
                            else {
                                y++;
                            }
                        }
                        if (canFlipHorRight) {
                            tmp[i][j].color = player;
                            x1 = i;
                            y1 = j + 1;
                            while (y1 < y) {
                                tmp[x1][y1].color = player;
                                y1++;
                            }
                        }
                    }
                    if (canFlipDownLeftDiag || canFlipDownRightDiag || canFlipHorLeft || canFlipHorRight
                            || canFlipUpLeftDiag || canFlipUpRightDiag || canFlipVertDown || canFlipVertUp)
                        movesMap.put(convert.get(j) + Integer.toString(i), tmp);
                }
            }
        }
        return movesMap;
    }

    //actions
    public void allActions(boolean darkTurn) {
        lightMap.clear();
        darkMap.clear();
        if (darkTurn)
            darkMap = possibleMove(board, "x", "o");
        else
            lightMap = possibleMove(board, "o", "x");
    }

    void print() {
        for (int i = 0; i <= board.length - 1; i++) {
            for (int j = 0; j <= board[i].length - 1; j++) {
                if (i == 0 && j == 0)
                    System.out.print("  ");
                if (i == 0 && j > 0)
                    System.out.print(convert.get(j) + " ");
                if (j == 0 && i > 0)
                    System.out.print(i + " ");
                if (board[i][j] == null || board[i][j].color.equals(" ")) {
                    if (i > 0 && j > 0) {
                        System.out.print("  ");
                    }
                } else if (board[i][j].color == "o") {
                    System.out.print("o ");
                } else if (board[i][j].color == "x") {
                    System.out.print("x ");
                }
            }
            System.out.println();
        }
    }

    public Object action(Object o) {
        return null;
    }

    public Object result() {
        return null;
    }

    public Reversi[][] state() {
        return board;
    }

    //cost
    public int cost() {
        return 1;
    }
    //Heuristic function: count the number of disks each side has
    public void getScore() {
        countDark = 0;
        countLight = 0;
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                if (board[i][j].color.equals("x"))
                    countDark++;
                else if (board[i][j].color.equals("o"))
                    countLight++;
            }
        }
    }

    //goal state
    public boolean goal() {
        boolean full = true;
        boolean noMoves = false;
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                if (board[i][j].color.equals(" "))
                    full = false;
            }
        }
        darkMap = possibleMove(board, "x", "o");
        lightMap = possibleMove(board, "o", "x");
        if (lightMap.keySet().isEmpty() && darkMap.keySet().isEmpty())
            noMoves = true;
        return full || noMoves;
    }
}