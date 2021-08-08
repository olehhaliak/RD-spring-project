package my.flick.rd.springproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.flick.rd.springproject.dto.validationgroups.OnCreate;
import my.flick.rd.springproject.dto.validationgroups.OnUpdate;
import my.flick.rd.springproject.model.enums.Role;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.Access;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties()
public class UserDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    long id;

    @Email
    @NotBlank(groups = OnCreate.class)
    String email;

    @Null(groups = OnUpdate.class)
    @NotBlank(groups = OnCreate.class)
    String password;

    @Null(groups = OnUpdate.class)
    @NotBlank(groups = OnCreate.class)
    String passwordRepeat;

    @NotNull(groups = OnCreate.class)
    Role role;
}
