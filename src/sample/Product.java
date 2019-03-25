package sample;

import java.sql.Date;

public class Product {
    private String productName;
    private String productDesc;
    private String quantityProd;
    private Date prodDate;

    public Product(String productName, String productDesc, String quantityProd, Date prodDate) {
        this.productName = productName;
        this.productDesc = productDesc;
        this.quantityProd = quantityProd;
        this.prodDate = prodDate;
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

    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }
}
