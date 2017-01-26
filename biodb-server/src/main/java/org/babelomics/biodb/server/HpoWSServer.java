package org.babelomics.biodb.server;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.commons.lang3.mutable.MutableLong;
import org.babelomics.biodb.lib.models.Hpo;
import org.babelomics.biodb.lib.ws.QueryResponse;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mmedina on 18/11/16.
 */
@Path("/hpo")
@Api(value = "hpo", description = "Hpo")
@Produces(MediaType.APPLICATION_JSON)
public class HpoWSServer extends BioDBWSServer{

    public HpoWSServer(@PathParam("version") String version, @Context UriInfo uriInfo, @Context HttpServletRequest httpServletRequest) throws IOException {
        super(version, uriInfo, httpServletRequest);
    }

    @GET
    @Path("/info")
    @Produces("application/json")
    @ApiOperation(value = "Get HPO Info")
    public Response getHpoInfo(
            @ApiParam(value = "id") @QueryParam("id") @DefaultValue("") String id
    ) {
        List<Hpo> hpoList = new ArrayList<>();

        if (id.length() > 0) {
            String[] idSplits = id.split(",");
            for (String s : idSplits) {
                Hpo hp = qm.getHpo(s);
                hpoList.add(hp);
            }
        }


        QueryResponse qr = createQueryResponse(hpoList);
        return createOkResponse(qr);
    }

    @GET
    @Path("/fetch")
    @Produces("application/json")
    @ApiOperation(value = "Get All Hpo")
    public Response getAll(
        @ApiParam(value = "limit") @QueryParam("limit") @DefaultValue("10") int limit,
        @ApiParam(value = "skip") @QueryParam("skip") @DefaultValue("0") int skip,
        @ApiParam(value = "sort") @QueryParam("sort") @DefaultValue("id") String sort,
        @ApiParam(value = "search") @QueryParam("search") @DefaultValue("") String search

    ) {

        MutableLong count = new MutableLong(-1);
        Iterable<Hpo> hpo = qm.getAllHpos(limit, skip, count, sort, search);
        QueryResponse qr = createQueryResponse(hpo);

        qr.setNumTotalResults(count.getValue());

        return createOkResponse(qr);
    }

}

