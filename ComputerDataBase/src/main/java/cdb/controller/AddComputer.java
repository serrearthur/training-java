package cdb.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cdb.controller.fields.ComputerFields;
import cdb.controller.fields.GeneralFields;
import cdb.service.ServiceCompany;
import cdb.service.ServiceComputer;
import cdb.view.dto.DTOComputer;

/**
 * Controller implementing the mechanics behind the addition of a new computer.
 * @author aserre
 */
@Controller
@RequestMapping("/add_computer")
public class AddComputer implements ComputerFields, GeneralFields {
    private static final String VIEW = "addComputer";
    private ServiceComputer serviceComputer;
    private ServiceCompany serviceCompany;

    /**
     * Constructor.
     * @param serviceCompany {@link ServiceCompany} bean
     * @param serviceComputer {@link ServiceComputer} bean
     */
    @Autowired
    private AddComputer(ServiceCompany serviceCompany, ServiceComputer serviceComputer) {
        this.serviceCompany = serviceCompany;
        this.serviceComputer = serviceComputer;
    }

    /**
     * Behaviour when we execute a GET request on AddComputer.
     * @param model {@link ModelMap} of the request
     * @return address of the jsp to visit
     */
    @GetMapping
    public String doGet(ModelMap model) {
        model.addAttribute(ATT_COMPANIES, serviceCompany.getCompanies());
        model.addAttribute("computerForm", new DTOComputer());
        return VIEW;
    }

    /**
     * Behaviour when we execute a POST request on AddComputer.
     * @param model {@link Model} of the request
     * @param computer {@link Computer} to add
     * @param result result of the form
     * @return addess of the jsp to visit
     */
    @PostMapping
    protected String doPost(Model model, @ModelAttribute("computerForm") DTOComputer computer, BindingResult result) {
        Map<String, String> errors = serviceComputer.addComputer(computer);

        if (errors.isEmpty()) {
            return "redirect:" + VIEW_HOME;
        } else {
            model.addAttribute(ATT_ERRORS, errors);
            model.addAttribute(ATT_COMPANIES, serviceCompany.getCompanies());
            model.addAttribute("computerForm", new DTOComputer());
            return VIEW;
        }
    }
}
