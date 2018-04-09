package dev.fypwenjie.fypapp.Domain;

/**
 * Created by VINTEDGE on 9/4/2018.
 */

public class Store {

    public String Category;
    public String CategoryID;

    public Store(String category, String categoryID) {
        Category = category;
        CategoryID = categoryID;
    }

    public Store() {

    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public Store copy() {
        Store tmp = new Store(this.Category, this.CategoryID);
        return tmp;
    }
}
