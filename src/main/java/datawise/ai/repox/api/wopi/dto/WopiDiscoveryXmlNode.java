package datawise.ai.repox.api.wopi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
public class WopiDiscoveryXmlNode {
    @Setter
    List<WopiDiscoveryXmlNode> children = new ArrayList<>();

    WopiDiscoveryXmlNode parent;
    String tag;
    String value;
    int depth;
    Map<String, String> attributes = new HashMap<>();

    public WopiDiscoveryXmlNode(String tag, String value) {
        this.tag = tag;
        this.value = value;
        this.depth = 0;
        this.parent = null;
    }

    public WopiDiscoveryXmlNode(String tag, String value, WopiDiscoveryXmlNode parent) {
        this.tag = tag;
        this.value = value;
        this.parent = parent;
        this.depth = parent!=null? parent.getDepth() + 1 : 0;
    }

    public void addAttribute(String name, String value) {
        attributes.put(name, value);
    }

    public Optional<String> getAttributeValue(String attributeName) {
        if (attributes.containsKey(attributeName)) return Optional.of(attributes.get(attributeName));
        else return Optional.empty();
    }

    public void addChild(WopiDiscoveryXmlNode child) {
        // the optional ignorePrefix is to avoid adding nodes whose name is #text, #comment etc.
        boolean ignore = false;
        String ignorePrefix = "#";
        if (child.tag.startsWith(ignorePrefix)) ignore = true;
        if (!ignore) this.children.add(child);
    }

    public String printNode() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<depth; i++) sb.append('\t');
        sb.append("<" + tag + "> ");
        if (value != null && !value.matches("^\\s*$")) sb.append("value = " + value + ' ');
        if (attributes.size() > 0) {
            sb.append("with attributes : ");
            for (String key : attributes.keySet()) {
                sb.append(key + " = " + attributes.get(key) + " ");
            }
        }
        return sb.toString();
    }

    public String printNodeExpanded() {
        StringBuilder sb = new StringBuilder();
        sb.append(printNode());
        if (!children.isEmpty()) {
            for (int c = 0; c<children.size(); c++)
                sb.append("\n" + children.get(c).printNodeExpanded());
        }
        return sb.toString();
    }

    public String printNodeOrigin() {
        StringBuilder sb = new StringBuilder();
        sb.append(printNode());
        if (parent!=null) {
            sb.append("\n" + parent.printNodeOrigin());
        }
        return sb.toString();
    }

    public List<WopiDiscoveryXmlNode> findChildrenWithName(String name) {
        List<WopiDiscoveryXmlNode> list = new ArrayList<>();
        children.forEach(child -> list.addAll(child.findChildrenWithName(name)));
        if (this.tag.compareToIgnoreCase(name)==0) list.add(this);
        return list;
    }

    public List<WopiDiscoveryXmlNode> findChildrenWithAttributeValue(String name, String value) {
        List<WopiDiscoveryXmlNode> list = new ArrayList<>();
        children.forEach(child -> list.addAll(child.findChildrenWithAttributeValue(name, value)));
        if (this.attributes.containsKey(name) && this.attributes.get(name).compareToIgnoreCase(value)==0)
            list.add(this);
        return list;
    }

}
