import java.util.ArrayList;
import java.util.Random;

public class RandomBot extends Player {

    @Override
    boolean play(Board board, boolean darkTurn, int depth) {
        ArrayList<String> arr = new ArrayList<>();
        Random random = new Random();
        if (color.equals("o")) {
            if (!board.lightMap.keySet().isEmpty()) {
                arr.addAll(board.lightMap.keySet());
                String s = arr.get(random.nextInt(arr.size()));
                board.board = board.lightMap.get(s);
                System.out.println("o: " + s);
                return true;
            } else {
                System.out.println("No moves possible. Pass!");
                return true;
            }
        }
        if (color.equals("x")) {
            if (!board.darkMap.keySet().isEmpty()) {
                arr.addAll(board.darkMap.keySet());
                String s = arr.get(random.nextInt(arr.size()));
                board.board = board.darkMap.get(s);
                System.out.println("x: " + s);
                return true;
            } else {
                System.out.println("No moves possible. Pass!");
                return true;
            }
        }
        return false;
    }
}
