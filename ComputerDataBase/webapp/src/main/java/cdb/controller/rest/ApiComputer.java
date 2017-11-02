package cdb.controller.rest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cdb.service.ServiceComputer;
import cdb.view.dto.DTOComputer;

@RestController
//@RequestMapping(value = "/api/computer", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/computer")
public class ApiComputer {
    private ServiceComputer serviceComputer;
    /**
     * Constructor.
     * @param serviceComputer {@link ServiceComputer} bean
     */
    @Autowired
    private ApiComputer(ServiceComputer serviceComputer) {
        this.serviceComputer = serviceComputer;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public DTOComputer getComputer( @PathVariable String id ) {
        return serviceComputer.getComputer(id);
    }

    @GetMapping(value = "/search/{search}/{page}/{limit}/{col}/{order}", produces = "application/json")
    public Page<DTOComputer> getComputers(@PathVariable String search, @PathVariable int page, @PathVariable int limit, @PathVariable String col, @PathVariable String order) {
        return serviceComputer.getPage(search, page, limit, col, order);
    }
    
    @GetMapping(value = "/all", produces = "application/json")
    public List<DTOComputer> getAllComputers() {
        return serviceComputer.getAllComputers();
    }
    
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public void createComputer(@RequestBody DTOComputer c ) {
        serviceComputer.addComputer(c);
    }
    
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public void editComputer(@RequestBody DTOComputer c ) {
        serviceComputer.editComputer(c);
    }
    
    @RequestMapping(value = "/", method = RequestMethod.DELETE, consumes = "application/json;charset=UTF-8")
    public void deleteComputer(@RequestBody int[] id ) {
        serviceComputer.delete(id);
    }
}
