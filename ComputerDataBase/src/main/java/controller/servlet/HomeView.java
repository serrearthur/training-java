package controller.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;
import model.Computer;
import view.Page;

/**
 * Servlet implementation class HomeView.
 */
@WebServlet("/home")
public class HomeView extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/home.jsp";
    private static final String ATT_PAGE = "page";
    private static final String FIELD_PAGESIZE = "itemPerPage";
    private static final String FIELD_PAGENUMBER = "pageNumber";
    private static final String FIELD_SEARCH = "search";

    private static final DAOFactory FACTORY = DAOFactory.getInstance();
    private static int itemPerPage = 10;
    private static Page<Computer> page;

    public void init() {
        page = new Page<Computer>(FACTORY.getComputerDao().getAll(), itemPerPage);
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requestedSearch = request.getParameter(FIELD_SEARCH);
        if (requestedSearch != null) {
            page = new Page<Computer>(FACTORY.getComputerDao().getFromName(requestedSearch), itemPerPage);
        }

        String requestedPage = request.getParameter(FIELD_PAGENUMBER);
        if (requestedPage != null) {
            page.moveToPageNumber(Integer.parseInt(requestedPage));
        }
        request.setAttribute(ATT_PAGE, page);
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requestedPageSize = request.getParameter(FIELD_PAGESIZE);
        itemPerPage = Integer.parseInt(requestedPageSize);
        page.setElementPerPage(itemPerPage);
        doGet(request, response);
    }

}
