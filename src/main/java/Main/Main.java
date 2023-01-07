package Main;

import configuration.SpringConfig;
import entity.OrderDetails;
import entity.Orders;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import repository.OrderDetailRepository;
import repository.OrderRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    static ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
    static OrderRepository orderRepository = context.getBean("orderRepository", OrderRepository.class);
    static OrderDetailRepository orderDetailRepository = context.getBean("orderDetailRepository", OrderDetailRepository.class);

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean checkStop = false;
        do {
            showMenu();
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    createNewOrder();
                    break;
                case 2:
                    createNewOrderDetail();
                    break;
                case 3:
                    listAllOrderAndOrderDetail();
                    break;
                case 4:
                    ListAllOrdersCurrentMonth();
                    break;
                case 5:
                    listAllOrderTotalAmoutThan1000();
                    break;
                case 6:
                    listAllOrderByProductName();
                    break;
                case 7:
                    checkStop = true;
                    System.out.println("Thoát Chương Trình");
                    break;

            }
        } while (checkStop == false);

    }

    private static void showMenu() {
        System.out.println("== MENU ==");
        System.out.println("1:Create new order");
        System.out.println("2:Create new orderDetail");
        System.out.println("3: List all order and order details in the database");
        System.out.println("4:List all the orders in the current month");
        System.out.println("5:List all orders which have total amount more than 1,000USD");
        System.out.println("6:List all orders buy Java book");
        System.out.println("7: Thoát chương trình");

    }

    public static Orders insertNewOrder() {
        Scanner sc = new Scanner(System.in);
        Orders orders = new Orders();
        System.out.println("Enter để nhập tên: ");
        orders.setCustomerName(sc.nextLine());
        System.out.println("Enter để nhập địa chỉ: ");
        orders.setCustomerAddress(sc.nextLine());
        orders.setOrderDate(LocalDate.now());
        orderRepository.save(orders);
        return orders;
    }

    public static OrderDetails insertNewOrderDetail() {
        Scanner sc = new Scanner(System.in);
        OrderDetails orderDetails = new OrderDetails();
        System.out.println("Enter để nhập tên sản phẩm: ");
        orderDetails.setProductName(sc.nextLine());
        System.out.println("Enter để nhập giá: ");
        orderDetails.setUnitPrice(sc.nextInt());
        System.out.println("Enter để nhập số lượng: ");
        orderDetails.setQuantity(sc.nextInt());
        return orderDetails;
    }

    public static void createNewOrder() {
        Orders orders = insertNewOrder();
        orderRepository.save(orders);
        System.out.println("TẠO THÀNH CÔNG NewOrder");
    }

    public static void createNewOrderDetail() {
        Scanner sc = new Scanner(System.in);
        OrderDetails orderDetails = insertNewOrderDetail();
        System.out.println("Nhập ID order = ");
        Optional<Orders> order = orderRepository.findById(sc.nextInt());
        orderDetails.setOrders(order.get());
        orderDetailRepository.save(orderDetails);
        System.out.println("TẠO THÀNH CÔNG NewOrderDetail");
    }

    public static void listAllOrderAndOrderDetail() {
        List<Orders> ordersList = orderRepository.findAll();
        for (Orders order : ordersList) {
            System.out.println(order.toString());
            List<OrderDetails> orderDetailsList = orderDetailRepository.findByOrderId(order.getId());
            for (OrderDetails orderDetails : orderDetailsList)
                System.out.println(orderDetails.toString());
            ;
        }
    }

    public static void ListAllOrdersCurrentMonth() {
        List<Orders> ordersList = orderRepository.ListAllOrderMonth();
        for (Orders orders : ordersList)
            System.out.println(orders.toString());
        ;
    }

    public static void listAllOrderTotalAmoutThan1000() {
        List<Orders> ordersList = orderRepository.ListAllOrderOver1000();
        for (Orders orders : ordersList)
            System.out.println(orders.toString());
        ;
    }

    public static void listAllOrderByProductName() {
        List<Orders> ordersList = orderRepository.ListAllOrderByProductName();
        for (Orders orders : ordersList)
            System.out.println(orders.toString());
        ;
    }

}
