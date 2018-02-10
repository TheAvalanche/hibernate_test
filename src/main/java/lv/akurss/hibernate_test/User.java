package lv.akurss.hibernate_test;

import javax.persistence.*;
import java.util.List;

@SequenceGenerator(name = "seq_gen", sequenceName = "users_seq")
@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(generator = "seq_gen", strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "surname")
	private String surname;
	
	@Column(name = "login")
	private String login;
	
	@Column(name = "password")
	private String password;
	
	@OneToMany(mappedBy = "user", 
				fetch = FetchType.LAZY,
				orphanRemoval = true,
				cascade = CascadeType.ALL)
	private List<Task> tasks;

	public User() {
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
		//this.tasks.forEach(t -> t.setUser(this));
	}
}
