/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.lwdatastore;

import com.hf.lwdatastore.exception.AttributeNotFoundException;
import java.util.Map;
import org.codehaus.jackson.JsonNode;

/**
 *
 * @author pldorrell
 */

public interface CollectionObjectConverter<K> {
    public CollectionObject convertToCollectionObject(K k);
    public K convertFromCollectionObject(CollectionObject object);
    public String getValue(K k,String attribute) throws AttributeNotFoundException;
    public K convertFromJSONNode(Map.Entry<String, JsonNode> jsonNode);
    public CollectionObject setId(CollectionObject collectionObject, String _id);
}
