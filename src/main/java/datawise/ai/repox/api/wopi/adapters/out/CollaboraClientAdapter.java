package datawise.ai.repox.api.wopi.adapters.out;

import datawise.ai.repox.api.wopi.application.ports.out.WopiClientPort;
import datawise.ai.repox.api.wopi.dto.WopiDiscovery;
import datawise.ai.repox.api.wopi.dto.WopiDiscoveryXmlNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

@Component
@Slf4j
class CollaboraClientAdapter implements WopiClientPort {
    @Override
    public Optional<WopiDiscovery> getClientDiscovery(String discoveryUrl) {
        URL url = null;
        Document doc = null;
        try {
            url = new URL(discoveryUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not contact client " + discoveryUrl);
        }

        try {

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/xml");

            InputStream inputStream = conn.getInputStream();


            //InputStream inputStream = url.openStream();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(inputStream);
            doc.getDocumentElement().normalize();

            /*
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer xform = tf.newTransformer();
            xform.transform(new DOMSource(doc), new StreamResult(System.out));
             */

            WopiDiscovery wopiDiscovery = toDomain(doc);
            wopiDiscovery.setSource(discoveryUrl);
            return Optional.ofNullable(wopiDiscovery);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(
                    "Could not open connection to client " + discoveryUrl);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(
                    "Could not open xml parser for discovery " + discoveryUrl);
        } catch (SAXException e) {
            throw new RuntimeException(
                    "Could not parse discovery input from " + discoveryUrl);
        }
    }

    private WopiDiscovery toDomain(Document doc) {
        if (doc == null) return null;
        WopiDiscovery wd = new WopiDiscovery();
        WopiDiscoveryXmlNode root = new WopiDiscoveryXmlNode(
                doc.getDocumentElement().getNodeName(),
                doc.getDocumentElement().getNodeValue());
        wd.setRoot(root);
        addRootChildren(root, doc.getDocumentElement().getChildNodes());
        return wd;
    }

    private void addRootChildren(WopiDiscoveryXmlNode root, NodeList rootChildren) {
        if (rootChildren == null || rootChildren.getLength()==0) return;
        for (int i=0; i<rootChildren.getLength(); i++) {
            root.addChild(mapNode(rootChildren.item(i), root));
        }
    }

    private WopiDiscoveryXmlNode mapNode(Node node, WopiDiscoveryXmlNode parent) {
        WopiDiscoveryXmlNode xmlNode = new WopiDiscoveryXmlNode(node.getNodeName(), node.getNodeValue(), parent);

        NamedNodeMap attributes = node.getAttributes();
        if (attributes != null && attributes.getLength() > 0) {
            for (int att = 0; att<attributes.getLength(); att++) {
                Node attribute = attributes.item(att);
                xmlNode.addAttribute(attribute.getNodeName(), attribute.getNodeValue());
            }
        }

        NodeList children = node.getChildNodes();
        if (children != null && children.getLength()>0) {
            for (int c = 0; c< children.getLength(); c++) {
                xmlNode.addChild(mapNode(children.item(c), xmlNode));
            }
        }

        return xmlNode;
    }

}
