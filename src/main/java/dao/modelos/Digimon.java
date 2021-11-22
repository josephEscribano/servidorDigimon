package dao.modelos;

import lombok.*;

import java.time.LocalDate;
import java.util.Objects;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Digimon {
    private int id;
    private String name;
    private int idSerie;
    private Atributos atributo;
    private Nivel nivel;
    private LocalDate nacimiento;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Digimon digimon = (Digimon) o;
        return id == digimon.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

