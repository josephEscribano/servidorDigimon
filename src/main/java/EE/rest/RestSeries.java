package EE.rest;


import EE.errores.ApiError;
import dao.modelos.ApiRespuesta;
import dao.modelos.Serie;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import servicios.ServiceSeries;

import java.time.LocalDate;
import java.util.List;

@Path(Constantes.PATH_SERIES)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestSeries {
    private final ServiceSeries serviceSeries;

    @Inject
    public RestSeries(ServiceSeries serviceSeries) {
        this.serviceSeries = serviceSeries;
    }

    @GET
    @Path(Constantes.PATH_ID)
    public Response getSerie(@PathParam(Constantes.STR_ID) int id) {
        Response response;
        Either<ApiError, Serie> resultado = serviceSeries.getSerie(id);
        if (resultado.isRight()) {
            response = Response.ok(resultado.get()).build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(resultado.getLeft())
                    .build();
        }

        return response;
    }


    @GET
    public Response getAllSeries() {
        Response response;
        Either<ApiError, List<Serie>> resultado = serviceSeries.getAllSeries();
        if (resultado.isRight()) {
            response = Response.status(Response.Status.OK)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(resultado.getLeft())
                    .build();
        }

        return response;
    }


    @POST
    public Serie addSerie(Serie serie) {
        return serviceSeries.addSerie(serie);
    }

    @DELETE
    @Path(Constantes.PATH_ID)
    public Response deleteSerie(@PathParam(Constantes.STR_ID) int id) {
        Response response;
        if (serviceSeries.deleteSerie(id)) {
            response = Response.status(Response.Status.OK)
                    .entity(new ApiRespuesta(Constantes.SERIE_BORRADA, LocalDate.now()))
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(new ApiError(Constantes.SERIE_NO_ENCONTRADA, LocalDate.now()))
                    .build();
        }
        return response;
    }

    @PUT
    public Response updateSerie(Serie serie) {
        Response response;
        if (serviceSeries.updateSerie(serie)) {
            response = Response.status(Response.Status.CREATED)
                    .entity(new ApiRespuesta(Constantes.SERIE_ACTUALIZADA, LocalDate.now()))
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(new ApiError(Constantes.SERIE_NO_ENCONTRADA, LocalDate.now()))
                    .build();
        }

        return response;
    }
}
