import java.time.LocalDateTime;

class Event {
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;

    private User user;

    public Event(String name, LocalDateTime start, LocalDateTime end, User user) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public User getUser() {
        return user;
    }
}