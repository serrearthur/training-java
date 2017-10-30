package cdb.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cdb.service.ServiceCompany;
import cdb.service.ServiceComputer;
import cdb.view.Page;
import cdb.view.dto.DTOCompany;
import cdb.view.dto.DTOComputer;

@RestController
@RequestMapping("/api")
public class RestApi {
    private ServiceComputer serviceComputer;
    private ServiceCompany serviceCompany;

    /**
     * Constructor.
     * @param serviceComputer {@link ServiceComputer} bean
     */
    @Autowired
    private RestApi(ServiceComputer serviceComputer, ServiceCompany serviceCompany) {
        this.serviceComputer = serviceComputer;
        this.serviceCompany = serviceCompany;
    }

    @RequestMapping(value = "/computer/{id}", method = RequestMethod.GET, produces = "application/json")
    public DTOComputer getComputer( @PathVariable String id ) {
        return serviceComputer.getComputer(id);
    }

    @RequestMapping(value = "/computer/search/{search}/{page}/{limit}/{col}/{order}", method = RequestMethod.GET, produces = "application/json")
    public Page<DTOComputer> getComputers(@PathVariable String search, @PathVariable int page, @PathVariable int limit, @PathVariable String col, @PathVariable String order) {
        return serviceComputer.getPage(search, page, limit, col, order);
    }
    
    @RequestMapping(value = "/computer/all", method = RequestMethod.GET, produces = "application/json")
    public List<DTOComputer> getAllComputers() {
        return serviceComputer.getAllComputers();
    }

    @RequestMapping(value = "/company/{id}", method = RequestMethod.GET, produces = "application/json")
    public DTOCompany getCompany( @PathVariable int id ) {
        return serviceCompany.getCompany(id);
    }
    
    @RequestMapping(value = "/company/all", method = RequestMethod.GET, produces = "application/json")
    public List<DTOCompany> getCompanies() {
        return serviceCompany.getCompanies();
    }
    
    @RequestMapping(value = "/computer", method = RequestMethod.POST)
    public void createComputer( @PathVariable String id, @RequestBody DTOComputer c ) {
        System.out.println("ok");
        serviceComputer.addComputer(c);
    }
}
