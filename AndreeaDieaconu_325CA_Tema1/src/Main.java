import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This execution entry point class handles parsing and executing commands from
 * the input file.
 * </p>
 * 
 * @author Andreea Loredana Dieaconu, 325CA
 */

public class Main {

	/**
	 * <p>
	 * Execution entry point.
	 * </p>
	 * 
	 * @param args
	 *            the name of the file containing commands to be executed
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Usage: java -cp <classpath> Main <input file>");
			System.exit(1);
		}

		Network network = Network.getInstance();
		FileParser fp = new FileParser(args[0]);
		List<String> commands = new ArrayList<String>();
		fp.open();
		while ((commands = fp.parseNextLine()) != null) {
			for (int i = 0; i < commands.size(); i++) {
				if (commands.get(i).equals("add")) {
					network.addUser(Integer.valueOf(commands.get(++i)),
							commands.get(++i));
				} else if (commands.get(i).equals("remove")) {
					network.removeUser(Integer.valueOf(commands.get(++i)));
				} else if (commands.get(i).equals("friend")) {
					network.friend(Integer.valueOf(commands.get(++i)),
							Integer.valueOf(commands.get(++i)));
				} else if (commands.get(i).equals("unfriend")) {
					network.unfriend(Integer.valueOf(commands.get(++i)),
							Integer.valueOf(commands.get(++i)));
				} else if (commands.get(i).equals("print")
						&& commands.get(i + 1).equals("user")) {
					network.printUser(Integer.valueOf(commands.get(i + 2)));
					i++;
				} else if (commands.get(i).equals("print")
						&& commands.get(i + 1).equals("network")) {
					network.printNetwork();
					i++;
				} else if (commands.get(i).equals("print")
						&& commands.get(i + 1).equals("communities")) {
					network.printCommunities();
					i++;
				} else if (commands.get(i).equals("print")
						&& commands.get(i + 1).equals("strength")) {
					network.printStrength(Integer.valueOf(commands.get(i + 2)));
					i++;
				}
			}

		}
		fp.close();
	}
}