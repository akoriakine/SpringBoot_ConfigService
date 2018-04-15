package com.ak.cfg;

import com.ak.cfg.model.Config;
import com.ak.cfg.service.ConfigService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@Import(ConfigService.class)
@SpringBootTest
public class ConfigServiceTests {

    @Autowired
    private ConfigService configService;

    @Before
    public void setUp() {
        this.configService.update("test1", 1, "{\"test\":\"new test1-1\"}");
        this.configService.update("test1", 2, "{\"test\":\"new test1-2\"}");
        this.configService.update("test1", 3, "{\"test\":\"new test1-3\"}");
        this.configService.update("test2", 1, "{\"test\":\"new test2-1\"}");
        this.configService.update("test2", 2, "{\"test\":\"new test2-2\"}");
        this.configService.update("test2", 3, "{\"test\":\"new test2-3\"}");
    }

    @Test
    public void testUpdate() throws Exception {
        String docBefore = this.configService.getDocument("test2", 3);
        this.configService.update("test2", 3, "{\"test\":\"update test2-3\"}");
        String docAfter = this.configService.getDocument("test2", 3);
        assertThat(docAfter).isNotEqualTo(docBefore);
        assertThat(docAfter).contains("update test2-3");
    }

    @Test
    public void testVersions() throws Exception {
        List<Config> list = this.configService.getVersions("test2");
        assertThat(list.size()).isEqualTo(3);
        assertThat(list.get(0).getModified()).isGreaterThan(list.get(2).getModified());
    }

    @Test
    public void testDocument() throws Exception {
        String doc = this.configService.getDocument("test1", 2);
        assertThat(doc).contains("new test1-2");
    }
}
