package mockito.mockitolearn;

// interface used to get the available credit limit for a given credit card number
public interface CreditLimitProvider {
	// return credit limit for the given credit card number in Rupees
	// Note : In this assignment , this interface needs to be mocked using mockito. No real implementation required
	int GetAvailableCreditLimit (String creditCardNumber); 
	
}
