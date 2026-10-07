package module02;

import java.math.BigDecimal;

interface WithdrawableAccount {
	abstract void withdraw(BigDecimal amount);
}

interface DepositableAccount {
	abstract void deposit(BigDecimal amount);
}

abstract class Account {
	private final String id;
	protected BigDecimal balance;

	Account(String id, BigDecimal balance) {
		this.id = id;
		this.balance = balance;
	}

	String id() {
		return id;
	}

	BigDecimal balance() {
		return balance;
	}

}

final class SavingsAccount extends Account implements DepositableAccount {
	SavingsAccount(String id, BigDecimal balance) {
		super(id, balance);
	}

	@Override
	public void deposit(BigDecimal amount) {
		this.balance = this.balance.add(amount);	
	}
}

class CheckingAccount extends Account implements WithdrawableAccount, DepositableAccount {
	private final BigDecimal overdraftAmount;

	CheckingAccount(String id, BigDecimal balance, BigDecimal overdraftAmount) {
		super(id, balance);
		this.overdraftAmount = overdraftAmount;
	}

	public BigDecimal overdraftAmount() {
		return overdraftAmount;
	}

	@Override
	public void deposit(BigDecimal amount) {
		this.balance = this.balance.add(amount);
	}

	@Override
	public void withdraw(BigDecimal amount) {
		this.balance = this.balance.subtract(amount);
	}

}

final class TransferService {
	void transfer(WithdrawableAccount fromAccount, DepositableAccount toAccount, BigDecimal amount) {
		fromAccount.withdraw(amount);
		toAccount.deposit(amount);
	}
}

public class Exercise04 {
	public static void main(String[] args) {
		TransferService transferService = new TransferService();
		var account1 = new CheckingAccount("TR1", new BigDecimal(10_000),new BigDecimal(5_000));
		var account2 = new SavingsAccount("TR1", new BigDecimal(10_000));
		transferService.transfer(account1, account2, new BigDecimal(1_000));
	}
}
