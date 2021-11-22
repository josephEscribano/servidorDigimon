package servicios;

import EE.errores.ApiError;
import dao.DaoDigimons;
import dao.DaoSeries;
import dao.modelos.Atributos;
import dao.modelos.Digimon;
import dao.modelos.Nivel;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;


public class ServiceDigimon {

    private final DaoDigimons dao;
    private final DaoSeries daoSeries;


    @Inject
    public ServiceDigimon(DaoDigimons dao, DaoSeries daoSeries) {
        this.dao = dao;

        this.daoSeries = daoSeries;
    }

    public Either<ApiError, List<Digimon>> getdigimonsPorSerie(int idSerie) {
        return dao.getDigimonsPorSerie(idSerie);
    }


    public Either<ApiError, List<Digimon>> getAll() {
        return dao.getAll();
    }


    public Either<ApiError, List<Atributos>> getAllAtributos() {
        return dao.getAllAtributos();
    }

    public Either<ApiError, List<Nivel>> getAllNiveles() {
        return dao.getAllNiveles();
    }

    public Either<ApiError, Digimon> getDigimon(int id) {
        return dao.getDigimon(id);
    }

    public Digimon addDigimon(Digimon digimon) {
        daoSeries.addDigimonSerie(digimon);
        return dao.addDigimon(digimon);
    }

    public boolean deleteDigimon(int id) {
        daoSeries.deleteDigimonSerie(id);
        return dao.deleteDigimon(id);
    }

    public boolean updateDigimon(Digimon digimon) {

        daoSeries.updateDigimonSerie(digimon);
        return dao.updateDigimon(digimon);
    }
}
