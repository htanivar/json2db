package sin.bse.json2db.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sin.bse.json2db.model.ScripStaging;
import sin.bse.json2db.service.File2DBService;

import java.io.File;
import java.util.List;

@RestController
@Slf4j
public class DBLoadController {

    @Autowired
    private File2DBService file2DBService;

    /**
     * Expected to fetch the files from test \src\test\resources\jsonPath
     * And load the data into local H2 Database (localhost:<port>/json2db)
     */
    @GetMapping("/localtest")
    public String localTest() {
        ClassLoader classLoader = getClass().getClassLoader();
        String jsonPath = classLoader.getResource("jsonPath").getPath();
        List<File> jsonFiles = file2DBService.getJsonFiles(jsonPath);
        for (File jsonFile : jsonFiles) {
            List<ScripStaging> scripList = null;
            scripList = file2DBService.getScripList(file2DBService.readJsonFile(jsonFile));


            log.info("picked file: {}", jsonFile.getName());
            for (ScripStaging scripStaging : scripList) {
                log.info(scripStaging.getScripname());
                file2DBService.json2db(scripList);
            }
        }
        return "Local test is complete..check data in H2 Database";
    }

    /**
     * STILL UNDER DEVELOPMENT
     * Expected to load the database
     *
     * @param pathString
     * @return
     */
    @GetMapping("/loaddb")
    public String loadDatabase(String pathString) {
        if (pathString == null)
            pathString = jsonPath;

        try {
            List<File> jsonFiles = loadDatabaseService.getJsonFiles(pathString);
            for (File jsonFile : jsonFiles) {
                loadDatabaseService.loadDb(jsonFile);
            }
        } catch (Exception e) {
            log.error("Unable to load database, look into log files", new IllegalArgumentException("check the files in path " + pathString));
        }
        return "Files from : " + pathString + " is loaded into Database";
    }
}
