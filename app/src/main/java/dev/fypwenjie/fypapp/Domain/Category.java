package dev.fypwenjie.fypapp.Domain;

/**
 * Created by VINTEDGE on 9/4/2018.
 */

public class Category {


    public String Category_name;
    public String Category_id;
    public String Category_banner;

    public Category(String category_name, String category_id, String category_banner) {
        Category_name = category_name;
        Category_id = category_id;
        Category_banner = category_banner;
    }

    public Category() {

    }

    public String getCategory_name() {
        return Category_name;
    }

    public void setCategory_name(String category_name) {
        Category_name = category_name;
    }

    public String getCategory_id() {
        return Category_id;
    }

    public void setCategory_id(String category_id) {
        Category_id = category_id;
    }

    public String getCategory_banner() {
        return Category_banner;
    }

    public void setCategory_banner(String category_banner) {
        Category_banner = category_banner;
    }

    public Category copy() {
        Category tmp = new Category(this.Category_name, this.Category_id, this.Category_banner);
        return tmp;
    }
}
