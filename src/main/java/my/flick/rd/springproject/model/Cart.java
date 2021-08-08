package my.flick.rd.springproject.model;

import my.flick.rd.springproject.exception.OrderItemNotFoundException;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Cart {//todo: make interface of it
    public Cart() {
        items = new HashMap<>();
    }

    private final Map<Long, OrderItem> items;

    public void saveItem(OrderItem item) {
        items.put(item.getProduct().getId(), item);
    }

    public void deleteItem(long productId) {
        if (!items.containsKey(productId)) {
            throw new OrderItemNotFoundException("Cart does not contains item with such id");
        }
        items.remove(productId);
    }

    public void clear() {
        items.clear();
    }

    public Set<OrderItem> getItems() {
        return (Set<OrderItem>) items.values();
    }

   public Set<OrderItem> pop(){
       Set<OrderItem> set = (Set<OrderItem>) items.values();
       items.clear();
       return set;
   }
}
