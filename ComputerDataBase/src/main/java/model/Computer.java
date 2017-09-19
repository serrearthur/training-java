package model;

import java.time.LocalDateTime;

public class Computer {
    private Integer id;
    private String name;
    private LocalDateTime introduced;
    private LocalDateTime discontinued;
    private Integer companyId;

    public Computer() {
    }

    public Computer(String name) {
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