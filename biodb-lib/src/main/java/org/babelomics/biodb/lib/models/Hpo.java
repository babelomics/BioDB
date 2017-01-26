package org.babelomics.biodb.lib.models;

import jdk.nashorn.internal.ir.annotations.Reference;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mmedina on 17/11/16.
 */
@Entity(noClassnameStored = true)
public class Hpo {

    @Id
    private ObjectId _id;

    private String id;
    private String name;


    @Reference
    private List<Hpo> children;

    public Hpo() {
        this.children = new ArrayList<>();
    }

    public Hpo(String id, String name) {
        this();
        this.id = id;
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Hpo> getChildren() {
        return children;
    }

    public void setChildren(List<Hpo> children) {
        this.children = children;
    }

    public void addChild(Hpo child) {
        this.children.add(child);
    }

    @Override
    public String toString() {
        return "Hpo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", children=" + children +
                '}';
    }

}
