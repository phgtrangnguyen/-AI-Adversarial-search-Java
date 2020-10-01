import java.util.ArrayList;

public class Node {
    Board board;
    int point;
    ArrayList<Node> child;
    String seq;

    public Node(Board board, String s) {
        this.board = board;
        this.child = new ArrayList<>();
        this.seq = s;
    }
}
