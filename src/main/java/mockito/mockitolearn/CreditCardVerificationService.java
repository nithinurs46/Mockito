package mockito.mockitolearn;
// interface used to verify the credit 
public interface CreditCardVerificationService {
	// returns if the credit card number + month + year + cvv combination is valid. Returns false if any of them is invalid
	// Note : In this assignment , this interface needs to be mocked using mockito. No real implementation required
	boolean VerifyCreditCard (String creditCardNumber, int month, int year, String cvv);
}
