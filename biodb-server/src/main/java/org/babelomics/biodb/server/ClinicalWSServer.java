package org.babelomics.biodb.server;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.commons.lang3.mutable.MutableLong;
import org.babelomics.biodb.lib.models.Clinical;
import org.babelomics.biodb.lib.ws.QueryResponse;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
/**
 * Created by mmedina on 24/11/16.
 */
@Path("/clinical")
@Api(value = "clinical", description = "Clinical Data")
@Produces(MediaType.APPLICATION_JSON)
public class ClinicalWSServer extends BioDBWSServer{

    public ClinicalWSServer(@PathParam("version") String version, @Context UriInfo uriInfo, @Context HttpServletRequest httpServletRequest) throws IOException {
        super(version, uriInfo, httpServletRequest);
    }

    @GET
    @Path("/fetch")
    @Produces("application/json")
    @ApiOperation(value = "Get Clinical Data")
    public Response getAll(
            @ApiParam(value = "limit") @QueryParam("limit") @DefaultValue("10") int limit,
            @ApiParam(value = "skip") @QueryParam("skip") @DefaultValue("0") int skip,
            @ApiParam(value = "sort") @QueryParam("sort") @DefaultValue("phenotype") String sort,
            @ApiParam(value = "search") @QueryParam("search") @DefaultValue("") String search,
            @ApiParam(value = "source") @QueryParam("source") @DefaultValue("") String source


    ) {

        MutableLong count = new MutableLong(-1);
        Iterable<Clinical> clinical = qm.getClinicalData(limit, skip, count, sort, search, source);
        QueryResponse qr = createQueryResponse(clinical);

        qr.setNumTotalResults(count.getValue());

        return createOkResponse(qr);
    }

}

