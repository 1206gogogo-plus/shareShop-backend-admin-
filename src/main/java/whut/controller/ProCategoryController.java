package whut.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import whut.pojo.ProductCategory;
import whut.service.ProCategoryService;
import whut.utils.ResponseData;

@Controller
@RequestMapping(value = "/pro/category")
public class ProCategoryController {
	
	@Autowired
	private ProCategoryService proCategoryService;
	
	//获取第一层级分类列表
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public @ResponseBody ResponseData getList() {
		return proCategoryService.getList();
	}
	
	//根据父分类ID获取子分类列表
	@RequestMapping(value = "/getListByParentId", method = RequestMethod.GET)
	public @ResponseBody ResponseData getListByParentId(String id) {
		return proCategoryService.getListByParentId(id);
	}
	
	//新增分类
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseData add(@RequestBody ProductCategory productCategory) {		
		return proCategoryService.add(productCategory);
			
	}
	
	//修改分类
	@RequestMapping(value = "/modify", method = RequestMethod.POST, consumes= "application/json")
	public @ResponseBody ResponseData modify(@RequestBody ProductCategory productCategory){
		return proCategoryService.modify(productCategory);
		
	}
	
	//删除分类,其下有子分类时判断
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public @ResponseBody ResponseData delete(String id){
		return proCategoryService.delete(id);
	}
	
	//删除分类,其下有子分类也删，修改其下子分类状态都为0
	@RequestMapping(value = "/deleteConfirm", method = RequestMethod.GET)
	public @ResponseBody ResponseData deleteConfirm(String id){
		return proCategoryService.deleteConfirm(id);
		
	}
	
	//根据分类id设置导航栏不显示（状态为2）modifyStatusNoShow
	@RequestMapping(value = "/modifyStatusNoShow", method = RequestMethod.GET)
	public @ResponseBody ResponseData modifyStatusNoShow(String id){
		return proCategoryService.modifyStatusNoShow(id);
	}
	
	//根据分类名称查找分类列表getCategoryByName
	@RequestMapping(value = "/getCategoryByName", method = RequestMethod.GET)
	public @ResponseBody ResponseData getCategoryByName(String name){
		return proCategoryService.getCategoryByName(name);
	}
	
	//根据分类id获取商品分类信息
	@RequestMapping(value = "/getCategoryById", method = RequestMethod.GET)
	public @ResponseBody ResponseData getCategoryById(int id){
		return proCategoryService.getCategoryById(id);
	}
	
	//根据子分类id查询父分类信息getCategoryByChildrenID
	@RequestMapping(value = "/getCategoryByChildrenID", method = RequestMethod.GET)
	public @ResponseBody ResponseData getCategoryByChildrenID(String id){
		return proCategoryService.getCategoryByChildrenID(id);
	}
}
