package controller.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.service.CompanyService;
import controller.service.ComputerService;
import view.dto.DTOComputer;

/**
 * Servlet implementing the mechanics behind the edition of a computer.
 * @author aserre
 */
@WebServlet("/EditComputer")
public class EditComputer extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/editComputer.jsp";
    private static final String ATT_COMPUTERID = "id";
    private static final String FIELD_NAME = "computerName";
    private static final String FIELD_INTRODUCED = "introduced";
    private static final String FIELD_DISCONTINUED = "discontinued";
    private static final String FIELD_COMPANYID = "companyId";

    /**
     * @param request HTTP request
     * @param response HTTP response to the request
     * @throws ServletException thrown by {@link  javax.servlet.RequestDispatcher#forward(ServletRequest arg0, ServletResponse arg1) }
     * @throws IOException thrown by {@link  javax.servlet.RequestDispatcher#forward(ServletRequest arg0, ServletResponse arg1) }
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DTOComputer currentComputer = null;
        String computerID = request.getParameter(ATT_COMPUTERID);
        if (computerID != null) {
            currentComputer = ComputerService.getComputer(computerID);
        }

        request.setAttribute("computer", currentComputer);
        request.setAttribute("companies", CompanyService.getCompanies());
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }

    /**
     * @param request HTTP request
     * @param response HTTP response to the request
     * @throws ServletException thrown by {@link  javax.servlet.RequestDispatcher#forward(ServletRequest arg0, ServletResponse arg1) }
     * @throws IOException thrown by {@link  javax.servlet.RequestDispatcher#forward(ServletRequest arg0, ServletResponse arg1) }
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter(ATT_COMPUTERID);
        String name = request.getParameter(FIELD_NAME);
        String introduced = request.getParameter(FIELD_INTRODUCED);
        String discontinued = request.getParameter(FIELD_DISCONTINUED);
        String companyId = request.getParameter(FIELD_COMPANYID);

        List<String> errors = ComputerService.editComputer(id, name, introduced, discontinued, companyId);

        if (errors.isEmpty()) {
            request.getSession().invalidate();
            response.sendRedirect("/ComputerDataBase/home");
        } else {
            request.setAttribute("errors", errors);
            doGet(request, response);
        }
    }
}
