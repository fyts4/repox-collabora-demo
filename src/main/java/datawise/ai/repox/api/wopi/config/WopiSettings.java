package datawise.ai.repox.api.wopi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Setter
@Getter
public class WopiSettings {

    private String clientAddress;
    private String clientUrlsrc;
    private List<String> clientSupportedFileFormats = new ArrayList<>();
    private String serverAddress;
    private String serverEndpointPath;
    private Boolean enableEdit;

    // private final String wopiClientUrl = "http://127.0.0.1:9980";  // docker container
    // private final String wopiClientUrl = "http://collabora.online.loc:9980";  // docker container with ssl disabled
    // private final String wopiClientUrl = "http://collabora-repox.datawise.ai:9980";  // ubuntu vm
    // private final String wopiClientUrl = "http://rhel-collabora-repox.datawise.ai:9980";  // rhel vm
    // private final String wopiClientUrl = "https://repox-collabora-development.dev.datawise.ai:443/";

    public WopiSettings() {
        this.clientAddress = "http://rhel-collabora-repox.datawise.ai:9980";
        // this.clientUrlsrc = ""
        // this.serverAddress = "http://192.168.1.8:8080";
        this.serverAddress = "http://repox-store-api-development.dev.datawise.ai:8080";
        this.serverEndpointPath = "/wopi";
        this.enableEdit = false;
    }

    public boolean isClientAddressProvided() {
        return this.clientAddress != null && !this.clientAddress.isEmpty();
    }

    public boolean isClientUrlsrcProvided() {
        return this.clientUrlsrc != null && !this.clientUrlsrc.isEmpty();
    }

}
