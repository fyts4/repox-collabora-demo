package datawise.ai.repox.api.wopi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class WopiDiscovery {
    private WopiDiscoveryXmlNode root;
    private String source;

    public String printXmlTree() {
        return root.printNodeExpanded();
    }

    @Override
    public String toString() {
        return printXmlTree();
    }

    public List<WopiDiscoveryXmlNode> findNodesWithName(String name) {
        return root.findChildrenWithName(name);
    }

    public List<WopiDiscoveryXmlNode> findNodesWithAttributeValue(String name, String value) {
        return root.findChildrenWithAttributeValue(name, value);
    }

}
