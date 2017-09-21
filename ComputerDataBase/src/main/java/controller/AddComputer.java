package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.validation.ComputerValidator;
import dao.DAOFactory;
import model.Computer;

/**
 * Servlet implementation class AddComputerView
 */
@WebServlet("/AddComputer")
public class AddComputer extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/addComputer.jsp";
    private static final String FIELD_NAME = "computerName";
    private static final String FIELD_INTRODUCED = "introduced";
    private static final String FIELD_DISONTINUED = "continued";
    private static final String FIELD_COMPANYID = "companyId";

    private static final DAOFactory FACTORY = DAOFactory.getInstance();

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("companies", FACTORY.getCompanyDao().getAll());
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter(FIELD_NAME);
        String introduced = request.getParameter(FIELD_INTRODUCED);
        String discontinued = request.getParameter(FIELD_DISONTINUED);
        String companyId = request.getParameter(FIELD_COMPANYID);

        List<String> errors = new ArrayList<String>();
        Computer c = ComputerValidator.validate(name, introduced, discontinued, companyId, errors);

        if (errors.isEmpty()) {
            FACTORY.getComputerDao().create(c);
            request.getSession().invalidate();
            response.sendRedirect("/ComputerDataBase/home");
        } else {
            System.out.println(errors.get(0));
            request.setAttribute("errors", errors);
            doGet(request, response);
        }
    }
}
