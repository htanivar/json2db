package sin.bse.json2db.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sin.bse.json2db.model.ScripStaging;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class File2DBServiceTest {

    @Autowired
    private File2DBService file2DBService;

    private FakeFtpServer fakeFtpServer = new FakeFtpServer();

    @Before
    public void setupFtpServer() {
        String server;
        int port;

        fakeFtpServer.setServerControlPort(2727);
        UserAccount userAccount = new UserAccount("ravi", "ravi", "/home/ravi");

        UnixFakeFileSystem fileSystem = new UnixFakeFileSystem();
        fileSystem.add(new DirectoryEntry("/home/ravi/fakeData"));
        fileSystem.add(new FileEntry("/home/ravi/fakeData/fakeFile01.txt", "1111111111111"));
        fileSystem.add(new FileEntry("/home/ravi/fakeData/fakeFile02.txt", "2222222222222"));
        fileSystem.add(new FileEntry("/home/ravi/fakeData/fakeFile03.txt", "3333333333333"));


        fileSystem.add(new DirectoryEntry("/home/ravi/dataFake"));
        fileSystem.add(new FileEntry("/home/ravi/dataFake/fakeFile01.txt", "1111111111111"));
        fileSystem.add(new FileEntry("/home/ravi/dataFake/fakeFile02.txt", "2222222222222"));
        fileSystem.add(new FileEntry("/home/ravi/dataFake/fakeFile03.txt", "3333333333333"));
        fakeFtpServer.setFileSystem(fileSystem);

        fakeFtpServer.addUserAccount(userAccount);

        fakeFtpServer.start();
    }

    @Test
    public void getJsonFilesFromResource() throws Exception {

        ClassLoader classLoader = getClass().getClassLoader();
        String jsonPath = classLoader.getResource("jsonPath").getPath();
        List<File> jsonFiles = file2DBService.getJsonFiles(jsonPath);
        for (File jsonFile : jsonFiles) {
            List<ScripStaging> scripList = file2DBService.getScripList(file2DBService.readJsonFile(jsonFile));
            for (ScripStaging scripStaging : scripList) {
                log.info(scripStaging.getScripname());
                file2DBService.json2db(scripList);
            }
        }
    }

    @Test
    public void readJsonFileFromFakeFtpServer() throws IOException {


        FTPClient client = new FTPClient();
        try {
            client.connect("localhost", 2727);
            client.login("ravi", "ravi");
            List<String> directoriesFromFTPServer = getDirectoriesFromFTPServer(client, "/home/ravi");
            for (String ftpDir : directoriesFromFTPServer) {
                List<FTPFile> filesFromFTPServer = getFilesFromFTPServer(client, ftpDir);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (client.isConnected()) {
                try {
                    client.disconnect();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    private List<String> getDirectoriesFromFTPServer(FTPClient client, String dirLocation) throws IOException {
        List<String> ret= new ArrayList<>();
        if(client.changeWorkingDirectory(dirLocation)){
            for (FTPFile dir : Arrays.asList(client.listDirectories())) {
                ret.add(dirLocation.concat("/").concat(dir.getName())) ;
            }
        }

        return ret;
    }


    private List<FTPFile> getFilesFromFTPServer(FTPClient client, String dirLocation) throws IOException {
        if(client.changeWorkingDirectory(dirLocation))
        {
            return Arrays.asList(client.listFiles());
        }
        return Collections.emptyList();
    }
}
