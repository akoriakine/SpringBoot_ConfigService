package com.ak.cfg;

import com.ak.cfg.dao.ConfigDao;
import com.ak.cfg.model.Config;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(ConfigDao.class)
@Transactional
public class ConfigRepositoryTests {

    @Autowired
    private ConfigDao configDao;

    @Before
    public void setUp() {
        this.configDao.update("test1", 1, "{\"test\":\"new test1-1\"}");
        this.configDao.update("test1", 2, "{\"test\":\"new test1-2\"}");
        this.configDao.update("test1", 3, "{\"test\":\"new test1-3\"}");
        this.configDao.update("test2", 1, "{\"test\":\"new test2-1\"}");
        this.configDao.update("test2", 2, "{\"test\":\"new test2-2\"}");
        this.configDao.update("test2", 3, "{\"test\":\"new test2-3\"}");
    }

    @Test
    public void testUpdate() throws Exception {
        Config config1 = this.configDao.findConfig("test2", 3);
        this.configDao.update("test2", 3, "{\"test\":\"update test2-3\"}");
        Config config2 = this.configDao.findConfig("test2", 3);
        assertThat(config2.getDocument()).contains("update test2-3");
    }

    @Test
    public void testVersions() throws Exception {
        List<Config> list = this.configDao.findVersions("test2");
        assertThat(list.size()).isEqualTo(3);
        assertThat(list.get(0).getModified()).isGreaterThan(list.get(2).getModified());
    }

    @Test
    public void testDocument() throws Exception {
        Config config = this.configDao.findConfig("test1", 2);
        assertThat(config.getDocument()).contains("new test1-2");
    }
}
