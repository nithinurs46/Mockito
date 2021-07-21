package mockito.mockitolearn;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Assignment : Unit tests have to be written for this class
public class CreditCardClientService {

	// Return values
	//      0 - If the operation is success - credit card is valid and amount is within credit limit
	//      1 - invalid Credit card number format (each digit must be numeric for the card number 
	//			to be of correct format and must be exactly 16 digits)
	//      2 -invalid amount (if negative or zero)
	//      3 - invalid month (must be 1-12)
	//      4 - invalid year (must be an integer between 2000 and 2100)
	//
	//     11 - Invalid credit card information (number does not exist, invalid year/month/cvv etc )
	//     12 - amount is more than the remaining credit limit (i.e. request should not be honored)
	//
	public int CheckCreditCardAndCredit (CreditCardVerificationService cvvService, 
											CreditLimitProvider creditLimitProvider,
											String creditCardNumber, 
											int monthExpiry, 
											int yearExpiry, 
											String cvv, 
											int amount)
	{
		//
		// TODO: Insert code to validate the input data here (return values 0 to 4)
		//
		if (creditCardNumber == null
				|| (creditCardNumber.length() != 16 || !checkIfOnlyDigitExists(creditCardNumber))) {
			return 1; // card number is invalid, as only digits are not present.
		}

		if (amount <= 0) {
			return 2;// Invalid amount
		}

		if (monthExpiry < 1 || monthExpiry > 12) {
			return 3; // Invalid month
		}

		if (yearExpiry < 2000 || yearExpiry > 2100) {
			return 4; // Invalid year
		}
		////
		boolean isValidCreditCard = cvvService.VerifyCreditCard(creditCardNumber, monthExpiry, yearExpiry, cvv);
		if (!isValidCreditCard)
			return 11;  // credit card is not valid
		
		int creditLimit = creditLimitProvider.GetAvailableCreditLimit(creditCardNumber);
		if (amount > creditLimit)
			return 12; // exceeded credit limit
		
		return 0; // Success - the credit card is valid and available credit limit is above the requested amount
	}
	
	
	private boolean checkIfOnlyDigitExists(String value) {
		String regex = "[0-9]+";
		if (value == null || value.isEmpty()) {
			return false;
		}
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(value);
		return m.matches();
	}
	
}
