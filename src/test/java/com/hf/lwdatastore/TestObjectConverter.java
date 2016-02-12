/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.lwdatastore;

import com.hf.lwdatastore.exception.AttributeNotFoundException;
import java.io.IOException;
import java.util.Map;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author pldorrell
 */
public class TestObjectConverter implements CollectionObjectConverter<TestObject> {

        @Override
        public CollectionObject convertToCollectionObject(TestObject k) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public TestObject convertFromCollectionObject(CollectionObject object) {
            return (TestObject)object.getTarget();
        }

        @Override
        public String getValue(TestObject k, String attribute) throws AttributeNotFoundException{
            if (attribute.equalsIgnoreCase("id")) {
                return k.getId();
            } else if (attribute.equalsIgnoreCase("parentNode")) {
                return k.getParentNode();
            } else if (attribute.equalsIgnoreCase("name")) {
                return k.getName();
            } else {
                throw new AttributeNotFoundException();
            }

        }

        @Override
        public TestObject convertFromJSONNode(Map.Entry<String, JsonNode> jsonNode) {
            JsonFactory factory = new JsonFactory();
            ObjectMapper mapper = new ObjectMapper(factory);
            JsonNode jsonNodex = mapper.valueToTree(jsonNode);
            try {
             TestObject account = mapper.treeToValue(jsonNode.getValue(), TestObject.class);
                return account;
            } catch (IOException ex) {
                ex.printStackTrace();
            
            }

        return null;

            /*
            TestObject tObject = new TestObject();
            ;
            String id = jsonNode.getValue().get("id").getTextValue();
            tObject.setId(id);
            String name = jsonNode.getValue().get("name").getTextValue();
            tObject.setName(name);
            String parentNode = jsonNode.getValue().get("parentNode").getTextValue();
            tObject.setParentNode(parentNode);
            
            //System.out.println(id);
            return tObject;
                    */
        }

    @Override
    public CollectionObject setId(CollectionObject collectionObject, String _id) {
        ((TestObject)collectionObject.getTarget()).setId(_id);
        return collectionObject;
    }




       
   }

