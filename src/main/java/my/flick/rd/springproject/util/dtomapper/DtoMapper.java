package my.flick.rd.springproject.util.dtomapper;

/**
 * Common interface for dto mappers
 * @param <M> - model
 * @param <D> - dto
 */
public interface DtoMapper<M,D>{
    M mapToModel(D dto);
    D mapToDto(M model);
}
