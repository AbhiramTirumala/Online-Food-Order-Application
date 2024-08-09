package onlineFoodOrder;

import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import onlineFoodOrder.entity.User;

public class UserService {

	public void saveUser(Scanner scanner) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			User user = new User();
			System.out.println("Enter UserName,Email,Password: ");
			user.setUsername(scanner.next());
			user.setEmail(scanner.next());
			user.setPassword(scanner.next());
			session.save(user);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public String Login(Scanner scanner) {
		System.out.println("Enter Registered Email ID and Password to Login :");
		String email = scanner.next();
		String passkey = scanner.next();
		return email;
	}

	public void getUserById(Scanner scanner) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			User user = new User();
			System.out.println("Enter user ID: ");
			User retrieveduser = session.get(User.class, scanner.nextInt());
			System.out.println(retrieveduser.getUserId() + " " + retrieveduser.getUsername() + " "
					+ retrieveduser.getEmail() + " " + retrieveduser.getPassword());
		}

	}

	public void updateUser(Scanner scanner) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			int i = 0;
			User user = new User();
			while (i == 0) {
				System.out.println("1.UserName\n2.Email\n3.Password\n4.Exit\nEnter your Choice: ");
				int n = scanner.nextInt();
				switch (n) {
				case 1: {
					// Query createQuery = session.createQuery("update users u set
					// u.username=:username where u.userId=:userId");
					System.out.println("Enter User Id: ");
					User retrieveduser = session.get(User.class, scanner.nextInt());
					// createQuery.setParameter("userId", scanner.nextInt());
					System.out.println("Enter New Username:");
					// createQuery.setParameter("username", scanner.next());
					retrieveduser.setUsername(scanner.next());
					// session.save(user);
					session.getTransaction().commit();
				}
					break;
				case 2: {
					System.out.println("Enter User Id: ");
					User retrieveduser = session.get(User.class, scanner.nextInt());
					System.out.println("Enter New Email: ");
					retrieveduser.setEmail(scanner.next());
					session.getTransaction().commit();

				}
					break;
				case 3: {
					System.out.println("Enter User Id: ");
					User retrieveduser = session.get(User.class, scanner.nextInt());
					System.out.println("Enter New Password: ");
					retrieveduser.setPassword(scanner.next());
					session.getTransaction().commit();

				}
					break;
				case 4:
					i++;
					break;
				default:
					System.out.println("Enter correct choice");
				}
			}
		}
	}
}
