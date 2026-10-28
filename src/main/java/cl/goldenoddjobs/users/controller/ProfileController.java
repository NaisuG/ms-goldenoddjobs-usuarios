package cl.goldenoddjobs.users.controller;

import cl.goldenoddjobs.users.model.Profile;
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
public class ProfileController {

    private final DynamoDbTable<Profile> profileTable;

    public ProfileController(DynamoDbTable<Profile> profileTable) {
        this.profileTable = profileTable;
    }

    @GetMapping("/usuarios/perfil")
    public ResponseEntity<Profile> myProfile(@AuthenticationPrincipal Jwt jwt) {
        Profile profile = profileTable.getItem(Key.builder().partitionValue(jwt.getSubject()).build());
        if (profile == null) {
            profile = new Profile();
            profile.setSub(jwt.getSubject());
            profile.setName(jwt.getClaimAsString("email"));
        }
        return ResponseEntity.ok(profile);
    }
}
