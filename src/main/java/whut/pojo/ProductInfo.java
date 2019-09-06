package whut.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品信息表
 * @author wangql
 *
 */
public class ProductInfo implements Serializable{
    private Integer productId;//商品ID

    private String productName;//商品名称

    private String brandName;//品牌
    
    private String spu;//商品spu

    private Integer oneCategoryId;//一级分类ID

    private Integer twoCategoryId;//二级分类ID

    private Integer threeCategoryId;//三级分类ID
    
    private Byte isRecommend;//是否推荐

    private String mainImage;//主图

    private String attributeList;//属性列表

    private Byte publishStatus;//上下架状态

    private Byte auditStatus;//审核状态

    private Byte useCoupon;//是否可以使用优惠券

    private BigDecimal discountRate;//折扣比率
    
   private BigDecimal minPrice;//最低价格
    
    private BigDecimal maxPrice;//最高价格
    
    private Double score;//商品评分

    private Date productionDate;//生产日期

    private String description;//商品简介

    private Integer stock;//商品库存
    
    private String html;//商品详细描述

    private Date inputTime;//商品录入时间

    private Date modifiedTime;//商品修改时间
    
    private List<ProductSpecs> productSpecs; //商品规格(单品详情)


	public List<ProductSpecs> getProductSpecs() {
		return productSpecs;
	}

	public void setProductSpecs(List<ProductSpecs> productSpecs) {
		this.productSpecs = productSpecs;
	}

	public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }
    
    public String getSpu() {
        return spu;
    }

    public void setSpu(String spu) {
        this.spu = spu == null ? null : spu.trim();
    }

    public Integer getOneCategoryId() {
        return oneCategoryId;
    }

    public void setOneCategoryId(Integer oneCategoryId) {
        this.oneCategoryId = oneCategoryId;
    }

    public Integer getTwoCategoryId() {
        return twoCategoryId;
    }

    public void setTwoCategoryId(Integer twoCategoryId) {
        this.twoCategoryId = twoCategoryId;
    }

    public Integer getThreeCategoryId() {
        return threeCategoryId;
    }

    public void setThreeCategoryId(Integer threeCategoryId) {
        this.threeCategoryId = threeCategoryId;
    }
    
    public Byte getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Byte isRecommend) {
        this.isRecommend = isRecommend;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage == null ? null : mainImage.trim();
    }

    public String getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(String attributeList) {
        this.attributeList = attributeList == null ? null : attributeList.trim();
    }

    public Byte getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(Byte publishStatus) {
        this.publishStatus = publishStatus;
    }

    public Byte getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Byte auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Byte getUseCoupon() {
        return useCoupon;
    }

    public void setUseCoupon(Byte useCoupon) {
        this.useCoupon = useCoupon;
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }
    
    public BigDecimal getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}

	public BigDecimal getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public Date getInputTime() {
        return inputTime;
    }

    public void setInputTime(Date inputTime) {
        this.inputTime = inputTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}