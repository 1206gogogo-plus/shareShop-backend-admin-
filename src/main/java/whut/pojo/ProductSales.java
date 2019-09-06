package whut.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品销售统计
 * @author wangql
 *
 */
public class ProductSales implements Serializable{

	private String productName;//商品名称
	
	private Integer salesVolume;//销量
	
	private BigDecimal saleroom;//销售价格

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(Integer salesVolume) {
		this.salesVolume = salesVolume;
	}

	public BigDecimal getSaleroom() {
		return saleroom;
	}

	public void setSaleroom(BigDecimal saleroom) {
		this.saleroom = saleroom;
	}

}
