package onlineFoodOrder;

import org.hibernate.Session;
import org.hibernate.Transaction;

import onlineFoodOrder.HibernateUtil;
import onlineFoodOrder.entity.FoodItems;

import java.util.List;
import java.util.Scanner;

public class FoodItemsService {

	public void saveFoodItem(FoodItems foodItem) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.save(foodItem);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public void getFoodItemById(Scanner scanner) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			FoodItems fooditem = new FoodItems();
			System.out.println("Enter item ID: ");
			FoodItems retreiveditem = session.get(FoodItems.class, scanner.nextInt());
			System.out.println(
					retreiveditem.getItemId() + " " + retreiveditem.getItemName() + " " + retreiveditem.getItemPrice());
		}
	}

	public void updateFoodItem(FoodItems foodItem) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.update(foodItem);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public void deleteFoodItem(int itemId) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			FoodItems foodItem = session.get(FoodItems.class, itemId);
			if (foodItem != null) {
				session.delete(foodItem);
				transaction.commit();
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public void getAllFoodItems() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			List l = session.createQuery("from FoodItems", FoodItems.class).list();
			System.out.println(l);
		}
	}
}
