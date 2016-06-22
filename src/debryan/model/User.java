package debryan.model;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table(value="user")
public class User {

	@PrimaryKey("email")
	private String email;
	
	@Column(value="password")
	private String password;
	
	@Column(value="name")
	private String name;
	
	@Column(value="phone")
	private Long phone;
	
	public boolean real;
	
	public User() {
		
	}
	
	public User(String email, String password, String name, Long phone) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

}
