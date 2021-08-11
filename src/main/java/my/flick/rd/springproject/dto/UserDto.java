package my.flick.rd.springproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.flick.rd.springproject.dto.validationgroups.OnCreate;
import my.flick.rd.springproject.dto.validationgroups.OnUpdate;
import my.flick.rd.springproject.model.enums.Role;

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
    private long id;

    @Email
    @NotBlank(groups = OnCreate.class)
    private String email;

    @Null(groups = OnUpdate.class)
    @NotBlank(groups = OnCreate.class)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String password;

    @Null(groups = OnUpdate.class)
    @NotBlank(groups = OnCreate.class)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String passwordRepeat;

    @NotNull(groups = OnCreate.class)
    private Role role;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean blocked;
}
