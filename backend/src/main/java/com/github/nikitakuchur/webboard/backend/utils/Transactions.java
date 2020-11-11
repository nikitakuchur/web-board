package com.github.nikitakuchur.webboard.backend.utils;

import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 * The utility class for working with transactions.
 */
public class Transactions {

    private Transactions() {
    }

    /**
     * Execute the given lambda in transaction.
     *
     * @param transaction the transaction
     * @param runnable the runnable
     */
    public static void executeInTransaction(UserTransaction transaction, Runnable runnable) {
        try {
            transaction.begin();
            runnable.run();
            transaction.commit();
        } catch (Exception e) {
            try {
                transaction.rollback();
            } catch (SystemException se) {
                e.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
