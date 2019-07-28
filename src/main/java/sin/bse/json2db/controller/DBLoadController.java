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
     * And load the data into local H2 Database (localhost:<port>/h2-console)
     */
    @GetMapping("/localtest")
    public String localTest() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        String jsonPath = classLoader.getResource("jsonPath").getPath();
        List<File> jsonFiles = file2DBService.getJsonFiles(jsonPath);
        for(File jsonFile: jsonFiles){
            List<ScripStaging> scripList = file2DBService.getScripList(file2DBService.readJsonFile(jsonFile));
            for(ScripStaging scripStaging:scripList){
                log.info(scripStaging.getScripname());
                file2DBService.json2db(scripList);
            }
        }
        return "Local test is complete..check data in H2 Database";
    }
}
