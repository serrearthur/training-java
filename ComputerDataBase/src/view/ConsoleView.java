package view;

import java.util.List;
import java.util.Scanner;

import controller.CLICommand;
import model.Company;
import model.Computer;

public class ConsoleView {
	static Scanner sc = new Scanner(System.in);
	static final int PAGE_SIZE=7;
	
	public static String homeView() {
		System.out.println("Available command lines :\n" + "\t-`list cpt' : List computers\n"
				+ "\t-`list cpn' : List companies\n"
				+ "\t-`show [-i ID | -n NAME]' : Show computer details (the detailed information of only one computer)\n"
				+ "\t-`create NAME' : Create a computer\n"
				+ "\t-`update ID [NAME DATE_INTRODUCED DATE_DISCONTINUED COMPANY_ID]' : Update a computer\n"
				+ "\t-`delete [-i ID | -n NAME]' : Delete a computer\n"
				+ "\t-`exit' : Exit application");
		String command = sc.nextLine();
		return command;
	}
	
	public static void CLIResultView(CLICommand cli) {
		System.out.println("Request : " + cli.getCommand() + "\n------------------------------------");
		String format = "%-5s%-20s%-20s%-20s%-5s%n";
		if (!cli.getCompanies().isEmpty()) {
			System.out.printf(format, "ID", "Name", "", "", "");
			System.out.println("------------------------------------");
			for (Company c : cli.getCompanies()) {
				System.out.printf(format, c.getId(), c.getName(), "", "", "");
			}
		}
		if (!cli.getComputers().isEmpty()) {
			System.out.printf(format, "ID", "Name", "Introduced", "Discontinued", "Company ID");
			System.out.println("---------------------------------------------------------------------------");
			for (Computer c : cli.getComputers()) {
				System.out.printf(format, c.getId(), c.getName(),
						c.getIntroduced() == null? "" : c.getIntroduced().toString(),
						c.getDiscontinued() == null? "" : c.getDiscontinued().toString(),
						c.getCompanyId() == null? "" : c.getCompanyId());
			}
		}
	}
	
//	private static void printPage(List<Company> companies, int p) {
//		for (int i=(p-1)*PAGE_SIZE; i<p*PAGE_SIZE; i++) {
//			if (i>companies.)
//		}
//	}

	public static void main(String[] args) {
		CLICommand cli = new CLICommand("");
		boolean parseResult;
		String readInput;
		while (true) {
			readInput=homeView();
			if (readInput.equals("exit")) {
				break;
			}
			try {
				cli.setCommand(readInput);
				parseResult = cli.parse();
				if (parseResult) {
					System.out.println("SUCCESS");
					CLIResultView(cli);
				} else {
					System.out.println("ERROR");
				}
				System.out.println("<--- press enter to continue --->");
				System.in.read();
			} catch (Exception e) {
				//ignoble
				System.err.println("found error : " + e.getMessage());
			}
		}
		System.out.println("Farewell!");
	}
}