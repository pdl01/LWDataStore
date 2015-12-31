/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.lwdatastore;

import com.hf.lwdatastore.exception.AttributeNotFoundException;
import com.hf.lwdatastore.exception.CollectionNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.JsonNode;
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
public class SaveTest extends LWDataStoreTest{

    
    public SaveTest() {
        
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
    // @Test
    // public void hello() {}
   @Test
   public void testSave() {
        try {
            //LWDataStoreFactory factory = new LWDataStoreFactory();
            //factory.init(this.getConfig());
            //DataStore dataStore = LWDataStoreFactory.getDataStore();
            CollectionObject obj = new CollectionObject();
            obj.setTarget(new TestObject("1","xP","x"));
            dataStore.putObject("testObject", obj,new TestObjectConverter());
            
            obj = new CollectionObject();
            obj.setTarget(new TestObject("2","xP","y"));
            dataStore.putObject("testObject", obj,new TestObjectConverter());

            obj = new CollectionObject();
            obj.setTarget(new TestObject("3","44","y"));
            dataStore.putObject("testObject", obj,new TestObjectConverter());
            try {
                Thread.sleep(100000);
            } catch (InterruptedException ex) {
                Logger.getLogger(SaveTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            //TODO: should shutdown datastore-not factory
            //factory.shutdown();
            
            
            //check that the files are saved
        } catch (CollectionNotFoundException ex) {
            Logger.getLogger(SaveTest.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
   }
}
