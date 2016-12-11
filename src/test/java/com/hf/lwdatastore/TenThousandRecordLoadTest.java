package com.hf.lwdatastore;

import com.hf.lwdatastore.exception.CollectionNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author pldorrell
 */
public class TenThousandRecordLoadTest extends LWDataStoreTest {
 
    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        factory = new LWDataStoreFactory();
        factory.init(this.getConfig());
        dataStore = LWDataStoreFactory.getDataStore();
        //doSave();
        
    }

    @After
    public void tearDown() {
        //delete all items
        //this.doClear();
        factory.shutdown();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    //@Test
    public void loadRecordsByPK() {

        CollectionObject cObject = null;
        try {
            cObject = dataStore.getObject("testObject", "22");
        } catch (CollectionNotFoundException ex) {
            Logger.getLogger(LoadRecordsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (cObject == null) {
            return;
        }
        TestObject tObject = (new TestObjectConverter()).convertFromCollectionObject(cObject);
        assertTrue(tObject.getId().equalsIgnoreCase("22"));

    }

    //@Test
    public void findRecordsByIndex() {
        
        try {
            List<CollectionObject> cObjects = dataStore.getByIndex("testObject", "parentNode", "xP");
            assertTrue(cObjects.size() != 0);

            cObjects = dataStore.getByIndex("testObject", "parentNode", "44");
            assertTrue(cObjects.size() == 0);

            cObjects = dataStore.getByIndex("testObject", "parentNode", "yy");
            assertTrue(cObjects.size() == 0);

            cObjects = dataStore.getByIndex("testObject", "parentNode", "xP654");
            assertTrue(cObjects.size() == 1);
            TestObjectConverter converter = new TestObjectConverter();
            for (CollectionObject cObject : cObjects) {
                TestObject tObject = converter.convertFromCollectionObject(cObject);
                System.out.println(tObject.getName());
            }

        } catch (Exception e) {
            e.printStackTrace();
            assertFalse(true);
        }
        
           
    }
    public void doClear() {
        for (int i=0;i<10000;i++) {
            try {
                this.dataStore.removeObject("testObject", ""+i);
            } catch (CollectionNotFoundException ex) {
                Logger.getLogger(TenThousandRecordLoadTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void doSave() {
        try {
            //LWDataStoreFactory factory = new LWDataStoreFactory();
            //factory.init(this.getConfig());
            //DataStore dataStore = LWDataStoreFactory.getDataStore();
            for (int i=10000;i<100000;i++) {
                CollectionObject obj = new CollectionObject();
                obj.setTarget(new TestObject(""+i, "xP"+i, "x"+i));
                dataStore.putObject("testObject", obj, new TestObjectConverter());
            }
        } catch (CollectionNotFoundException ex) {
            Logger.getLogger(SaveTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
