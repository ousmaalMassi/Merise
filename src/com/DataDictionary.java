package com;

import com.mcd.Property;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataDictionary {
    private Map<String, List<Property>> dictionary;

    public DataDictionary() {}

    public DataDictionary(Map<String, List<Property>> dictionary) {
        this.dictionary = dictionary;
    }


    public Map<String, List<Property>> getDictionary() {
        return dictionary;
    }

    public void addData(String name, Property property) {
        List<Property> propertyList = this.dictionary.get(name);
        propertyList.add(property);
        this.dictionary.put(name, propertyList);
    }

    public void removeData(String name, String propertyName) {
        List<Property> propertyList = this.dictionary.get(name);
        Property property = propertyList.stream()
                .filter(p -> p.getName().equals(propertyName))
                .findAny()
                .orElse(null);
        if (property != null)
            propertyList.remove(property);
        this.dictionary.put(name, propertyList);
    }

    public void setDictionary(Map<String, List<Property>> dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public String toString() {
        return "DataDictionary{" +
                "dictionary=" + dictionary +
                '}';
    }
}
