//package com.aiden.submissionhub.service.dto;
//
//import java.io.Serializable;
//import java.util.Objects;
//
//public class Response implements Serializable {
//    private String fileName;
//    private String fileDownloadUri;
//    private String fileType;
//    private long size;
//
//    public Response() {
//    }
//
//    public Response(String fileName, String fileDownloadUri, String fileType, long size) {
//        this.fileName = fileName;
//        this.fileDownloadUri = fileDownloadUri;
//        this.fileType = fileType;
//        this.size = size;
//    }
//
//    public String getFileName() {
//        return fileName;
//    }
//
//    public void setFileName(String fileName) {
//        this.fileName = fileName;
//    }
//
//    public String getFileDownloadUri() {
//        return fileDownloadUri;
//    }
//
//    public void setFileDownloadUri(String fileDownloadUri) {
//        this.fileDownloadUri = fileDownloadUri;
//    }
//
//    public String getFileType() {
//        return fileType;
//    }
//
//    public void setFileType(String fileType) {
//        this.fileType = fileType;
//    }
//
//    public long getSize() {
//        return size;
//    }
//
//    public void setSize(long size) {
//        this.size = size;
//    }
//
//    @Override
//    public String toString() {
//        return "Response{" +
//            "fileName='" + fileName + '\'' +
//            ", fileDownloadUri='" + fileDownloadUri + '\'' +
//            ", fileType='" + fileType + '\'' +
//            ", size=" + size +
//            '}';
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (o == null || getClass() != o.getClass()) return false;
//        Response response = (Response) o;
//        return getSize() == response.getSize() && Objects.equals(getFileName(), response.getFileName()) && Objects.equals(getFileDownloadUri(), response.getFileDownloadUri()) && Objects.equals(getFileType(), response.getFileType());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getFileName(), getFileDownloadUri(), getFileType(), getSize());
//    }
//}
