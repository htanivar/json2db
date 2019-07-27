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
import java.util.Arrays;
import java.util.List;


@Service
@Slf4j
public class File2DBService {


    @Autowired
    private IScripStaging repository;

    private Gson gson = new GsonBuilder().create();

    /**
     * Get all the files from the path
     * @param jsonSrcFolder
     * @return
     */
    public List<File> getJsonFiles(String jsonSrcFolder) {
        File folder = new File(jsonSrcFolder);
        return Arrays.asList(folder.listFiles());
    }
}
