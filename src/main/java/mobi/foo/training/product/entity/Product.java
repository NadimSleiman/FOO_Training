package mobi.foo.training.product.entity;



import jakarta.persistence.*;
import lombok.Data;


@Table(name = "product")
@Entity
@Data
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private String productName;


}
