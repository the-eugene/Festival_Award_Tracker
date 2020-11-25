package com.example.festivalawardtracker;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class DBHashMapInstrumentedTest {

    static Map<String, Person> testMap = new DBHashMap<>(Person.class);

    @Test
    public void HashMapTest() {
        Person person1 = TestStaticObjects.setUpPerson();
        testMap.put(null,person1);
        assertNotNull(person1.ID); //Check that we now have an ID
        String ID = person1.ID;
        testMap.remove(ID); //remove from map but not database
        assertFalse(testMap.containsKey(ID)); //should be gone from map
        Person reloaded_person1 = testMap.get(ID);
        assertEquals(person1.lastName,reloaded_person1.lastName);
        person1 = TestStaticObjects.setUpPerson2();
        person1.ID = ID; //new person info but with same ID
        person1.save(); //update database but not map
        reloaded_person1 = testMap.get(ID);
        assertNotEquals(person1.lastName,reloaded_person1.lastName); //retrieved from map not database
    }
}
