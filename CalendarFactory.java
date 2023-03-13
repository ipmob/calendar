import java.time.LocalDateTime;

public class CalendarFactory {
    public static User createUser(String name, String email) {
        return new User(name, email);
    }

    public static Event createEvent(String name, LocalDateTime start, LocalDateTime end, User user) {
        return new Event(name, start, end, user);
    }

    public static CalendarService createCalendarService() {
        return CalendarService.getInstance();
    }
}

