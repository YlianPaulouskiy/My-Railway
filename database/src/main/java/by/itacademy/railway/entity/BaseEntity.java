package by.itacademy.railway.entity;

import java.io.Serializable;

public interface BaseEntity<K extends Serializable> {

    void setId(K id);

    K getId();
}
