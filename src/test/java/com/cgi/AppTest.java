package com.cgi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void testWallet()
    {
      Wallet wallet = new Wallet();
      assertEquals(0,wallet.getBalance());
    }

    
}
