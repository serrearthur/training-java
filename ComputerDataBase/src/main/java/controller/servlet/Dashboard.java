package controller.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.service.ComputerService;
import view.dto.DTOComputer;
import view.Page;

/**
 * Servlet implementing the mechanics behind the home page.
 * @author aserre
 */
@WebServlet("/home")
public class Dashboard extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/home.jsp";
    private static final String ATT_PAGE = "page";
    private static final String ATT_PAGELIMIT = "limit";
    private static final String ATT_PAGENUMBER = "pageNb";
    private static final String ATT_SEARCH = "search";
    private static final String ATT_DELETE = "selection";

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
        Page<DTOComputer> page = null;
        int limit = 10;
        int pageNb = 1;

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
            page = ComputerService.getPage(requestedSearch, limit);
        } else {
            page = ComputerService.getPage(limit);
        }
        page.setLimit(limit);
        page.moveToPageNumber(pageNb);

        request.setAttribute(ATT_PAGE, page);
        request.setAttribute(ATT_SEARCH, requestedSearch);
        request.setAttribute(ATT_PAGELIMIT, page.getLimit());
        request.setAttribute(ATT_PAGENUMBER, page.getCurrentPageNumber());
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
            ComputerService.delete(requestedDelete);
            response.sendRedirect("/ComputerDataBase/home");
        } else {
            doGet(request, response);
        }
    }
}
