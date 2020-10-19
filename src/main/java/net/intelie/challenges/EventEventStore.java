package net.intelie.challenges;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;

public class EventEventStore implements EventStore {
    private ConcurrentLinkedQueue<Event> eventStore;
    
    public EventEventStore() {
    	this.eventStore = null;
    }
    
    public Queue<Event> getEventStore () {
        return this.eventStore;
    }

    public void insert(Event event) {
    	//eventStorei is only created when the first element is inserted.
    	if (this.eventStore == null) {
    		synchronized (this) {
    			this.eventStore = new ConcurrentLinkedQueue<Event>();
    		}
    	}
        this.eventStore.add(event);
    }

    public void removeAll(String type) {
    	if (this.eventStore != null && !this.eventStore.isEmpty()) {
    		this.eventStore.spliterator().forEachRemaining(p -> {
        		if (p.type().equals(type)) {
        			eventStore.remove(p);
        		}
        	});
    	} else {
    		throw new IllegalStateException("Event store is empty");
    	}
    	
    }

    public EventIterator query(String type, long startTime, long endTime) {
        Predicate <Event> predicate = p -> p.type().equals(type) &&
                                      p.timestamp() >= startTime &&
                                      p.timestamp() < endTime;
                                      
        return new EventStoreIterator(this.eventStore, predicate);
        
    }
}

