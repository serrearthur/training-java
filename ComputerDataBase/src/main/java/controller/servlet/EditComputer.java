package controller.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.ComputerFields;
import controller.GeneralFields;
import controller.service.ServiceCompany;
import controller.service.ServiceComputer;
import view.dto.DTOComputer;

/**
 * Servlet implementing the mechanics behind the edition of a computer.
 * @author aserre
 */
@WebServlet("/EditComputer")
public class EditComputer extends HttpServlet implements ComputerFields, GeneralFields {
    private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/editComputer.jsp";
    private static final String ATT_COMPUTER = "computer";

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
            currentComputer = ServiceComputer.getComputer(computerID);
        }

        request.setAttribute(ATT_COMPUTER, currentComputer);
        request.setAttribute(ATT_COMPANIES, ServiceCompany.getCompanies());
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
        String name = request.getParameter(ATT_NAME);
        String introduced = request.getParameter(ATT_INTRODUCED);
        String discontinued = request.getParameter(ATT_DISCONTINUED);
        String companyId = request.getParameter(ATT_COMPANYID);

        Map<String, String> errors = ServiceComputer.editComputer(id, name, introduced, discontinued, companyId);

        if (errors.isEmpty()) {
            response.sendRedirect(VIEW_HOME);
        } else {
            request.setAttribute(ATT_ERRORS, errors);
            doGet(request, response);
        }
    }
}
