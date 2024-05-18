package pdf.generator.model;

public class Warehouse {
    private Long id;
    private String building;
    private String zone;
    private Long spaceId;
    private int spaceHeight;
    private int spaceWidth;
    private int spaceLength;
    private Long productId;
    private String productName;
    private String productCode;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public int getSpaceHeight() {
        return spaceHeight;
    }

    public void setSpaceHeight(int spaceHeight) {
        this.spaceHeight = spaceHeight;
    }

    public Long getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Long spaceId) {
        this.spaceId = spaceId;
    }

    public int getSpaceWidth() {
        return spaceWidth;
    }

    public void setSpaceWidth(int spaceWidth) {
        this.spaceWidth = spaceWidth;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getSpaceLength() {
        return spaceLength;
    }

    public void setSpaceLength(int spaceLength) {
        this.spaceLength = spaceLength;
    }
}
