package org.babelomics.biodb.server;

import com.fasterxml.jackson.databind.util.ArrayIterator;
import com.google.common.base.Splitter;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.commons.lang3.mutable.MutableLong;
import org.babelomics.biodb.lib.io.BioDBQueryManager;
import org.babelomics.biodb.lib.models.Go;
import org.babelomics.biodb.lib.ws.QueryResponse;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.util.*;

/**
 * Created by mmedina on 18/11/16.
 */
@Path("/go")
@Api(value = "go", description = "Go")
@Produces(MediaType.APPLICATION_JSON)
public class GoWSServer extends BioDBWSServer {

    public GoWSServer(@PathParam("version") String version, @Context UriInfo uriInfo, @Context HttpServletRequest httpServletRequest) throws IOException {
        super(version, uriInfo, httpServletRequest);
    }

    @GET
    @Path("/info")
    @Produces("application/json")
    @ApiOperation(value = "Get GO info")
    public Response getGoInfo(
            @ApiParam(value = "id") @QueryParam("id") @DefaultValue("") String id
    ) {
        List<Go> goList = new ArrayList<>();

        if (id.length() > 0) {
            String[] idSplits = id.split(",");
            for (String s : idSplits) {
                Go g = qm.getGo(s);
                goList.add(g);
            }
        }

        QueryResponse qr = createQueryResponse(goList);
        return createOkResponse(qr);
    }

    @GET
    @Path("/fetch")
    @Produces("application/json")
    @ApiOperation(value = "Get All Go")
    public Response getAll(
            @ApiParam(value = "limit") @QueryParam("limit") @DefaultValue("10") int limit,
            @ApiParam(value = "skip") @QueryParam("skip") @DefaultValue("0") int skip,
            @ApiParam(value = "sort") @QueryParam("sort") @DefaultValue("id") String sort,
            @ApiParam(value = "search") @QueryParam("search") @DefaultValue("") String search

    ) {

        MutableLong count = new MutableLong(-1);
        Iterable<Go> go = qm.getAllGos(limit, skip, count, sort, search);
        QueryResponse qr = createQueryResponse(go);

        qr.setNumTotalResults(count.getValue());

        return createOkResponse(qr);
    }

    @GET
    @Path("/filter")
    @Produces("application/json")
    @ApiOperation(value = "Get GO by filter")
    public Response getGoFilter(
            @ApiParam(value = "genes") @QueryParam("genes") @DefaultValue("") String genes
    ) {
        List<String> geneList = Splitter.on(",").splitToList(genes);
        Iterable<Go> goList = qm.getFilteredGo(geneList);

        QueryResponse qr = createQueryResponse(goList);
        qr.setNumTotalResults(qr.getNumResults());

        return createOkResponse(qr);
    }

}
