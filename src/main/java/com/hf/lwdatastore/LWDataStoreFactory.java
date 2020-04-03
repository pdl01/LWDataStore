package com.hf.lwdatastore;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author pldorrell
 */
public class LWDataStoreFactory {
    private static final Logger log = LogManager.getLogger(LWDataStoreFactory.class);
    
    private static DataStore dataStore = null;
    private DiskSyncronizer synchronizer;
    public void init() {

        //build
    }

    public void init(DataStoreConfig dsConfig) {
        String dataDir = "";
        if (dataStore == null) {
            initDataStore(dsConfig);
        }
        //kick off a sychronizer thread
        synchronizer = new DiskSyncronizer();
        synchronizer.setDataStore(dataStore);
        synchronizer.setDataStoreConfig(dsConfig);
        Thread thread = new Thread(synchronizer);
        thread.start();

    }
    public void shutdown() {
        if (synchronizer != null) {
            synchronizer.doWork();
        }

    }
    public static DataStore getDataStore(){
        return LWDataStoreFactory.dataStore;
    }
    
    private void initDataStore(DataStoreConfig dsConfig) {
        dataStore = DataStoreImpl.getInstance();
        
        File dataDir = new File(dsConfig.getDataDir());
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        //load the data for all known collectionNames
        List<CollectionDescription> collectionDescriptions = dsConfig.getCollections();
        if (collectionDescriptions != null) {
            //there are no collections defined
            for (CollectionDescription c : collectionDescriptions) {
                dataStore.createCollection(c.getName(), c, null);
                DSCollection collection = dataStore.getCollection(c.getName());

                //load the data file
                this.readDataFile(collection, dsConfig.getDataDir());
                //do we need to load the index file
                //load the index file
                
            }
        }

    }
    /*
     Index set will be a json file in the form:
     {"indexSet":{"name":"attribute1",values:[{"value1":["key1","key2","key3"],"value2":["key1","key2","key3"}],{"name":"attribute2",values:}}
     */

    private void readIndexFile(DSCollection collection, String dataDir) {
        File file = new File(dataDir + File.separator + collection.getCollectionDescription().getName() + ".idx");
        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        try {
            JsonNode rootNode = mapper.readTree(file);
            Iterator<Map.Entry<String, JsonNode>> fields = rootNode.getFields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                if (field.getKey().equals("indexSet")) {
                    Iterator<Map.Entry<String, JsonNode>> repo_fields = field.getValue().getFields();
                    while (repo_fields.hasNext()) {
                        Map.Entry<String, JsonNode> repo_field = repo_fields.next();
                        if (repo_field.getKey().equals("host")) {
                        } else if (repo_field.getKey().equals("port")) {
                        } else if (repo_field.getKey().equals("db")) {
                        } else if (repo_field.getKey().equals("username")) {
                        } else if (repo_field.getKey().equals("password")) {
                        } else if (repo_field.getKey().equals("type")) {
                            //applicationRepositoryConfig.setType(repo_field.getValue().asText());
                        }
                    }
                }
            }
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            log.error(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error(e);
        }

    }

    private void readDataFile(DSCollection collection, String dataDir) {
        File file = new File(dataDir + File.separator + collection.getCollectionDescription().getName() + ".data");
        log.info("Reading:"+file.getPath());
        if (file.exists() && file.canRead()) {
        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        try {
            
            
            JsonNode rootNode = mapper.readTree(file);
            System.out.println(rootNode);
            
            //List<JsonNode> dataEntries = rootNode.findValues("_data");
            
            
            //JsonNode jnode = rootNode.get(0);
            Iterator<Map.Entry<String, JsonNode>> fields = rootNode.getFields();
            
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                System.out.println(field.getKey());
                System.out.println(field.getValue());
                Object obj = collection.getCollectionDescription().getConverter().convertFromJSONNode(field);
                CollectionObject cObj = new CollectionObject();
                cObj.setTarget(obj);
                collection.putObject(cObj, collection.getCollectionDescription().getConverter());                
            } 
/*            
            while (fields.hasNext()) {
                
                Map.Entry<String, JsonNode> field = fields.next();
                //if (field.getKey().equals("_data")) {

                    Iterator<Map.Entry<String, JsonNode>> data_entries = field.getValue().getFields();
                    while (data_entries.hasNext()) {
                        Map.Entry<String, JsonNode> dataEntry = data_entries.next();
                        
                        Object obj = collection.getCollectionDescription().getConverter().convertFromJSONNode(dataEntry);
                        CollectionObject cObj = new CollectionObject();
                        cObj.setTarget(obj);
                        collection.putObject(cObj, collection.getCollectionDescription().getConverter());
                    }
                //}
            }
  */          
            
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            log.error(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error(e);
        }

            
            
        }
    }


}
