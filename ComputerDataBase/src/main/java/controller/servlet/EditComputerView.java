package controller.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.validator.ComputerValidator;
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

        List<String> errors = new ArrayList<String>();
        Computer c = ComputerValidator.validate(name, introduced, discontinued, companyId, errors);
        
        if (errors.isEmpty()) {
            c.setId(Integer.parseInt(id));
            FACTORY.getComputerDao().update(c);
            request.getSession().invalidate();
            response.sendRedirect("/ComputerDataBase/home");
        } else {
            request.setAttribute("errors", errors);
            doGet(request, response);
        }
    }
}
