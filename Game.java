import java.util.*;

public class Game {

	int size, size1, agent;
	int depth = 0;
	String side;
	Board board;
	Player player;
	Player bot;
	boolean darkTurn;
	boolean xWins = false;
	boolean draw = false;

	void startGame() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Reversi by Trang Nguyen - Razan Alshehri - Dominique Dorvil" + "\nChoose your game:" + "\n1. Small 4x4 Reversi"
				+ "\n2. Medium 6x6 Reversi" + "\n3. Standard 8x8 Reversi");
		do {
			System.out.print("Your choice? ");
			size1 = scanner.nextInt();
		} while (size1 != 1 && size1 != 2 && size1 != 3);

		System.out.println("Choose your opponent" + "\n1. An agent that plays randomly"
				+ "\n2. An agent that uses MINIMAX" + "\n3. An agent that uses MINIMAX with alpha-beta pruning"
				+ "\n4. An agent that uses H-MINIMAX with a fixed depth cutoff and a-b pruning");
		do {
			System.out.print("Your choice? ");
			agent = scanner.nextInt();
		} while (agent != 0 && agent != 1 && agent != 2 && agent != 3 && agent != 4);
		do {
			System.out.print("Do you want to play DARK (x) or LIGHT (o)? ");
			side = scanner.next();
		} while (!side.equals("x") && !side.equals("o"));

		player = new Player();
		if (agent == 1)
			bot = new RandomBot();
		else if (agent == 2)
			bot = new MinimaxBot();
		else if (agent == 3)
			bot = new ABMinimaxBot();
		else if (agent == 4) {
			bot = new HMinimaxBot();
			System.out.println("Choose your depth limit:");
			do {
				System.out.print("Your choice? ");
				depth = scanner.nextInt();
			} while (depth <= 0);
		}
		if (side.equals("x")) {
			player.color = "x";
			bot.color = "o";
		} else if (side.equals("o")) {
			player.color = "o";
			bot.color = "x";
		}
		darkTurn = true;
		if (size1 == 1)
			size = 4;
		else if (size1 == 2)
			size = 6;
		else if (size1 == 3)
			size = 8;
		board = new Board(size);
		board.initiate();
		board.print();
		System.out.println();
	}

	void gameLoop() {
		board.allActions(darkTurn);
		while (true) {
			double start = System.currentTimeMillis();
			/** dark turn **/
			if (darkTurn) {
				System.out.println("Next to play: DARK");
				System.out.println("Dark available: " + board.darkMap.keySet());
				if (player.color.equals("x")) {
					if (!player.play(board, darkTurn, depth))
						continue;
				} else {
					if (!bot.play(board, darkTurn, depth))
						continue;
				}
			}
			/** light turn **/
			if (!darkTurn) {
				System.out.println("Next to play: LIGHT");
				System.out.println("Light available: " + board.lightMap.keySet());
				if (player.color.equals("o")) {
					if (!player.play(board, darkTurn, depth))
						continue;
				} else {
					if (!bot.play(board, darkTurn, depth))
						continue;
				}
			}
			double finish = System.currentTimeMillis();
			double elapsed = (finish - start) / 1000;
			System.out.println("Elapsed time: " + elapsed + " secs");
			System.out.println();
			board.print();
			System.out.println();
			darkTurn = !darkTurn;
			board.allActions(darkTurn);
			if (board.goal()) {
				System.out.println();
				System.out.print("GAME OVER. ");
				board.getScore();
				if (board.countDark > board.countLight) {
					System.out.println("DARK WINS.");
				} else if (board.countDark < board.countLight) {
					System.out.println("LIGHT WINS.");
				} else {
					System.out.println("DRAW.");
				}
				darkTurn = true;
				break;
			}
		}
	}
}