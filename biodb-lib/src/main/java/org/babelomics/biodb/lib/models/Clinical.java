package org.babelomics.biodb.lib.models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;


/**
 * Created by mmedina on 24/11/16.
 */
@Entity(noClassnameStored = true)
@Indexes({
        @Index(fields = @Field("phenotype"), options = @IndexOptions(unique = true))
})
public class Clinical {
    @Id
    private ObjectId _id;

    private String phenotype;
    private String src;

    public Clinical() {
    }

    public Clinical(String phenotype, String source) {
        this();
        this.phenotype = phenotype;
        this.src = source;
    }

    public String getPhenotype() {
        return phenotype;
    }

    public void setPhenotype(String phenotype) {
        this.phenotype = phenotype;
    }

    public String getSource() {
        return src;
    }

    public void setSource(String source) {
        this.src = source;
    }

    @Override
    public String toString() {
        return "Clinical{" +
                "phenotype='" + phenotype + '\'' +
                ", src='" + src + '\'' +
                '}';
    }
}
