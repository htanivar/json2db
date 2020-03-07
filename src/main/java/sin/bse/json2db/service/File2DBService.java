package sin.bse.json2db.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sin.bse.json2db.model.ScripStaging;
import sin.bse.json2db.model.TableRoot;
import sin.bse.json2db.repository.IScripStaging;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Service
@Slf4j
public class File2DBService {
    @Autowired
    private IScripStaging repository;

    private Gson gson = new GsonBuilder().create();

    /**
     * Get all the files from the path
     *
     * @param jsonSrcFolder
     * @return
     */
    public List<File> getJsonFiles(String jsonSrcFolder) {
        File folder = new File(jsonSrcFolder);
        if (!folder.exists())
            throw new IllegalArgumentException("No folders found in " + jsonSrcFolder);
        return Arrays.asList(folder.listFiles());
    }

    public String readJsonFile(File jsonFilePath) {
        String returnString = null;
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            returnString = new String(Files.readAllBytes(jsonFilePath.toPath()));
        } catch (NoSuchFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    /**
     * Pass input string from downloaded Json File and you will get the List of Scrip for staging
     *
     * @param jsonString
     * @return
     * @throws Exception
     */
    public List<ScripStaging> getScripList(String jsonString) {
        TableRoot tableRoot = null;
        if (!jsonString.isEmpty()) {
            tableRoot = gson.fromJson(jsonString, TableRoot.class);
        }
        if (null != tableRoot) {
            if (tableRoot.getTable().size() > 0) {
                return tableRoot.getTable();
            }
        }
        return Collections.emptyList();
    }

    public void json2db(List<ScripStaging> stagingList) {
        for (ScripStaging scripStaging : stagingList) {
            insertIntoScripStaging(scripStaging);
        }
    }

    public ScripStaging insertIntoScripStaging(ScripStaging scripStaging) {
        ScripStaging savedScript = repository.save(scripStaging);
        return savedScript;
    }

}
