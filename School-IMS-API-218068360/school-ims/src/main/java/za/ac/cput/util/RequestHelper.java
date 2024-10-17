package za.ac.cput.util;
import za.ac.cput.domain.Product;

public class RequestHelper {

    public static boolean isValidQuantity(int quantity) {
        return quantity > 0;
    }

    public static boolean hasValidProduct(Product product) {
        return product != null && product.getProductId() > 0;
    }

    public static boolean isValidStatus(String status) {
        return status != null && (status.equalsIgnoreCase("Pending") ||
                status.equalsIgnoreCase("Approved") ||
                status.equalsIgnoreCase("Rejected"));
    }
}
