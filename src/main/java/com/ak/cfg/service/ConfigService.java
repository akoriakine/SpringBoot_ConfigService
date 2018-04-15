package com.ak.cfg.service;

import com.ak.cfg.dao.ConfigDao;
import com.ak.cfg.model.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ConfigService {
    @Autowired
    private ConfigDao configDao;

    public String getDocument(String appCode, Integer version) {
        Config c = configDao.findConfig(appCode, version);
        if (c != null)
            return c.getDocument();
        return null;
    }

    public List<Config> getVersions(String appCode) {
        List<Config> list = configDao.findVersions(appCode);
        return list;
    }

    public void update(String appCode, Integer version, String document) {
        configDao.update(appCode, version, document);
    }

}
