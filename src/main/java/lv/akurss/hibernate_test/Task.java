package lv.akurss.hibernate_test;

import javax.persistence.*;

@SequenceGenerator(name = "seq_gen", sequenceName = "tasks_seq")
@Entity
@Table(name="tasks")
public class Task {
	
	@Id
	@GeneratedValue(generator = "seq_gen", strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "title")	
	private String title;
	
	@Column(name = "done")
	private boolean done;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	private Task() {
	}
	
	public Task(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Task{" +
				"id=" + id +
				", title='" + title + '\'' +
				", done=" + done +
				", user=" + user +
				'}';
	}
}
