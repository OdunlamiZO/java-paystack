package odunlamizo.paystack;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PaystackException extends RuntimeException {

    private static final long serialVersionUID = 9002135571226598881L;

    public PaystackException(String message) {
        super(message);
    }

    public PaystackException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
