package my.flick.rd.springproject.util.mapper.impl;

import my.flick.rd.springproject.dto.UserDto;
import my.flick.rd.springproject.model.User;
import my.flick.rd.springproject.util.mapper.UserMapper;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

@Component
public class UserMapperImpl implements UserMapper {
    private final PropertyUtilsBean propertyUtils = new PropertyUtilsBean();

    @Override
    public User mapToModel(UserDto dto) {
        User model = new User();
        try {
            propertyUtils.copyProperties(model, dto);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return model;

    }

    @Override
    public UserDto mapToDto(User model) {
        UserDto dto = new UserDto();
        try {
            propertyUtils.copyProperties(dto, model);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        dto.setPassword(null);
        return dto;
    }

    @Override
    public void overwriteNotNullProperties(User source, User destination) {
        if(source.getEmail()!=null){
            destination.setEmail(source.getEmail());
        }
        if(source.getRole()!=null){
            destination.setRole(source.getRole());
        }
    }
}
