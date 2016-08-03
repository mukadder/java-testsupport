package futures;

public class OrderProcessor {

    CustomerLookupService customerLookupService = 
            new CustomerLookupService();
    ProductLookupService productLookupService = 
            new ProductLookupService();
    PaymentService paymentService = 
            new PaymentService();

    public static void main(String[] args) {
        new OrderProcessor().validateOrder(14L, 21L);
    }

    private Boolean validateOrder(Long customerId, Long productId) {
        return Try.of(customerLookupService::lookup, customerId)
                .flatMap(customerFound ->
                        Try.of(productLookupService::lookup, productId))
                .flatMap(price ->
                        Try.of(paymentService::pay, customerId, price))
                .recover(Function.identity(), this::handleFailure);
    }

    private Boolean handleFailure(Throwable throwable) {
        System.out.println(
                "Could not process order because of " + 
                        throwable.getCause());
        return Boolean.FALSE;
    }
}

class CustomerLookupService {
    Boolean lookup(Long customerId) throws Exception { 
        return Boolean.TRUE; 
    }
}

class ProductLookupService {
    Long lookup(Long productId) throws Exception { 
        return 50L; 
    }
}

class PaymentService {
    Boolean pay(Long customerId, Long price) throws Exception { 
        return Boolean.TRUE;
    }
}