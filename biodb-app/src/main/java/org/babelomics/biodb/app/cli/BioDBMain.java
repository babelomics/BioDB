package org.babelomics.biodb.app.cli;

import com.mongodb.MongoClient;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.babelomics.biodb.lib.models.*;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by mmedina on 17/11/16.
 */
public class BioDBMain {
    public static void main(String[] args) {

        Datastore datastore;

        final Morphia morphia = new Morphia();
        morphia.mapPackage("org.babelomics.biodb.lib.models");

        MongoClient mongoClient = new MongoClient("mem18");

        datastore = morphia.createDatastore(mongoClient, "biodb");
        datastore.ensureIndexes();

        String hpoFilePath = "/home/mmedina/projects/Ontology/hp.obo";
        String goFilePath = "/home/mmedina/projects/Ontology/go.obo";
        String goFileGene = "/home/mmedina/projects/Ontology/gene_association.goa_human";
        String clinicalFilePath = "/home/mmedina/projects/Ontology/clinical/phenotypes_from_clinical.txt";
        String tissueFilePath = "/home/mmedina/projects/Ontology/tissues/normal_tissue.csv";


//        parseHPO(datastore, hpoFilePath);
//        Map<String, Set<String>> goMap = parseGoGene(goFileGene);
//        parseGO(datastore, goFilePath, goMap);
//        parseClinical(datastore,clinicalFilePath);
        parseTissues(datastore, tissueFilePath);


    }

    private static void parseTissues(Datastore datastore, String tissueFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(tissueFilePath))) {
            String line;
            Map<String, Tissue> tissueMap = new HashMap<>();

            while ((line = br.readLine()) != null) {
                if(line.contains("Gene")){
                    continue;
                }else{
                    Tissue tissue;
                    String[] lineSplits = line.split("\t");
                    String t = lineSplits[2];

                    if (tissueMap.containsKey(t)) {
                        tissue = tissueMap.get(t);
                    } else {
                        tissue = new Tissue();
                        tissue.setName(t);
                    }
                    Double value=0.0;

                    if (lineSplits[4].equalsIgnoreCase("Low")){
                        value=0.1;
                    }else if(lineSplits[4].equalsIgnoreCase("Medium")){
                        value=0.5;
                    }else if(lineSplits[4].equalsIgnoreCase("High")){
                        value=1.0;
                    }

                    tissue.addNewGene(lineSplits[1], value);

                    tissueMap.put(t, tissue);
                }

            }
            datastore.save(tissueMap.values());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseClinical(Datastore datastore, String clinicalFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(clinicalFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                Clinical clinical = new Clinical();
                clinical.setPhenotype(line.trim());
                datastore.save(clinical);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseHPO(Datastore datastore, String hpoFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(hpoFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("[Term]")) {
                    Hpo hpo = new Hpo();
                    while ((line = br.readLine()) != null && !line.equalsIgnoreCase("")) {
                        if (line.startsWith("id:")) {
                            hpo.setId(line.replace("id: ", ""));
                        } else if (line.startsWith("name:")) {
                            hpo.setName(line.replace("name: ", ""));
                        }
                    }
                    datastore.save(hpo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        try (BufferedReader br = new BufferedReader(new FileReader(hpoFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("[Term]")) {
                    Hpo parent = null;
                    while ((line = br.readLine()) != null && !line.equalsIgnoreCase("")) {
                        Hpo child = null;
                        Query<Hpo> query = datastore.createQuery(Hpo.class);

                        if (line.startsWith("id:")) {
                            String id = line.replace("id: ", "");
                            parent = query.field("id").equal(id).get();
                            parent.setChildren(new ArrayList<>());

                        } else if (line.startsWith("is_a:")) {
                            String[] splits = line.split(" ");
                            child = query.field("id").equal(splits[1]).get();
                        }
                        if (parent != null && child != null) {
                            child.addChild(parent);
                            datastore.save(child);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseGO(Datastore datastore, String goFilePath, Map<String, Set<String>> goMap) {
        try (BufferedReader br = new BufferedReader(new FileReader(goFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("[Term]")) {
                    Go go = new Go();
                    while ((line = br.readLine()) != null && !line.equalsIgnoreCase("")) {
                        if (line.startsWith("id:")) {
                            go.setId(line.replace("id: ", ""));
                        } else if (line.startsWith("name:")) {
                            go.setName(line.replace("name: ", ""));
                        }
                    }

                    if (goMap.containsKey(go.getId())) {
                        go.setGenes(goMap.get(go.getId()));
                    }
                    datastore.save(go);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        try (BufferedReader br = new BufferedReader(new FileReader(goFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("[Term]")) {
                    Go parent = null;
                    while ((line = br.readLine()) != null && !line.equalsIgnoreCase("")) {
                        Go child = null;
                        Query<Go> query = datastore.createQuery(Go.class);

                        if (line.startsWith("id:")) {
                            String id = line.replace("id: ", "");
                            parent = query.field("id").equal(id).get();
                            parent.setChildren(new ArrayList<>());

                        } else if (line.startsWith("is_a:")) {
                            String[] splits = line.split(" ");
                            child = query.field("id").equal(splits[1]).get();
                        }
                        if (parent != null && child != null) {
                            child.addChild(parent);
                            datastore.save(child);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Set<String>> parseGoGene(String goFileGene) {
        Map<String, Set<String>> goMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(goFileGene))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("UniProtKB")) {
                    String[] splits = line.split("\t");
                    String gen = splits[2];
                    String go = splits[4];
                    Set<String> genes;
                    if (goMap.containsKey(go)) {
                        genes = goMap.get(go);
                    } else {
                        genes = new HashSet<>();
                    }

                    genes.add(gen);
                    goMap.put(go, genes);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return goMap;

    }
}
