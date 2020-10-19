package net.intelie.challenges;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

public class Event implements Comparable<Event> {
    private final String type;
    private final long timestamp;

    public Event(String type, long timestamp) {
        this.type = type;
        this.timestamp = timestamp;
    }

    public String type() {
        return type;
    }

    public long timestamp() {
        return timestamp;
    }

    public int compareTo(Event e) {
		if (e!= null && e.type().equals(this.type) && e.timestamp() == this.timestamp) {
			return 1;
		}
		return 0;
	}
    
    @Override
    public String toString() {
    	Date dt = new Date (this.timestamp);
    	SimpleDateFormat sdf = new SimpleDateFormat ("dd/MM/yyyy HH:mm:ss.SSS");
        sdf.setTimeZone (TimeZone.getTimeZone ("GMT"));
    	return "Type: "+this.type+" timestamp: "+sdf.format (dt);
    }
    
    @Override
    public boolean equals(Object o) {
    	
    	if (o == this) return true;
    	else if (!(o instanceof Event)) return false;
        Event e = (Event) o;
        return this.timestamp == e.timestamp &&
                Objects.equals(this.type, e.type) ;
    	
    }

    @Override
    public int hashCode() {
    	return Objects.hash(type, timestamp);
    }
}
