package pl.asiewiera.springclient1;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@ConfigurationProperties(prefix = "info")
@RestController
public class MyController {

/*
    @Value(("${spring.application.name}"))
    private String appName;
*/

    private String valueFromConfig;
    private String applicationName;
    private String instanceId;

    @GetMapping("/name")
    public String getName(){
        StringBuilder sb = new StringBuilder();
        sb.append("Name:  ").append(applicationName).append("  Id: ").append(instanceId).append(System.lineSeparator())
                .append("valueFromConfig:  ").append(valueFromConfig).append(System.lineSeparator());

        return sb.toString();
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getValueFromConfig() {
        return valueFromConfig;
    }

    public void setValueFromConfig(String valueFromConfig) {
        this.valueFromConfig = valueFromConfig;
    }
}
