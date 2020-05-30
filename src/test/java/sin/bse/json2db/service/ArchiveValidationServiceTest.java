package sin.bse.json2db.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
@Slf4j
public class ArchiveValidationServiceTest {

    private ArchiveValidationService archiveValidationService = new ArchiveValidationService();

    @Test
    public void validateFolderExists_trueTest() {
        String property =  System.getProperty("user.dir");
        assertThat(archiveValidationService.validateFolderExists(property),is(true));
    }
    @Test
    public void validateFolderExists_falseTest() {
        String property =  System.getProperty("user.dir").concat("iDontExist");
        assertThat(archiveValidationService.validateFolderExists(property),is(false));
    }


}