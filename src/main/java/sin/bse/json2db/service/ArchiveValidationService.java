package sin.bse.json2db.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Timestamp;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@Slf4j
public class ArchiveValidationService {

    @Value("${fileSeperator}")
    private String fileSeperator;

    public boolean validateFolderExists(String inputFolder) {
        File inputDir = new File(inputFolder);
        if (inputDir.exists()) {
            log.info("folder {} does exists", inputFolder);
            return true;
        }else{
            throw new IllegalStateException("folder " + inputFolder + " does not exists");
        }
    }

    public void archiveProcessedJsonFile(File jsonFile,String archiveFolder) {
        archiveFile(jsonFile, archiveFolder);
    }

    private void deleteJsonFile(File jsonFile) {
        if(!jsonFile.delete())
            throw new IllegalStateException("Unable to delete file "+jsonFile.getAbsolutePath());
    }

    private void renameJsonFile(File existingFile) {
        String jsonFileParentPath = existingFile.getParent();
        String jsonFileName = existingFile.getName();

        File newFile = new File(jsonFileParentPath+fileSeperator+jsonFileName+getTimeStamp());
        try{
            Files.copy(existingFile.toPath(),newFile.toPath(),REPLACE_EXISTING);
            deleteJsonFile(existingFile);
            log.info("File renamed to {}",newFile.getAbsolutePath());
        } catch (IOException e) {
            log.error("JsonFileName: {}",existingFile.getAbsolutePath());
            log.error("Expected new JsonFileName: {}",newFile.getAbsolutePath());
            throw new IllegalStateException("Unable to create file with timeStamp");
        }
    }

    private String getTimeStamp() {
        return new Timestamp(System.currentTimeMillis()).toString().replace(":","");
    }

    private void archiveFile(File jsonFile, String archiveFolder){
        checkArchiveFolderExist(archiveFolder);
        File archiveFile = new File(archiveFolder.concat("\\").concat(jsonFile.getName()));
        log.info("archiveFile: {}",archiveFile.getAbsolutePath());
        if(archiveFile.exists()){
            renameJsonFile(archiveFile);
        }
        moveFileToArchiveFolder(jsonFile, archiveFile);

    }

    private void moveFileToArchiveFolder(File jsonFile, File archiveFile){
        try {
            Files.move(jsonFile.toPath(),archiveFile.toPath());
            log.info("File Archived to: {}",archiveFile.getAbsolutePath());
        } catch (IOException e) {
            log.error("Unable to move file from: {} to: {}",jsonFile,archiveFile);
            throw new IllegalStateException("Unable to archive file",e);
        }
    }

    private void checkArchiveFolderExist(String archiveFolder) {
         if(!new File(archiveFolder).isDirectory())
             throw new IllegalStateException("Archive Folder"+ archiveFolder +" DOES NOT Exist");
    }
}
