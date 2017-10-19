package cdb.view.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.lang.Nullable;

/**
 * DTO representation of a {@link cdb.model.Computer}.
 * @author aserre
 */
public class DTOComputer {
    @Min(value = 1, message = "Id can't be empty or equal to 0")
    private String id;

    @NotBlank(message = "Computer name can't be empty")
    private String name;

    @Nullable
    private String introduced;

    @Nullable
    private String discontinued;

    @Nullable
    private String company;

    @Nullable
    @Min(value = 1, message = "Id can't be empty or equal to 0")
    private String companyId;

    /**
     * Constructor.
     */
    public DTOComputer() {
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

    public String getIntroduced() {
        return this.introduced;
    }

    public void setIntroduced(String introduced) {
        this.introduced = introduced;
    }

    public String getDiscontinued() {
        return this.discontinued;
    }

    public void setDiscontinued(String discontinued) {
        this.discontinued = discontinued;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
