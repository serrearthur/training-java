package model;

import java.util.ArrayList;
import java.util.List;

public class Company {
	private Integer id;
	private String name;

	public Company() {
	}

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
	
//	public List<String> toArray() {
//		List<String> ret = new ArrayList<String>();
//		ret.add(this.id.toString());
//		ret.add(this.name);
//		return ret;
//	}
}