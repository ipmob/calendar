import java.time.LocalDateTime;
import java.util.*;

class User {
    private String name;
    private String email;
    private List<Event> events;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.events = new ArrayList<>();
    }

    public List<Event> getEvents() {
        return events;
    }

    public void addEvent(Event event) {
        events.add(event);
    }
}