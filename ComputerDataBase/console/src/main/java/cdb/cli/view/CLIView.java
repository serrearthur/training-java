package cdb.cli.view;

import java.io.IOException;
import java.util.Scanner;

import cdb.cli.controller.CLICommand;
import cdb.view.dto.DTOCompany;
import cdb.view.dto.DTOComputer;

/**
 * Class designed to display a menu for the user to enter their input,
 * and display the result of their query.
 * <p>
 * Only called when the app is used in CLI mode.
 *
 * @author aserre
 */
public class CLIView {
    static Scanner sc = new Scanner(System.in);

    /**
     * Displays the main menu and reads the input of the user.
     * @return input of the user
     */
    public static String homeView() {
        System.out.println("Available command lines :\n" + "\t-`list cpt' : List computers\n"
                + "\t-`list cpn' : List companies\n"
                + "\t-`show [-i ID | -n NAME ]' : Show computer details (the detailed information of only one computer)\n"
                + "\t-`create NAME' : Create a computer\n"
                + "\t-`update ID NAME [DATE_INTRODUCED DATE_DISCONTINUED COMPANY_ID]' : Update a computer\n"
                + "\t-`delete [-i ID | -c COMPANY_ID]' : Delete a computer\n" + "\t-`exit' : Exit application");
        String command = sc.nextLine();
        return command;
    }

    /**
     * Displays the result of a command once it has been parsed.
     * @param cli {@link CLICommand} object containing a parsed request
     */
    public static void cliResultView(CLICommand cli) {
        System.out.println("Request : " + cli.getCommand() + "\n------------------------------------");
        String format = "%-5s%-30s%-20s%-20s%-5s%n";

        if (!cli.getCompanies().isEmpty()) {
            System.out.printf(format, "ID", "Name", "", "", "");
            System.out.println("------------------------------------");
            for (DTOCompany c : cli.getCompanies()) {
                System.out.printf(format, c.getId(), c.getName(), "", "", "");
            }
        }
        if (!cli.getComputers().isEmpty()) {
            System.out.printf(format, "ID", "Name", "Introduced", "Discontinued", "Company ID");
            System.out.println("-------------------------------------------------------------------------------------");
            for (DTOComputer c : cli.getComputers()) {
                System.out.printf(format, c.getId(), c.getName(),
                        c.getIntroduced() == null ? "" : c.getIntroduced().toString(),
                                c.getDiscontinued() == null ? "" : c.getDiscontinued().toString(),
                                        c.getCompanyName() == null ? "" : c.getCompanyName());
            }
        }
    }

    /**
     * Infinite loop used to call the main menu, parse the input of the user
     * and display the result of their request.
     * @param args required for the main function signature
     * @throws IOException thrown by the System.in.read()
     */
    public static void main(String[] args) throws IOException  {
        CLICommand cli = new CLICommand("");
        boolean parseResult;
        String readInput;
        while (true) {
            readInput = homeView();
            if (readInput.equals("exit")) {
                break;
            }

            cli.setCommand(readInput);
            parseResult = cli.parse();
            if (parseResult) {
                System.out.println("SUCCESS");
                cliResultView(cli);
            } else {
                System.out.println("ERROR");
            }
            System.out.println("<--- press enter to continue --->");
            System.in.read();
            sc.nextLine();
        }
        sc.close();
        System.out.println("Farewell!");
    }
}