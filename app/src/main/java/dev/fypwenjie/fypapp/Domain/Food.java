package dev.fypwenjie.fypapp.Domain;

/**
 * Created by VINTEDGE on 9/4/2018.
 */
public class Food {
    public String CouponID;
    public String CProductDesc;
    public byte[] Productimage;
    public String DiscAmount;
    public String UnitPrice;
    public String UserID;
    public String TotalCoupon;
    public String AvailableCoupon;
    public String ValidFrom;
    public String ValidTo;
    public String CouponDesc;

    public Food(byte[] productimage, String couponID, String CProductDesc, String discAmount, String unitPrice,
                  String userID,
                  String TotalCoupon,
                  String AvailableCoupon,
                  String ValidFrom,
                  String ValidTo,
                  String CouponDesc) {

        this.Productimage = productimage;
        this.CouponID = couponID;
        this.CProductDesc = CProductDesc;
        this.DiscAmount = discAmount;
        this.UnitPrice = unitPrice;
        this.UserID = userID;
        this.TotalCoupon = TotalCoupon;
        this.AvailableCoupon = AvailableCoupon;
        this.ValidFrom = ValidFrom;
        this.ValidTo = ValidTo;
        this.CouponDesc = CouponDesc;
    }

    public Food() {

    }

    public Food copy() {
        Food tmp = new Food(this.Productimage,
                this.CouponID,
                this.CProductDesc,
                this.DiscAmount,
                this.UnitPrice,
                this.UserID,
                this.TotalCoupon,
                this.AvailableCoupon,
                this.ValidFrom,
                this.ValidTo,
                this.CouponDesc
        );
        return tmp;
    }

    public String getCouponID() {
        return CouponID;
    }

    public void setCouponID(String couponID) {
        CouponID = couponID;
    }

    public byte[] getProductimage() {
        return Productimage;
    }

    public void setProductimage(byte[] productimage) {
        Productimage = productimage;
    }

    public String getCProductDesc() {
        return CProductDesc;
    }

    public void setCProductDesc(String CProductDesc) {
        this.CProductDesc = CProductDesc;
    }

    public String getDiscAmount() {
        return DiscAmount;
    }

    public void setDiscAmount(String discAmount) {
        DiscAmount = discAmount;
    }

    public String getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getTotalCoupon() {
        return TotalCoupon;
    }

    public void setTotalCoupon(String totalCoupon) {
        TotalCoupon = totalCoupon;
    }

    public String getAvailableCoupon() {
        return AvailableCoupon;
    }

    public void setAvailableCoupon(String availableCoupon) {
        AvailableCoupon = availableCoupon;
    }

    public String getValidFrom() {
        return ValidFrom;
    }

    public void setValidFrom(String validFrom) {
        ValidFrom = validFrom;
    }

    public String getValidTo() {
        return ValidTo;
    }

    public void setValidTo(String validTo) {
        ValidTo = validTo;
    }

    public String getCouponDesc() {
        return CouponDesc;
    }

    public void setCouponDesc(String couponDesc) {
        CouponDesc = couponDesc;
    }
}


