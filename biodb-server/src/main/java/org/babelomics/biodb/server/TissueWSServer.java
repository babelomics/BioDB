package org.babelomics.biodb.server;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.commons.lang3.mutable.MutableLong;
import org.babelomics.biodb.lib.models.Hpo;
import org.babelomics.biodb.lib.models.Tissue;
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
 * Created by mmedina on 15/12/16.
 */
@Path("/tissue")
@Api(value = "tissue", description = "Tissue")
@Produces(MediaType.APPLICATION_JSON)
public class TissueWSServer extends BioDBWSServer {

    public TissueWSServer(@PathParam("version") String version, @Context UriInfo uriInfo, @Context HttpServletRequest httpServletRequest) throws IOException {
        super(version, uriInfo, httpServletRequest);
    }

    @GET
    @Path("/info")
    @Produces("application/json")
    @ApiOperation(value = "Get Tissue")
    public Response getAll(
            @ApiParam(value = "name") @QueryParam("name") @DefaultValue("") String name,
            @ApiParam(value = "geneExp") @QueryParam("geneExp") @DefaultValue("0") Double geneExp
    ) {
        List<Tissue> tissueList = new ArrayList<>();

        if (name.length() > 0) {
            String[] nameSplits = name.split(",");
            for (String s : nameSplits) {
                Tissue tissue = qm.getTissue(s,geneExp);
                tissueList.add(tissue);
            }
        }


        QueryResponse qr = createQueryResponse(tissueList);
        return createOkResponse(qr);
    }

    @GET
    @Path("/fetch")
    @Produces("application/json")
    @ApiOperation(value = "Get All Tissues")
    public Response getAll(
            @ApiParam(value = "limit") @QueryParam("limit") @DefaultValue("10") int limit,
            @ApiParam(value = "skip") @QueryParam("skip") @DefaultValue("0") int skip,
            @ApiParam(value = "sort") @QueryParam("sort") @DefaultValue("name") String sort,
            @ApiParam(value = "search") @QueryParam("search") @DefaultValue("") String search

    ) {

        MutableLong count = new MutableLong(-1);
        Iterable<Tissue> tissue = qm.getAllTissues(limit, skip, count, sort, search);
        QueryResponse qr = createQueryResponse(tissue);

        qr.setNumTotalResults(count.getValue());

        return createOkResponse(qr);
    }

}
