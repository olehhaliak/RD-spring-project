package my.flick.rd.springproject.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private BigDecimal price;
    private String description;
    private int quantity;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime creationTime;
    @UpdateTimestamp
    private LocalDateTime updateTime;
}
