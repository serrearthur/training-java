package cdb.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Model class implementing a Computer.
 * @author aserre
 */
@Entity
@Table(name = "computer")
public class Computer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "introduced")
    private LocalDateTime introduced;
    @Column(name = "discontinued")
    private LocalDateTime discontinued;
    @Column(name = "company_id")
    private Integer companyId;

    /**
     * Constructor.
     */
    public Computer() {
    }

    /**
     * Constructor.
     * @param name name of the computer
     */
    public Computer(String name) {
        this.name = name;
    }

    /**
     * Constructor.
     * @param id id of the computer
     * @param name name of the computer
     */
    public Computer(int id, String name) {
        this.id = id;
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

    public LocalDateTime getIntroduced() {
        return introduced;
    }

    public void setIntroduced(LocalDateTime introduced) {
        this.introduced = introduced;
    }

    public LocalDateTime getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(LocalDateTime discontinued) {
        this.discontinued = discontinued;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}