package org.babelomics.biodb.lib.models;

import java.util.Objects;

public class Gene{
    private String name;
    private Double expression;
    public Gene() {
    }
    public Gene(String name, Double expression) {
        this();
        this.name = name;
        this.expression = expression;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getExpression() {
        return expression;
    }

    public void setExpression(Double expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "Gene{" +
                "name='" + name + '\'' +
                ", expression=" + expression +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Gene gene = (Gene) o;

        if (!name.equals(gene.name)) return false;

        return expression.equals(gene.expression);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + expression.hashCode();
        return result;
    }
}
