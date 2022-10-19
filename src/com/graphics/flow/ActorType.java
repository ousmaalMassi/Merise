package com.graphics.flow;

public enum ActorType {
    INTERNAL_ACTOR("internal actor"),
    EXTERNAL_ACTOR("external actor");

    final String actorType;

    ActorType(String actorType) {
        this.actorType = actorType;
    }

    @Override
    public String toString() {
        return actorType;
    }
}
