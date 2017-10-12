package cdb.controller.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cdb.controller.service.ServiceComputer;
import cdb.controller.servlet.fields.GeneralFields;
import cdb.controller.servlet.fields.PageFields;
import cdb.view.Page;
import cdb.view.dto.DTOComputer;

/**
 * Servlet implementing the mechanics behind the home page.
 * @author aserre
 */
@WebServlet("/home")
public class Dashboard extends HttpServlet implements GeneralFields, PageFields {
    private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/jsp/home.jsp";
    private static ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-service.xml");
    private static final ServiceComputer SERVICE_COMPUTER = (ServiceComputer) ctx.getBean("serviceComputer");

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
        Page<DTOComputer> page = requestParser(request);

        request.setAttribute(ATT_PAGE, page);
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
