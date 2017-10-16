package cdb.controller.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import cdb.controller.service.ServiceCompany;
import cdb.controller.service.ServiceComputer;
import cdb.controller.servlet.fields.ComputerFields;
import cdb.controller.servlet.fields.GeneralFields;
import cdb.dao.DAOConfig;
import cdb.view.dto.DTOComputer;

/**
 * Servlet implementing the mechanics behind the edition of a computer.
 * @author aserre
 */
@WebServlet("/EditComputer")
public class EditComputer extends HttpServlet implements ComputerFields, GeneralFields {
    private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/jsp/editComputer.jsp";
    private static ApplicationContext ctx = new AnnotationConfigApplicationContext(DAOConfig.class);
    private static final ServiceComputer SERVICE_COMPUTER = (ServiceComputer) ctx.getBean(ServiceComputer.class);
    private static final ServiceCompany SERVICE_COMPANY = (ServiceCompany) ctx.getBean(ServiceCompany.class);

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
            currentComputer = SERVICE_COMPUTER.getComputer(computerID);
        }

        request.setAttribute(ATT_COMPUTER, currentComputer);
        request.setAttribute(ATT_COMPANIES, SERVICE_COMPANY.getCompanies());
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

        Map<String, String> errors = SERVICE_COMPUTER.editComputer(id, name, introduced, discontinued, companyId);

        if (errors.isEmpty()) {
            response.sendRedirect(VIEW_HOME);
        } else {
            request.setAttribute(ATT_ERRORS, errors);
            doGet(request, response);
        }
    }
}
