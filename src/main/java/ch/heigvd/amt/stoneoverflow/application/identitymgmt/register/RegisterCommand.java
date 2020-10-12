package ch.heigvd.amt.StoneOverflow.application.identitymgmt.register;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RegisterCommand {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String plaintextPassword;
    private String plaintextPasswordConfirmation;
}