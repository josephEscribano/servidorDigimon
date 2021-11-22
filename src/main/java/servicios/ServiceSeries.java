package servicios;

import EE.errores.ApiError;
import dao.DaoSeries;
import dao.modelos.Serie;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class ServiceSeries {
    private final DaoSeries dao;

    @Inject
    public ServiceSeries(DaoSeries dao) {
        this.dao = dao;
    }

    public Either<ApiError, List<Serie>> getAllSeries() {
        return dao.getAllSeries();
    }

    public Serie addSerie(Serie serie) {
        return dao.addSerie(serie);
    }

    public boolean deleteSerie(int id) {
        return dao.deleteSerie(id);
    }

    public boolean updateSerie(Serie serie) {
        return dao.updateSerie(serie);
    }

    public Either<ApiError, Serie> getSerie(int id) {
        return dao.getSerie(id);

    }


}
