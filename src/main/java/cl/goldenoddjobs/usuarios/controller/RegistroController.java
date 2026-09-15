package cl.goldenoddjobs.usuarios.controller;

import cl.goldenoddjobs.usuarios.dto.AsignarGrupoRequest;
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
public class RegistroController {

    private static final List<String> GRUPOS_VALIDOS = List.of("SOLICITANTE", "DESARROLLADOR");

    private final CognitoIdentityProviderClient cognitoClient;

    @Value("${cognito.user-pool-id}")
    private String userPoolId;

    public RegistroController(CognitoIdentityProviderClient cognitoClient) {
        this.cognitoClient = cognitoClient;
    }

    @PostMapping("/asignar-grupo")
    public ResponseEntity<Void> asignarGrupo(@RequestBody AsignarGrupoRequest request) {
        String grupo = GRUPOS_VALIDOS.contains(request.getRol()) ? request.getRol() : "SOLICITANTE";

        cognitoClient.adminAddUserToGroup(AdminAddUserToGroupRequest.builder()
            .userPoolId(userPoolId)
            .username(request.getEmail())
            .groupName(grupo)
            .build());

        return ResponseEntity.ok().build();
    }
}