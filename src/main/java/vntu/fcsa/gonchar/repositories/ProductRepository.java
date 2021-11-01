package vntu.fcsa.gonchar.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import vntu.fcsa.gonchar.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {


}
