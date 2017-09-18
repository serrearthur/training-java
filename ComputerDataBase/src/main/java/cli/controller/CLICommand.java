package cli.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dao.DAOFactory;
import model.Company;
import model.Computer;

public class CLICommand {
    private String command;
    private DAOFactory factory;
    private List<Computer> result_computers;
    private List<Company> result_companies;

    public CLICommand(String command) {
        this.command = command;
        this.factory = DAOFactory.getInstance();
        this.result_companies = new ArrayList<Company>();
        this.result_computers = new ArrayList<Computer>();
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public List<Computer> getComputers() {
        return result_computers;
    }

    public List<Company> getCompanies() {
        return result_companies;
    }

    public boolean parse() {
        // we reset the values for the request
        boolean ret = false;
        this.result_companies = new ArrayList<Company>();
        this.result_computers = new ArrayList<Computer>();

        List<String> parsed = new ArrayList<String>();
        Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(this.command);
        while (m.find()) {
            parsed.add(m.group(1).replace("\"", ""));
        }

        if (parsed.size() > 0) {
            switch (parsed.get(0)) {
            case "list":
                ret = parseList(parsed);
                break;
            case "show":
                ret = parseShow(parsed);
                break;
            case "create":
                ret = parseCreate(parsed);
                break;
            case "update":
                ret = parseUpdate(parsed);
                break;
            case "delete":
                ret = parseDelete(parsed);
                break;
            default:
                System.out.println("UNKNOWN_COMMAND : " + command);
            }
        } else {
            System.out.println("EMPTY_COMMAND : " + command);
        }
        return ret;
    }

    private boolean parseList(List<String> parsed) {
        boolean ret = false;
        if (parsed.size() >= 2 && parsed.get(1).equals("cpt")) {
            // case when we list all computers
            System.out.println("LIST CPT");
            this.result_computers.addAll(factory.getComputerDao().getAll());
            ret = true;
        } else if (parsed.get(1).equals("cpn")) {
            // case when we list all companies
            System.out.println("LIST CPN");
            this.result_companies.addAll(factory.getCompanyDao().getAll());
            ret = true;
        } else {
            System.out.println("LIST + ERROR");
        }
        return ret;
    }

    private boolean parseShow(List<String> parsed) {
        boolean ret = false;
        if (parsed.size() >= 3 && parsed.get(1).equals("-i")) {
            if (!parsed.get(2).isEmpty()) {
                // case when we show computer with id X
                System.out.println("SHOW -i " + parsed.get(2));
                this.result_computers.addAll(factory.getComputerDao().getFromId(Integer.parseInt(parsed.get(2))));
                ret = true;
            } else {
                System.out.println("SHOW -i + EMPTY : " + command);
            }
        } else if (parsed.get(1).equals("-n")) {
            if (!parsed.get(2).isEmpty()) {
                // case when we show computer with name X
                System.out.println("SHOW -n " + parsed.get(2));
                this.result_computers.addAll(factory.getComputerDao().getFromName(parsed.get(2)));
                ret = true;
            } else {
                System.out.println("SHOW -n + EMPTY");
            }
        } else if (parsed.get(1).equals("-c")) {
            if (!parsed.get(2).isEmpty()) {
                // case when we show computer with name X
                System.out.println("SHOW -c " + parsed.get(2));
                this.result_computers.addAll(factory.getComputerDao().getFromCompanyId(Integer.parseInt(parsed.get(2))));
                ret = true;
            } else {
                System.out.println("SHOW -n + EMPTY");
            }
        } else {
            System.out.println("SHOW + ERROR : " + command);
        }
        return ret;
    }

    private boolean parseCreate(List<String> parsed) {
        boolean ret = false;
        if (parsed.size() >= 2 && !parsed.get(1).isEmpty()) {
            // case when we create a new computer with name X
            System.out.println("CREATE " + parsed.get(1));
            factory.getComputerDao().create(new Computer(parsed.get(1)));
            ret = true;
        } else {
            System.out.println("CREATE + ERROR : " + command);
        }
        return ret;
    }

    private boolean parseUpdate(List<String> parsed) {
        boolean ret = false;
        // we check if each parameter is present, if yes we add it to our computer object
        if (parsed.size() >= 3 && !parsed.get(2).isEmpty()) {
            Computer c = new Computer();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            c.setId(Integer.parseInt(parsed.get(1)));
            c.setName(parsed.get(2));
            if (parsed.size() >= 4 && !parsed.get(3).isEmpty()) {
                c.setIntroduced(LocalDateTime.of(LocalDate.parse(parsed.get(3), dtf), null));
                if (parsed.size() >= 5 && !parsed.get(4).isEmpty()) {
                    c.setDiscontinued(LocalDateTime.of(LocalDate.parse(parsed.get(4), dtf), null));
                    if (parsed.size() >= 6 && !parsed.get(5).isEmpty()) {
                        c.setCompanyId(Integer.parseInt(parsed.get(5)));
                    }
                }
            }
            System.out.println("UPDATE : " + command);
            factory.getComputerDao().update(c);
            ret = true;
        } else {
            System.out.println("UPDATE + NOT_ENOUGH_ARGS : " + command);
        }
        return ret;
    }

    private boolean parseDelete(List<String> parsed) {
        boolean ret = false;
        Computer c = new Computer();
        if (parsed.size() >= 3 && parsed.get(1).equals("-i")) {
            if (!parsed.get(2).isEmpty()) {
                System.out.println("DELETE -i " + parsed.get(2));
                c.setId(Integer.parseInt(parsed.get(2)));
                factory.getComputerDao().delete(c);
                ret = true;
            } else {
                System.out.println("DELETE -i + EMPTY");
            }
        } else if (parsed.size() >= 3 && parsed.get(1).equals("-n")) {
            if (!parsed.get(2).isEmpty()) {
                System.out.println("DELETE -n " + parsed.get(2));
                c.setName(parsed.get(2));
                factory.getComputerDao().delete(c);
                ret = true;
            } else {
                System.out.println("DELETE -n + EMPTY");
            }
        } else {
            System.out.println("DELETE + ERROR : " + command);
        }
        return ret;
    }
}
