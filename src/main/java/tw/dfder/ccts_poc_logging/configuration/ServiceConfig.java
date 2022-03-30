package tw.dfder.ccts_poc_logging.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * To load application.yml setting
 * @Author df
 * @version v2
 */
@Component
@ConfigurationProperties(prefix = "serviceinfo")
public class ServiceConfig {
    public String serviceName;
//
//    @Value("${serviceInfo.pact}")
//    public static String correspondingPact;

    public List<String> destinations;


    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public List<String> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<String> destinations) {
        this.destinations = destinations;
    }
}