package sin.bse.json2db.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sin.bse.json2db.model.ScripStaging;
import sin.bse.json2db.service.ArchiveValidationService;
import sin.bse.json2db.service.File2DBService;
import sin.bse.json2db.service.LoadDatabaseService;

import java.io.File;
import java.util.List;
import java.util.Objects;

@RestController
@Slf4j
public class DBLoadController {

    private final ArchiveValidationService archiveValidationService;

    private final File2DBService file2DBService;

    private final LoadDatabaseService loadDatabaseService;

    @Value("${json.path:jsonPath}")
    private String jsonPath;

    @Value("${json.archive.path}")
    private String jsonArchivePath;

    public DBLoadController(ArchiveValidationService archiveValidationService, File2DBService file2DBService, LoadDatabaseService loadDatabaseService) {
        this.archiveValidationService = archiveValidationService;
        this.file2DBService = file2DBService;
        this.loadDatabaseService = loadDatabaseService;
    }

    @GetMapping("/localtest")
    public String localTest() {
        folderValidation();
        for (File jsonFile : getJsonResourceFiles()) {
            List<ScripStaging> scripList;
            scripList = file2DBService.getScripList(file2DBService.readJsonFile(jsonFile));


            log.info("picked file: {}", jsonFile.getName());
            for (ScripStaging scripStaging : scripList) {
                log.info(scripStaging.getScripname());
                file2DBService.json2db(scripList);
            }
            archiveValidationService.archiveProcessedJsonFile(jsonFile, jsonArchivePath);
        }
        return "Local test is complete..check data in H2 Database";
    }

    @GetMapping("/loaddb")
    public String loadDatabase(String pathString) {
        folderValidation();
        if (pathString == null)
            pathString = jsonPath;

        try {
            List<File> jsonFiles = loadDatabaseService.getJsonFiles(pathString);
            for (File jsonFile : jsonFiles) {
                if (jsonFile.length() > 0)
                    loadDatabaseService.loadDb(jsonFile);
                archiveValidationService.archiveProcessedJsonFile(jsonFile, jsonArchivePath);
            }
            log.info("Load Completed");
            return "Files from : " + pathString + " is loaded into Database";
        } catch (Exception e) {
            log.error("Unable to load database, look into log files", new IllegalArgumentException("check the files in path " + pathString), e);
        }

        return "Controller Exiting";
    }

    private void folderValidation() {
            isFolderExists(jsonPath);
            isFolderExists(jsonArchivePath);
    }

    private void isFolderExists(String jsonPath) {
        if (!archiveValidationService.validateFolderExists(jsonPath))
            log.error("Folder does not exist {}", jsonPath);
    }

    private List<File> getJsonResourceFiles() {
        ClassLoader classLoader = getClass().getClassLoader();
        String jsonResourcePath = Objects.requireNonNull(classLoader.getResource("jsonPath")).getPath();
        return file2DBService.getJsonFiles(jsonResourcePath);
    }
}
