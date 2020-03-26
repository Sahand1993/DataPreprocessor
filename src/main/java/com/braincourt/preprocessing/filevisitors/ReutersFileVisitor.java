package com.braincourt.preprocessing.filevisitors;

import com.braincourt.preprocessing.Tokenizer;
import com.braincourt.preprocessing.dataobjects.DataObject;
import com.braincourt.preprocessing.dataobjects.ReutersDataObject;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.File;

public class ReutersFileVisitor extends FileVisitor {

    public ReutersFileVisitor(Tokenizer tokenizer) {
        super(tokenizer);
    }


    public Stream<DataObject> toDataObjects(Stream<Path> articlePaths) {
        return articlePaths.map(this::toDataObject);
    }

    private ReutersDataObject toDataObject(Path articlePath) {
        ReutersDataObject dataObject = new ReutersDataObject();
        try {

            Document xmlDoc = getXmlDocument(articlePath);

            dataObject.setArticleId(getArticleId(xmlDoc));

            List<String> articleTokens = getTokens(xmlDoc);
            dataObject.setTokens(articleTokens);

            List<String> topics = getTopics(xmlDoc);
            dataObject.setTopicTags(topics);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            return null;
        }

        return dataObject;
    }

    private List<String> getTopics(Document xmlDoc) {
        Node newsitem = xmlDoc.getElementsByTagName("newsitem").item(0);
        Node metadataTag = getMetadataTag(newsitem);
        List<String> topics = new ArrayList<>();

        NodeList potentialCodesTags = metadataTag.getChildNodes();

        for (int j = 0; j < potentialCodesTags.getLength(); j++) {
            Node potentialCodesTag = potentialCodesTags.item(j);
            if (potentialCodesTag.getNodeName().equals("codes")) {
                NamedNodeMap attributes = potentialCodesTag.getAttributes();
                if (attributes != null && "bip:topics:1.0".equals(attributes.getNamedItem("class").getNodeValue())) {
                    Node topicCodesTag = potentialCodesTag;
                    topics.addAll(getTopics(topicCodesTag));

                }
            }
        }
        return topics;
    }

    private Node getMetadataTag(Node newsitem) {
        NodeList children = newsitem.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeName().equals("metadata")) {
                return child;
            }
        }
        return null;
    }

    private List<String> getTopics(Node codesTag) {
        List<String> topics = new ArrayList<>();
        NodeList codeTags = codesTag.getChildNodes();

        for (int i = 0; i < codeTags.getLength(); i++) {
            Node codeTag = codeTags.item(i);
            NamedNodeMap attributes = codeTag.getAttributes();
            if (attributes != null) {
                topics.add(attributes.getNamedItem("code").getNodeValue());
            }

        }
        return topics;
    }

    private String getArticleId(Document xmlDoc) {
        return xmlDoc.getElementsByTagName("newsitem")
                .item(0)
                .getAttributes()
                .getNamedItem("itemid").getNodeValue();
    }

    private Document getXmlDocument(Path articlePath) throws ParserConfigurationException, SAXException, IOException {
        File xmlFile = new File(articlePath.toAbsolutePath().toString());
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        return dBuilder.parse(xmlFile);
    }

    private List<String> getTokens(Document xmlDoc) {
        NodeList textElements = xmlDoc.getElementsByTagName("text");
        List<String> articleTokens = new ArrayList<>();
        for (int i = 0; i < textElements.getLength(); i++) {
            Node text = textElements.item(i);
            NodeList paragraphs = text.getChildNodes();
            for (int j = 0; j < paragraphs.getLength(); j++) {
                Node paragraph = paragraphs.item(j);
                String paragraphText = paragraph.getTextContent();
                List<String> paragraphTokens = tokenizer.tokenize(paragraphText);
                articleTokens.addAll(paragraphTokens);
            }
        }
        return articleTokens;
    }
}
