package za.ac.cput.factory;

import za.ac.cput.domain.Order;
import za.ac.cput.domain.User;
import za.ac.cput.util.OrderHelper;

import java.time.LocalDate;

public class OrderFactory {

    public static Order buildOrder(User user, LocalDate orderDate, String status) {
        if (user == null ||
                OrderHelper.isNullOrEmpty(status) ||
                orderDate == null) {
            return null;
        }
        return new Order.Builder()
                .setUser(user)
                .setOrderDate(orderDate)
                .setStatus(status)
                .build();
    }
}
