package dev.fypwenjie.fypapp.Domain;

/**
 * Created by VINTEDGE on 17/4/2018.
 */

public class Cart{
    public static final String TABLE_NAME = "cart";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERID = "c_uid";
    public static final String COLUMN_FOODID = "c_fid";
    public static final String COLUMN_QUANTITY = "c_quantity";
    public static final String COLUMN_PRICE = "c_price";
    public static final String COLUMN_REMARK = "c_remark";
    public static final String COLUMN_TOKEN = "c_token";
    public static final String COLUMN_STATUS = "c_status";
    public static final String COLUMN_CREATETIME = "c_createtime";

    private String Cart_id;
    private String Food_id;
    private String Quantity;
    private String Price;
    private String Remark;
    private String Token;
    private String Status;
    private String Createtime;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_USERID + " INTEGER,"
                    + COLUMN_FOODID + " INTEGER,"
                    + COLUMN_QUANTITY + " INTEGER,"
                    + COLUMN_PRICE + " TEXT,"
                    + COLUMN_REMARK + " TEXT,"
                    + COLUMN_TOKEN + " TEXT,"
                    + COLUMN_STATUS + " INTEGER,"
                    + COLUMN_CREATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public Cart() {
    }

    public Cart(String Cart_id, String Food_id, String Quantity, String Price, String Remark, String Token, String Status, String Createtime) {
        this.Cart_id = Cart_id;
        this.Food_id = Food_id;
        this.Quantity = Quantity;
        this.Price = Price;
        this.Remark = Remark;
        this.Token = Token;
        this.Status = Status;
        this.Createtime = Createtime;
    }

    public String getCart_id() {
        return Cart_id;
    }

    public void setCart_id(String cart_id) {
        Cart_id = cart_id;
    }

    public String getFood_id() {
        return Food_id;
    }

    public void setFood_id(String food_id) {
        Food_id = food_id;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCreatetime() {
        return Createtime;
    }

    public void setCreatetime(String createtime) {
        Createtime = createtime;
    }
}