package org.babelomics.biodb.lib.ws;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mmedina on 18/11/16.
 */
public class QueryResult<T> {
    private String id;
    private int time;
    private int dbTime;
    private int numResults;
    private long numTotalResults;
    private String warningMsg;
    private String errorMsg;
    private String featureType;
    private String resultType;
    public Class<T> clazz;
    private List<T> result;

    public QueryResult() {
        this("", -1, -1, -1L, "", "", new ArrayList());
    }

    public QueryResult(String id) {
        this(id, -1, -1, -1L, "", "", new ArrayList());
    }

    public QueryResult(String id, int dbTime, int numResults, long numTotalResults, String warningMsg, String errorMsg, List<T> result) {
        this.id = id;
        this.dbTime = dbTime;
        this.numResults = numResults;
        this.numTotalResults = numTotalResults;
        this.warningMsg = warningMsg;
        this.errorMsg = errorMsg;
        this.resultType = result.size() > 0?result.get(0).getClass().getCanonicalName():"";
        this.result = result;
    }

    public T first() {
        return this.result != null && this.result.size() > 0?this.result.get(0):null;
    }

    public String toString() {
        return "QueryResult{\nid=\'" + this.id + '\'' + "\n" + ", dbTime=" + this.dbTime + "\n" + ", numResults=" + this.numResults + "\n" + ", warningMsg=\'" + this.warningMsg + '\'' + "\n" + ", errorMsg=\'" + this.errorMsg + '\'' + "\n" + ", resultType=\'" + this.resultType + '\'' + "\n" + ", result=" + this.result + "\n" + '}';
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getDbTime() {
        return this.dbTime;
    }

    public void setDbTime(int dbTime) {
        this.dbTime = dbTime;
    }

    public int getNumResults() {
        return this.numResults;
    }

    public void setNumResults(int numResults) {
        this.numResults = numResults;
    }

    public long getNumTotalResults() {
        return this.numTotalResults;
    }

    public void setNumTotalResults(long numTotalResults) {
        this.numTotalResults = numTotalResults;
    }

    public String getWarningMsg() {
        return this.warningMsg;
    }

    public void setWarningMsg(String warningMsg) {
        this.warningMsg = warningMsg;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getFeatureType() {
        return this.featureType;
    }


    public void setFeatureType(String featureType) {
        this.featureType = featureType;
    }

    public String getResultType() {
        return this.resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public List<T> getResult() {
        return this.result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }


    public void addResult(T result) {
        this.resultType = result.getClass().getCanonicalName();
        this.result.add(result);
        this.numResults = this.result.size();
    }

    public void addAllResults(List<T> result) {
        if(result.size() > 0) {
            this.resultType = result.get(0).getClass().getCanonicalName();
        }

        this.result.addAll(result);
        this.numResults = this.result.size();
    }
}

