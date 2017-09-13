package controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import dao.ConnexionManager;
import model.Company;
import model.Computer;

public class CLICommand {
	private String command;
	private ConnexionManager factory;
	private List<Computer> computers;
	private List<Company> companies;

	public CLICommand(String command) {
		this.command = command;
		this.factory = ConnexionManager.getInstance();
		this.companies = new ArrayList<Company>();
		this.computers = new ArrayList<Computer>();
	}

	public boolean parse() throws ParseException {
		String[] parsed = command.split(" ");
		boolean ret = false;
		// we reinitialize the values for the request
		this.companies = new ArrayList<Company>();
		this.computers = new ArrayList<Computer>();

		// case when parsed[0] == "list"
		if (parsed.length >= 2 && parsed[0].equals("list")) {
			if (parsed[1].equals("cpt")) {
				// case when we list all computers
				System.out.println("LIST CPT");
				this.computers.addAll(factory.getComputerDao().getAll());
				ret = true;
			} else if (parsed[1].equals("cpn")) {
				// case when we list all companies
				System.out.println("LIST CPN");
				this.companies.addAll(factory.getCompanyDao().getAll());
				ret = true;
			} else {
				System.out.println("LIST + ERROR");
			}
		}
		// case when parsed[0] == "show"
		else if (parsed.length >= 3 && parsed[0].equals("show")) {
			if (parsed[1].equals("-i")) {
				if (!parsed[2].isEmpty()) {
					// case when we show computer with id X
					System.out.println("SHOW -i " + parsed[2]);
					this.computers.addAll(factory.getComputerDao().getFromId(parsed[2]));
					ret = true;
				} else {
					System.out.println("SHOW -i + EMPTY : " + command);
				}
			} else if (parsed[1].equals("-n")) {
				if (!parsed[2].isEmpty()) {
					// case when we show computer with name X
					System.out.println("SHOW -n " + parsed[2]);
					this.computers.addAll(factory.getComputerDao().getFromName(parsed[2]));
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
				// case when we create a new computer with name X
				System.out.println("CREATE " + parsed[1]);
				factory.getComputerDao().create(new Computer(parsed[1]));
				ret = true;
			} else {
				System.out.println("CREATE + ERROR : " + command);
			}
		}
		// case when parsed[0] == "update"
		else if (parsed.length >= 2 && parsed[0].equals("update")) {
			Computer c = new Computer();
			SimpleDateFormat df = new SimpleDateFormat("dd/mm/yyyy");
			c.setId(Integer.parseInt(parsed[1]));
			// we check if each parameter is present, if yes we add it to our computer
			// object
			if (parsed.length >= 3 && !parsed[2].isEmpty()) {
				c.setName(parsed[2]);
				if (parsed.length >= 4 && !parsed[3].isEmpty()) {
					c.setIntroduced(new Date(df.parse(parsed[3]).getTime()));
					if (parsed.length >= 5 && !parsed[4].isEmpty()) {
						c.setDiscontinued(new Date(df.parse(parsed[3]).getTime()));
						if (parsed.length >= 5 && !parsed[5].isEmpty()) {
							c.setCompanyId(Integer.parseInt(parsed[5]));
						}
					}
				}
				System.out.println("UPDATE : " + command);
				factory.getComputerDao().update(c);
				ret = true;
			} else {
				System.out.println("UPDATE + NOT_ENOUGH_ARGS : " + command);
			}
		}
		// case when parsed[0] == "delete"
		else if (parsed.length >= 3 && parsed[0].equals("delete")) {
			Computer c = new Computer();
			if (parsed[1].equals("-i")) {
				if (!parsed[2].isEmpty()) {
					System.out.println("DELETE -i " + parsed[2]);
					c.setId(Integer.parseInt(parsed[2]));
					factory.getComputerDao().delete(c);
					ret = true;
				} else {
					System.out.println("DELETE -i + EMPTY");
				}
			} else if (parsed[1].equals("-n")) {
				if (!parsed[2].isEmpty()) {
					System.out.println("DELETE -n " + parsed[2]);
					c.setName(parsed[2]);
					factory.getComputerDao().delete(c);
					ret = true;
				} else {
					System.out.println("DELETE -n + EMPTY");
				}
			} else {
				System.out.println("DELETE + ERROR : " + command);
			}
		}
		// case when command is not recognized
		else {
			System.out.println("UNKNOWN_COMMAND : " + command);
		}

		return ret;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public List<Computer> getComputers() {
		return computers;
	}

	public List<Company> getCompanies() {
		return companies;
	}
}