package my.flick.rd.springproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.flick.rd.springproject.model.enums.Status;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "Receipt")
//@Table(name = "receipt")
public class Order {
    @Id
    @GeneratedValue
    long id;

    @ManyToOne
    User customer;

    @Enumerated(EnumType.STRING)
    Status status;

    @OneToMany(fetch = FetchType.LAZY,orphanRemoval = true,cascade = CascadeType.ALL)
            @JoinColumn(name = "item_id")
    Set<OrderItem> items;

    @CreationTimestamp
    @Column(updatable = false)
    LocalDateTime creationTime;

    @UpdateTimestamp
    LocalDateTime updateTime;
    @PrePersist
    public void onInsert() {
        LocalDateTime now = LocalDateTime.now();
        creationTime = now;
        updateTime = now;
    }

    @PreUpdate
    public void onUpdate() {
        updateTime = LocalDateTime.now();
    }
}
