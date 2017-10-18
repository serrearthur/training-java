package cdb.controller.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cdb.controller.service.ServiceCompany;
import cdb.controller.service.ServiceComputer;
import cdb.controller.servlet.fields.ComputerFields;
import cdb.controller.servlet.fields.GeneralFields;
import cdb.dao.DAOConfig;

/**
 * Servlet implementing the mechanics behind the addition of a new computer.
 * @author aserre
 */
@Controller
public class ControllerAddComputer implements ComputerFields, GeneralFields {
    private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/jsp/addComputer.jsp";

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
    @RequestMapping("/AddComputer")
    public ModelAndView doGet(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(VIEW, ATT_COMPANIES, SERVICE_COMPANY.getCompanies());
    }

    /**
     * @param request HTTP request
     * @param response HTTP response to the request
     * @throws ServletException thrown by {@link  javax.servlet.RequestDispatcher#forward(ServletRequest arg0, ServletResponse arg1) }
     * @throws IOException thrown by {@link  javax.servlet.RequestDispatcher#forward(ServletRequest arg0, ServletResponse arg1) }
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter(ATT_NAME);
        String introduced = request.getParameter(ATT_INTRODUCED);
        String discontinued = request.getParameter(ATT_DISCONTINUED);
        String companyId = request.getParameter(ATT_COMPANYID);

        Map<String, String> errors = SERVICE_COMPUTER.addComputer(name, introduced, discontinued, companyId);

        if (errors.isEmpty()) {
            response.sendRedirect(VIEW_HOME);
        } else {
            request.setAttribute(ATT_ERRORS, errors);
            doGet(request, response);
        }
    }
}
