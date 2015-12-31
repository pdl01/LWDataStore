/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.lwdatastore;

import com.hf.lwdatastore.exception.CollectionNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pldorrell
 */
public class LoadRecordsTest extends LWDataStoreTest {

    public LoadRecordsTest() {
    }

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
    }

    @After
    public void tearDown() {
        factory.shutdown();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void loadRecordsByPK() {

        CollectionObject cObject = null;
        try {
            cObject = dataStore.getObject("testObject", "1");
        } catch (CollectionNotFoundException ex) {
            Logger.getLogger(LoadRecordsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        TestObject tObject = (new TestObjectConverter()).convertFromCollectionObject(cObject);
        assertTrue(tObject.getId().equalsIgnoreCase("1"));
    
       
    }

    @Test
    public void findRecordsByIndex() {

        CollectionObject cObject = null;
        try {
            cObject = dataStore.getObject("testObject", "1");
        } catch (CollectionNotFoundException ex) {
            Logger.getLogger(LoadRecordsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        TestObject tObject = (TestObject)cObject.getTarget();
        assertTrue(tObject.getId().equalsIgnoreCase("1"));
        
        
        
    }

}
