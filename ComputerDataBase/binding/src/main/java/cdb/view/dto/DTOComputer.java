package cdb.view.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

/**
 * DTO representation of a {@link cdb.model.Computer}.
 * @author aserre
 */
public class DTOComputer {
    private String id;

    @NotBlank(message = "Computer name can't be empty")
    private String name;

    @Nullable
    @DateTimeFormat(pattern = "YYYY-MM-dd")
    private String introduced;

    @Nullable
    @DateTimeFormat(pattern = "YYYY-MM-dd")
    private String discontinued;

    @Nullable
    private String companyName;

    @Nullable
    @Min(value = 0, message = "Id must be a positive integer")
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

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
