package dev.fypwenjie.fypapp.Domain;

/**
 * Created by VINTEDGE on 9/4/2018.
 */
public class Food {
    public String Food_id;
    public String Food_name;
    public String Food_quantity;
    public String Food_desc;
    public String Food_banner;
    public String Food_price;
    public String Food_discount;
    public String Food_category;

    public Food(String Food_id, String Food_name,String Food_quantity,String Food_desc, String Food_banner, String Food_price, String Food_discount , String Food_category) {

        this.Food_id = Food_id;
        this.Food_name = Food_name;
        this.Food_quantity = Food_quantity;
        this.Food_desc = Food_desc;
        this.Food_banner = Food_banner;
        this.Food_price = Food_price;
        this.Food_discount = Food_discount;
        this.Food_category = Food_category;
    }

    public Food() {

    }

    public Food copy() {
        Food tmp = new Food(this.Food_id,
                this.Food_name,
                this.Food_quantity,
                this.Food_desc,
                this.Food_banner,
                this.Food_price,
                this.Food_discount,
                this.Food_category
        );
        return tmp;
    }

    public String getFood_discount() {
        return Food_discount;
    }

    public void setFood_discount(String food_discount) {
        Food_discount = food_discount;
    }

    public String getFood_quantity() {
        return Food_quantity;
    }

    public void setFood_quantity(String food_quantity) {
        Food_quantity = food_quantity;
    }

    public String getFood_name() {
        return Food_name;
    }

    public void setFood_name(String food_name) {
        Food_name = food_name;
    }

    public String getFood_id() {
        return Food_id;
    }

    public void setFood_id(String food_id) {
        Food_id = food_id;
    }

    public String getFood_desc() {
        return Food_desc;
    }

    public void setFood_desc(String food_desc) {
        Food_desc = food_desc;
    }

    public String getFood_banner() {
        return Food_banner;
    }

    public void setFood_banner(String food_banner) {
        Food_banner = food_banner;
    }

    public String getFood_price() {
        return Food_price;
    }

    public void setFood_price(String food_price) {
        Food_price = food_price;
    }

    public String getFood_category() {
        return Food_category;
    }

    public void setFood_category(String food_category) {
        Food_category = food_category;
    }
}


