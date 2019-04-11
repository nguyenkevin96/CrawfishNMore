package sample;

public class Inventory {
    private String productName;
    private int productid;
    private int currentProd;
    private int requiredProd;

/*    public Inventory (String productName, int productid, int currentProd, int requiredProd) {
        this.productName = productName;
        this.productid = productid;
        this.currentProd = currentProd;
        this.requiredProd = requiredProd;
    }*/

    public Inventory(String productName, int currentProd, int requiredProd) {
        this.productName = productName;
        this.currentProd = currentProd;
        this.requiredProd = requiredProd;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public int getCurrentProd() {
        return currentProd;
    }

    public void setCurrentProd(int currentProd) {
        this.currentProd = currentProd;
    }

    public int getRequiredProd() {
        return requiredProd;
    }

    public void setRequiredProd(int requiredProd) {
        this.requiredProd = requiredProd;
    }
}
