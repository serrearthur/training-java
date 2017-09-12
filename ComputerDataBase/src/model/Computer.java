package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Computer {
	private Integer id;
	private String name;
	private Date introduced;
	private Date discontinued;
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

	public Date getIntroduced() {
		return introduced;
	}

	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}

	public Date getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
//	public List<String> toArray() {
//		List<String> ret = new ArrayList<String>();
//		ret.add(this.id.toString());
//		ret.add(this.name);
//		ret.add(this.introduced.toString());
//		ret.add(this.discontinued.toString());
//		ret.add(this.companyId.toString());
//		return ret;
//	}
}