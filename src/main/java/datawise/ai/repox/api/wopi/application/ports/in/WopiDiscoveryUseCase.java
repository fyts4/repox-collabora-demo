package datawise.ai.repox.api.wopi.application.ports.in;

import java.util.Optional;

public interface WopiDiscoveryUseCase {
    String discoveryPath = "/hosting/discovery";
    void initializeWithUrlsrc(String urlsrc);
    void initializeWithDiscovery(String wopiClientUrl);
    Optional<String> createWopiSrc(String fileId, String extension, Optional<String> accessToken);
}
