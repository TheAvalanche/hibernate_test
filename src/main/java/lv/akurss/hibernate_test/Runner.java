package lv.akurss.hibernate_test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Runner {

	public static void main(String[] args) {
		SessionFactory sessionFactory = new Configuration()
										.configure() //takes settings from hibernate.cfg.xml
										.buildSessionFactory();
		
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		User user = new User();
		
		user.setName("John");
		user.setSurname("Doe");
		user.setLogin("john111");
		user.setPassword("123456");
		
		Task task1 = new Task(user);
		task1.setTitle("Do homework");
		task1.setDone(false);
		
		Task task2 = new Task(user);
		task2.setTitle("Sleep");
		task2.setDone(true);

		List<Task> tasks = new ArrayList<>();
		tasks.add(task1);
		tasks.add(task2);
		user.setTasks(tasks);
		
		session.save(user);
		
		tx.commit();
		
		session.close();
		
		
		//================================
		
		Session session1 = sessionFactory.openSession();
		
		Task t = session1.get(Task.class, 1L);
		System.out.println("Found: " + t.getTitle());
		
		Query<Task> q = session1.createQuery("from Task where done = :done");
		q.setParameter("done", true);
		List<Task> qTasks = q.list();
		
		qTasks.forEach(System.out::println);
		session1.close();
		
		//=======================================
		
		Session session2 = sessionFactory.openSession();
		Transaction tx2 = session2.beginTransaction();
		
		Optional<Task> oTask = session2.get(User.class, 1L).getTasks()
													.stream()
													.filter(it -> !it.isDone())
													.findFirst();
		oTask.ifPresent(it -> {
			it.setDone(true);
			session2.update(it);
		});
		
		tx2.commit();
		session2.close();
		
		//======================================
		//Lazy Exception try on

		Session session3 = sessionFactory.openSession();

		User u = session3.get(User.class, 1L);
		System.out.println(u.getName());
		u.getTasks().size(); //how to fix LazyInitializationException

		session3.close();
		System.out.println(u.getTasks().size());
		
		//==========================================
		//detached entity error
		Session session4 = sessionFactory.openSession();
		Transaction tx4 = session4.beginTransaction();
		
		User u2 = session4.get(User.class, 1L);
		
		User copy = new User();
		copy.setName(u2.getName());
		copy.setId(u2.getId());
		copy.setLogin("johndoe2222");
		copy.setPassword("321654");
		
		copy.setId(null); //fix of detached entity passed to persist
		
		session4.persist(copy);
		tx4.commit();
		session4.close();
		
	}
	
}
