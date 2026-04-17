package PlanetMel.service;

import PlanetMel.domain.Customer;
import PlanetMel.domain.Order;
import PlanetMel.domain.Product;
import PlanetMel.repo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vaadin.flow.component.Component;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    public Order placeOrder(Customer customer, Product product, int quantity, String shippingAddress) {
        Order order = new Order(customer, product, quantity);
        order.setShippingAddress(shippingAddress);
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}