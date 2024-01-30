package datawise.ai.repox.api.wopi.service;

import datawise.ai.repox.api.wopi.application.ports.out.WopiClientPort;
import datawise.ai.repox.api.wopi.config.WopiSettings;
import datawise.ai.repox.api.wopi.dto.WopiDiscovery;
import datawise.ai.repox.api.wopi.dto.WopiDiscoveryXmlNode;
import datawise.ai.repox.api.wopi.application.ports.in.WopiDiscoveryUseCase;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
class WopiDiscoveryService implements WopiDiscoveryUseCase {
    private WopiSettings wopiSettings;
    private WopiDiscovery wopiDiscovery;
    private WopiClientPort wopiClientPort;
    private String hardcodedUrlsrc;

    private static final String wopisrcTemplate = "WOPISrc=_serverAddress__serverEndpointPath_/files/_id_";
    private static final String SERVER_ADDRESS = "_serverAddress_";
    private static final String SERVER_ENDPOINT_PATH = "_serverEndpointPath_";
    private static final String ID = "_id_";

    @Autowired
    public WopiDiscoveryService(WopiClientPort wopiClientPort, WopiSettings wopiSettings) {
        this.wopiClientPort = wopiClientPort;
        this.wopiSettings = wopiSettings;
        this.initializeWithDiscovery(wopiSettings.getClientAddress());
    }

    @Override
    public void initializeWithUrlsrc(String urlsrc) {
        this.hardcodedUrlsrc = urlsrc;
    }

    @Override
    public void initializeWithDiscovery(String wopiClientUrl) {
        Optional<WopiDiscovery> discovery = this.wopiClientPort.getClientDiscovery(wopiClientUrl + discoveryPath);
        if (discovery.isEmpty()) throw new RuntimeException("Could not get discovery xml from client " + wopiClientUrl);
        this.wopiDiscovery = discovery.get();
        log.info("Loaded discovery XML from client " + wopiClientUrl);
        log.info(wopiDiscovery.printXmlTree());
    }

    @Override
    public Optional<String> createWopiSrc(String fileId, String filename, Optional<String> accessToken) {
        if (this.wopiDiscovery == null){
            log.info("The discovery xml has not been loaded.");
            return Optional.empty();
        }

        String extension = FilenameUtils.getExtension(filename);
        List<WopiDiscoveryXmlNode> actions = wopiDiscovery.findNodesWithAttributeValue("ext", extension);
        if (actions.isEmpty()) {
            log.info("There is not any available processing option for the file " + filename);
            return Optional.empty();
        }

        WopiDiscoveryXmlNode action = actions.get(0);
        Optional<String> urlsrcAttribute = action.getAttributeValue("urlsrc");
        if (urlsrcAttribute.isEmpty()) {
            log.info("Could not get the url part for the WOPI Client");
            return Optional.empty();
        }

        String wopiSrc = createWopiSrcUrl(urlsrcAttribute.get(), fileId, accessToken);

        return Optional.of(wopiSrc);
    }

    private String createWopiSrcUrl(String urlsrc, String fileid, Optional<String> accessToken) {
        StringBuilder urlSb = new StringBuilder();
        urlSb.append(urlsrc);
        if (!urlsrc.endsWith("?")) urlSb.append('?');
        String fileUrl = wopisrcTemplate
                .replace(ID, fileid)
                .replace(SERVER_ADDRESS, wopiSettings.getServerAddress())
                .replace(SERVER_ENDPOINT_PATH, wopiSettings.getServerEndpointPath());
        urlSb.append(fileUrl);
        if (accessToken.isPresent()) urlSb.append("&access_token=" + accessToken.get());
        return urlSb.toString();
    }

}
