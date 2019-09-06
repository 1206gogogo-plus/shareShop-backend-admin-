package whut.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *    订单商品详情
 * @author wangql
 *
 */
public class OrderDetail implements Serializable{
    private Integer orderDetailId; //订单详情ID

    private Integer orderId;  //订单ID
    
    private Integer productId;//商品ID

    private Integer productSpecsId;  //单品ID

    private String productName;    //商品名称

    private Integer productQuantity; //商品数量

    private BigDecimal productPrice;  //商品价格
    
    private BigDecimal actualPaidMoney;//实际付款金额
    
    private Byte status; //订单商品状态

    public Integer getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductSpecsId() {
        return productSpecsId;
    }

    public void setProductSpecsId(Integer productSpecsId) {
        this.productSpecsId = productSpecsId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }
    
    public BigDecimal getActualPaidMoney() {
        return actualPaidMoney;
    }

    public void setActualPaidMoney(BigDecimal actualPaidMoney) {
        this.actualPaidMoney = actualPaidMoney;
    }
    
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}