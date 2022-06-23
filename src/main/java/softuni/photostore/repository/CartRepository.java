package softuni.photostore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.photostore.model.entity.CartItem;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartItem, String> {

    List<CartItem> getAllByCustomerId(String customerId);

    @Query("select c from CartItem c where c.customerId = 'anonymousUser' and c.customerIP = :ip")
    List<CartItem> getAllByCustomerIP(@Param("ip") String customerIP);

    Optional<CartItem> getCartItemByCustomerIdAndCustomerIPAndProductId(String customerId, String customerIP, String productId);

    @Query("select c from CartItem c where c.dateAdded <= :dateAdded")
    List<CartItem> findAllByDateAddedBefore(@Param("dateAdded") LocalDateTime dateAdded);

}
