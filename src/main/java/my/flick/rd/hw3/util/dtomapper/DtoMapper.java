package my.flick.rd.hw3.util.dtomapper;

/**
 * Common class for dto mappers
 * @param <M> - model
 * @param <D> - dto
 */
public interface DtoMapper<M,D>{
    M mapToModel(D dto);
    D mapToDto(M model);
}
