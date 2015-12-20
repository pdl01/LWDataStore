package com.hfw.lwdatastore;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author pldorrell
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class IndexObject {
    private String indexName;
    private Set<String> values;

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public Set<String> getValues() {
        return values;
    }

    public void setValues(Set<String> values) {
        this.values = values;
    }
    public void addValue(String value){
        if (this.values != null) {
            this.values.add(value);
        } else {
            this.values = new TreeSet<String>();
            this.values.add(value);
        }
        
    }
    
}
