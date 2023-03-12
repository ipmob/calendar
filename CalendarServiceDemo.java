import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class CalendarServiceDemo {

    public static void main(String[] args) {
        // Create calendar service
        CalendarService calendarService = CalendarFactory.createCalendarService();

        // Create users
        User alice = CalendarFactory.createUser("Alice", "alice@example.com");
        User bob = CalendarFactory.createUser("Bob", "bob@example.com");

        // Add users
        calendarService.addUser(alice);
        calendarService.addUser(bob);

        // Add busy slots for Alice
        LocalDateTime start1 = LocalDateTime.of(2022, 3, 14, 9, 0);
        LocalDateTime end1 = LocalDateTime.of(2022, 3, 14, 10, 0);
        Event busySlot1 = CalendarFactory.createEvent("Busy", start1, end1, alice);
        calendarService.addBusySlot(busySlot1);

        LocalDateTime start2 = LocalDateTime.of(2022, 3, 14, 13, 0);
        LocalDateTime end2 = LocalDateTime.of(2022, 3, 14, 14, 0);
        Event busySlot2 = CalendarFactory.createEvent("Busy", start2, end2, alice);
        calendarService.addBusySlot(busySlot2);

        // Add busy slots for Bob
        LocalDateTime start3 = LocalDateTime.of(2022, 3, 14, 10, 0);
        LocalDateTime end3 = LocalDateTime.of(2022, 3, 14, 12, 0);
        Event busySlot3 = CalendarFactory.createEvent("Busy", start3, end3, bob);
        calendarService.addBusySlot(busySlot3);

        LocalDateTime start4 = LocalDateTime.of(2022, 3, 14, 15, 0);
        LocalDateTime end4 = LocalDateTime.of(2022, 3, 14, 16, 0);
        Event busySlot4 = CalendarFactory.createEvent("Busy", start4, end4, bob);
        calendarService.addBusySlot(busySlot4);

        // Fetch busy slots for Alice
        System.out.println("Busy slots for Alice:");
        List<Event> aliceBusySlots = calendarService.getBusySlotsForUser(alice);
        for (Event busySlot : aliceBusySlots) {
            System.out.printf("%s: %s - %s\n", busySlot.getName(), busySlot.getStart(), busySlot.getEnd());
        }
        System.out.println();

        // Fetch available slots for Bob
        System.out.println("Available slots for Bob:");
        LocalDateTime start5 = LocalDateTime.of(2022, 3, 14, 14, 0);
        LocalDateTime end5 = LocalDateTime.of(2022, 3, 14, 16, 0);
        int duration = 60; // in minutes
        System.out.println("Available slots for Bob from " + start5 + " to " + end5 + " with duration " + duration + " minutes:");
        for (LocalDateTime slot : calendarService.getAvailableSlotsForUser(bob, start5, end5, duration)) {
            System.out.println(slot);
        }

        // Create event with Alice and Bob
        LocalDateTime eventStart = LocalDateTime.of(2022, 3, 14, 11, 0);
        LocalDateTime eventEnd = LocalDateTime.of(2022, 3, 14, 12, 0);
        Event eventForAlice = CalendarFactory.createEvent("Meeting", eventStart, eventEnd, alice);
        Event eventForBob = CalendarFactory.createEvent("Meeting", eventStart, eventEnd, bob);
        calendarService.addEvent(eventForAlice);
        calendarService.addEvent(eventForBob);
        System.out.printf("Event '%s' created by %s \n", eventForAlice.getName(), eventForAlice.getUser().getName());
        System.out.println();

        // Fetch events for Alice
        System.out.println("Events for Alice:");
        List<Event> aliceEvents = calendarService.getEventsForUser(alice);
        for (Event userEvent : aliceEvents) {
            System.out.printf("Event '%s' from %s to %s \n", userEvent.getName(), userEvent.getStart(), userEvent.getEnd());
        }
        System.out.println();
        // Fetch events for Bob
        System.out.println("Events for Bob:");
        List<Event> bobEvents = calendarService.getEventsForUser(bob);
        for (Event userEvent : bobEvents) {
            System.out.printf("Event '%s' from %s to %s \n", userEvent.getName(), userEvent.getStart(), userEvent.getEnd());
        }
        System.out.println();

        // Delete event created by Alice
        Event deleteEvent =  aliceEvents.get(0);
        calendarService.deleteEvent(aliceEvents.get(0));
        System.out.printf("Event '%s' deleted by %s\n", deleteEvent.getName(), deleteEvent.getUser().getName());
        System.out.println();

        // Fetch events for Alice after deletion
        System.out.println("Events for Alice after deletion:");
        aliceEvents = calendarService.getEventsForUser(alice);
        for (Event userEvent : aliceEvents) {
            System.out.printf("Event '%s' from %s to %s with %s\n", userEvent.getName(), userEvent.getStart(), userEvent.getEnd());
        }
        System.out.println();

        // Create a meeting between Alice, Bob and another user Charlie
        User charlie = CalendarFactory.createUser("Charlie", "charlie@example.com");
        calendarService.addUser(charlie);

        LocalDateTime meetingStart = LocalDateTime.of(2022, 3, 14, 13, 0);
        LocalDateTime meetingEnd = LocalDateTime.of(2022, 3, 14, 14, 0);
        Event meetingForAlic = CalendarFactory.createEvent("Meeting", meetingStart, meetingEnd, alice);
        Event meetingForBob = CalendarFactory.createEvent("Meeting", meetingStart, meetingEnd, bob);
        Event meetingForCharlie = CalendarFactory.createEvent("Meeting", meetingStart, meetingEnd, charlie);

        calendarService.addEvent(meetingForAlic);
        calendarService.addEvent(meetingForBob);
        calendarService.addEvent(meetingForCharlie);

        // Find the most favorable empty slot for a meeting between Alice, Bob and Charlie for 1 hour
        List<User> attendees = new ArrayList<>();
        attendees.add(alice);
        attendees.add(bob);
        attendees.add(charlie);
        LocalDateTime favorableSlotStart = calendarService.getFavorableSlot(attendees, 60);
        LocalDateTime favorableSlotEnd = favorableSlotStart.plusMinutes(60);
        System.out.printf("The most favorable empty slot for a meeting between Alice, Bob and Charlie for 1 hour is from %s to %s\n", favorableSlotStart, favorableSlotEnd);
    }
}

