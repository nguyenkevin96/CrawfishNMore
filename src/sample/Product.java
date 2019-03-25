package sample;

import java.sql.Date;

public class Product {
    private String productName;
    private String productDesc;
    private String quantityProd;
    private Date productDate;

    public Product(String productName, String productDesc, String quantityProd, Date productDate) {
        this.productName = productName;
        this.productDesc = productDesc;
        this.quantityProd = quantityProd;
        this.productDate = productDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getQuantityProd() {
        return quantityProd;
    }

    public void setQuantityProd(String quantityProd) {
        this.quantityProd = quantityProd;
    }

    public Date getProductDate() {
        return productDate;
    }

    public void setProductDate(Date productDate) {
        this.productDate = productDate;
    }
}
