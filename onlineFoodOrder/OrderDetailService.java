package onlineFoodOrder;

import org.hibernate.Session;
import org.hibernate.Transaction;

import onlineFoodOrder.HibernateUtil;
import onlineFoodOrder.entity.FoodItems;
import onlineFoodOrder.entity.OrderDetail;

import java.util.List;
import java.util.Scanner;

public class OrderDetailService {

	public void saveOrderDetail(OrderDetail orderDetail) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.save(orderDetail);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public OrderDetail getOrderDetailById(int orderDetailId) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.get(OrderDetail.class, orderDetailId);
		}
	}

	public void updateOrderDetail(Scanner scanner) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			OrderDetail orderDetail = new OrderDetail();
			int b = 0;
			while (b == 0) {
				System.out.println("1.Add FoodItem\n2.Delete FoodItem\n3.Update Quantity\n4.Exit");
				switch (scanner.nextInt()) {
				
				case 1: {
					System.out.println("Enter Order ID: ");
					OrderDetail retreivedorderdetail = session.get(OrderDetail.class, scanner.nextInt());
					System.out.println("Enter ID of the FoodItem you want to Add: ");
					FoodItems retreivedfooditem = session.get(FoodItems.class, scanner.nextInt());
					retreivedorderdetail.setFoodItems(retreivedfooditem);
					session.save(retreivedorderdetail);

				}
					break;
					
				case 2: {
					FoodItemsService fooditemsservice = new FoodItemsService();
					System.out.println("Enter the ID of the FoodItem to be Deleted: ");
					fooditemsservice.deleteFoodItem(scanner.nextInt());
				}
					break;
					
				case 3: {
					// FoodItemsService fooditemsservice1=new FoodItemsService();
					System.out.println("Enter the ID of the FoodItem for which Quantity Should be Updated: ");
					OrderDetail retreivedorderdetail1 = session.get(OrderDetail.class, scanner.nextInt());
					System.out.println("Enter the New Quantity for the Item: ");
					retreivedorderdetail1.setQuantity(scanner.nextInt());
					session.save(retreivedorderdetail1);
				}
					break;
					
				case 4:
					b++;
					break;
					
				default:
					System.out.println("Enter Correct Choice");
				}
			}
			session.update(orderDetail);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public void deleteOrderDetail(int orderDetailId) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			OrderDetail orderDetail = session.get(OrderDetail.class, orderDetailId);
			if (orderDetail != null) {
				session.delete(orderDetail);
				transaction.commit();
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public List<OrderDetail> getAllOrderDetails() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from OrderDetail", OrderDetail.class).list();
		}
	}
}
