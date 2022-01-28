package com.cgi;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class WalletTest {

    private Wallet walletWith100Dollars;
    private Wallet emptyWallet;

    @BeforeAll
    static void beforeAllTest() {
        System.out.println("this method should run before all the tests");
    }

    @BeforeEach
    void setUp() {
        System.out.println("this method should run before all the tests");
        emptyWallet = new Wallet();
        walletWith100Dollars = new Wallet();
        walletWith100Dollars.deposit(100);
    }

    @AfterEach
    void tearDown() {
        emptyWallet = null;
    }

    @Test
    public void testWalletShouldHaveZeroToBegin() {
        Wallet wallet = new Wallet();
        assertThat(wallet.getBalance()).isZero();
    }

    @Test
    public void testWalletDepositOf1BalanceIs1() {
        Wallet wallet = new Wallet();
        wallet.deposit(1);
        assertThat(wallet.getBalance()).isOne();
    }

    @Test
    public void testWalletWithDepositOf1AndWithdrawalOf1() {
        Wallet wallet = new Wallet();
        wallet.deposit(1);
        wallet.withdrawal(1);
        assertThat(wallet.getBalance()).isZero();
    }

    @Test
    public void testWalletWithTransferOf1() {
        Wallet wallet = new Wallet();
        wallet.deposit(1);
        assertThat(wallet.getBalance()).isGreaterThan(0);
        wallet.withdrawal(1);
        assertThat(wallet.getBalance()).isZero();
    }

    @Test
    public void depositMoreThanMaxInteger() {
        Wallet wallet = new Wallet();
        wallet.deposit(Integer.MAX_VALUE);
        assertThatThrownBy(() -> wallet.deposit(1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void depositMoreThanMaxIntegerWithAComputationThatWouldExceedMaxValue() {
        Wallet wallet = new Wallet();
        wallet.deposit(Integer.MAX_VALUE - 10);
        assertThatThrownBy(() -> wallet.deposit(20))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    public void testAnEmptyWalletShouldNotAllowWithdrawals() {
        Wallet wallet = new Wallet();
        assertThatThrownBy(() -> wallet.withdrawal(20))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The result will end up negative try again");
    }


    @Test
    public void testAWalletShouldNotAcceptANegativeWithdrawal() {
        Wallet wallet = new Wallet();
        wallet.deposit(50);
        assertThatThrownBy(() -> wallet.withdrawal(-20))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("You cannot withdrawal a negative value");
    }

    @Test
    public void testWalletDepositShouldIncreaseBalance() {
        Wallet wallet = new Wallet();
        wallet.deposit(5);
        assertThat(wallet.getBalance()).isPositive();
    }

    @Test
    public void testWalletDepositShouldBeWithPositiveAmount() {
        Wallet wallet = new Wallet();
        assertThatThrownBy(() -> wallet.deposit(-5)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testWalletAlreadyHas10DepositAndWeWithdrawalMore() {
        Wallet wallet = new Wallet();
        wallet.deposit(10);
        assertThatThrownBy(() -> wallet.deposit(-20))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testThatAfterDepositingOneTimeThatTheSecondTimeRuns() {
        Wallet wallet = new Wallet();
        wallet.deposit(10);
        wallet.deposit(0);
        assertThat(wallet.getBalance()).isEqualTo(40);
    }


    //Specification
    //Feature:
    //  This is a wallet that maintains a balance
    //  Scenario:
    //     That a deposit will increase the balance
    //  Scenario:
    //     If a deposit of one and withdrawal the result should be 0
    //  Scenario:
    //     Given a wallet
    //     When a deposit of a negative value is made
    //     Thro
    //  Scenario:
    //     There should not are any negative withdrawals
    //  Scenario:
    //     There should be a max of Integer Max Value in Java

}