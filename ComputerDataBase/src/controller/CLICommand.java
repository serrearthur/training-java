package controller;

public class CLICommand {

	public static boolean parse(String command) {
		String[] parsed = command.split(" ");
		boolean ret = false;

		// case when parsed[0] == "list"
		if (parsed.length >= 2 && parsed[0].equals("list")) {
			if (parsed[1].equals("cpt")) {
				System.out.println("LIST CPT");
				ret = true;
			} else if (parsed[1].equals("cpn")) {
				System.out.println("LIST CPN");
				ret = true;
			} else {
				System.out.println("LIST + ERROR");
			}
		}
		// case when parsed[0] == "show"
		else if (parsed.length >= 3 && parsed[0].equals("show")) {
			if (parsed[1].equals("-i")) {
				if (!parsed[2].isEmpty()) {
					System.out.println("SHOW -i " + parsed[2]);
					ret = true;
				} else {
					System.out.println("SHOW -i + EMPTY : " + command);
				}
			} else if (parsed[1].equals("-n")) {
				if (!parsed[2].isEmpty()) {
					System.out.println("SHOW -n " + parsed[2]);
					ret = true;
				} else {
					System.out.println("SHOW -n + EMPTY");
				}
			} else {
				System.out.println("SHOW + ERROR : " + command);
			}
		}
		// case when parsed[0] == "create"
		else if (parsed.length >= 2 && parsed[0].equals("create")) {
			if (!parsed[1].isEmpty()) {
				System.out.println("CREATE " + parsed[1]);
				ret = true;
			} else {
				System.out.println("CREATE + ERROR : " + command);
			}
		}
		// case when parsed[0] == "update"
		else if (parsed.length >= 3 && parsed[0].equals("update")) {
			if (!parsed[1].isEmpty() && !parsed[2].isEmpty()) {
				if (parsed.length >= 6 && !parsed[3].isEmpty() && !parsed[4].isEmpty() && !parsed[5].isEmpty()) {
					System.out.println("UPDATE + " + parsed[1] + " " + parsed[2] + " " + parsed[3] + " " + parsed[4]
							+ " " + parsed[5] + " ");
					ret = true;
				} else {
					System.out.println("UPDATE + " + parsed[1] + " " + parsed[2]);
					ret = true;
				}
			} else {
				System.out.println("UPDATE + NOT_ENOUGH_ARGS : " + command);
			}
		}
		// case when parsed[0] == "delete"
		else if (parsed.length >= 3 && parsed[0].equals("delete")) {
			if (parsed[1].equals("-i")) {
				if (!parsed[2].isEmpty()) {
					System.out.println("DELETE -i " + parsed[2]);
					ret = true;
				} else {
					System.out.println("DELETE -i + EMPTY");
				}
			} else if (parsed[1].equals("-n")) {
				if (!parsed[2].isEmpty()) {
					System.out.println("DELETE -n " + parsed[2]);
					ret = true;
				} else {
					System.out.println("DELETE -n + EMPTY");
				}
			} else {
				System.out.println("DELETE + ERROR : " + command);
			}
		}
		//case when command is not recognized
		else {
			System.out.println("UNKNOWN_COMMAND : " + command);
		}

		return ret;
	}
}
