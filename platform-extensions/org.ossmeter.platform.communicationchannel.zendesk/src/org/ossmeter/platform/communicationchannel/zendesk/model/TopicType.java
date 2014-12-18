package org.ossmeter.platform.communicationchannel.zendesk.model;

public enum TopicType {
    ARTICLES("Articles"),
    QUESTIONS("Questions"),
    IDEAS("Ideas");

    private final String name;

    private TopicType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}