package org.babelomics.biodb.lib.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;
import org.mongodb.morphia.utils.IndexType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mmedina on 18/11/16.
 */
@Entity(noClassnameStored = true)
@Indexes({
        @Index(fields = @Field("id"), options = @IndexOptions(unique = true))
})
public class Go {
    @Id
    private ObjectId _id;

    private String id;
    private String name;

    @JsonIgnore
    private Set<String> genes;

    @Reference
    private List<Go> children;

    public Go() {
        this.children = new ArrayList<>();
        this.genes = new HashSet<>();
    }

    public Go(String name, String id) {
        this();
        this.name = name;
        this.id = id;
    }


    public Set<String> getGenes() {
        return genes;
    }

    public void setGenes(Set<String> genes) {
        this.genes = genes;
    }

    public List<Go> getChildren() {
        return children;
    }

    public void setChildren(List<Go> children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addChild(Go child) {
        this.children.add(child);
    }

    @Override
    public String toString() {
        return "Go{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", children=" + children +
                '}';
    }
}
