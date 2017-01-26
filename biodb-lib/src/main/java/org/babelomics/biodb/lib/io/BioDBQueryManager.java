package org.babelomics.biodb.lib.io;

import com.mongodb.MongoClient;
import org.babelomics.biodb.lib.models.*;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.apache.commons.lang3.mutable.MutableLong;

import java.util.List;
import java.util.Set;


/**
 * Created by mmedina on 18/11/16.
 */
public class BioDBQueryManager {
    final Datastore datastore;

    public BioDBQueryManager(String host, String dbName) {
        Morphia morphia = new Morphia();
        morphia.mapPackage("org.babelomics.biodb.lib.models");
        this.datastore = morphia.createDatastore(new MongoClient(host), dbName);
        this.datastore.ensureIndexes();
    }

    public BioDBQueryManager(String dbName) {
        this("localhost", dbName);
    }

    public BioDBQueryManager(Datastore datastore) {
        this.datastore = datastore;
    }

    public Hpo getHpo(String id) {
        Query<Hpo> query = this.datastore.createQuery(Hpo.class);

        query.filter("id =", id);

        Hpo hpo = query.get();
        return hpo;
    }

    public Iterable<Hpo> getAllHpos(int limit, int skip, MutableLong count, String sort, String search) {
        Query<Hpo> query = this.datastore.createQuery(Hpo.class);
        
        if(!search.isEmpty()){
            String[] filterSplits =search.split(",");
            for (String s : filterSplits){
                String[] aux=s.split(":");
                query.field(aux[0]).containsIgnoreCase(aux[1]);
            }
        }

        if(!sort.isEmpty()){
            query.order(sort);
        }
        if ((skip != -1) && (limit != -1)) {
            query.offset(skip).limit(limit);
        }
//        System.out.println("query = " + query);
        Iterable<Hpo> aux = query.fetch();
        count.setValue(query.countAll());

        return aux;
    }

    public Go getGo(String id) {
        Query<Go> query = this.datastore.createQuery(Go.class);

        query.filter("id =", id);

        Go go = query.get();
        return go;
    }

    public Iterable<Go> getAllGos(int limit, int skip, MutableLong count, String sort, String search) {
        Query<Go> query = this.datastore.createQuery(Go.class);

        if(!search.isEmpty()){
            String[] filterSplits =search.split(",");
            for (String s : filterSplits){
                String[] aux=s.split(":");
                query.field(aux[0]).containsIgnoreCase(aux[1]);
            }
        }

        if(!sort.isEmpty()){
            query.order(sort);
        }
        if ((skip != -1) && (limit != -1)) {
            query.offset(skip).limit(limit);
        }

        Iterable<Go> aux = query.fetch();
        count.setValue(query.countAll());
        return aux;
    }


    public Iterable<Go> getFilteredGo(List<String> genes){
        Query<Go> query = this.datastore.createQuery(Go.class);

        query.field("genes").in(genes); //Go contains one of genes
//        query.field("genes").hasAllOf(genes); //Go contains all of genes

        Iterable<Go> aux = query.fetch();
        return aux;
    }

    public Iterable<Clinical> getClinicalData(int limit, int skip, MutableLong count, String sort, String search, String source) {
        Query<Clinical> query = this.datastore.createQuery(Clinical.class);

        if(!source.isEmpty()){
            String[] srcSplits =source.split(",");
            for (String s : srcSplits){
                query.filter("src =", s);
            }
        }

        if(!search.isEmpty()){
            String[] filterSplits =search.split(",");
            for (String s : filterSplits){
                String[] aux=s.split(":");
                query.field(aux[0]).containsIgnoreCase(aux[1]);
            }
        }

        if(!sort.isEmpty()){
            query.order(sort);
        }
        if ((skip != -1) && (limit != -1)) {
            query.offset(skip).limit(limit);
        }

        Iterable<Clinical> aux = query.fetch();
        count.setValue(query.countAll());
        return aux;
    }

     public Tissue getTissue(String name, Double geneExp) {
        Query<Tissue> query = this.datastore.createQuery(Tissue.class);

        query.filter("name =", name);
         //TODO:filter by expression value

        Tissue tissue = query.get();
        return tissue;
    }
    public Iterable<Tissue> getAllTissues(int limit, int skip, MutableLong count, String sort, String search) {
        Query<Tissue> query = this.datastore.createQuery(Tissue.class);

        if(!search.isEmpty()){
            String[] filterSplits =search.split(",");
            for (String s : filterSplits){
                String[] aux=s.split(":");
                query.field(aux[0]).containsIgnoreCase(aux[1]);
            }
        }

        if(!sort.isEmpty()){
            query.order(sort);
        }
        if ((skip != -1) && (limit != -1)) {
            query.offset(skip).limit(limit);
        }
//        System.out.println("query = " + query);
        Iterable<Tissue> aux = query.fetch();
        //TODO:return only the tissue names
        count.setValue(query.countAll());

        return aux;
    }
}
