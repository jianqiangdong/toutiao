package com.running.toutiao.async;

import java.util.HashMap;
import java.util.Map;

public class EventModel {

    private EventType type;
    private int actorId;//触发这id
    private int entityType;
    private int entityId;//触发的目标对象
    private int entityOwnerId;//触发目标对象的拥有者

    private Map<String, String> exts = new HashMap<String, String>();

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public void setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public void setExts(Map<String, String> exts) {
        this.exts = exts;
    }

    public String getExts(String key) {
        return exts.get(key);
    }

    public void setExts(String key, String value) {
        exts.put(key, value);
    }


}
