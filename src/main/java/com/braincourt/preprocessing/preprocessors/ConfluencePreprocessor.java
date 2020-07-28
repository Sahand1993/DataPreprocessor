package com.braincourt.preprocessing.preprocessors;

import com.braincourt.preprocessing.DataWriter;
import com.braincourt.preprocessing.Tokenizer;
import com.braincourt.preprocessing.dataobjects.ConfluenceDataObject;
import com.braincourt.preprocessing.dataobjects.DataObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
public class ConfluencePreprocessor extends Preprocessor {

    Gson gson;

    private Environment env;

    private String confluenceUrl;

    private Tokenizer tokenizer;

    Logger LOG = LoggerFactory.getLogger(ConfluencePreprocessor.class);

    public ConfluencePreprocessor(
            DataWriter dataWriter,
            @Value("${processed.data.dir}") String preprocessedHome,
            @Value("${confluence.folder}") String confluenceDir,
            @Value("${confluence.endpoint.url}") String confluenceUrl,
            Environment env,
            Tokenizer tokenizer
            ) {
        super(dataWriter);
        this.preprocessedDir = preprocessedHome + confluenceDir;
        this.env = env;
        this.confluenceUrl = confluenceUrl;
        this.tokenizer = tokenizer;

        GsonBuilder gsonBuilder = new GsonBuilder();
        JsonDeserializer<ConfluenceDataObject> deserializer = new JsonDeserializer<ConfluenceDataObject>() {
            @Override
            public ConfluenceDataObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                JsonObject jsonObject = json.getAsJsonObject();

                String id;
                String webUi;
                String entityType = jsonObject.get("entityType").getAsString();
                if (entityType.equals("content")) {
                    id = jsonObject.get("content").getAsJsonObject().get("id").getAsString();
                    webUi = jsonObject.get("content").getAsJsonObject().get("_links").getAsJsonObject().get("webui").getAsString();
                } else if (entityType.equals("user")) {
                    id = jsonObject.get("user").getAsJsonObject().get("userKey").getAsString();
                    webUi = jsonObject.get("url").getAsString();
                } else if (entityType.equals("space")) {
                    id = jsonObject.get("space").getAsJsonObject().get("id").getAsString();
                    webUi = jsonObject.get("space").getAsJsonObject().get("_links").getAsJsonObject().get("webui").getAsString();
                } else {
                    throw new RuntimeException("Unknown entityType:" + jsonObject.toString());
                }

                String title = jsonObject.get("title").getAsString();
                List<String> titleTokens = tokenizer.processTokens(jsonObject.get("title").getAsString());
                return new ConfluenceDataObject()
                        .setId(id)
                        .setTitle(title)
                        .setTitleTokens(titleTokens)
                        .setWebUi(webUi);
            }
        };
        gsonBuilder.registerTypeAdapter(ConfluenceDataObject.class, deserializer);

        gson = gsonBuilder.create();
    }

    private HttpURLConnection connect(String confluenceUrl) {
        HttpURLConnection connection = Objects.requireNonNull(createConnection(confluenceUrl));
        setAuthHeader(connection, env.getProperty("confluence_username"), env.getProperty("confluence_password"));
        return connection;
    }

    private HttpURLConnection createConnection(String url) {
        try {
            URL endpoint = new URL(url);
            return (HttpURLConnection) endpoint.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setAuthHeader(HttpURLConnection connection, String username, String password) {
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
        String authHeaderValue = "Basic " + new String(encodedAuth);
        connection.setRequestProperty("Authorization", authHeaderValue);
    }

    @Override
    public void preprocess() {
        Stream<DataObject> dataObjects = getDataObjects();
        dataWriter.writeObjects(dataObjects, preprocessedDir);
    }

    private Stream<DataObject> getDataObjects() {
        Stream<DataObject> dataObjects = Stream.empty();
        String baseUrl = confluenceUrl + "rest/api/search?excerpt=highlight&expand=space.icon&includeArchivedSpaces=false&src=next.ui.search&os_authType=basic&cql=extranet.privacy.granted=true%20AND%20type%20in%20(%22space%22%2C%22user%22%2C%22page%22%2C%22blogpost%22%2C%22attachment%22%2C%22net.seibertmedia.plugin.confluence.microblog%3AmicropostContent%22%2C%22com.atlassian.confluence.plugins.confluence-questions%3Aquestion%22%2C%22com.atlassian.confluence.plugins.confluence-questions%3Aanswer%22)&limit=500";
        int start = 0;
        int interval = 500;
        JsonObject responseJson = null;
        while (responseJson == null || responseJson.get("size").getAsInt() == interval) {
            HttpURLConnection connection = connect(baseUrl + "&start=" + start);
            responseJson = getResponse(connection);
            responseJson.get("size").getAsInt();
            dataObjects = Stream.concat(dataObjects, getDataObjects(responseJson.get("results").getAsJsonArray()));
            LOG.info(String.format("Downloaded %d documents", start + responseJson.get("size").getAsInt()));
            start += interval;
        }
        return dataObjects.filter(dataObject -> !((ConfluenceDataObject)dataObject).getTitleTokens().isEmpty());
    }

    private String getNext(JsonObject responseJson) {
        try {
            return responseJson.get("_links").getAsJsonObject().get("next").getAsString();
        } catch (NullPointerException e) {
            return null;
        }
    }

    private JsonObject getResponse(HttpURLConnection connection) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            in.lines().forEach(response::append);
            return JsonParser.parseString(response.toString()).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Stream<DataObject> getDataObjects(JsonArray documents) {
        return StreamSupport.stream(documents.spliterator(), false)
                .map(JsonElement::toString)
                .map(jsonStr -> gson.fromJson(jsonStr, ConfluenceDataObject.class));
    }
}
