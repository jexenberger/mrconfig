package org.github.mrconfig.domain;

import org.github.mrconfig.framework.activerecord.ActiveRecord;
import org.github.mrconfig.framework.templating.FreemarkerEngine;

import javax.persistence.*;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.xml.bind.annotation.XmlElement;
import java.io.Writer;
import java.util.Date;

/**
 * Created by w1428134 on 2014/07/07.
 */
@javax.persistence.Entity
public class Template extends KeyEntity<Template> {


    @Lob()
    @Basic(fetch = FetchType.LAZY)
    @XmlElement(namespace = "http://www.github.org/mrconfig")
    byte[] content;

    @Lob()
    @Basic(fetch = FetchType.LAZY)
    @XmlElement(namespace = "http://www.github.org/mrconfig")
    byte[] preScript;

    @Lob()
    @Basic(fetch = FetchType.LAZY)
    @XmlElement(namespace = "http://www.github.org/mrconfig")
    byte[] postScript;

    @XmlElement(namespace = "http://www.github.org/mrconfig")
    String namePattern;
    @XmlElement(namespace = "http://www.github.org/mrconfig")
    byte[] targetPath;

    @Temporal(TemporalType.TIMESTAMP)
    Date lastModified;

    boolean copyScriptsFirst;

    public Template() {
        lastModified = new Date();
    }

    public Template(String key, byte[] content) {
        this();
        setKey(key);
        this.content = content;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public byte[] getPreScript() {
        return preScript;
    }

    public void setPreScript(byte[] preScript) {
        this.preScript = preScript;
    }

    public byte[] getPostScript() {
        return postScript;
    }

    public void setPostScript(byte[] postScript) {
        this.postScript = postScript;
    }

    public String getNamePattern() {
        return namePattern;
    }

    public void setNamePattern(String namePattern) {
        this.namePattern = namePattern;
    }

    public byte[] getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(byte[] targetPath) {
        this.targetPath = targetPath;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public boolean isCopyScriptsFirst() {
        return copyScriptsFirst;
    }

    public void setCopyScriptsFirst(boolean copyScriptsFirst) {
        this.copyScriptsFirst = copyScriptsFirst;
    }

    public void generate(final Environment<?> source, final Writer target) {
        ActiveRecord.doWork(() -> {
            FreemarkerEngine.generate(source.getModel(), getId().toString(),target);
            return null;
        });
    }

}
