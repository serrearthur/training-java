package cdb.view.dto;

/**
 * DTO representation of a {@link cdb.model.Company}.
 * @author aserre
 *
 */
public class DTOCompany {
    String id;
    String name;

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
