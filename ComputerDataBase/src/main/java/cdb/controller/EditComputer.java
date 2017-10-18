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
import org.springframework.web.bind.annotation.RequestParam;

import cdb.controller.fields.ComputerFields;
import cdb.controller.fields.GeneralFields;
import cdb.model.Computer;
import cdb.service.ServiceCompany;
import cdb.service.ServiceComputer;

/**
 * Servlet implementing the mechanics behind the edition of a computer.
 * @author aserre
 */
@Controller
@RequestMapping("/EditComputer")
public class EditComputer implements ComputerFields, GeneralFields {
    private static final String VIEW = "editComputer";
    private ServiceComputer serviceComputer;
    private ServiceCompany serviceCompany;

    @Autowired
    public void setServiceComputer(ServiceComputer serviceComputer) {
        this.serviceComputer = serviceComputer;
    }

    @Autowired
    public void setServiceCompany(ServiceCompany serviceCompany) {
        this.serviceCompany = serviceCompany;
    }

    /**
     * Behaviour when we execute a GET request on EditComputer.
     * @param computerId ID of the computer we are editing
     * @param model {@link ModelMap} containing the parameters of the request
     * @return URL of the EditComputer view
     */
    protected String doGet(ModelMap model, @RequestParam(value = ATT_COMPUTERID, required = true) String computerId) {
        if (computerId != null) {
            model.addAttribute(ATT_COMPUTER, serviceComputer.getComputer(computerId));
        }
        model.addAttribute(ATT_COMPANIES, serviceCompany.getCompanies());
        return VIEW;
    }

    /**
     * Behaviour when we execute a POST request on AddComputer.
     */
    @RequestMapping(method = RequestMethod.POST)
    protected String doPost(Model model, @ModelAttribute("computerForm") Computer computer, BindingResult result) {
//        String String id = request.getParameter(ATT_COMPUTERID);
//        String name = request.getParameter(ATT_NAME);
//        String introduced = request.getParameter(ATT_INTRODUCED);
//        String discontinued = request.getParameter(ATT_DISCONTINUED);
//        String companyId = request.getParameter(ATT_COMPANYID);
        String id = null;
        String name = null;
        String introduced = null;
        String discontinued = null;
        String companyId = null;

        Map<String, String> errors = serviceComputer.editComputer(id, name, introduced, discontinued, companyId);

        if (errors.isEmpty()) {
            return "redirect:" + VIEW_HOME;
        } else {
            model.addAttribute(ATT_ERRORS, errors);
            model.addAttribute(ATT_COMPANIES, serviceCompany.getCompanies());
            return VIEW;
        }
    }
}
