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
import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
public class ConfluencePreprocessor extends Preprocessor {

    Gson gson;

    private Environment env;

    private String confluenceUrl;

    private Tokenizer tokenizer;

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
                return new ConfluenceDataObject()
                        .setId(jsonObject.get("id").getAsString())
                        .setTitle(jsonObject.get("title").getAsString())
                        .setTitleTokens(tokenizer.processTokens(jsonObject.get("title").getAsString()))
                        .setWebUi(jsonObject.get("_links").getAsJsonObject().get("webui").getAsString());
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
        String next = "rest/api/content?limit=500&start=0&os_authType=basic";
        while (next != null) {
            HttpURLConnection connection = connect(confluenceUrl + next);
            JsonObject responseJson = getResponse(connection);
            dataObjects = Stream.concat(dataObjects, getDataObjects(responseJson.get("results").getAsJsonArray()));
            next = getNext(responseJson);
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
