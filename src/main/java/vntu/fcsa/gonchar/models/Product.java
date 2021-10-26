package vntu.fcsa.gonchar.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    //fix validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    //  @NotEmpty(message = "Name shouldn`t be empty")
    //  @Size(min = 2, max = 30, message = "Name shouldn`t be too short or long")
    @Column(name = "name")
    String name;

    // @NotNull
    // @NotEmpty(message = "Weight shouldn`t be empty")
    // @Min(value = 0, message = "Weight should be greater than 0")
    // @Column(name = "weight")
    double weight;

    //    @NotNull
//    @NotEmpty(message = "Cost shouldn`t be empty")
//    @Min(value = 0, message = "Cost should be greater than 0")
    @Column(name = "price")
    double price;

//    @NotEmpty(message = "Type shouldn`t be empty")
//    @Column(name = "type")
    String type;

    public Product(String name, double weight, double price, String type) {
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.type = type;
    }



}
