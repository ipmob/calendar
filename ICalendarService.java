import java.time.LocalDateTime;
import java.util.List;

public interface ICalendarService {
    void addEvent(Event event);
    void addUser(User user);
    List<Event> getEventsForUser(User user);
    void addBusySlot(Event busySlot);
    List<Event> getBusySlotsForUser(User user);
    List<LocalDateTime> getAvailableSlotsForUser(User user, LocalDateTime startDate, LocalDateTime endDate, int durationInMinutes);
    void deleteEvent(Event event);
    LocalDateTime getFavorableSlot(List<User> attendees, int durationInMinutes);
}