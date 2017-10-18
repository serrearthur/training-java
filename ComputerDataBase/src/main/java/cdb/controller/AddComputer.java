package cdb.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cdb.controller.fields.ComputerFields;
import cdb.controller.fields.GeneralFields;
import cdb.model.Computer;
import cdb.service.ServiceCompany;
import cdb.service.ServiceComputer;

/**
 * Servlet implementing the mechanics behind the addition of a new computer.
 * @author aserre
 */
@Controller
@RequestMapping("/AddComputer")
public class AddComputer implements ComputerFields, GeneralFields {
    private static final String VIEW = "addComputer";
    private ServiceComputer serviceComputer;
    private ServiceCompany serviceCompany;

    @Autowired
    public void setServiceCompany(ServiceCompany serviceCompany) {
        this.serviceCompany = serviceCompany;
    }

    @Autowired
    public void setServiceComputer(ServiceComputer serviceComputer) {
        this.serviceComputer = serviceComputer;
    }

    /**
     * Behaviour when we execute a GET request on AddComputer.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String doGet(ModelMap model) {
        model.addAttribute(ATT_COMPANIES, serviceCompany.getCompanies());
        return VIEW;
    }

    /**
     * Behaviour when we execute a POST request on AddComputer.
     */
    @RequestMapping(method = RequestMethod.POST)
    protected String doPost(Model model, @ModelAttribute("computerForm") Computer computer, BindingResult result) {
//        String name = request.getParameter(ATT_NAME);
//        String introduced = request.getParameter(ATT_INTRODUCED);
//        String discontinued = request.getParameter(ATT_DISCONTINUED);
//        String companyId = request.getParameter(ATT_COMPANYID);
        String name = null;
        String introduced = null;
        String discontinued = null;
        String companyId = null;

        Map<String, String> errors = serviceComputer.addComputer(name, introduced, discontinued, companyId);

        if (errors.isEmpty()) {
            return "redirect:" + VIEW_HOME;
        } else {
            model.addAttribute(ATT_ERRORS, errors);
            model.addAttribute(ATT_COMPANIES, serviceCompany.getCompanies());
            return VIEW;
        }
    }
}
