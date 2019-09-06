package whut.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品属性value表
 * @author wangql
 *
 */
public class ProductAttributeValue implements Serializable{
    private Integer valueId;//属性valueID

    private Integer attrKeyId;//属性keyID

    private String attrValue;//属性值

    private Byte valueSort;//属性值排序
    
    private Byte status;//状态

    public Integer getValueId() {
        return valueId;
    }

    public void setValueId(Integer valueId) {
        this.valueId = valueId;
    }

    public Integer getAttrKeyId() {
        return attrKeyId;
    }

    public void setAttrKeyId(Integer attrKeyId) {
        this.attrKeyId = attrKeyId;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue == null ? null : attrValue.trim();
    }

    public Byte getValueSort() {
        return valueSort;
    }

    public void setValueSort(Byte valueSort) {
        this.valueSort = valueSort;
    }
    
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}