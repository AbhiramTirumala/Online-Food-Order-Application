package onlineFoodOrder;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import onlineFoodOrder.HibernateUtil;
import onlineFoodOrder.entity.Order;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("unused")
public class OrderService {

	@SuppressWarnings("deprecation")
	public void saveOrder(Order order) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.save(order);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public void getOrderById(Scanner scanner) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Order order = new Order();
			System.out.println("Enter Order ID");
			Order retreivedorder = session.get(Order.class, scanner.nextInt());
			System.out.println(retreivedorder.getOrderId() + " " + retreivedorder.getUser() + " "
					+ retreivedorder.getTotalAmount() + " " + retreivedorder.getOrderDate());
		}
	}

	@SuppressWarnings("deprecation")
	public void updateOrder(Scanner scanner) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Order order = new Order();
			session.update(order);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public void deleteOrder(Scanner scanner) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			System.out.println("Enter OrderId: ");
			int orderId=scanner.nextInt();
			Order order = session.get(Order.class, orderId);
			if (order != null) {
				session.delete(order);
				transaction.commit();
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public void getAllOrders(String email,Scanner scanner) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			int userid = getuserId(session, email);
			Query q=session.createQuery("from Order where user.userId=:Id");
			q.setParameter("Id", userid);
			List l=q.list();
			System.out.println(l);
		}

	}

	public void Order(String email, Scanner scanner) {
		@SuppressWarnings("unused")
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			
			System.out.println("Do u want to place order from the above Menu?(Y/N)");
			char c = scanner.next().charAt(0);
			if (c == 'Y' || c == 'y') {
				
				int userid = getuserId(session, email);
				insertorder(session, userid);
				
				int orderid = orderid(session, userid);
				
				System.out.println("Enter ItemID Which you want to Order: ");
				int itemid = scanner.nextInt();
				System.out.println("Enter Quantity of the Food Item: ");
				int quant = scanner.nextInt();
				
				insertorderdetail(session, orderid, itemid, quant);
				
				float price = getitemprice(session, itemid);
				float totamt = price * quant;
				
				updateorder(session, orderid, totamt);
			}
		}
	}

	public static int getuserId(Session session, String email) {
		@SuppressWarnings({ "deprecation", "rawtypes" })
		Query query = session.createQuery("select userId from User where email=:email");
		query.setParameter("email", email);
		@SuppressWarnings("rawtypes")
		List q = query.list();
		return (int) q.get(0);

	}

	@SuppressWarnings("deprecation")
	public static void insertorder(Session session, int userid) {
		String sql = String.format("INSERT INTO orders(userId,totalAmount,orderDate) VALUES(%s,0,now())", userid);
		session.createNativeQuery(sql).executeUpdate();
	}

	public static int orderid(Session session, int userid) {
		@SuppressWarnings({ "deprecation", "rawtypes" })
		Query query1 = session.createQuery("select o.orderId from Order o where o.user.userId=:Id");
		query1.setParameter("Id", userid);
		List q1 = query1.list();
		return (int) q1.get(0);
	}

	@SuppressWarnings("deprecation")
	public static void insertorderdetail(Session session, int orderid, int itemid, int quant) {
		String sql1 = String.format("INSERT INTO OrderDetails(orderId,itemId,quantity) VALUES(%s,%s,%s)", orderid,
				itemid, quant);
		session.createNativeQuery(sql1).executeUpdate();
	}

	public static float getitemprice(Session session, int itemid) {
		@SuppressWarnings({ "deprecation", "rawtypes" })
		Query query2 = session.createQuery("select itemPrice from FoodItems where itemId=:itemId");
		query2.setParameter("itemId", itemid);
		List q2 = query2.list();
		return (float) q2.get(0);
	}

	public static void updateorder(Session session, int orderid, float totamt) {
		@SuppressWarnings({ "deprecation", "rawtypes" })
		Query query3 = session.createQuery("update Order o set o.totalAmount=:totalAmount where o.orderId=:orderId");
		query3.setParameter("orderId", orderid);
		query3.setParameter("totalAmount", totamt);
		query3.executeUpdate();
	}
}
