package dev.fypwenjie.fypapp.Domain;

/**
 * Created by VINTEDGE on 9/4/2018.
 */

public class Store {

    public String Store_name;
    public String Store_id;
    public String Store_category;
    public String Store_banner;

    public Store(String store_name, String store_id, String store_category, String store_banner ) {
        Store_name = store_name;
        Store_id = store_id;
        Store_category = store_category;
        Store_banner = store_banner;
    }

    public Store() {

    }

    public String getStore_banner() {
        return Store_banner;
    }

    public void setStore_banner(String store_banner) {
        Store_banner = store_banner;
    }

    public String getStore_category() {
        return Store_category;
    }

    public void setStore_category(String store_category) {
        Store_category = store_category;
    }

    public String getStore_name() {
        return Store_name;
    }

    public void setStore_name(String store_name) {
        Store_name = store_name;
    }

    public String getStore_id() {
        return Store_id;
    }

    public void setStore_id(String store_id) {
        Store_id = store_id;
    }

    public Store copy() {
        Store tmp = new Store(this.Store_name, this.Store_id, this.Store_category, this.Store_banner);
        return tmp;
    }
}
