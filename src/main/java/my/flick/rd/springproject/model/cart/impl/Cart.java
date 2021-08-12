package my.flick.rd.springproject.model.cart.impl;

import my.flick.rd.springproject.exception.OrderItemNotFoundException;
import my.flick.rd.springproject.model.OrderItem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public interface Cart {

    public void saveItem(OrderItem item) ;

    public void deleteItem(long productId) ;

    public void clear() ;

    public Set<OrderItem> getItems() ;
}
