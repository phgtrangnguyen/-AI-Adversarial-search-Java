import java.util.ArrayList;
import java.util.Random;

public class MinimaxBot extends Player {

    @Override
    boolean play(Board board, boolean darkTurn, int depth) {
        Node root = new Node(board, "");
        minimax(root, darkTurn);
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

    //utility function
    void getUltility(Node node) {
        node.board.getScore();
        if (node.board.countDark > node.board.countLight) {
            if (color.equals("x")) {
                node.point = 1;
            } else if (color.equals("o")) {
                node.point = -1;
            }
        } else if (node.board.countDark < node.board.countLight) {
            if (color.equals("x")) {
                node.point = -1;
            } else if (color.equals("o")) {
                node.point = 1;
            }
        } else if (node.board.countDark == node.board.countLight)
            node.point = 0;
    }

    void minimax(Node root, boolean darkTurn) {
        root.board.allActions(darkTurn);
        if (root.board.goal()) {
            getUltility(root);
        } else {
            /** light turn **/
            if (!darkTurn) {

                if (color.equals("x")) {
                    root.point = 999;
                } else {
                    root.point = -999;
                }

                for (String s : root.board.lightMap.keySet()) {
                    Board light = new Board(root.board.size);
                    light.retrieve(root.board.lightMap.get(s));
                    /** min **/
                    if (color.equals("x")) {
                        // root.point = 999;
                        Node child = new Node(light, s);
                        root.child.add(child);
                        minimax(child, !darkTurn);
                        root.point = Math.min(root.point, child.point);
                    }
                    /** max **/
                    else if (color.equals("o")) {
                        // root.point = -999;
                        Node child = new Node(light, s);
                        root.child.add(child);
                        minimax(child, !darkTurn);
                        root.point = Math.max(root.point, child.point);
                    }
                }
            }
            /** black turn **/
            if (darkTurn) {

                if (color.equals("x")) {
                    root.point = -999;
                } else {
                    root.point = 999;
                }

                for (String s : root.board.darkMap.keySet()) {
                    Board dark = new Board(root.board.size);
                    dark.retrieve(root.board.darkMap.get(s));
                    /** max **/
                    if (color.equals("x")) {
                        // root.point = -999;
                        Node child = new Node(dark, s);
                        root.child.add(child);
                        minimax(child, !darkTurn);
                        root.point = Math.max(root.point, child.point);
                    }
                    /** min **/
                    else if (color.equals("o")) {
                        // root.point = 999;
                        Node child = new Node(dark, s);
                        root.child.add(child);
                        minimax(child, !darkTurn);
                        root.point = Math.min(root.point, child.point);
                    }
                }
            }
        }
    }
}