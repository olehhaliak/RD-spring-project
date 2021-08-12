package my.flick.rd.springproject.model.cart;

import my.flick.rd.springproject.exception.OrderItemNotFoundException;
import my.flick.rd.springproject.model.OrderItem;
import my.flick.rd.springproject.model.cart.impl.Cart;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartImpl implements Cart {

    public CartImpl() {
        items = new HashMap<>();
    }

    private final Map<Long, OrderItem> items;

    @Override
    public void saveItem(OrderItem item) {
        items.put(item.getProductId(), item);
        System.out.println(Arrays.toString(items.values().toArray()));
    }

    @Override
    public void deleteItem(long productId) {
        if (!items.containsKey(productId)) {
            throw new OrderItemNotFoundException("Cart does not contains item with such id");
        }
        items.remove(productId);
    }

    @Override
    public void clear() {
        items.clear();
    }

    @Override
    public Set<OrderItem> getItems() {
        return new HashSet<>(items.values());
    }
}
