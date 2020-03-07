package sin.bse.json2db.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sin.bse.json2db.service.File2DBService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;


@RunWith(MockitoJUnitRunner.class)
@Slf4j
@SpringBootTest
public class DBLoadControllerTest {

    @InjectMocks
    DBLoadController dbLoadController;

    @Mock
    File2DBService file2DBService;

    @Test
    public void localTestController() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Mockito.when(file2DBService.getJsonFiles(any())).thenReturn(Collections.emptyList());
        String localTestControllerResponse = dbLoadController.localTest();
        log.info(localTestControllerResponse);
    }
}