package net.intelie.challenges;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class EventTest {
    @Test
    public void testEvent() throws Exception {
        Event event = new Event("some_type", 123L);
        assertEquals(123L, event.timestamp());
        assertEquals("some_type", event.type());
    }
    
    @Test
    public void testEquals() throws Exception {
    	Event e1 = new Event("some_type", 123L);
    	Event e2 = new Event("some_type", 123L);
    	assertEquals(true, e1.equals(e2));
    }
    
    @Test
    public void testCompareTo() throws Exception {
    	Event e1 = new Event("some_type", 123L);
    	Event e2 = new Event("some_type", 123L);
    	assertEquals(1, e1.compareTo(e2));
    }

}