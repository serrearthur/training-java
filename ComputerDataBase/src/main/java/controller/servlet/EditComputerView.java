package controller.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;
import model.Computer;
import view.dto.DTOComputer;

/**
 * Servlet implementation class EditComputerView
 */
@WebServlet("/EditComputer")
public class EditComputerView extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/editComputer.jsp";
    private static final String ATT_COMPUTERID = "computerId";
    private static final String FIELD_ID = "id";
    private static final String FIELD_NAME = "computerName";
    private static final String FIELD_INTRODUCED = "introduced";
    private static final String FIELD_DISONTINUED = "continued";
    private static final String FIELD_COMPANYID = "companyId";

    private static final DAOFactory FACTORY = DAOFactory.getInstance();
    private DTOComputer currentComputer = null;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String computerID = request.getParameter(ATT_COMPUTERID);
        if (computerID != null) {
            currentComputer = view.dto.DTOComputer
                    .toDTOComputer(FACTORY.getComputerDao().getFromId(Integer.parseInt(computerID))).get(0);
        }

        request.setAttribute("computer", currentComputer);
        request.setAttribute("companies", FACTORY.getCompanyDao().getAll());
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter(FIELD_ID);
        String name = request.getParameter(FIELD_NAME);
        String introduced = request.getParameter(FIELD_INTRODUCED);
        String discontinued = request.getParameter(FIELD_DISONTINUED);
        String companyId = request.getParameter(FIELD_COMPANYID);

        String result;
        List<String> errors = new ArrayList<String>();
        Computer c = new Computer();

        try {
            c.setId(Integer.parseInt(id));
        } catch (Exception e) {
            errors.add(e.getMessage());
        }

        try {
            validationName(name);
            c.setName(name);
            System.out.println(name + "-" + c.getName());
        } catch (Exception e) {
            errors.add(e.getMessage());
        }

        try {
            validationIntroduced(introduced);
            c.setIntroduced(LocalDateTime.of(LocalDate.parse(introduced), null));
        } catch (Exception e) {
            errors.add(e.getMessage());
        }

        try {
            validationDiscontinued(discontinued);
            c.setIntroduced(LocalDateTime.of(LocalDate.parse(discontinued), null));
        } catch (Exception e) {
            errors.add(e.getMessage());
        }

        try {
            validationCompanyId(companyId);
            c.setCompanyId(Integer.parseInt(companyId));
        } catch (Exception e) {
            errors.add(e.getMessage());
        }

        if (errors.isEmpty()) {
            result = "ok";
        } else {
            result = "nok";
        }
        
        FACTORY.getComputerDao().update(c);
        request.setAttribute("errors", errors);
        request.setAttribute("result", result);
        doGet(request, response);
    }

    private void validationName(String name) throws Exception {
        if (name == null || name.trim().length() == 0)
            throw new Exception("Le nom du nouveau membre ne peut pas être vide");
    }

    private void validationIntroduced(String introduced) throws Exception {
        try {
            LocalDate.parse(introduced);
        } catch (Exception e) {
            throw e;
        }
    }

    private void validationDiscontinued(String discontinued) throws Exception {
        try {
            LocalDate.parse(discontinued);
        } catch (Exception e) {
            throw e;
        }
    }

    private void validationCompanyId(String companyId) throws Exception {
        try {
            Integer.parseInt(companyId);
        } catch (Exception e) {
            throw e;
        }
    }
}
