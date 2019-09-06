package whut.service;


import whut.pojo.ProductCategory;
import whut.utils.ResponseData;


public interface ProCategoryService {

	public ResponseData getList();

	public ResponseData add(ProductCategory productCategory);
	
	public ResponseData modify(ProductCategory productCategory);

	public ResponseData delete(String id);

	public ResponseData deleteConfirm(String id);

	public ResponseData getListByParentId(String id);

	public ResponseData modifyStatusNoShow(String id);

	public ResponseData getCategoryByName(String name);

	public ResponseData getCategoryById(int categoryId);

	public ResponseData getCategoryByChildrenID(String id);

}
