import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class CalendarService implements ICalendarService {
    private static CalendarService instance;

    private List<User> users;
    private List<Event> events;
    private List<Event> busySlots;

    public CalendarService() {
        this.users = new ArrayList<>();
        this.events = new ArrayList<>();
        this.busySlots = new ArrayList<>();
    }
    public static CalendarService getInstance() {
        if (instance == null) {
            instance = new CalendarService();
        }
        return instance;
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public List<Event> getEventsForUser(User user) {
        List<Event> userEvents = new ArrayList<>();
        for (Event event : events) {
            if (event.getUser().equals(user)) {
                userEvents.add(event);
            }
        }
        return userEvents;
    }

    public void addBusySlot(Event busySlot) {
        busySlots.add(busySlot);
    }

    public List<Event> getBusySlotsForUser(User user) {
        List<Event> userBusySlots = new ArrayList<>();
        for (Event busySlot : busySlots) {
            if (busySlot.getUser().equals(user)) {
                userBusySlots.add(busySlot);
            }
        }
        return userBusySlots;
    }

    /**
     *
     * @param user {@link User}
     * @param startDate startDate
     * @param endDate endDate
     * @param durationInMinutes duration of event in minutes
     * @return  list of `LocalDateTime` objects representing the available time slots for a given User between  and endDate,
     * with a minimum duration of durationInMinutes.
     */
    public List<LocalDateTime> getAvailableSlotsForUser(User user, LocalDateTime startDate, LocalDateTime endDate, int durationInMinutes) {
        List<Event> userEvents = getEventsForUser(user);
        List<Event> userBusySlots = getBusySlotsForUser(user);
        List<LocalDateTime> availableSlots = new ArrayList<>();
        LocalDateTime slotStart = startDate;
        LocalDateTime slotEnd;

        // add busy slots to events list for overlap checking
        userEvents.addAll(userBusySlots);

        for (Event event : userEvents) {
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

    public void deleteEvent(Event event) {
        events.remove(event);
    }

    /**
     * This method checks the busy slots for all attendees and finds the first available time slot.
     *
     * @param attendees         list of attendees
     * @param durationInMinutes duration of event in minutes
     * @return `LocalDateTime`  object representing the first available time slot of the specified duration
     */
    public LocalDateTime getFavorableSlot(List<User> attendees, int durationInMinutes) {
        // Get all busy slots for the attendees
        List<Event> busySlots = new ArrayList<>();
        for (User attendee : attendees) {
            busySlots.addAll(getBusySlotsForUser(attendee));
        }

        // Find the first empty slot of the specified duration
        LocalDateTime start = LocalDateTime.now();
        while (true) {
            LocalDateTime end = start.plusMinutes(durationInMinutes);
            boolean found = true;

            for (Event busySlot : busySlots) {
                if (busySlot.getStart().isBefore(end) && start.isBefore(busySlot.getEnd())) {
                    // There's a busy slot during this time, so move the start time to the end of this busy slot
                    start = busySlot.getEnd();
                    found = false;
                    break;
                }
            }

            if (found) {
                return start;
            }
        }
    }

}

