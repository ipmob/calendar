import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Create calendar service
        CalenderService calendarService = new CalenderService();

        // Create users
        User user1 = new User("Alice", "alice@example.com");
        User user2 = new User("Bob", "bob@example.com");

        // Add users to the calendar service
        calendarService.addUser(user1);
        calendarService.addUser(user2);

        // Create events for user 1
        Event event1 = new Event("Meeting", LocalDateTime.of(2023, 3, 12, 9, 0), LocalDateTime.of(2023, 3, 12, 10, 0), user1);
        Event event2 = new Event("Lunch", LocalDateTime.of(2023, 3, 12, 11, 0), LocalDateTime.of(2023, 3, 12, 12, 0), user1);
        Event event3 = new Event("Presentation", LocalDateTime.of(2023, 3, 12, 14, 0), LocalDateTime.of(2023, 3, 12, 15, 0), user1);

        // Create events for user 2
        Event event4 = new Event("Call", LocalDateTime.of(2023, 3, 12, 10, 0), LocalDateTime.of(2023, 3, 12, 11, 0), user2);
        Event event5 = new Event("Meeting", LocalDateTime.of(2023, 3, 12, 12, 0), LocalDateTime.of(2023, 3, 12, 13, 0), user2);

        // Add events to the calendar service
        calendarService.addEvent(event1);
        calendarService.addEvent(event2);
        calendarService.addEvent(event3);
        calendarService.addEvent(event4);
        calendarService.addEvent(event5);

        // Get events for user 1
        List<Event> eventsForUser1 = calendarService.getEventsForUser(user1);
        System.out.println("Events for user 1:");
        for (Event event : eventsForUser1) {
            System.out.println(event.getName() + ": " + event.getStart() + " - " + event.getEnd());
        }

        // Get available slots for user 1
        LocalDateTime startDate = LocalDateTime.of(2023, 3, 12, 8, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 3, 12, 16, 0);
        int durationInMinutes = 60;
        List<LocalDateTime> availableSlotsForUser1 = calendarService.getAvailableSlotsForUser(user1, startDate, endDate, durationInMinutes);

        System.out.println("Available slots for user 1:");
        for (LocalDateTime slot : availableSlotsForUser1) {
            System.out.println(slot);
        }


        System.out.printf("Deleting event : %s " , event1.getName());
        // Deleting event1
        calendarService.deleteEvent(event1);


        System.out.println("Available slots for user 1:");
        for (LocalDateTime slot : availableSlotsForUser1) {
            System.out.println(slot);
        }
    }
}
