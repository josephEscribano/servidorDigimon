package dao;

import EE.errores.ApiError;
import EE.rest.Constantes;
import dao.modelos.Atributos;
import dao.modelos.Digimon;
import dao.modelos.Nivel;
import dao.modelos.Serie;
import io.vavr.control.Either;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DaoSeries {

    private static final List<Serie> serieList = new ArrayList<>();

    static {

        List<Digimon> d1 = new ArrayList<>();
        d1.add(new Digimon(1, ConstantesDao.NAME_AGUMON, 1, Atributos.DATA, Nivel.ROOKIE, LocalDate.now()));
        d1.add(new Digimon(3, ConstantesDao.GREYMON, 1, Atributos.DATA, Nivel.CHAMPION, LocalDate.now()));

        List<Digimon> d2 = new ArrayList<>();
        d2.add(new Digimon(4, ConstantesDao.X_VEEMON, 2, Atributos.DATA, Nivel.CHAMPION, LocalDate.now()));
        d2.add(new Digimon(2, ConstantesDao.NAME_VEEMON, 2, Atributos.DATA, Nivel.ROOKIE, LocalDate.now()));

        serieList.add(new Serie(1, ConstantesDao.DIGIMON_ADVENTURES, LocalDate.now(), d1));
        serieList.add(new Serie(2, ConstantesDao.DIGIMON_02, LocalDate.now(), d2));

    }

    public Either<ApiError, List<Serie>> getAllSeries() {
        Either<ApiError, List<Serie>> resultado;

        if (!serieList.isEmpty()) {
            resultado = Either.right(serieList);
        } else {
            resultado = Either.left(new ApiError(ConstantesDao.NO_HAY_ELEMENTOS, LocalDate.now()));
        }
        return resultado;
    }

    public Either<ApiError, Serie> getSerie(int id) {
        Either<ApiError, Serie> resultado;
        Serie serie = serieList.stream().filter(serie1 -> serie1.getId() == id)
                .findFirst().orElse(null);
        if (serie != null) {
            resultado = Either.right(serie);
        } else {
            resultado = Either.left(new ApiError(Constantes.SERIE_NO_ENCONTRADA, LocalDate.now()));
        }

        return resultado;
    }


    public Serie addSerie(Serie serie) {
        Serie ser = serieList.stream().reduce(((serie1, serie2) -> serie1.getId() >= serie2.getId() ? serie1 : serie2 )).get();
        serie.setId(ser.getId() + 1);
        serie.setDigimons(new ArrayList<>());
        serieList.add(serie);
        return serie;
    }

    public boolean deleteSerie(int id) {

        return serieList.removeIf(serie -> serie.getId() == id);
    }


    public boolean updateSerie(Serie serie) {
        boolean confirmacion = false;
        if (serieList.contains(serie)) {
            int index = -1;
            for(int i = 0; i < serieList.size();i++){
                if (serie.getId() == serieList.get(i).getId()){
                    index = i;
                }
            }
            serieList.set(index, serie);
            confirmacion = true;

        }

        return confirmacion;
    }

    public void deleteDigimonSerie(int id) {
        serieList.forEach(serie -> serie.getDigimons().removeIf(digimon -> digimon.getId() == id));

    }

    public void updateDigimonSerie(Digimon digimon) {
        for(int i = 0; i < serieList.size(); i++){
            for(int j = 0; j < serieList.get(i).getDigimons().size(); j++){
                if (digimon.getId() == serieList.get(i).getDigimons().get(j).getId()){
                    serieList.get(i).getDigimons().set(j,digimon);
                }
            }
        }


    }

    public void addDigimonSerie(Digimon digimon) {
        serieList.stream().filter(serie -> serie.getId() == digimon.getIdSerie()).forEach(serie -> serie.getDigimons().add(digimon));
    }
}
