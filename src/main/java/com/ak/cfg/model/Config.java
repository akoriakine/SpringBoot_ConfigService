package com.ak.cfg.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(name = "config")
public class Config implements Serializable {

    private static final long serialVersionUID = -3009157732242241606L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "appcode")
    private String appCode;

    @Column(name = "version")
    private Integer version;

    @Column(name = "modified")
    private LocalDateTime modified;

    @Column(name = "document")
    private String document;

    protected Config() {
    }

    public Config(String appCode, Integer version, LocalDateTime modified, String document) {
        this.appCode = appCode;
        this.version = version;
        this.modified = modified;
        this.document = document;
    }

    @Override
    public String toString() {
        return String.format("Config[id=%d, appCode='%s', version='%d', modified='%s', document='%s']",
                id, appCode, version, modified.toString(), document);
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getAppCode() {
        return appCode;
    }
    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public Integer getVersion() {
        return version;
    }
    public void setVersion(Integer version) {
        this.version = version;
    }

    public LocalDateTime getModified() {
        return modified;
    }
    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public String getDocument() {
        return document;
    }
    public void setDocument(String document) {
        this.document = document;
    }
}
