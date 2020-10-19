package net.intelie.challenges;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EventStoreIteratorTest {
	
	private EventEventStore eventStore = new EventEventStore();
	
    @Test
    public void testHasNextFalse() throws Exception {
        EventStoreIterator i = new EventStoreIterator(eventStore.getEventStore());
        assertEquals(false, i.hasNext());
    }
    
    @Test
    public void testHasNextTrue() throws Exception {
    	Event e = new Event("my_type", 127L);
    	eventStore.insert(e);
        EventStoreIterator i = new EventStoreIterator(eventStore.getEventStore());
        assertEquals(false, i.hasNext());
    }
    
    @Test
    public void testCurrent() throws Exception {
    	Event e = new Event("my_type", 127L);
    	eventStore.insert(e);
        EventStoreIterator i = new EventStoreIterator(eventStore.getEventStore());
        assertEquals(e, i.current());
    }
    
    @Test (expected = IllegalStateException.class)
    public void testCurrentException() throws Exception {	
        EventStoreIterator i = new EventStoreIterator(eventStore.getEventStore());
        i.current();
    }
    
    @Test
    public void testMoveNext() throws Exception {
    	
        EventStoreIterator i = new EventStoreIterator(eventStore.getEventStore());
        assertEquals(false, i.moveNext());
    }
    
    @Test
    public void testRemove() throws Exception {
    	Event e = new Event("my_type", 127L);
    	eventStore.insert(e);
        EventStoreIterator i = new EventStoreIterator(eventStore.getEventStore());
        i.remove();
        assertEquals(false, i.hasNext());
    }
    
    @Test (expected = IllegalStateException.class)
    public void testRemoveException() throws Exception {
    	
        EventStoreIterator i = new EventStoreIterator(eventStore.getEventStore());
        i.remove();
    }
    
    @Test
    public void testMove() throws Exception {
    	Event e1 = new Event("some_type", 123L);
    	Event e2 = new Event("other_type", 124L);
    	Event e3 = new Event("my_type", 127L);
    	
    	eventStore.insert(e1);
    	eventStore.insert(e2);
    	eventStore.insert(e3);
        EventStoreIterator i = new EventStoreIterator(eventStore.getEventStore());
   
        assertEquals(e1, i.current());
        i.moveNext();
        assertEquals(e2, i.current());
        i.moveNext();
        assertEquals(e3, i.current());
        i.moveNext();
        assertEquals(false, i.hasNext());
    }
    
    @Test
    public void testNextHasNext() throws Exception {
    	
    	Event e1 = new Event("my_type", 127L);
    	eventStore.insert(e1);
        EventStoreIterator i = new EventStoreIterator(eventStore.getEventStore());
        assertEquals(e1, i.current());
        assertEquals(false, i.moveNext());
        
    }
    
    @Test (expected = IllegalStateException.class)
    public void testNextHasNextRemoveException() throws Exception {
    	
    	Event e1 = new Event("my_type", 127L);
    	eventStore.insert(e1);
        EventStoreIterator i = new EventStoreIterator(eventStore.getEventStore());
        i.moveNext();
        i.remove();
        
    }

}