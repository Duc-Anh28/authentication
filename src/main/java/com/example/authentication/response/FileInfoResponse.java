package com.example.authentication.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileInfoResponse {
    private Long id;
    private String filename;
    private String type;
    private Long size;
    private String url;
    private String object;
    private String objectId;
    private Long uploadDate;

}
