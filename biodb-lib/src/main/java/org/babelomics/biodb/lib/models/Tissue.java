package org.babelomics.biodb.lib.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.mongodb.morphia.annotations.*;
import org.bson.types.ObjectId;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mmedina on 14/12/16.
 */

@Entity(noClassnameStored = true)
@Indexes({
        @Index(fields = @Field("name"), options = @IndexOptions(unique = true))
})
public class Tissue {
    @Id
    private ObjectId _id;

    private String name;
    @JsonIgnore
    private Set<Gene> genes;

    public Tissue() {
        this.genes = new HashSet<>();
    }

    public Tissue(String name) {
        this();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Gene> getGenes() {
        return genes;
    }

    public void setGenes(Set<Gene> genes) {
        this.genes = genes;
    }

    public void addNewGene(String name, double expression) {
        this.genes.add(new Gene(name, expression));
    }

    @Override
    public String toString() {
        return "Tissue{" +
                ", name='" + name + '\'' +
                ", genes=" + genes +
                '}';
    }
}
