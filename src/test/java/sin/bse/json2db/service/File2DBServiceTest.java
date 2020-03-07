package sin.bse.json2db.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class File2DBServiceTest {

    @Autowired
    private File2DBService file2DBService;

    @Test
    public void getJsonFiles_success_UT() {
        List<File> testResult = file2DBService.getJsonFiles("src/test/resources/jsonPath")
                .stream()
                .peek(System.out::println)
                .collect(Collectors.toList());
        assertThat(testResult.size(), is(3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getJsonFiles_checkExceptionThown_UT() {
        List<File> testResult = file2DBService.getJsonFiles("i/dont/exist")
                .stream()
                .peek(System.out::println)
                .collect(Collectors.toList());
    }
}
