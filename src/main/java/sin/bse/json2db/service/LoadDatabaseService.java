package sin.bse.json2db.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sin.bse.json2db.model.ScripStaging;
import sin.bse.json2db.model.TableRoot;
import sin.bse.json2db.repository.IScripStaging;

import javax.persistence.EntityManager;
import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@Slf4j
@Transactional
public class LoadDatabaseService {

    @Autowired
    private IScripStaging repository;

    @Autowired
    private EntityManager entityManager;

    private final Gson gson = new GsonBuilder().create();

    public List<File> getJsonFiles(String jsonSrcFolder) {
        File folder = new File(jsonSrcFolder);
        if (!folder.exists())
            throw new IllegalArgumentException("No folders found in " + jsonSrcFolder+" Please provide the json folder location in spring property file");
        return Arrays.asList(Objects.requireNonNull(folder.listFiles()))
                .stream()
                .filter(e -> e.getName().endsWith(".json"))
                .collect(Collectors.toList());
    }

    public void loadDb(File jsonFile) {
        log.info("processing fileSize {} fileName: {}", jsonFile.length(), jsonFile.getName());
        try {
            TableRoot tableRoot = gson.fromJson(new String(Files.readAllBytes(jsonFile.toPath())), TableRoot.class);
            List<ScripStaging> scripStagingsList = getScripStagingsList(tableRoot);
            if (!scripStagingsList.isEmpty())
                bulkInsert(scripStagingsList);
        } catch (JsonSyntaxException je) {
            throw new JsonSyntaxException("Unable to parse file " + jsonFile.getName() + " against class ScripStaging");
        } catch (Exception e) {
            throw new IllegalArgumentException("unable to load file",e);
        }
    }

    private List<ScripStaging> getScripStagingsList(TableRoot tableRoot) {
        try {
            List<ScripStaging> scripStagings = Arrays.asList(tableRoot.getTable().stream().toArray(ScripStaging[]::new));
            if (!scripStagings.isEmpty())
                return scripStagings;
        } catch (Exception e) {
            log.error("Unable to process file TableRoot, {}", tableRoot, e);
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }

    public void bulkInsert(List<ScripStaging> scripStagings) {
        for (ScripStaging scriptStaging : scripStagings) {
            entityManager.persist(scriptStaging);
        }
        entityManager.flush();
        entityManager.clear();
    }
}
