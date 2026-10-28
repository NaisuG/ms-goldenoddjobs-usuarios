package cl.goldenoddjobs.users.controller;

import cl.goldenoddjobs.users.dto.AssignGroupRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminAddUserToGroupRequest;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class RegistrationController {

    private static final List<String> VALID_GROUPS = List.of("SOLICITANTE", "DESARROLLADOR");

    private final CognitoIdentityProviderClient cognitoClient;

    @Value("${cognito.user-pool-id}")
    private String userPoolId;

    public RegistrationController(CognitoIdentityProviderClient cognitoClient) {
        this.cognitoClient = cognitoClient;
    }

    @PostMapping("/asignar-grupo")
    public ResponseEntity<Void> assignGroup(@RequestBody AssignGroupRequest request) {
        String group = VALID_GROUPS.contains(request.getRole()) ? request.getRole() : "SOLICITANTE";

        cognitoClient.adminAddUserToGroup(AdminAddUserToGroupRequest.builder()
            .userPoolId(userPoolId)
            .username(request.getEmail())
            .groupName(group)
            .build());

        return ResponseEntity.ok().build();
    }
}
