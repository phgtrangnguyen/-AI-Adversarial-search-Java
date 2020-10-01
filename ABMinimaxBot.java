import java.util.ArrayList;
import java.util.Random;

public class ABMinimaxBot extends MinimaxBot {

    @Override
    boolean play(Board board, boolean darkTurn, int depth) {
        Node root = new Node(board, "");
        abMinimax(root, darkTurn, -999, 999);
        int max = -999;
        for (Node child : root.child) {
            if (child.point > max) {
                max = child.point;
            }
        }
        ArrayList<Node> temp = new ArrayList<>();
        for (Node child : root.child) {
            if (child.point == max) {
                temp.add(child);
            }
        }
        if (temp.size() == 0) {
            System.out.println("No moves possible. Pass!");
            return true;
        }
        Node choice = temp.get(0);
        board.board = choice.board.board;
        if (color.equals("o")) {
            System.out.print("o: ");
        } else if (color.equals("x")) {
            System.out.print("x: ");
        }
        System.out.println(choice.seq);
        return true;
    }

    void abMinimax(Node root, boolean darkTurn, int alpha, int beta) {
        root.board.allActions(darkTurn);
        if (root.board.goal()) {
            getUltility(root);
        } else {
            /** light turn **/
            if (!darkTurn) {
                for (String s : root.board.lightMap.keySet()) {
                    Board light = new Board(root.board.size);
                    light.retrieve(root.board.lightMap.get(s));
                    /** min **/
                    if (color.equals("x")) {
                        root.point = 999;
                        Node child = new Node(light, s);
                        root.child.add(child);
                        abMinimax(child, !darkTurn, alpha, beta);
                        root.point = Math.min(root.point, child.point);
                        beta = Math.min(beta, root.point);
                        if (root.point <= alpha) {
                            break;
                        }
                    }
                    /** max **/
                    else if (color.equals("o")) {
                        root.point = -999;
                        Node child = new Node(light, s);
                        root.child.add(child);
                        abMinimax(child, !darkTurn, alpha, beta);
                        root.point = Math.max(root.point, child.point);
                        alpha = Math.max(alpha, root.point);
                        if (beta <= root.point) {
                            break;
                        }
                    }
                }
            }
            /** black turn **/
            if (darkTurn) {
                for (String s : root.board.darkMap.keySet()) {
                    Board dark = new Board(root.board.size);
                    dark.retrieve(root.board.darkMap.get(s));
                    /** max **/
                    if (color.equals("x")) {
                        root.point = -999;
                        Node child = new Node(dark, s);
                        root.child.add(child);
                        abMinimax(child, !darkTurn, alpha, beta);
                        root.point = Math.max(root.point, child.point);
                        alpha = Math.max(alpha, root.point);
                        if (beta <= root.point) {
                            break;
                        }
                    }
                    /** min **/
                    else if (color.equals("o")) {
                        root.point = 999;
                        Node child = new Node(dark, s);
                        root.child.add(child);
                        abMinimax(child, !darkTurn, alpha, beta);
                        root.point = Math.min(root.point, child.point);
                        beta = Math.min(beta, root.point);
                        if (root.point <= alpha) {
                            break;
                        }
                    }
                }
            }
        }
    }
}