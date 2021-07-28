package my.flick.rd.hw3.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
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
