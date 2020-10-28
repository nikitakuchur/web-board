package com.github.nikitakuchur.webboard.utils;

import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

public class Transactions {

    private Transactions() {
    }

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
