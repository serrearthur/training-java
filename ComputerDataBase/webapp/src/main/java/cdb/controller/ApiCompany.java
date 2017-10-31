package cdb.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cdb.service.ServiceCompany;
import cdb.view.dto.DTOCompany;

@RestController
@RequestMapping(value = "/api/company", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
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

    @GetMapping(value = "/{id}", produces = "application/json")
    public DTOCompany getCompany( @PathVariable int id ) {
        return serviceCompany.getCompany(id);
    }
    
    @GetMapping(value = "/all", produces = "application/json")
    public List<DTOCompany> getCompanies() {
        return serviceCompany.getCompanies();
    }
    
//    @PostMapping("/")
//    public void createCompany(@RequestBody DTOComputer c ) {
//    }
//    
//    @PutMapping("/")
//    public void editCompany(@RequestBody DTOComputer c ) {
//    }
    
    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable Integer id ) {
        serviceCompany.deleteCompany(id);
    }
}
