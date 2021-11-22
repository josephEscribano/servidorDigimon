package EE.rest;

import EE.errores.ApiError;
import dao.modelos.ApiRespuesta;
import dao.modelos.Atributos;
import dao.modelos.Digimon;
import dao.modelos.Nivel;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import servicios.ServiceDigimon;

import java.time.LocalDate;
import java.util.List;

@Path(Constantes.PATH_DIGIMONS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestDigimons {

    private final ServiceDigimon serviceDigimon;


    @Inject
    public RestDigimons(ServiceDigimon serviceDigimon) {
        this.serviceDigimon = serviceDigimon;

    }

    @GET
    @Path(Constantes.PATH_ID)
    public Response getDigimon(@PathParam(Constantes.STR_ID) int id) {
        Response response;
        Either<ApiError, Digimon> resultado = serviceDigimon.getDigimon(id);
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
    @Path(Constantes.PATH_SERIEDIGIMONS)
    public Response getdigimonsPorSerie(@QueryParam(Constantes.ID_SERIE) int idSerie) {
        Response response;
        Either<ApiError, List<Digimon>> resultado = serviceDigimon.getdigimonsPorSerie(idSerie);
        if (resultado.isRight()) {
            response = Response.ok(resultado.get()).build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(resultado.getLeft())
                    .build();
        }

        return response;
    }

    @POST
    public Digimon addDigimon(Digimon digimon) {
        return serviceDigimon.addDigimon(digimon);
    }


    @GET
    public Response getAll() {
        Response response;
        Either<ApiError, List<Digimon>> resultado = serviceDigimon.getAll();
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


    @GET
    @Path(Constantes.PATH_ATRIBUTOS)
    public Response getAllAtributos() {
        Response response;
        Either<ApiError, List<Atributos>> resultado = serviceDigimon.getAllAtributos();
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

    @GET
    @Path(Constantes.PATH_NIVELES)
    public Response getAllNiveles() {
        Response response;
        Either<ApiError, List<Nivel>> resultado = serviceDigimon.getAllNiveles();
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

    @DELETE
    @Path(Constantes.PATH_ID)
    public Response deleteDigimon(@PathParam(Constantes.STR_ID) int id) {
        Response response;
        if (serviceDigimon.deleteDigimon(id)) {
            response = Response.status(Response.Status.OK)
                    .entity(new ApiRespuesta(Constantes.DIGIMON_BORRADO, LocalDate.now()))
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(new ApiError(Constantes.DIGIMON_NO_ENCONTRADO, LocalDate.now()))
                    .build();
        }
        return response;
    }

    @PUT
    public Response updateDigimon(Digimon digimon) {
        Response response;
        if (serviceDigimon.updateDigimon(digimon)) {
            response = Response.status(Response.Status.CREATED)
                    .entity(new ApiRespuesta(Constantes.DIGIMON_ACTUALIZADO, LocalDate.now()))
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(new ApiError(Constantes.DIGIMON_NO_ENCONTRADO, LocalDate.now()))
                    .build();
        }

        return response;
    }

}
