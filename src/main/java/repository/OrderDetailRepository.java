package repository;

import entity.OrderDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends CrudRepository<OrderDetails, Integer> {
    @Query(value = "select * from orderdetails where orderId = ?1", nativeQuery = true)
    List<OrderDetails> findByOrderId(int id);
}
