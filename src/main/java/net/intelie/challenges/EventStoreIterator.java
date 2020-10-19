package net.intelie.challenges;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EventStoreIterator implements EventIterator {
    
    private final ConcurrentLinkedQueue<Event> eventStore;
    private Event current = null;
    private Boolean hasNext = null;
    private Iterator<Event> i;

    public EventStoreIterator(Queue<Event> eS) {
      this.eventStore = (ConcurrentLinkedQueue<Event>) eS;
      if (this.eventStore != null ) this.i = eventStore.iterator();
      else this.i = null;
      moveNext();
    }
    
    public EventStoreIterator(Queue<Event> eS, Predicate<Event> p) {
        this.eventStore = (ConcurrentLinkedQueue<Event>) eS;
        ArrayList<Event> l	= (ArrayList<Event>) eventStore.parallelStream().filter(p).collect(Collectors.toList());
        this.i = l.parallelStream().iterator();
        moveNext();
      }
    
    @Override
    public boolean moveNext() {
    	if (i == null ) return false;
    	else {
            this.hasNext = i.hasNext();
            if (this.hasNext()) {
            	this.current = i.next();
            }
    	}     
        return this.hasNext;
    }

    @Override
    public Event current() {
    	if (this.hasNext != null && this.hasNext.booleanValue()) {
    		return this.current;
    	} else {
    		throw new IllegalStateException("Event store is empty");
    	}
    	
    }

    @Override
    public void remove() {
      if (this.hasNext != null && this.hasNext.booleanValue()) {
        eventStore.remove(this.current);
        moveNext();
      } else {
          throw new IllegalStateException("Remove error");
          
      }

    }
    
    public boolean hasNext() {
    	if (i == null) return false;
    	this.hasNext = this.i.hasNext();
    	if (this.hasNext != null)
    		return this.hasNext;
    	else throw new IllegalStateException("Event store is empty");
    }

	@Override
	public void close() throws Exception {
		this.i = null;
	}
    
}
