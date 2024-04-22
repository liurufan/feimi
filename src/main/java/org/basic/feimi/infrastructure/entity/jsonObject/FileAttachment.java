package org.basic.feimi.infrastructure.entity.jsonObject;

import lombok.Data;

import java.io.Serializable;

@Data
public class FileAttachment implements Serializable {

    private String key;

    private String filename;
}
