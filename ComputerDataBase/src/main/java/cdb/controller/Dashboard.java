package cdb.controller;

import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cdb.controller.fields.GeneralFields;
import cdb.controller.fields.PageFields;
import cdb.service.ServiceComputer;
import cdb.view.Page;
import cdb.view.dto.DTOComputer;

/**
 * Controller implementing the mechanics behind the home page.
 * @author aserre
 */
@Controller
@RequestMapping("/home")
public class Dashboard implements GeneralFields, PageFields {
    private static final String VIEW = "home";
    private ServiceComputer serviceComputer;

    /**
     * Constructor.
     * @param serviceComputer {@link ServiceComputer} bean
     */
    @Autowired
    private Dashboard(ServiceComputer serviceComputer) {
        this.serviceComputer = serviceComputer;
    }

    /**
     * Behaviour when we execute a GET request on home.
     * @param request {@link HttpServletRequest} to handle
     * @return jsp address to visit
     */
    @GetMapping
    public String doGet(HttpServletRequest request) {
        Page<DTOComputer> page = requestParser(request);
        request.setAttribute(ATT_PAGE, page);
        return VIEW;
    }

    /**
     * Behaviour when we execute a POST request on home.
     * @param requestedDelete list of ids to delete
     * @return jsp address to visit
     * @throws ServletException thrown when a bad POST request is done
     */
    @PostMapping
    public String doPost(@RequestParam(value = "selection", required = true) String requestedDelete) throws ServletException {
        if (requestedDelete != null) {
            serviceComputer.delete(Arrays.asList(requestedDelete.split(",")));
            return "redirect:" + VIEW_HOME;
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

        return serviceComputer.getPage(search, pageNb, limit, col, order);
    }
}
