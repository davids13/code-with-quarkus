package entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Sort;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Fruit extends PanacheEntity {

    public String name;

    public static List<PanacheEntityBase> findAllSorted() {
        return listAll(Sort.by("name"));
    }

    public void save() {
        persistAndFlush();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
