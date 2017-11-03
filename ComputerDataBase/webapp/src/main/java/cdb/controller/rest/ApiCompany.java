package cdb.controller.rest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cdb.model.Company;
import cdb.service.ServiceCompany;

@RestController
@RequestMapping(value = "/api/company")
public class ApiCompany {
    private ServiceCompany serviceCompany;

    /**
     * Constructor.
     * @param serviceCompany {@link ServiceCompany} bean
     */
    @Autowired
    private ApiCompany(ServiceCompany serviceCompany) {
        this.serviceCompany = serviceCompany;
    }

    @GetMapping(value = "/{id}")
    public Company getCompany( @PathVariable int id ) {
        return serviceCompany.getCompany(id);
    }
    
    @GetMapping(value = "/all")
    public List<Company> getCompanies() {
        return serviceCompany.getCompanies();
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteCompany(@PathVariable Integer id ) {
        serviceCompany.deleteCompany(id);
    }
}
