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
import controller.service.CompanyService;
import controller.service.ComputerService;

/**
 * Servlet implementing the mechanics behind the addition of a new computer.
 * @author aserre
 */
@WebServlet("/AddComputer")
public class AddComputer extends HttpServlet implements ComputerFields, GeneralFields {
    private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/addComputer.jsp";

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
        request.setAttribute(ATT_COMPANIES, CompanyService.getCompanies());
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
        String name = request.getParameter(ATT_NAME);
        String introduced = request.getParameter(ATT_INTRODUCED);
        String discontinued = request.getParameter(ATT_DISCONTINUED);
        String companyId = request.getParameter(ATT_COMPANYID);

        Map<String, String> errors = ComputerService.addComputer(name, introduced, discontinued, companyId);

        if (errors.isEmpty()) {
            response.sendRedirect(VIEW_HOME);
        } else {
            request.setAttribute(ATT_ERRORS, errors);
            doGet(request, response);
        }
    }
}
