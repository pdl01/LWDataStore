/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hfw.lwdatastore;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author pldorrell
 */
public class LWDataStoreFactory {

    private static DataStore dataStore = null;

    public void init() {

        //build
    }

    public void init(DataStoreConfig dsConfig) {
        String dataDir = "";
        if (dataStore == null) {
            initDataStore(dsConfig);
        }
        //kick off a sychronizer thread

    }

    private void initDataStore(DataStoreConfig dsConfig) {
        dataStore = new DataStoreImpl();
        //load the data for all known collectionNames
        List<CollectionDescription> collectionDescriptions = dsConfig.getCollections();
        if (collectionDescriptions != null) {
            //there are no collections defined
            for (CollectionDescription c : collectionDescriptions) {
                dataStore.createCollection(c.getName(), c, null);
                DSCollection collection = dataStore.getCollection(c.getName());

                //load the index file
                //load the data file\
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
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void readDataFile(DSCollection collection, String dataDir) {
        File file = new File(dataDir + File.separator + collection.getCollectionDescription().getName() + ".data");
    }

    private void writeIndexFile(DSCollection collection, String dataDir) {
        File file = new File(dataDir + File.separator + collection.getCollectionDescription().getName() + ".idx");

    }

    public static void saveToConfig(Object config,String fileName) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonFactory factory = new JsonFactory();
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("repository", config);
        JsonNode jsonNode = objectMapper.valueToTree(hashMap);
        try {
            objectMapper.writeValue(new File(fileName), jsonNode);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
