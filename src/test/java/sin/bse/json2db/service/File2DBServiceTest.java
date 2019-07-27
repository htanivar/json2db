package sin.bse.json2db.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sin.bse.json2db.model.ScripStaging;

import javax.annotation.Resource;
import java.io.File;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class File2DBServiceTest {



    @Autowired
    private File2DBService file2DBService;

    @Test
    public void getJsonFiles() throws Exception {

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
    }
}
