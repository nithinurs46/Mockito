package mockito.mockitolearn;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.mockito.BDDMockito.given;

public class CreditCardClientServiceTest {

	CreditCardClientService clientSvc = null;
	CreditCardVerificationService creditCardVerificationServiceMock = null;
	CreditCardClientService creditCardClientSvcMock = null;
	CreditLimitProvider creditLimitProviderMock = null;

	@Before
	public void init() {
		clientSvc = new CreditCardClientService();
		creditCardVerificationServiceMock = mock(CreditCardVerificationService.class);
		creditLimitProviderMock = mock(CreditLimitProvider.class);
	}

	// CreditCardVerificationService cvvService,
	// CreditLimitProvider creditLimitProvider,
	// String creditCardNumber,
	// int monthExpiry,
	// int yearExpiry,
	// String cvv,
	// int amount

	@Test
	public void testForInValidCreditCardNo_nonDigits() {
		assertEquals(1, clientSvc.CheckCreditCardAndCredit(creditCardVerificationServiceMock, creditLimitProviderMock,
				"ABCDEFGHIJKLMNOP", 0, 0, null, 0));
	}

	@Test
	public void testForInValidCreditCardNo_DigitsMoreThen16() {
		assertEquals(1, clientSvc.CheckCreditCardAndCredit(creditCardVerificationServiceMock, creditLimitProviderMock,
				"123456789123456789", 0, 0, null, 0));
	}

	@Test
	public void testForInValidCreditCardNo_DigitsLessThen16() {
		assertEquals(1, clientSvc.CheckCreditCardAndCredit(creditCardVerificationServiceMock, creditLimitProviderMock,
				"178987655109876", 0, 0, null, 0));
	}

	@Test
	public void testForInValidCreditCardNo_16SplChars() {
		assertEquals(1, clientSvc.CheckCreditCardAndCredit(creditCardVerificationServiceMock, creditLimitProviderMock,
				"*&^%$#@!)(*&^%$#", 0, 0, null, 0));
	}

	@Test
	public void testForValidCreditCardNoAndInvalidAmt() {
		assertEquals(2, clientSvc.CheckCreditCardAndCredit(creditCardVerificationServiceMock, creditLimitProviderMock,
				"9876567809871546", 0, 0, null, 0));
	}

	@Test
	public void testForInvalidAmount_NegativeNo() {
		assertEquals(2, clientSvc.CheckCreditCardAndCredit(creditCardVerificationServiceMock, creditLimitProviderMock,
				"9876567809871546", 0, 0, null, -1));
	}

	@Test
	public void testForValidAmountAndInvalidMonth() {
		assertEquals(3, clientSvc.CheckCreditCardAndCredit(creditCardVerificationServiceMock, creditLimitProviderMock,
				"9876567809871546", 0, 0, null, 100));
	}

	@Test
	public void testForInvalidMonth() {
		assertEquals(3, clientSvc.CheckCreditCardAndCredit(creditCardVerificationServiceMock, creditLimitProviderMock,
				"9876567809871546", 13, 0, null, 100));
	}

	@Test
	public void testForValidMonthInvalidYear() {
		assertEquals(4, clientSvc.CheckCreditCardAndCredit(creditCardVerificationServiceMock, creditLimitProviderMock,
				"9876567809871546", 05, 1999, null, 100));
	}

	@Test
	public void testForInvalidYear_2101() {
		assertEquals(4, clientSvc.CheckCreditCardAndCredit(creditCardVerificationServiceMock, creditLimitProviderMock,
				"9876567809871546", 05, 2101, null, 100));
	}

	@Test
	public void testForValidYear_2000() {
		assertEquals(11, clientSvc.CheckCreditCardAndCredit(creditCardVerificationServiceMock, creditLimitProviderMock,
				"9876567809871546", 05, 2000, null, 100));
	}

	@Test
	public void testForValidYear_2100() {
		assertEquals(11, clientSvc.CheckCreditCardAndCredit(creditCardVerificationServiceMock, creditLimitProviderMock,
				"9876567809871546", 05, 2100, null, 100));
	}

	@Test
	@DisplayName("Scenario 1, Unit Test A.. Transaction amount is within the credit limit approved")
	public void testApprovePurchaseAmt() {
		String creditCardNo = "1876543567890987";
		String cvv = "100";
		int creditLimit = 20000;
		int expMonth = 05;
		int expYear = 2100;
		int transactionAmt = 10000;
		given(creditCardVerificationServiceMock.VerifyCreditCard(creditCardNo, expMonth, expYear, cvv))
				.willReturn(true);
		given(creditLimitProviderMock.GetAvailableCreditLimit(creditCardNo)).willReturn(creditLimit);

		int returnVal = clientSvc.CheckCreditCardAndCredit(creditCardVerificationServiceMock, creditLimitProviderMock,
				creditCardNo, expMonth, expYear, cvv, transactionAmt);

		assertEquals(0, returnVal);
	}

	@Test
	@DisplayName("Scenario 1, Unit Test B.. Transaction amount is higher then the credit limit")
	public void testDeclinePurchaseAmt() {
		String creditCardNo = "1876543567890987";
		String cvv = "100";
		int creditLimit = 20000;
		int expMonth = 05;
		int expYear = 2100;
		int transactionAmt = 31000;
		given(creditCardVerificationServiceMock.VerifyCreditCard(creditCardNo, 05, 2100, cvv)).willReturn(true);
		given(creditLimitProviderMock.GetAvailableCreditLimit(creditCardNo)).willReturn(creditLimit);

		int returnVal = clientSvc.CheckCreditCardAndCredit(creditCardVerificationServiceMock, creditLimitProviderMock,
				creditCardNo, expMonth, expYear, cvv, transactionAmt);

		assertEquals(12, returnVal);
	}

	@Test
	@DisplayName("Scenario 2, Unit Test C.. Card details are invalid")
	public void testInvalidCardDetails() {
		String creditCardNo = "1234567890123456";
		String cvv = "123";
		int creditLimit = 30000;
		int expMonth = 12;
		int expYear = 2022;
		int transactionAmt = 10000;
		given(creditCardVerificationServiceMock.VerifyCreditCard(creditCardNo, expMonth, expYear, cvv))
				.willReturn(false);
		given(creditLimitProviderMock.GetAvailableCreditLimit(creditCardNo)).willReturn(creditLimit);

		int returnVal = clientSvc.CheckCreditCardAndCredit(creditCardVerificationServiceMock, creditLimitProviderMock,
				creditCardNo, expMonth, expYear, cvv, transactionAmt);

		assertEquals(11, returnVal);
	}

	@Test
	@DisplayName("Scenario 2, Unit Test D.. Card details are valid, but transaction amount is higher then the credit limit")
	public void testValidCardDetailsHigherTransAmt() {
		String creditCardNo = "1234567890123456";
		String cvv = "999";
		int creditLimit = 30000;
		int expMonth = 12;
		int expYear = 2022;
		int transactionAmt = 40000;
		given(creditCardVerificationServiceMock.VerifyCreditCard(creditCardNo, expMonth, expYear, cvv))
				.willReturn(true);
		given(creditLimitProviderMock.GetAvailableCreditLimit(creditCardNo)).willReturn(creditLimit);

		int returnVal = clientSvc.CheckCreditCardAndCredit(creditCardVerificationServiceMock, creditLimitProviderMock,
				creditCardNo, expMonth, expYear, cvv, transactionAmt);

		assertEquals(12, returnVal);
	}

}
