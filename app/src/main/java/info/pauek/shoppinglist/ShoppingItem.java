package info.pauek.shoppinglist;

public class ShoppingItem {
    private String name;
    private boolean check;

    public ShoppingItem(String name, boolean check) {
        this.name = name;
        this.check = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check){
       this.check = check;
    }

}
