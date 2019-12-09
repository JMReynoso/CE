import java.io.*;
import java.util.*;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class CE {

	public static ArrayList<String> RobPos = new ArrayList<String>();
	public static ArrayList<String> obstacles = new ArrayList<String>();
	public static ArrayList<String> visited = new ArrayList<String>();
	public static int tnumRobots;
	public static int boardSize = 20;

	protected static void sleep() {
		try {
			Thread.sleep(100);
		} catch (Exception e) {
		}
	}

	// obstacle ver.
	public static void makeEnv2(Graph graph, int numRobots) {

		// size of square graph
		int T = boardSize;

		// add nodes
		for (int i = 0; i < T; i++) {
			for (int j = 0; j < T; j++) {

				// roll
				int test = (int) (Math.random() * (7 - 0));

				// if 0, make obstacle
				if (test == 0) {
					String ID = i + ", " + j;
					graph.addNode(ID).addAttribute("xy", i, j);
					graph.getNode(ID).addAttribute("ui.class", "obst");
					obstacles.add(ID);
				}

				else {
					String ID = i + ", " + j;
					graph.addNode(ID).addAttribute("xy", i, j);
				}

			}
		}

		// add edges
		for (int i = 0; i < T; i++) {
			for (int j = 0; j < T; j++) {

				String current = i + ", " + j;
				String up = ++i + ", " + j;
				String right = --i + ", " + ++j;

				if (++i != T) {
					graph.addEdge(current + " " + up, current, up);
				}
				if (j != T) {
					graph.addEdge(current + " " + right, current, right);
				}
				// reset
				--i;
				--j;
			}
		}
		
		long start = System.currentTimeMillis();
		ArrayList<String[]> toExecute = CEa(numRobots);
		long end = System.currentTimeMillis();
		float msec = end - start;
		float sec= msec/1000F;
		float minutes=sec/60F;
		System.out.println(minutes + " minutes");

		// markers for robots
		String styleSheet = "node.marked0 {" + "	fill-color: red;" + "}" + "node.marked1 {" + "	fill-color: green;"
				+ "}" + "node.marked2 {" + "	fill-color: gold;" + "}" + "node.marked3 {" + "	fill-color: DeepPink;"
				+ "}" + "node.marked4 {" + "	fill-color: Cyan;" + "}" + "node.obst {"
				+ "	fill-color: CornflowerBlue; size: 20px;" + "}";

		graph.addAttribute("ui.stylesheet", styleSheet);
		graph.addAttribute("ui.quality");
		graph.addAttribute("ui.antialias");
		graph.display(false);

		// always display before iterator. use this for config
		resetRobots(tnumRobots);
		for (int i = 0; i < tnumRobots; i++) {
			graph.getNode(RobPos.get(i)).addAttribute("ui.class", "marked" + i);
		}

		System.out.println("Finished generating, enter anything");
		Scanner scan = new Scanner(System.in);
		String next = scan.next();

		for (int i = 0; i < toExecute.size(); i++) {

			String[] array = toExecute.get(i);

			for (int j = 0; j < array.length; j++) {
				String toMove = array[j];
				String move = "";

				if (toMove.equals("U")) {
					String currentPos = RobPos.get(j);
					move = goUp(currentPos);
					RobPos.set(j, move);
				}

				else if (toMove.equals("D")) {
					String currentPos = RobPos.get(j);
					move = goDown(currentPos);
					RobPos.set(j, move);
				}

				else if (toMove.equals("L")) {
					String currentPos = RobPos.get(j);
					move = goLeft(currentPos);
					RobPos.set(j, move);
				}

				else if (toMove.equals("R")) {
					String currentPos = RobPos.get(j);
					move = goRight(currentPos);
					RobPos.set(j, move);
				}

				graph.getNode(move).addAttribute("ui.class", "marked" + j);
			}
			sleep();
		}

		System.out.println("Finished");

	}

	// no obstacles
	public static void makeEnv1(Graph graph, ArrayList<String[]> config) {

		// size of square graph
		int T = 25;

		// add nodes
		for (int i = 0; i < T; i++) {
			for (int j = 0; j < T; j++) {
				String ID = i + ", " + j;
				graph.addNode(ID).addAttribute("xy", i, j);
				;
			}
		}

		// add edges
		for (int i = 0; i < T; i++) {
			for (int j = 0; j < T; j++) {

				String current = i + ", " + j;
				String up = ++i + ", " + j;
				String right = --i + ", " + ++j;

				if (++i != T) {
					graph.addEdge(current + " " + up, current, up);
				}
				if (j != T) {
					graph.addEdge(current + " " + right, current, right);
				}
				// reset
				--i;
				--j;
			}
		}

		// markers for robots
		String styleSheet = "node {" + "	fill-color: black;" + "}" + "node.marked0 {" + "	fill-color: red;" + "}"
				+ "node.marked1 {" + "	fill-color: green;" + "}" + "node.marked2 {" + "	fill-color: gold;" + "}"
				+ "node.marked3 {" + "	fill-color: DeepPink;" + "}" + "node.marked4 {" + "	fill-color: Cyan;" + "}";

		graph.addAttribute("ui.stylesheet", styleSheet);
		graph.addAttribute("ui.quality");
		graph.addAttribute("ui.antialias");
		graph.display(false);

		// always display before iterator. use this for config

		// mark initial
		resetRobots(tnumRobots);
		for (int i = 0; i < tnumRobots; i++) {
			graph.getNode(RobPos.get(i)).addAttribute("ui.class", "marked" + i);
		}

		for (int i = 0; i < config.size(); i++) {

			String[] array = config.get(i);

			for (int j = 0; j < array.length; j++) {
				String toMove = array[j];
				String move = "";

				if (toMove.equals("U")) {
					String currentPos = RobPos.get(j);
					move = goUp(currentPos);
					RobPos.set(j, move);
				}

				else if (toMove.equals("D")) {
					String currentPos = RobPos.get(j);
					move = goDown(currentPos);
					RobPos.set(j, move);
				}

				else if (toMove.equals("L")) {
					String currentPos = RobPos.get(j);
					move = goLeft(currentPos);
					RobPos.set(j, move);
				}

				else if (toMove.equals("R")) {
					String currentPos = RobPos.get(j);
					move = goRight(currentPos);
					RobPos.set(j, move);
				}

				graph.getNode(move).addAttribute("ui.class", "marked" + j);
			}
			sleep();
		}

		System.out.println("Finished");
	}

	// ---------------actual algorithm --------------------------
	public static ArrayList<String[]> CEa(int numRobots) {

		int t = 0, T = 300, k = 50, p = 0;

		ArrayList<String[]> config = new ArrayList<String[]>();
		ArrayList<ArrayList<String[]>> population = new ArrayList<ArrayList<String[]>>();
		String[] movement = { "U", "D", "L", "R" };
		ArrayList<String[]> toExecute = new ArrayList<String[]>();

		// k could be [25, 50, 100, 250, 500, 1000

		initializeRobots(numRobots);

		System.out.println("Generating movements... ");

		while (t < T) {
			// make random movement
			for (int i = 0; i < k; i++) {
				String[] tempArray = new String[numRobots];
				for (int j = 0; j < numRobots; j++) {
					Random Dice = new Random();
					int n = Dice.nextInt(4);
					tempArray[j] = movement[n];
				}

				// add movement of robots to array (0 is 1st robot) config.add(tempArray);

				config.add(tempArray);
			}

			// add config to population
			population.add(config);

			String[] configMAX = config.get(0);

			for (int h = 1; h < config.size(); h++) {

				String[] toPass = config.get(h);
				// UCE
				int check = UCE(toPass);
				int toComp = UCE(configMAX);
				if (check >= toComp) {
					configMAX = toPass;
				}
			}

			// move robots with maximum fitness
			moveRobots(configMAX);

			// IDK WHY BUT THIS IS BAD
			// execute config_cMAX here
			toExecute.add(configMAX);

			t++;

			if (t % (T * 0.05) == 0) {
				p += 5;
				System.out.println(p + "% Complete.");
			}

		}

		return toExecute;
	}

	public static void moveRobots(String[] configMAX) {

		for (int i = 0; i < configMAX.length; i++) {

			String toMove = configMAX[i];
			String move = "";

			if (toMove.equals("U")) {
				String currentPos = RobPos.get(i);
				move = goUp(currentPos);
				RobPos.set(i, move);
				visited.add(move);
			}

			else if (toMove.equals("D")) {
				String currentPos = RobPos.get(i);
				move = goDown(currentPos);
				RobPos.set(i, move);
				visited.add(move);
			}

			else if (toMove.equals("L")) {
				String currentPos = RobPos.get(i);
				move = goLeft(currentPos);
				RobPos.set(i, move);
				visited.add(move);
			}

			else if (toMove.equals("R")) {
				String currentPos = RobPos.get(i);
				move = goRight(currentPos);
				RobPos.set(i, move);
				visited.add(move);
			}
		}

	}

	// work on
	@SuppressWarnings("unlikely-arg-type")
	public static int UCE(String[] robotsP) {

		// is obstacle
		for (int i = 0; i < robotsP.length; i++) {

			String check = robotsP[i];
			String newPos = "";

			if (check.equals("U")) {
				newPos = goUp(RobPos.get(i));
			} else if (check.equals("D")) {
				newPos = goDown(RobPos.get(i));
			}

			else if (check.equals("L")) {
				newPos = goLeft(RobPos.get(i));
			}

			else if (check.equals("R")) {
				newPos = goRight(RobPos.get(i));
			}

			if (obstacles.contains(newPos)) {
				return -15;
			}
		}

		// check range;
		for (int i = 0; i < tnumRobots; i++) {
			for (int j = 0; j < tnumRobots; j++) {

				String pos1 = RobPos.get(i);
				String pos2 = RobPos.get(j);

				int range = checkRange(pos1, pos2);
				// out of range (max range = 8)
				if (range > 10) {
					return -10;
				}
			}
		}

		// if already visited
		for (int i = 0; i < robotsP.length; i++) {

			String check = robotsP[i];
			String newPos = "";

			if (check.equals("U")) {
				newPos = goUp(RobPos.get(i));
			} else if (check.equals("D")) {
				newPos = goDown(RobPos.get(i));
			}

			else if (check.equals("L")) {
				newPos = goLeft(RobPos.get(i));
			}

			else if (check.equals("R")) {
				newPos = goRight(RobPos.get(i));
			}

			if (visited.contains(newPos)) {
				return -10;
			}
		}

		return 10;

	}

	public static int checkRange(String R1P, String R2P) {
		String[] s = R1P.split(", ");
		String[] s2 = R2P.split(", ");

		int xRange = Integer.parseInt(s2[0]) - Integer.parseInt(s[0]);
		int yRange = Integer.parseInt(s2[1]) - Integer.parseInt(s[1]);

		int xSquare = (int) Math.pow(xRange, 2);
		int ySquare = (int) Math.pow(yRange, 2);

		return (int) Math.sqrt(xSquare + ySquare);
	}

	public static String goUp(String position) {
		String[] s = position.split(", ");

		int x = Integer.parseInt(s[0]);
		int y = Integer.parseInt(s[1]);

		if (y + 1 >= boardSize) {
			return x + ", " + y;
		}

		return x + ", " + ++y;
	}

	public static String goRight(String position) {
		String[] s = position.split(", ");

		int x = Integer.parseInt(s[0]);
		int y = Integer.parseInt(s[1]);

		if (x + 1 >= boardSize) {
			return x + ", " + y;
		}

		return ++x + ", " + y;
	}

	public static String goDown(String position) {
		String[] s = position.split(", ");

		int x = Integer.parseInt(s[0]);
		int y = Integer.parseInt(s[1]);

		if (y - 1 < 0) {
			return x + ", " + y;
		}

		return x + ", " + --y;
	}

	public static String goLeft(String position) {
		String[] s = position.split(", ");

		int x = Integer.parseInt(s[0]);
		int y = Integer.parseInt(s[1]);

		if (x - 1 < 0) {
			return x + ", " + y;
		}

		return --x + ", " + y;
	}

	public static void initializeRobots(int numRobots) {
		tnumRobots = numRobots;

		int j = 0;

		for (int i = 0; i < numRobots; i++) {
			String startingPos = j + ", 0";
			RobPos.add(startingPos);
			j += 2;
		}
	}

	public static void resetRobots(int numRobots) {
		int j = 0;

		for (int i = 0; i < numRobots; i++) {
			String startingPos = j + ", 0";
			RobPos.set(i, startingPos);
			visited.add(startingPos);
			j += 2;
		}
	}

	public static void main(String args[]) throws FileNotFoundException {

		Graph graph = new SingleGraph("grid");

		System.out.println("Enter size of robots: ");
		Scanner scan = new Scanner(System.in);
		int numRobots = scan.nextInt();

		makeEnv2(graph, numRobots);


	}
}
