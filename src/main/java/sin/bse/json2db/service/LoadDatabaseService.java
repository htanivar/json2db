package sin.bse.json2db.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sin.bse.json2db.model.ScripStaging;
import sin.bse.json2db.model.TableRoot;
import sin.bse.json2db.repository.IScripStaging;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;


@Service
@Slf4j
public class LoadDatabaseService {

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


    public boolean loadDb(File jsonFile) {
        boolean ret = false;
        try {
            TableRoot tableRoot = gson.fromJson(new String(Files.readAllBytes(jsonFile.toPath())), TableRoot.class);
            ScripStaging[] scripStagings = tableRoot.getTable().stream().toArray(ScripStaging[]::new);
            for (ScripStaging scriptStaging : scripStagings) {
                repository.save(scriptStaging);
            }
        } catch (JsonSyntaxException je) {
            throw new JsonSyntaxException("Unable to parse file " + jsonFile.getName() + " against class ScripStaging");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("unable to load file");
        }
        return ret;
    }

}
