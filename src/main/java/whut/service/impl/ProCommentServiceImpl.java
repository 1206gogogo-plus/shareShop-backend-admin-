package whut.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import whut.dao.ProCommentDao;
import whut.pojo.ProductComment;
import whut.service.ProCommentService;
import whut.utils.ResponseData;



@Service
public class ProCommentServiceImpl implements ProCommentService{

	@Autowired
	private ProCommentDao proCommentDao;

	@Override
	public ResponseData getListByProduct(String id,Integer pageindex, Integer pagesize) {
		// TODO Auto-generated method stub
		if(pageindex == null)
			pageindex = 0;
		if(pagesize == null)
			pagesize = 20;
		Map<String,Object> map = new HashMap<>();
		map.put("productId", id);
		map.put("pageindex", pageindex);
		map.put("pagesize", pagesize);
		List<ProductComment> list = proCommentDao.getListByProduct(map);
		if(list != null) {
			Integer num = proCommentDao.getListByProductNum(id);
			return new ResponseData(200,"success",list,num);
		}else {
			return new ResponseData(400,"no data",null);
		}
	}

	@Override
	public ResponseData getListByUser(String id,Integer pageindex, Integer pagesize) {
		// TODO Auto-generated method stub
		if(pageindex == null)
			pageindex = 0;
		if(pagesize == null)
			pagesize = 20;
		Map<String,Object> map = new HashMap<>();
		map.put("userId", id);
		map.put("pageindex", pageindex);
		map.put("pagesize", pagesize);
		List<ProductComment> list = new ArrayList<>();
		list = proCommentDao.getListByUser(map);
		if(list == null)
			return new ResponseData(400,"No Comments",null);
		Integer num = proCommentDao.getListByUserNum(id);
		return new ResponseData(200,"success",list,num);
	}

	@Override
	public ResponseData add(ProductComment productComment) {
		// TODO Auto-generated method stub
		proCommentDao.add(productComment);
		return new ResponseData(200,"add success",null);
	}

	@Override
	public ResponseData delete(String id) {
		// TODO Auto-generated method stub
		proCommentDao.delete(id);
		return new ResponseData(200,"delete success",null);
	}

	@Override
	public ResponseData addAgain(String id, String content) {
		// TODO Auto-generated method stub
		ProductComment productComment = proCommentDao.getCommentById(id);
		if(productComment.getSecondContent() == null || productComment.getSecondContent().isEmpty()) {
			Map<String, String> map = new HashMap<>();
			map.put("commentId", id);
			map.put("secondContent", content);
			proCommentDao.addAgain(map);
			return new ResponseData(200,"add success",null);
		}
		return new ResponseData(500,"Sorry,you can't evaluate again",null);
		
	}

	@Override
	public ResponseData getCommentById(String id) {
		// TODO Auto-generated method stub
		ProductComment productComment = proCommentDao.getCommentById(id);
		if(productComment == null) {
			return new ResponseData(406,"There are no comments",null);
		}
		return new ResponseData(200,"success",productComment);
	}

	@Override
	public ResponseData getListByCondition(String proName, String proCode, String userName,String star1,String star2,Integer pageindex, Integer pagesize) {
		// TODO Auto-generated method stub
		//商品评论：管理员要显示商品编码、商品名称、评价的用户名。（新建一个POJO类）
		if(pageindex == null)
			pageindex = 0;
		if(pagesize == null)
			pagesize = 20;
		Map<String,Object> map = new HashMap<>();
		map.put("proName", proName);
		map.put("spu", proCode);
		map.put("userName", userName);
		map.put("star1", star1);
		map.put("star2", star2);
		map.put("pageindex", pageindex);
		map.put("pagesize", pagesize);
		List<ProductComment> list = proCommentDao.getListByCondition(map);
		if(list.size() > 0)
			return new ResponseData(200,"success",list);
		return new ResponseData(400,"no data",null);
	}
	
}
