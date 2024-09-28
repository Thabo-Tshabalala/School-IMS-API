package za.ac.cput.factory;

import za.ac.cput.domain.Request;
import za.ac.cput.domain.Product;
import za.ac.cput.util.RequestHelper;

public class RequestFactory {

    public static Request buildRequest(Product product, int quantity, String status) {
        if (product == null ||
                RequestHelper.hasValidProduct(product) ||
                !RequestHelper.isValidQuantity(quantity) ||
                !RequestHelper.isValidStatus(status)) {
            return null;
        }

        return new Request.Builder()
                .setProduct(product)
                .setQuantity(quantity)
                .setStatus(status)
                .build();
    }
}
