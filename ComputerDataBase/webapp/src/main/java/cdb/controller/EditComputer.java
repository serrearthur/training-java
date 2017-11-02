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
import org.springframework.web.bind.annotation.RequestParam;

import cdb.service.ServiceCompany;
import cdb.service.ServiceComputer;
import cdb.view.dto.DTOComputer;
import cdb.view.fields.ComputerFields;
import cdb.view.fields.Fields;
import cdb.view.fields.GeneralFields;

/**
 * Controller implementing the mechanics behind the edition of a computer.
 * @author aserre
 */
@Controller
@RequestMapping("/edit_computer")
public class EditComputer implements ComputerFields, GeneralFields {
    private static final String VIEW = "editComputer";
    private static final DTOComputer FORM = new DTOComputer();
    private static final Fields FIELDS = new Fields();
    private ServiceComputer serviceComputer;
    private ServiceCompany serviceCompany;

    /**
     * Constructor.
     * @param serviceCompany {@link ServiceCompany} bean
     * @param serviceComputer {@link ServiceComputer} bean
     */
    @Autowired
    private EditComputer(ServiceCompany serviceCompany, ServiceComputer serviceComputer) {
        this.serviceCompany = serviceCompany;
        this.serviceComputer = serviceComputer;
    }

    /**
     * Behaviour when we execute a GET request on EditComputer.
     * @param computerId ID of the computer we are editing
     * @param model {@link ModelMap} containing the parameters of the request
     * @return URL of the EditComputer view
     */
    @GetMapping
    protected String doGet(ModelMap model, @RequestParam(value = ATT_COMPUTERID, required = true) String computerId) {
        model.addAttribute(ATT_COMPUTER, serviceComputer.getComputer(computerId));
        model.addAttribute(ATT_COMPANIES, serviceCompany.getCompanies());
        model.addAttribute(ATT_COMPUTER_FORM, FORM);
        model.addAttribute(ATT_FIELDS, FIELDS);
        return VIEW;
    }

    /**
     * Behaviour when we execute a POST request on AddComputer.
     * @param model model of the request
     * @param computer computer object to edit
     * @param result result of the form
     * @param computerId ID of the computer we are editing
     * @return address of the jsp to visit
     */
    @PostMapping
    protected String doPost(Model model, @ModelAttribute(value = ATT_COMPUTER_FORM) DTOComputer computer,
            BindingResult result, @RequestParam(value = ATT_COMPUTERID, required = true) String computerId) {
        Map<String, String> errors = serviceComputer.editComputer(computer);
        if (errors.isEmpty()) {
            return "redirect:" + VIEW_HOME;
        } else {
            model.addAttribute(ATT_ERRORS, errors);
            model.addAttribute(ATT_COMPUTER, serviceComputer.getComputer(computerId));
            model.addAttribute(ATT_COMPANIES, serviceCompany.getCompanies());
            model.addAttribute(ATT_COMPUTER_FORM, FORM);
            model.addAttribute(ATT_FIELDS, FIELDS);
            return VIEW;
        }
    }
}
