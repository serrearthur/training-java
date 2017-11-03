package cdb.controller.rest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cdb.model.Computer;
import cdb.service.ServiceComputer;

@RestController
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

    @GetMapping(value = "/{id}")
    public Computer getComputer( @PathVariable String id ) {
        return serviceComputer.getComputer(id);
    }

    @GetMapping(value = "/search/{search}/{page}/{limit}/{col}/{order}")
    public Page<Computer> getComputers(@PathVariable String search, @PathVariable int page, @PathVariable int limit, @PathVariable String col, @PathVariable String order) {
        PageRequest request = PageRequest.of(page, limit, order.equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC, col);
        return serviceComputer.getPage(search, request);
    }
    
    @GetMapping(value = "/all")
    public List<Computer> getAllComputers() {
        return serviceComputer.getAllComputers();
    }
    
    @RequestMapping(value = "/", method = {RequestMethod.POST, RequestMethod.PUT})
    public void saveComputer(@RequestBody Computer c ) {
        serviceComputer.saveComputer(c);
    }
   
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public void deleteComputer(@RequestBody int[] id ) {
        serviceComputer.delete(id);
    }
}
