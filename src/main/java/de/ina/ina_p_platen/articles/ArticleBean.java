package de.ina.ina_p_platen.articles;

public class ArticleBean {

    private int ID;
    private String name;
    private int amount;

    public ArticleBean() {

    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
