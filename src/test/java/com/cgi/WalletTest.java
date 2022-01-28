package com.cgi;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class WalletTest {

    private Wallet emptyWallet;
    private Wallet walletWith100Dollars;

    @BeforeAll
    static void beforeAllTest() {
        System.out.println("This method should run before all the tests");
    }

    @BeforeEach
    void setUp() {
        System.out.println("Creating some wallets for testing");
        emptyWallet = new Wallet();
        walletWith100Dollars = new Wallet();
        walletWith100Dollars.deposit(100);
    }

    @SuppressWarnings("ConstantConditions")
    @AfterEach
    void tearDown() {
        emptyWallet = null;
        System.out.printf("Destroying Wallets for testing, here is the proof: %s%n", emptyWallet);
    }

    @AfterAll
    static void afterAllTests() {
        System.out.println("This method should run after all the tests");
    }

    @Test
    public void testWalletDeposited1000() {
        emptyWallet.deposit(1000);
        assertThat(emptyWallet.getBalance()).isEqualTo(1000);
    }

    @Test
    public void testWalletShouldHaveZeroToBegin() {
        //empty
        assertThat(emptyWallet.getBalance()).isZero();
    }

    @Test
    public void testWalletDepositOf1BalanceIs1() {
        emptyWallet.deposit(1);
        assertThat(emptyWallet.getBalance()).isOne();
    }

    @Test
    @DisplayName("If a deposit of one and withdrawal of the same amount the result should be 0")
    public void testWalletWithDepositOf1AndWithdrawalOf1() {
        emptyWallet.deposit(1);
        emptyWallet.withdrawal(1);
        assertThat(emptyWallet.getBalance()).isZero();
    }

    @Test
    public void testWalletWithTransferOf1() {
        emptyWallet.deposit(1);
        assertThat(emptyWallet.getBalance()).isGreaterThan(0);
        emptyWallet.withdrawal(1);
        assertThat(emptyWallet.getBalance()).isZero();
    }

    @Test
    public void depositMoreThanMaxInteger() {
        emptyWallet.deposit(Integer.MAX_VALUE);
        assertThatThrownBy(() -> emptyWallet.deposit(1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void depositMoreThanMaxIntegerWithAComputationThatWouldExceedMaxValue() {
        emptyWallet.deposit(Integer.MAX_VALUE - 10);
        assertThatThrownBy(() -> emptyWallet.deposit(20))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    public void testAnEmptyWalletShouldNotAllowWithdrawals() {
        assertThatThrownBy(() -> emptyWallet.withdrawal(20))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The result will end up negative try again");
    }


    @Test
    public void testAWalletShouldNotAcceptANegativeWithdrawal() {
        emptyWallet.deposit(50);
        assertThatThrownBy(() -> emptyWallet.withdrawal(-20))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("You cannot withdrawal a negative value");
    }

    @Test
    public void testWalletDepositShouldIncreaseBalance() {
        emptyWallet.deposit(5);
        assertThat(emptyWallet.getBalance()).isPositive();
    }

    @Test
    public void testWalletDepositShouldBeWithPositiveAmount() {
        assertThatThrownBy(() -> emptyWallet.deposit(-5)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testWalletAlreadyHas10DepositAndWeWithdrawalMore() {
        emptyWallet.deposit(10);
        assertThatThrownBy(() -> emptyWallet.deposit(-20))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @Disabled(value = "This test is critical it did not run, please check")
    public void testThatAfterDepositingOneTimeThatTheSecondTimeRuns() {
        emptyWallet.deposit(10);
        emptyWallet.deposit(30);
        assertThat(emptyWallet.getBalance()).isEqualTo(40);
    }


    public static Stream<Arguments> accountDataProvider() {
        return Stream.of(
                Arguments.of(100, 50, 50),
                Arguments.of(1, 1, 0),
                Arguments.of(2, 1, 1),
                Arguments.of(1, 0, 1),
                Arguments.of(2, 0, 2)
        );
    }

    @ParameterizedTest(name = "{index}: deposit = {0}, withdrawal = {1}, balance = {2}")
    @MethodSource("accountDataProvider")
    public void testDepositsWithdrawalsAndBalances(int deposit, int withdrawal, int balance) {
        emptyWallet.deposit(deposit);
        emptyWallet.withdrawal(withdrawal);
        assertThat(emptyWallet.getBalance()).isEqualTo(balance);
    }


    //Specification
    //Feature:
    //  This is a wallet that maintains a balance
    //  Scenario:
    //     That a deposit will increase the balance
    //  Scenario:
    //     If a deposit of one and withdrawal of the same amount the result should be 0
    //  Scenario:
    //     Given a wallet
    //     When a deposit of a negative value is made
    //     Thro
    //  Scenario:
    //     There should not are any negative withdrawals
    //  Scenario:
    //     There should be a max of Integer Max Value in Java

}