package domain;

public class FileUploadPayload {

    private int id;
    private String fileName;
    private String contentType;
    private int fileSize;
    private String imageData;


    public FileUploadPayload() {
        id = 0;
        fileName = "";
        contentType = "";
        fileSize = 0;
        imageData = "";
    }

    public FileUploadPayload(int id, String fileName,
                             String contentType, int fileSize,
                             String imageData) {
        this.id = id;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.imageData = imageData;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getFileName() { return fileName; }

    public void setFileName(String f) { fileName = f; }

    public String getContentType() { return contentType; }

    public void setContentType(String ct) { contentType = ct; }

    public int getFileSize() { return fileSize; }

    public void setFileSize(int fileSize) { this.fileSize = fileSize; }

    public String getImageData() { return imageData; }

    public void setImageData(String imageData)  { this.imageData = imageData; }
}