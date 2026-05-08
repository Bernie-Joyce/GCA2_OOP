package domain;

/**
 * Represents the payload for an image file upload request.
 * <p>
 * Carries the owner ID, file metadata, and Base64-encoded image data
 * required to transfer an image to the server.
 * </p>
 * @author Michal Salabura
 */
public class FileUploadPayload {

    private int id;
    private String fileName;
    private String contentType;
    private int fileSize;
    private String imageData;

    /**
     * Constructs a {@code FileUploadPayload} with default empty values.
     */
    public FileUploadPayload() {
        id = 0;
        fileName = "";
        contentType = "";
        fileSize = 0;
        imageData = "";
    }

    /**
     * Constructs a {@code FileUploadPayload} with the specified values.
     *
     * @param id          the ID of the owner the image is associated with
     * @param fileName    the original name of the image file
     * @param contentType the MIME type of the image (e.g. {@code image/jpeg})
     * @param fileSize    the size of the image file in bytes
     * @param imageData   the Base64-encoded content of the image file
     */
    public FileUploadPayload(int id, String fileName,
                             String contentType, int fileSize,
                             String imageData) {
        this.id = id;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.imageData = imageData;
    }

    /**
     * Returns the ID associated with this upload.
     *
     * @return the ID
     */
    public int getId() { return id; }

    /**
     * Sets the ID associated with this upload.
     *
     * @param id the ID to set
     */
    public void setId(int id) { this.id = id; }

    /**
     * Returns the original name of the image file.
     *
     * @return the file name
     */
    public String getFileName() { return fileName; }

    /**
     * Sets the original name of the image file.
     *
     * @param f the file name to set
     */
    public void setFileName(String f) { fileName = f; }

    /**
     * Returns the MIME type of the image.
     *
     * @return the content type (e.g. {@code image/jpeg})
     */
    public String getContentType() { return contentType; }

    /**
     * Sets the MIME type of the image.
     *
     * @param ct the content type to set (e.g. {@code image/jpeg})
     */
    public void setContentType(String ct) { contentType = ct; }

    /**
     * Returns the size of the image file in bytes.
     *
     * @return the file size in bytes
     */
    public int getFileSize() { return fileSize; }

    /**
     * Sets the size of the image file in bytes.
     *
     * @param fileSize the file size to set in bytes
     */
    public void setFileSize(int fileSize) { this.fileSize = fileSize; }

    /**
     * Returns the Base64-encoded content of the image file.
     *
     * @return the Base64-encoded image data
     */
    public String getImageData() { return imageData; }

    /**
     * Sets the Base64-encoded content of the image file.
     *
     * @param imageData the Base64-encoded image data to set
     */
    public void setImageData(String imageData)  { this.imageData = imageData; }
}