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

//    public FileInfoResponse(FileObject fileObject) {
//        this.id = fileObject.getId();
//        this.filename = fileObject.getFilename();
//        this.type = fileObject.getType();
//        this.size = fileObject.getSize();
//        this.url = fileObject.getUrl();
//        this.object = fileObject.getObject();
//        this.objectId = fileObject.getObjectId();
//        this.uploadDate = CommonUtil.isNotEmpty(fileObject.getModifiedAt()) ? fileObject.getModifiedAt().toEpochMilli() : null;
//    }

}
