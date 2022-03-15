package com;

import com.mcd.Property;

import java.util.List;
import java.util.Map;

public class DataDictionary {
    private List<Property> dictionary;

    public DataDictionary() {}

    public DataDictionary(List<Property> dictionary) {
        this.dictionary = dictionary;
    }

    public List<Property> getDictionary() {
        return dictionary;
    }

    public void setDictionary(List<Property> dictionary) {
        this.dictionary = dictionary;
    }

    public void addData(Property property) {
        this.dictionary.add(property);
    }

    public void removeData(Property property) {
        this.dictionary.remove(property);
    }

    /**
     * @param propertyCode is the code of the code or name of the property we're looking for
     * @return Property
     */
    public Property searchProperty(String propertyCode){
        return this.dictionary.stream()
                .filter(p -> p.getCode().equals(propertyCode))
                .findAny()
                .orElse(null);
    }

    @Override
    public String toString() {
        return "DataDictionary{" +
                "dictionary=" + dictionary +
                "}";
    }
}
