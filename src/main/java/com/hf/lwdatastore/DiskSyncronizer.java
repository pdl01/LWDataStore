package com.hf.lwdatastore;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author pldorrell
 */
public class DiskSyncronizer implements Runnable {

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
                    System.out.println("start of loop");
                    this.doWork();
                    Thread.sleep(5000);
                    System.out.println("after sleep");
                } catch (InterruptedException ex) {
                    System.out.println(ex);
                }
            }
        } finally {
            this.doWork();
        }

    }

    public void doWork() {
        for (CollectionDescription coll : dataStoreConfig.getCollections()) {
            DSCollection dsCollection = dataStore.getCollection(coll.getName());
            if (dsCollection != null && dsCollection.isDirty()) {
                this.writeIndexFile(dsCollection, this.dataStoreConfig.getDataDir());
                this.writeDataFile(dsCollection, this.dataStoreConfig.getDataDir());
            }
        }

    }

    private void writeIndexFile(DSCollection collection, String dataDir) {
        String fileName = dataDir + File.separator + collection.getCollectionDescription().getName() + ".idx";

        HashMap<String, Object> indexMap = new HashMap<String, Object>();
        indexMap.put("_primaryKey", collection.getKeyIndex());

        for (String keyName : collection.getCollectionDescription().getIndexedAttributes()) {
            Map<String, Set<String>> index = collection.getIndex(keyName);
            indexMap.put(keyName, index);
        }
        this.writeJsonFile("_indexSet", indexMap, fileName);
    }

    private void writeDataFile(DSCollection collection, String dataDir) {
        String fileName = dataDir + File.separator + collection.getCollectionDescription().getName() + ".data";

        this.writeJsonFile("_data", collection.getData(), fileName);
    }

    private void writeJsonFile(String rootElement, Object config, String fileName) {
        File outputFile = new File(fileName);
        if (!outputFile.exists()) {
            try {
                outputFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(DiskSyncronizer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        ObjectMapper objectMapper = new ObjectMapper();
        JsonFactory factory = new JsonFactory();
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put(rootElement, config);
        JsonNode jsonNode = objectMapper.valueToTree(hashMap);
        try {
            objectMapper.writeValue(outputFile, jsonNode);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
