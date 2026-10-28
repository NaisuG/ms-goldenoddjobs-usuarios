package cl.goldenoddjobs.users.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.List;

@DynamoDbBean
public class Profile {

    private String sub;
    private String name;
    private String mainRole;
    private List<String> skills;

    @DynamoDbPartitionKey
    public String getSub() { return sub; }
    public void setSub(String sub) { this.sub = sub; }

    @JsonProperty("nombre")
    @DynamoDbAttribute("nombre")
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @JsonProperty("rolPrincipal")
    @DynamoDbAttribute("rolPrincipal")
    public String getMainRole() { return mainRole; }
    public void setMainRole(String mainRole) { this.mainRole = mainRole; }

    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }
}
