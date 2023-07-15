package by.itacademy.railway.mapper;

public interface CommonMapper<M, T> {

    M toModel(T dto);

    T toDto(M model);

}
