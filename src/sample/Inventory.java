package sample;

public class Inventory {
    private String supplierName;
    private String productName;
    private double currentP;
    private double requiredP;

    public Inventory(String supplierName, String productName, double currentP, double requiredP) {
        this.supplierName = supplierName;
        this.productName = productName;
        this.currentP = currentP;
        this.requiredP = requiredP;
    }

    public Inventory(String productName, double currentP, double requiredP) {
        this.productName = productName;
        this.currentP = currentP;
        this.requiredP = requiredP;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getCurrentP() {
        return currentP;
    }

    public void setCurrentP(double currentP) {
        this.currentP = currentP;
    }

    public double getRequiredP() {
        return requiredP;
    }

    public void setRequiredP(double requiredP) {
        this.requiredP = requiredP;
    }

    @Override
    public String toString(){
        return "Supplier: " + supplierName + "\nProduct: " + productName + "\nCurrent: " + currentP + "\nRequired: " + requiredP;
    }
}
