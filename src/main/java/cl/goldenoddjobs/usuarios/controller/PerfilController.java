package cl.goldenoddjobs.usuarios.controller;

import cl.goldenoddjobs.usuarios.model.Perfil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

@RestController
@RequestMapping("/api")
public class PerfilController {

    private final DynamoDbTable<Perfil> perfilTable;

    public PerfilController(DynamoDbTable<Perfil> perfilTable) {
        this.perfilTable = perfilTable;
    }

    // Endpoint compartido: accesible para cualquier rol autenticado
    @GetMapping("/usuarios/perfil")
    public ResponseEntity<Perfil> miPerfil(@AuthenticationPrincipal Jwt jwt) {
        Perfil perfil = perfilTable.getItem(Key.builder().partitionValue(jwt.getSubject()).build());
        if (perfil == null) {
            perfil = new Perfil();
            perfil.setSub(jwt.getSubject());
            perfil.setNombre(jwt.getClaimAsString("email"));
        }
        return ResponseEntity.ok(perfil);
    }
}
