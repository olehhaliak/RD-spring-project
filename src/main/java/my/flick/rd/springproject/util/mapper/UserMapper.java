package my.flick.rd.springproject.util.mapper;

import my.flick.rd.springproject.dto.UserDto;
import my.flick.rd.springproject.model.User;

public interface UserMapper extends DtoMapper<User, UserDto>{

    /**
     * copies all non-null properties from source to destination
      * @param source
     * @param destination
     */
    void overwriteNotNullProperties(User source, User destination);
}
