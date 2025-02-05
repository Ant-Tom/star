package com.starbank.star.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import java.util.List;

@Embeddable
public class RuleQuery {

    @Column(name = "query_type", nullable = false)
    private String queryType;

    // Храним список аргументов в одном столбце с помощью конвертера
    @Convert(converter = com.starbank.star.config.StringListConverter.class)
    @Column(name = "arguments")
    private List<String> arguments;

    @Column(name = "negate", nullable = false)
    private boolean negate;

    public RuleQuery() {}

    public RuleQuery(String queryType, List<String> arguments, boolean negate) {
        this.queryType = queryType;
        this.arguments = arguments;
        this.negate = negate;
    }

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
