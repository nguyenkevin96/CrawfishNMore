package sample;

public class MenuItem {
    private String menuItemN;
    private int menuType;
    private double menuItemP;
    private String menuItemD;

    public MenuItem(String menuItemN, int menuType, double menuItemP, String menuItemD) {
        this.menuItemN = menuItemN;
        this.menuType = menuType;
        this.menuItemP = menuItemP;
        this.menuItemD = menuItemD;
    }

    public String getMenuItemN() {
        return menuItemN;
    }

    public void setMenuItemN(String menuItemN) {
        this.menuItemN = menuItemN;
    }

    public int getMenuType() {
        return menuType;
    }

    public void setMenuType(int menuType) {
        this.menuType = menuType;
    }

    public double getMenuItemP() {
        return menuItemP;
    }

    public void setMenuItemP(double menuItemP) {
        this.menuItemP = menuItemP;
    }

    public String getMenuItemD() {
        return menuItemD;
    }

    public void setMenuItemD(String menuItemD) {
        this.menuItemD = menuItemD;
    }
}
