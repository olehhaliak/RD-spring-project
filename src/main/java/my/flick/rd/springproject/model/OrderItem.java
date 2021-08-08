package my.flick.rd.springproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class OrderItem {
    @Id
    private long id;
    private long orderId;//@Todo: replace with Order object
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private Product product;
    private int quantity;
}
