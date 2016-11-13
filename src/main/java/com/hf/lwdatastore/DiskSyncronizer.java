package com.hf.lwdatastore;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author pldorrell
 */
public class DiskSyncronizer implements Runnable {

    private static final Logger log = Logger.getLogger(DiskSyncronizer.class);
    private DataStore dataStore;
    private DataStoreConfig dataStoreConfig;

    public DataStore getDataStore() {
        return dataStore;
    }

    public void setDataStore(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public void setDataStoreConfig(DataStoreConfig config) {
        this.dataStoreConfig = config;
    }

    @Override
    public void run() {
        try {
            while (true) {
                try {
                    log.debug("start of loop");
                    this.doWork();
                    Thread.sleep(5000);
                    log.debug("after sleep");
                } catch (InterruptedException ex) {
                    log.error("Interuptted while doing work", ex);
                    System.out.println(ex);
                }
            }
        } finally {
            this.doWork();
        }

    }

    public synchronized void doWork() {
        log.debug("Entering doWork");
        for (CollectionDescription coll : dataStoreConfig.getCollections()) {
            DSCollection dsCollection = dataStore.getCollection(coll.getName());
            log.debug(coll.getName() + ":" + dsCollection.isDirty());
            if (dsCollection != null && dsCollection.isDirty()) {
                this.writeIndexFile(dsCollection, this.dataStoreConfig.getDataDir());
                this.writeDataFile(dsCollection, this.dataStoreConfig.getDataDir());
            }
        }
        log.debug("Exiting doWork");

    }

    private void writeIndexFile(DSCollection collection, String dataDir) {
        log.debug("Entering writeIndexFile");
        String fileName = dataDir + File.separator + collection.getCollectionDescription().getName() + ".idx";

        HashMap<String, Object> indexMap = new HashMap<String, Object>();
        indexMap.put("_primaryKey", collection.getKeyIndex());

        for (String keyName : collection.getCollectionDescription().getIndexedAttributes()) {
            Map<String, Set<String>> index = collection.getIndex(keyName);
            indexMap.put(keyName, index);
        }
        
        this.writeJsonFile("_indexSet", indexMap, fileName);
        log.debug("Exiting writeIndexFile");
    }

    private void writeDataFile(DSCollection collection, String dataDir) {
        log.debug("Entering writeDataFile");
        String fileName = dataDir + File.separator + collection.getCollectionDescription().getName() + ".data";

        this.writeJsonFile("_data", collection.getData(), fileName);
        log.debug("Exiting writeDataFile");
    }

    private void writeJsonFile(String rootElement, Object data, String fileName) {
        log.debug("Entering writeJsonFile"+fileName);
        File outputFile = new File(fileName);
        if (!outputFile.exists()) {
            try {
                outputFile.createNewFile();
            } catch (IOException ex) {
                log.warn("IOExcption encountered", ex);
            }

        }
        log.debug("Writing " + outputFile.getName());
        ObjectMapper objectMapper = new ObjectMapper();
        JsonFactory factory = new JsonFactory();
        //HashMap<String, Object> hashMap = new HashMap<String, Object>();
        //hashMap.put(rootElement, config);
        log.debug("Object:" + data);
        //HashMap<String,Object> fileDataMap = new HashMap<>();
        //fileDataMap.put(rootElement, data);
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.valueToTree(data);
            //log.debug(jsonNode);
            objectMapper.writeValue(outputFile, jsonNode);
        } catch (IOException ex) {
            //log.error(ex);
        } catch (Exception e) {
            log.error("Error in writing out jsonNode", e);
            log.error(jsonNode);
        }
        //log.debug("Exiting writeJSONFile");
    }

}
