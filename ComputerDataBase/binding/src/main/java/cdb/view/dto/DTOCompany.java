package cdb.view.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * DTO representation of a {@link cdb.model.Company}.
 * @author aserre
 *
 */
public class DTOCompany {
    @Min(value = 1, message = "Id can't be empty or equal to 0")
    private String id;

    @NotBlank(message = "Company name can't be empty")
    private String name;

    /**
     * Constructor.
     */
    public DTOCompany() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
