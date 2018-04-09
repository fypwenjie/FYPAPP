package dev.fypwenjie.fypapp.Domain;

/**
 * Created by VINTEDGE on 9/4/2018.
 */

public class Store {

    public String Store_name;
    public String Store_id;

    public Store(String store_name, String store_id) {
        Store_name = store_name;
        Store_id = store_id;
    }

    public Store() {

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
        Store tmp = new Store(this.Store_name, this.Store_id);
        return tmp;
    }
}
