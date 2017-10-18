package cdb.controller.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cdb.controller.service.ServiceComputer;
import cdb.controller.servlet.fields.GeneralFields;
import cdb.controller.servlet.fields.PageFields;
import cdb.view.Page;
import cdb.view.dto.DTOComputer;

/**
 * Servlet implementing the mechanics behind the home page.
 * @author aserre
 */
@Controller
public class ControllerDashboard implements GeneralFields, PageFields {
    private static final long serialVersionUID = 1L;
    private static final String VIEW = "home";
    private static ApplicationContext ctx = new AnnotationConfigApplicationContext(DAOConfig.class);
    private static final ServiceComputer SERVICE_COMPUTER = (ServiceComputer) ctx.getBean(ServiceComputer.class);

    /**
     * @param request HTTP request
     * @param response HTTP response to the request
     */
    @RequestMapping("/home")
    public ModelAndView doGet(HttpServletRequest request, HttpServletResponse response) {
        Page<DTOComputer> page = requestParser(request);
        return new ModelAndView(VIEW, ATT_PAGE, page);
    }

    /**
     * @param request HTTP request
     * @param response HTTP response to the request
     * @throws ServletException thrown by {@link  javax.servlet.RequestDispatcher#forward(ServletRequest arg0, ServletResponse arg1) }
     * @throws IOException thrown by {@link  javax.servlet.RequestDispatcher#forward(ServletRequest arg0, ServletResponse arg1) }
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @RequestMapping(value = "/home", method = RequestMethod.POST)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String requestedDelete = request.getParameter(ATT_DELETE);
        if (requestedDelete != null) {
            SERVICE_COMPUTER.delete(requestedDelete);
            response.sendRedirect(VIEW_HOME);
        } else {
            throw new ServletException("Invalid operation : POST.");
        }
    }

    /**
     * Method in charge of parsing the request and creating the adequate {@link Page} object.
     * @param request the {@link HttpServletRequest} to parse
     * @return the {@link Page} object corresponding to the request
     */
    private Page<DTOComputer> requestParser(HttpServletRequest request) {
        int limit = 10;
        int pageNb = 1;
        String search = "";
        String col = "cpt.id";
        String order = "ASC";

        String requestedPage = request.getParameter(ATT_PAGENUMBER);
        if (requestedPage != null) {
            pageNb = Integer.parseInt(requestedPage);
        }

        String requestedLimit = request.getParameter(ATT_PAGELIMIT);
        if (requestedLimit != null) {
            limit = Integer.parseInt(requestedLimit);
        }

        String requestedSearch = request.getParameter(ATT_SEARCH);
        if (requestedSearch != null) {
            search = requestedSearch;
        }

        String requestedCol = request.getParameter(ATT_COL);
        if (requestedCol != null) {
            col = requestedCol;
        }

        String requestedOrder = request.getParameter(ATT_ORDER);
        if (requestedOrder != null) {
            order = requestedOrder;
        }

        return SERVICE_COMPUTER.getPage(search, pageNb, limit, col, order);
    }
}
