package datawise.ai.repox.api.wopi.application.ports.out;

import datawise.ai.repox.api.wopi.dto.WopiDiscovery;

import java.util.Optional;

public interface WopiClientPort {
    Optional<WopiDiscovery> getClientDiscovery(String discoveryUrl);
}
