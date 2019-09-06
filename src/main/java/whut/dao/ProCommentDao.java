package whut.dao;

import java.util.List;

import java.util.Map;

import whut.pojo.ProductComment;

public interface ProCommentDao {

	//根据商品id获取评论列表
	public List<ProductComment> getListByProduct(Map<String, Object> map);

	//根据用户id查询用户评论列表
	public List<ProductComment> getListByUser(Map<String, Object> map);

	public void add(ProductComment productComment);

	public void delete(String id);

	public void addAgain(Map<String, String> map);

	public ProductComment getCommentById(String id);

	//管理员根据商品名称、商品编码、用户名称联合查询
	public List<ProductComment> getListByCondition(Map<String, Object> map);

	//以下为分页新增查询总数
	//根据商品id获取评论列表数目
	public Integer getListByProductNum(String id);

	//根据用户id查询用户评论列表数目
	public Integer getListByUserNum(String id);


}
