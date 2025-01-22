package com.starbank.star.entity;

import jakarta.persistence.*;
import java.util.List;

@Embeddable
public class RuleQuery {

    @Column(name = "query_type", nullable = false)
    private String queryType;

    @ElementCollection
    @CollectionTable(name = "rule_query_arguments")
    private List<String> arguments;

    @Column(name = "negate", nullable = false)
    private boolean negate;

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public boolean isNegate() {
        return negate;
    }

    public void setNegate(boolean negate) {
        this.negate = negate;
    }
}