package onlineFoodOrder;

import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Main extends HibernateUtil {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int user = 0, fooditem = 0, order = 0, orderdetails = 0, app = 0, log = 0;
		String email = null;
		UserService userservice = new UserService();
		System.out.println("!!!!!!!!!Welcome To Online Food Order Application!!!!!!!!!\n1.Create Account\n2.Login");
		switch (sc.nextInt()) {
		case 1: {
			userservice.saveUser(sc);
			email = userservice.Login(sc);
		}
			break;
		case 2:
			email=userservice.Login(sc);
			break;
		}
		while (app == 0 && log == 0) {
			System.out.println(
					"Choose One of our Services\n1.UserServices\n2.FoodItemsServices\n3.OrderServices\n4.OrderDetailsServices\n5.Exit\n"
							+ "Please Enter Your Choice :");
			switch (sc.nextInt()) {

			case 1: {
				UserService userservice1 = new UserService();
				while (user == 0) {
					System.out.println("1.GetDetails\n2.UpdateDetails\n3.GO Back\nEnter your Choice: ");
					switch (sc.nextInt()) {

					case 1:
						userservice1.getUserById(sc);
						break;

					case 2:
						userservice1.updateUser(sc);
						break;

					case 3:
						user++;
						break;

					default:
						System.out.println("Enter Correct Choice");
					}
				}
			}
				break;

			case 2: {
				FoodItemsService fooditemservice = new FoodItemsService();
				OrderService orderservice = new OrderService();
				while (fooditem == 0) {
					System.out.println("1.Display Menu\n2.Display Food Item by ID\n3.Go Back");
					switch (sc.nextInt()) {

					case 1:
						fooditemservice.getAllFoodItems();
						orderservice.Order(email, sc);
						break;

					case 2:
						fooditemservice.getFoodItemById(sc);
						break;
					case 3:fooditem++;
						break;
					default:
						System.out.println("Enter Correct Choice");
					}
				}
			}
				break;

			case 3: {
				OrderService orderservice = new OrderService();
				OrderDetailService orderdetailservice = new OrderDetailService();
				while (order == 0) {
					System.out.println("1.Get Order by ID\n2.Update Order\n3.Cancel Order\n4.Get All Orders\n5.Go Back");
					switch (sc.nextInt()) {

					case 1:
						orderservice.getOrderById(sc);
						break;

					case 2:
						orderdetailservice.updateOrderDetail(sc);
						break;

					case 3:
						orderservice.deleteOrder(sc);
						break;

					case 4:
						orderservice.getAllOrders(email,sc);
						break;

					case 5:
						order++;
						break;

					default:
						System.out.println("Enter Correct Choice");
					}
				}
			}
				break;

			case 4: {
				OrderDetailService orderdetailservice = new OrderDetailService();
				while (orderdetails == 0) {
					System.out.println("1.Get Order Details by ID\n2.Get All Order Details\n3.Go Back");
					switch (sc.nextInt()) {

					case 1:
						orderdetailservice.getOrderDetailById(orderdetails);
						break;

					case 2:
						orderdetailservice.getAllOrderDetails();
						break;

					case 3:
						orderdetails++;
						break;

					default:
						System.out.println("Enter correct Choice");
					}
				}
			}
				break;

			case 5:
				app++;
				break;

			default:
				System.out.println("Enter Correct choice");
			}
		}

	}
}
