import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class CalenderService {
    private List<User> users;

    public CalenderService() {
        this.users = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void addEvent(Event event) {
        User user = event.getUser();
        user.addEvent(event);
    }

    public List<Event> getEventsForUser(User user) {
        return user.getEvents();
    }

    public void deleteEvent(Event event) {
        User user = event.getUser();
        user.getEvents().remove(event);
    }

    public List<LocalDateTime> getAvailableSlotsForUser(User user, LocalDateTime startDate, LocalDateTime endDate, int durationInMinutes) {
        List<Event> events = user.getEvents();
        List<LocalDateTime> availableSlots = new ArrayList<>();
        LocalDateTime slotStart = startDate;
        LocalDateTime slotEnd;

        for (Event event : events) {
            if (slotStart.isBefore(event.getStart())) {
                slotEnd = event.getStart();
                while (slotStart.plusMinutes(durationInMinutes).isBefore(slotEnd) || slotStart.plusMinutes(durationInMinutes).isEqual(slotEnd)) {
                    availableSlots.add(slotStart);
                    slotStart = slotStart.plusMinutes(durationInMinutes);
                }
            }
            slotStart = event.getEnd();
        }

        if (slotStart.isBefore(endDate)) {
            slotEnd = endDate;
            while (slotStart.plusMinutes(durationInMinutes).isBefore(slotEnd) || slotStart.plusMinutes(durationInMinutes).isEqual(slotEnd)) {
                availableSlots.add(slotStart);
                slotStart = slotStart.plusMinutes(durationInMinutes);
            }
        }

        return availableSlots;
    }
}