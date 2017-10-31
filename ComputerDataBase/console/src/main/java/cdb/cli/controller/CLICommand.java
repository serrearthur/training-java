package cdb.cli.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import cdb.model.Computer;
import cdb.view.dto.DTOCompany;
import cdb.view.dto.DTOComputer;

/**
 * Class designed to parse a user input and execute backend actions accordingly.
 * <p>
 * Only called when the app is used in CLI mode.
 *
 * @author aserre
 */
public class CLICommand {
    private String command;
    private List<DTOComputer> result_computers;
    private List<DTOCompany> result_companies;
    private HttpEntity<DTOCompany> headersCompany;
    private HttpEntity<DTOComputer> headersComputer;
    private RestTemplate restTemplate;
    
    private static final String API_URL = "http://localhost:8080/ComputerDataBase/api/";
    private static final String AUTH = Base64.getEncoder().encodeToString("bob:azerty".getBytes());

    /**
     * Creates a CLICommand object from a command line input. A CLICommand contains
     * methods to convert text input to backend actions.
     *
     * @param command
     *            User input to analyze
     */
    public CLICommand(String command) {
        this.command = command;
        this.result_companies = new ArrayList<DTOCompany>();
        this.result_computers = new ArrayList<DTOComputer>();
        this.restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + AUTH);
        this.headersCompany = new HttpEntity<DTOCompany>(headers);
        this.headersComputer = new HttpEntity<DTOComputer>(headers);
    }

    /**
     * Command-line instruction to parse.
     *
     * @return this CLICommand command String
     */
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * {@link ArrayList} containing the computers resulting from the parsed
     * {@link #command}.
     *
     * @return result of the backend operations on the computers
     */
    public List<DTOComputer> getComputers() {
        return result_computers;
    }

    /**
     * {@link ArrayList} containing the companies resulting from the parsed
     * {@link #command}.
     *
     * @return result of the backend operations on the companies
     */
    public List<DTOCompany> getCompanies() {
        return result_companies;
    }

    /**
     * Method called to parse this object {@link #command} String. It first splits
     * the {@link #command} string, then call the relevant parsing method according
     * to the keywords encountered.
     *
     * @return boolean <code>true</code> if the command was valid,
     *         <code>false</code> otherwise
     */
    public boolean parse() {
        // we reset the values for the request
        boolean ret = false;
        this.result_companies = new ArrayList<DTOCompany>();
        this.result_computers = new ArrayList<DTOComputer>();

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

    /**
     * Method called if the string 'list' is encountered in the {@link #command}. It
     * returns either all the computers, or all the companies, depending wether the
     * user called 'list cpt' or 'list cpn'.
     *
     * @param parsed
     *            sub command to parse
     * @return result of the Query from the database
     * @see DAOFactory
     */
    private boolean parseList(List<String> parsed) {
        boolean ret = false;
        if (parsed.size() >= 2 && parsed.get(1).equals("cpt")) {
            // case when we list all computers
            System.out.println("LIST CPT");
            ResponseEntity<DTOComputer[]> res = restTemplate.exchange(API_URL+"computer/all", HttpMethod.GET, headersComputer, DTOComputer[].class);
            this.result_computers.addAll(Arrays.asList(res.getBody()));
            ret = true;
        } else if (parsed.get(1).equals("cpn")) {
            // case when we list all companies
            System.out.println("LIST CPN");
            ResponseEntity<DTOCompany[]> res = restTemplate.exchange(API_URL+"company/all", HttpMethod.GET, headersCompany, DTOCompany[].class);
            this.result_companies.addAll(Arrays.asList(res.getBody()));
            ret = true;
        } else {
            System.out.println("LIST + ERROR");
        }
        return ret;
    }

    /**
     * Method called if the string 'show' is encountered in the {@link #command}. It
     * returns a list of computers from the database according to their
     * {@link Computer#getId()}, {@link Computer#getName()} or
     * {@link Computer#getCompanyId()}, depending on wether the user called 'show -i
     * ID', 'show -n NAME' or 'show -c COMPANYID'.
     *
     * @param parsed
     *            sub command to parse
     * @return result of the Query from the database
     * @see DAOFactory
     */
    private boolean parseShow(List<String> parsed) {
        boolean ret = false;
        if (parsed.size() >= 3 && parsed.get(1).equals("-i")) {
            if (!parsed.get(2).isEmpty()) {
                // case when we show computer with id X
                System.out.println("SHOW -i " + parsed.get(2));
                ResponseEntity<DTOComputer> res = restTemplate.exchange(API_URL+"computer/"+parsed.get(2), HttpMethod.GET, headersComputer, DTOComputer.class);
                this.result_computers.add(res.getBody());
                ret = true;
            } else {
                System.out.println("SHOW -i + EMPTY : " + command);
            }
        } else if (parsed.get(1).equals("-n")) {
            if (!parsed.get(2).isEmpty()) {
                // case when we show computer with name X
                System.out.println("SHOW -n " + parsed.get(2));
                //this.result_computers.addAll(serviceComputer.getPage(parsed.get(2), 1, 1000, "cpt.id", "ASC").getData());
                ret = true;
            } else {
                System.out.println("SHOW -n + EMPTY");
            }
        } else {
            System.out.println("SHOW + ERROR : " + command);
        }
        return ret;
    }

    /**
     * Method called if the string 'create' is encountered in the {@link #command}.
     * It creates a new {@link Computer} in the database if the {@link #command}
     * follows the syntax 'create NAME'.
     *
     * @param parsed
     *            sub command to parse
     * @return <code>true</code> if the command was valid, <code>false</code>
     *         otherwise
     * @see DAOFactory
     */
    private boolean parseCreate(List<String> parsed) {
        boolean ret = false;
        if (parsed.size() >= 2 && !parsed.get(1).isEmpty()) {
            // case when we create a new computer with name X
            System.out.println("CREATE " + parsed.get(1));
            DTOComputer c = new DTOComputer();
            c.setName(parsed.get(1));
            restTemplate.exchange(API_URL+"computer/", HttpMethod.POST, headersComputer, DTOComputer.class, c);
            ret = true;
        } else {
            System.out.println("CREATE + ERROR : " + command);
        }
        return ret;
    }

    /**
     * Method called if the string 'update' is encountered in the {@link #command}.
     * It updates a new {@link Computer} in the database if the {@link #command}
     * follows the syntax 'update ID NAME [INTRODUCED DISCONTINUED COMPANYID]'.
     * Dates must be in the "dd/MM/yyyy" format.
     *
     * @param parsed
     *            sub command to parse
     * @return <code>true</code> if the command was valid, <code>false</code>
     *         otherwise
     * @see DAOFactory
     */
    private boolean parseUpdate(List<String> parsed) {
        boolean ret = false;
        // we check if each parameter is present, if yes we add it to our computer object
        if (parsed.size() >= 3 && !parsed.get(2).isEmpty()) {
            DTOComputer c = new DTOComputer();
            c.setId(parsed.get(1));
            c.setName(parsed.get(2));
            if (parsed.size() >= 4 && !parsed.get(3).isEmpty()) {
                c.setIntroduced(parsed.get(3));
                if (parsed.size() >= 5 && !parsed.get(4).isEmpty()) {
                    c.setDiscontinued(parsed.get(4));
                    if (parsed.size() >= 6 && !parsed.get(5).isEmpty()) {
                        c.setCompanyId(parsed.get(5));
                    }
                }
            }
            System.out.println("UPDATE : " + command);
            restTemplate.exchange(API_URL+"computer/", HttpMethod.PUT, headersComputer, DTOComputer.class, c);
            ret = true;
        } else {
            System.out.println("UPDATE + NOT_ENOUGH_ARGS : " + command);
        }
        return ret;
    }

    /**
     * Method called if the string 'delete' is encountered in the {@link #command}.
     * It deletes a list of computers from the database according to their
     * {@link Computer#getId()} or {@link Computer#getName()}, depending on wether
     * the user called 'delete -i ID' or 'delete -n NAME'.
     *
     * @param parsed
     *            sub command to parse
     * @return <code>true</code> if the command was valid, <code>false</code>
     *         otherwise
     * @see DAOFactory
     */
    private boolean parseDelete(List<String> parsed) {
        boolean ret = false;
        if (parsed.size() >= 3 && parsed.get(1).equals("-i")) {
            if (!parsed.get(2).isEmpty()) {
                System.out.println("DELETE -i " + parsed.get(2));
                restTemplate.exchange(API_URL+"computer/", HttpMethod.DELETE, headersComputer, DTOComputer.class, Arrays.asList(parsed.get(2).split(",")));
                ret = true;
            } else {
                System.out.println("DELETE -i + EMPTY");
            }
        } else if (parsed.size() >= 3 && parsed.get(1).equals("-c")) {
            if (!parsed.get(2).isEmpty()) {
                System.out.println("DELETE -c " + parsed.get(2));
                restTemplate.exchange(API_URL+"computer/"+parsed.get(2), HttpMethod.DELETE, headersCompany, DTOCompany.class);
                ret = true;
            } else {
                System.out.println("DELETE -c + EMPTY");
            }
        } else {
            System.out.println("DELETE + ERROR : " + command);
        }
        return ret;
    }
}
