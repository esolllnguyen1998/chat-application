package com.dack.spring.ws.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileModel implements Serializable {
    private String fileName;
    private String url;
    private Long size;
}
