package com.embit.recurrent.models;

import com.embit.recurrent.model.Item;
import com.embit.recurrent.model.TransactionType;

import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.*;

public class ItemTests {


    @Test
    public void testPastInitialDateItems() {
        LocalDate pastDate = LocalDate.of(2021, Month.JANUARY, 10);
        Item item = new Item("New Item", "Item Description", 10.00, TransactionType.EXPENSE, pastDate, 10);

        assertEquals(pastDate, item.getLastOccurrence());

        item.updateOccurrence();
        assertNotEquals(pastDate, item.getLastOccurrence());

    }

    @Test
    public void testFutureInitialDateItems() {
        LocalDate futureDate = LocalDate.now().plusMonths(2);
        Item item = new Item("New Item", "Item Description", 10.00, TransactionType.EXPENSE, futureDate, 10);

        assertNotEquals(futureDate, item.getLastOccurrence());
    }
    
}
