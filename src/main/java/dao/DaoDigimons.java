package dao;

import EE.errores.ApiError;
import EE.rest.Constantes;
import dao.modelos.Atributos;
import dao.modelos.Digimon;
import dao.modelos.Nivel;
import io.vavr.control.Either;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class DaoDigimons {
    private static final List<Digimon> digimonList = new ArrayList<>();

    private static final List<Atributos> atributosList = new ArrayList<>();
    private static final List<Nivel> nivelList = new ArrayList<>();

    static {

        digimonList.add(new Digimon(1, ConstantesDao.NAME_AGUMON, 1, Atributos.DATA, Nivel.ROOKIE,
                LocalDate.now()));
        digimonList.add(new Digimon(2, ConstantesDao.NAME_VEEMON, 2, Atributos.DATA, Nivel.ROOKIE, LocalDate.now()));
        digimonList.add(new Digimon(3, ConstantesDao.GREYMON, 1, Atributos.DATA, Nivel.CHAMPION, LocalDate.now()));
        digimonList.add(new Digimon(4, ConstantesDao.X_VEEMON, 2, Atributos.DATA, Nivel.CHAMPION, LocalDate.now()));

        atributosList.add(Atributos.DATA);
        atributosList.add(Atributos.LIBRE);
        atributosList.add(Atributos.VACUNA);
        atributosList.add(Atributos.VIRUS);

        nivelList.add(Nivel.ROOKIE);
        nivelList.add(Nivel.CHAMPION);
        nivelList.add(Nivel.ULTIMATE);
        nivelList.add(Nivel.MEGA);
        nivelList.add(Nivel.ARMOR);
        nivelList.add(Nivel.JOGRES);
        nivelList.add(Nivel.ULTRA);

    }

    public Either<ApiError, List<Digimon>> getAll() {
        Either<ApiError, List<Digimon>> resultado;

        if (!digimonList.isEmpty()) {
            resultado = Either.right(digimonList);
        } else {
            resultado = Either.left(new ApiError(ConstantesDao.NO_HAY_ELEMENTOS, LocalDate.now()));
        }


        return resultado;
    }


    public Either<ApiError, List<Atributos>> getAllAtributos() {
        Either<ApiError, List<Atributos>> either;

        if (!atributosList.isEmpty()) {
            either = Either.right(atributosList);
        } else {
            either = Either.left(new ApiError(ConstantesDao.NO_HAY_ELEMENTOS, LocalDate.now()));
        }


        return either;
    }

    public Either<ApiError, List<Nivel>> getAllNiveles() {
        Either<ApiError, List<Nivel>> resultado;

        if (!nivelList.isEmpty()) {
            resultado = Either.right(nivelList);
        } else {
            resultado = Either.left(new ApiError(ConstantesDao.NO_HAY_ELEMENTOS, LocalDate.now()));
        }


        return resultado;
    }

    public Either<ApiError, Digimon> getDigimon(int id) {
        Either<ApiError, Digimon> resultado;
        Digimon digimon = digimonList.stream().filter(digimon1 -> digimon1.getId() == id)
                .findFirst().orElse(null);
        if (digimon != null) {
            resultado = Either.right(digimon);
        } else {
            resultado = Either.left(new ApiError(Constantes.EL_DIGIMON_NO_EXISTE, LocalDate.now()));
        }

        return resultado;
    }

    public Either<ApiError, List<Digimon>> getDigimonsPorSerie(int id) {
        Either<ApiError, List<Digimon>> resultado;
        List<Digimon> list = digimonList.stream().filter(digimon -> digimon.getIdSerie() == id).collect(Collectors.toList());

        if (!list.isEmpty()) {
            resultado = Either.right(list);
        } else {
            resultado = Either.left(new ApiError(ConstantesDao.NO_EXITEN_DIGIMONS, LocalDate.now()));
        }

        return resultado;
    }

    public Digimon addDigimon(Digimon digimon) {

        Digimon digi = digimonList.stream().reduce(((digimon1, digimon2) -> digimon1.getId() >= digimon2.getId() ? digimon1 : digimon2)).get();

        digimon.setId(digi.getId() + 1);
        digimonList.add(digimon);
        return digimon;
    }

    public boolean deleteDigimon(int id) {

        return digimonList.removeIf(digimon -> digimon.getId() == id);
    }

    public boolean updateDigimon(Digimon digimon) {
        boolean confirmacion = false;
        if (digimonList.contains(digimon)) {

            int index = 0;
            for (int i = 0; i < digimonList.size(); i++) {
                if (digimon.getId() == digimonList.get(i).getId()) {
                    index = i;
                }
            }

            digimonList.set(index, digimon);
            confirmacion = true;

        }

        return confirmacion;


    }
}
