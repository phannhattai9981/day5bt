package repository;

import entity.Orders;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Orders, Integer> {
    List<Orders> findAll();
    @Query(value = "select * from orders where month(orderDate) = month(CURDATE())", nativeQuery = true)
    List<Orders> ListAllOrderMonth();
    @Query(value = "select * from orders o join orderdetails od on o.id = od.orderId group by o.id having sum(od.quantity*od.unitPrice)>1000", nativeQuery = true)
    List<Orders> ListAllOrderOver1000();
    @Query(value = "select * from orders o inner join orderdetails od on o.id = od.orderId where od.productName =  'java'", nativeQuery = true)
    List<Orders> ListAllOrderByProductName();

}
