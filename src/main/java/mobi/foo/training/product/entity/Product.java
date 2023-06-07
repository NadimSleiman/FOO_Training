package mobi.foo.training.product.entity;



import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;





@Table(name = "product")
@Entity
@Data
public class Product {


    @Id
    @GeneratedValue
    private long id;

    @NotNull(message = "Product Name cannot be null")
    @Size(min=1 , max = 30, message="Product name must be between 1 and 30" )
    private String productName;


}
