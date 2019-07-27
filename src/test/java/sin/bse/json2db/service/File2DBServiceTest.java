package sin.bse.json2db.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class File2DBServiceTest {


    @Autowired
    private File2DBService file2DBService;

    @Test
    public void getJsonFiles() {

        ClassLoader classLoader = getClass().getClassLoader();
        String jsonPath = classLoader.getResource("jsonPath").getPath();
        List<File> jsonFiles = file2DBService.getJsonFiles(jsonPath);
        for(File jsonFile: jsonFiles){
            System.out.println(jsonFile.getAbsolutePath());
        }
    }
}
