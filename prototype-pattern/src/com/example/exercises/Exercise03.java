package com.example.exercises;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Exercise03 {

    public static void main(String[] args) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

        // The prototype: the day an invoice was issued
        Calendar issued = Calendar.getInstance();
        issued.set(2026, Calendar.AUGUST, 27, 0, 0, 0);

        // Copies derived from it. clone() returns Object, hence the cast.
        var due = (Calendar) issued.clone();
        due.add(Calendar.DAY_OF_MONTH, 30);

        var reminder = (Calendar) due.clone();
        reminder.add(Calendar.DAY_OF_MONTH, -7);

        System.out.println("issued   : " + fmt.format(issued.getTime()));    // 2026-08-27, unchanged
        System.out.println("due      : " + fmt.format(due.getTime()));       // 2026-09-26
        System.out.println("reminder : " + fmt.format(reminder.getTime()));  // 2026-09-19

        // The copies are independent objects
        System.out.println("same object?  " + (issued == due));              // false
        System.out.println("same moment?  " + issued.equals(due));           // false

        // What goes wrong WITHOUT clone(): add() mutates the original
        Calendar alias = issued;
        alias.add(Calendar.DAY_OF_MONTH, 30);
        System.out.println("issued after aliasing: " + fmt.format(issued.getTime())); // 2026-09-26 - corrupted
    }
}