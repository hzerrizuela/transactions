package ar.com.transactions.exceptions;

public class AccountBalanceException extends RuntimeException {

	public AccountBalanceException() {
		super();
	}

	public AccountBalanceException(String message) {
		super(message);
	}

}
