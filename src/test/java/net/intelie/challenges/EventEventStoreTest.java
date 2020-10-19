package net.intelie.challenges;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import java.util.stream.Stream;


public class EventEventStoreTest {
	
	private EventEventStore eventStore = new EventEventStore();
	
	@Before
    public void init() {
		Event e1 = new Event("some_type", 123L);
    	Event e2 = new Event("other_type", 124L);
    	eventStore.insert(e1);
    	eventStore.insert(e2);
    }
	   
    @Test
    public void testInsert() throws Exception {
    	Event e = new Event("some_type", 127L);
    	eventStore.insert(e);
    	Stream<Event> stream = eventStore.getEventStore().stream();
    	assertEquals(3, stream.count());
    }
    
    @Test
    public void testremoveAll() throws Exception {
    	eventStore.removeAll("some_type");
    	Stream<Event> stream = eventStore.getEventStore().stream();
    	assertEquals(1, stream.count());
    }
    
    @Test (expected = IllegalStateException.class) 
    public void testremoveAllException() throws Exception {
    	eventStore = new EventEventStore();
    	eventStore.removeAll("some_type");

    }
    
    @Test
    public void testQuery() throws Exception {
    	Event e = new Event("my_type", 127L);
    	eventStore.insert(e);
    	EventIterator i = eventStore.query("my_type", 127L, 128L);
    	assertEquals(e, i.current());
    }
    
    @Test 
    public void testQueryException() throws Exception {

    	EventStoreIterator i = (EventStoreIterator) eventStore.query("my_type", 127L, 128L);
    	assertEquals(false, i.hasNext());
    }
    
    @Test
    public void testInsertMultiThread() throws InterruptedException {
    	
    	Event e1 = new Event("some_type", 129L);
    	Event e2 = new Event("other_type", 130L);
    	
    	Thread t1 = new Thread(() -> {
    		        eventStore.insert(e1);
    		       });
    	Thread t2 = new Thread(() -> {
	        eventStore.insert(e2);
	       });
    	
    	t1.start();
    	t2.start();
    	t1.join();
    	t2.join();
    	
    	assertEquals(4,eventStore.getEventStore().size());
    	
    }
    
    @Test
    public void testRemoveMultiThread() throws InterruptedException {
    	
    	Event e1 = new Event("some_type", 129L);
    	Event e2 = new Event("other_type", 130L);
    	eventStore.insert(e1);
    	eventStore.insert(e2);
    	Thread t1 = new Thread(() -> {
    		        eventStore.removeAll("some_type");
    		       });
    	Thread t2 = new Thread(() -> {
    				eventStore.removeAll("some_type");
	       		   });
    	
    	t1.start();
    	t2.start();
    	t1.join();
    	t2.join();
    	
    	assertEquals(2,eventStore.getEventStore().size());
    	
    }

    @Test
    public void testQueryMultiThread() throws InterruptedException {
    	
    	Event e1 = new Event("this_type", 123L);
    	Event e2 = new Event("that_type", 124L);
    	eventStore.insert(e1);
    	eventStore.insert(e2);
    	
    	Thread t1 = new Thread(() -> {
    		EventStoreIterator i1 = (EventStoreIterator)eventStore.query("this_type",123L,130L);
    		assertEquals(e1, i1.current());
    		       });
    	Thread t2 = new Thread(() -> {
    		EventStoreIterator i2 = (EventStoreIterator)eventStore.query("that_type",124L,131L);
    		assertEquals(e2, i2.current());
	       		   });
    	
    	t1.start();
    	t2.start();
    	t1.join();
    	t2.join();

    }

}