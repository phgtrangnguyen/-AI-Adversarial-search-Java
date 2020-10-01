import java.util.Scanner;

public class Player {

    String color;

    boolean play(Board board, boolean darkTurn, int depth) {
        Scanner scanner = new Scanner(System.in);
        if (color.equals("o")) {
            if (!board.lightMap.keySet().isEmpty()) {
                System.out.print("Your move? ");
                String s = scanner.next();
                if (board.lightMap.containsKey(s)) {
                    board.board = board.lightMap.get(s);
                    System.out.println("o: " + s);
                    return true;
                } else
                    System.out.println("Invalid move! Try again.");
            } else {
                System.out.println("No moves possible. Pass!");
                return true;
            }
        }
        if (color.equals("x")) {
            if (!board.darkMap.keySet().isEmpty()) {
                System.out.print("Your move? ");
                String s = scanner.next();
                if (board.darkMap.containsKey(s)) {
                    board.board = board.darkMap.get(s);
                    System.out.println("x: " + s);
                    return true;
                } else
                    System.out.println("Invalid move! Try again.");
            } else {
                System.out.println("No moves possible. Pass!");
                return true;
            }
        }
        return false;
    }
}
