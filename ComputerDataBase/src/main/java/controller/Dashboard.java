package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DAOFactory;
import view.dto.DTOComputer;
import model.Computer;
import view.Page;

/**
 * Servlet implementation class HomeView.
 */
@SuppressWarnings("unchecked")
@WebServlet("/home")
public class Dashboard extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/home.jsp";
    private static final String ATT_PAGE = "page";
    private static final String FIELD_PAGESIZE = "itemPerPage";
    private static final String FIELD_PAGENUMBER = "pageNumber";
    private static final String FIELD_SEARCH = "search";
    private static final String FIELD_DELETE = "selection";

    private final DAOFactory FACTORY = DAOFactory.getInstance();
    private Page<DTOComputer> page = null;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        page = (Page<DTOComputer>) session.getAttribute(ATT_PAGE);

        if (page == null) {
            page = new Page<DTOComputer>(DTOComputer.toDTOComputer(FACTORY.getComputerDao().getAll()), 10);
        }

        String requestedSearch = request.getParameter(FIELD_SEARCH);
        if (requestedSearch != null) {
            page = new Page<DTOComputer>(
                    DTOComputer.toDTOComputer(FACTORY.getComputerDao().getFromName(requestedSearch)),
                    page.getElementPerPage());
        }

        String requestedPage = request.getParameter(FIELD_PAGENUMBER);
        if (requestedPage != null) {
            page.moveToPageNumber(Integer.parseInt(requestedPage));
        }
        session.setAttribute(ATT_PAGE, page);
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        page = (Page<DTOComputer>) session.getAttribute(ATT_PAGE);
        String requestedPageSize = request.getParameter(FIELD_PAGESIZE);
        if (requestedPageSize != null) {
            page.setElementPerPage(Integer.parseInt(requestedPageSize));
        }

        String requestedDelete = request.getParameter(FIELD_DELETE);
        if (requestedDelete != null) {
            for (String s : requestedDelete.split(",")) {
                FACTORY.getComputerDao().delete(new Computer(Integer.parseInt(s), ""));
            }
            page = new Page<DTOComputer>(DTOComputer.toDTOComputer(FACTORY.getComputerDao().getAll()),
                    page.getElementPerPage());
            session.setAttribute(ATT_PAGE, page);
            response.sendRedirect("/ComputerDataBase/home");
        } else {
            session.setAttribute(ATT_PAGE, page);
            doGet(request, response);
        }
    }
}
