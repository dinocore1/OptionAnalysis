package com.devsmart;


import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Calendar;

public class DatabaseTest {

    @Test
    public void testInsert() {
        Database db = new Database();

        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, 1, 17);

        Option o1 = Option.create("sune", calendar.getTime(), 20.5);
        Option o2 = Option.create("sune", calendar.getTime(), 20.5);

        assertTrue(o1.equals(o2));

    }
}
