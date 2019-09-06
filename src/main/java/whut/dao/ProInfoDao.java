package whut.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

import whut.pojo.ProductInfo;
import whut.pojo.ProductInfoForSearch;

public interface ProInfoDao {

	//获取所有商品列表
	List<ProductInfo> getList(Map<String, Object> map);

	ProductInfo getDetail(String id);

	void add(ProductInfo productInfo);

	//根据商品名称查找商品
	//List<ProductInfo> search(@Param(value = "name") String name);
	List<ProductInfo> search(Map<String, Object> map);

	void modify(ProductInfo productInfo);

	void modifyAuditStatus(Map<String, String> map);

	void modifyShelfStatus(Map<String, String> map);

	//根据分类获取商品列表
	List<ProductInfo> getListByCategory(Map<String, Object> map);

	ProductInfo getDetailByCode(String id);

	List<ProductInfo> getIfcheckedList(Map<String, Object> map);

	List<ProductInfo> getIfShelfList(Map<String, Object> map);

	//管理员联合查询
	List<ProductInfo> getListByCondition(Map<String, Object> map);

	//获取全部商品信息solr所有信息【参考ProductInfoForSearch pojo对象】与ProductInfo相比删除了productSpecs，暂时新增6个参数
	List<ProductInfoForSearch> getSolrDoucumentList();

	//以下为分页求和
	
	//获取所有商品列表总数
	Integer getListNum();

	//根据商品名称找到的商品总数
	Integer searchNum(@Param(value = "name") String name);

	//根据分类获取商品列表总数
	Integer getListByCategoryNum(String id);

	//根据审核状态获取所有的商品列表总数
	Integer getIfcheckedListNum(String status);

	//根据上下架状态获取所有的商品列表总数
	Integer getIfShelfListNum(String status);

	//管理员联合查询的商品总数
	Integer getListByConditionNum(Map<String, Object> map1);

}
