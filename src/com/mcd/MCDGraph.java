package com.mcd;

import com.exception.DuplicateMeriseObject;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MCDGraph {

    private final List<Entity> entities;
    private final List<Association> associations;

    /**
     * constructor
     */
    public MCDGraph() {
        entities = new ArrayList<>();
        associations = new ArrayList<>();
    }

    /**
     * @return entity list
     */
    public List<Entity> getEntities() {
        return entities;
    }

    /**
     * @return association list
     */
    public List<Association> getAssociations() {
        return associations;
    }

    /**
     * @param entity to add in the list
     */
    public void addEntity(Entity entity) throws DuplicateMeriseObject {
        if (this.containsEntity(entity.getName()) == null) {
            this.entities.add(entity);
        } else {
            throw new DuplicateMeriseObject("Duplicate Entity: '" + entity.getName() + "' Entity");
        }
    }

    /**
     * @param association to add in the list
     */
    public void addAssociation(Association association) throws DuplicateMeriseObject {
        if (this.containsAssociation(association.getName()) == null) {
            this.associations.add(association);
        } else {
            throw new DuplicateMeriseObject("Duplicate Association in: '" + association.getName() + "' Association");
        }
    }

    public Entity containsEntity(String string) {
        return this.entities.stream()
                .filter(entity -> entity.getName().equals(string))
                .findAny()
                .orElse(null);
    }

    public Association containsAssociation(String string) {
        return this.associations.stream()
                .filter(association -> association.getName().equals(string))
                .findAny()
                .orElse(null);
    }

    public void removeEntity(Entity entity) {
        this.entities.remove(entity);
        this.associations.forEach(association ->
                association.getLinks().remove(entity.getName())
        );
    }

    public void removeAssociation(Association association) {
        this.associations.remove(association);
    }

    /**
     * @param association that associate a list of entities
     * @param entity      to associate with
     */
    public void link(Entity entity, Association association) {
        String entityName = entity.getName();
        association.getLinks().put(entityName, Cardinalities.DEFAULT_CARDINALITY);
    }

    /**
     * @param association that associate a list of entities
     * @param entityName  to associate with
     */
    public void unlink(Association association, String entityName) {
        association.getLinks().remove(entityName);
    }

    @Override
    public String toString() {
        return """
                   __ entityList : %s,
                  |
                  |__ associationList : %s""".formatted(this.entities, this.associations);
    }
}
