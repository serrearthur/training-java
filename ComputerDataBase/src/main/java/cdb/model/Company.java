package cdb.model;

/**
 * Model class implementing a Company.
 * @author aserre
 */
public class Company {
    private Integer id;
    private String name;

    /**
     * Constructor.
     */
    public Company() {
    }

    /**
     * Constructor.
     * @param name name of the company
     */
    public Company(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}