package cl.goldenoddjobs.usuarios.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.List;

@DynamoDbBean
public class Perfil {

    private String sub;
    private String nombre;
    private String rolPrincipal;
    private List<String> skills;

    @DynamoDbPartitionKey
    public String getSub() { return sub; }
    public void setSub(String sub) { this.sub = sub; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getRolPrincipal() { return rolPrincipal; }
    public void setRolPrincipal(String rolPrincipal) { this.rolPrincipal = rolPrincipal; }

    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }
}
