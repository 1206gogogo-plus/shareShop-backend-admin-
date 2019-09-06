package whut.service.impl;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import whut.dao.ProInfoDao;
import whut.pojo.ProductInfo;
import whut.pojo.ProductInfoForSearch;
import whut.service.ProInfoService;
import whut.utils.JedisUtil;
import whut.utils.ResponseData;
import whut.utils.SolrJUtil;



@Service
public class ProInfoServiceImpl implements ProInfoService{

	@Autowired
	private ProInfoDao proInfoDao;
	
	private HttpSolrClient solrClient = SolrJUtil.getSolrClient();
	private String coreName = SolrJUtil.getCoreName();//创建的内核名
	
	@Override
	public ResponseData updateSolrData() {
		try{
			this.updateData();
			return new ResponseData(200,"success",null);
		}catch(Exception e) {
			System.out.println(e);
			return new ResponseData(400,"error",null);
		}
	}
	
	private void updateData() {
		List<ProductInfoForSearch> productInfoForSearchs = new ArrayList<ProductInfoForSearch>();
		productInfoForSearchs = proInfoDao.getSolrDoucumentList();
		//productInfoForSearchs.add(new ProductInfoForSearch(1, "Fashion hat", "", 1,1, 1, "", "", 1,1, 1, 21, null, "",1, null, null,12, 12.0, 11.0,11.0, 11, 11, 0));
		
		//获取每个商品浏览量.
		//如果直接操作的是对象productInfoForSearchNews就不需要定义了
		List<ProductInfoForSearch> productInfoForSearchNews = new ArrayList<ProductInfoForSearch>();
		Jedis jedis = JedisUtil.getJedis();
		int viewOne = 0;
		for(ProductInfoForSearch productInfoForSearch : productInfoForSearchs) {
			try {
				viewOne = Integer.parseInt(jedis.get("view:"+productInfoForSearch.getProductId()));
			}catch(Exception e) {
				viewOne = 0;
			}
			productInfoForSearch.setView(viewOne);
			productInfoForSearchNews.add(productInfoForSearch);
		}
		JedisUtil.closeJedis(jedis);
		productInfoForSearchs = productInfoForSearchNews;
		
		if(!productInfoForSearchs.isEmpty()) {
			deleteData();
		}else {
			return;
		}
		try {
			solrClient.addBeans(coreName,productInfoForSearchs);
			solrClient.commit(coreName);
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void deleteData() {
		try {
			solrClient.deleteByQuery(coreName,"*:*");
			solrClient.commit(coreName);
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void deletRedis() {   //删除redis中的缓存
		try{
			Jedis jedis = JedisUtil.getJedis();
			jedis.del("recommendPro");//删除redis中的缓存
			JedisUtil.closeJedis(jedis);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public ResponseData getList(Integer pageindex, Integer pagesize) {
		if(pageindex == null)
			pageindex = 0;
		if(pagesize == null)
			pagesize = 20;
		Map<String,Object> map = new HashMap<>();
		map.put("pageindex", pageindex);
		map.put("pagesize", pagesize);
		List<ProductInfo> list = proInfoDao.getList(map);
		if(list != null) {
			Integer num = proInfoDao.getListNum();
			return new ResponseData(200,"success",list,num);
		}else {
			return new ResponseData(400,"no data",null);
		}
	}

	@Override
	public ResponseData getDetail(String id) {
		ProductInfo productInfo = proInfoDao.getDetail(id);
		if(productInfo != null) {
			return new ResponseData(200,"success",productInfo);
		}else {
			return new ResponseData(400,"no data",null);
		}
	}

	
	@Override
	public ResponseData add(ProductInfo productInfo) {
		if(productInfo.getProductName() != null) {
			proInfoDao.add(productInfo);
			deletRedis();
			return new ResponseData(200,"Successfully added",null);
		}else {
			return new ResponseData(500,"Add failed",null);
		}
	}

	@Override
	public ResponseData search(String name,Integer pageindex, Integer pagesize) {
		if(pageindex == null)
			pageindex = 0;
		if(pagesize == null)
			pagesize = 20;
		Map<String,Object> map = new HashMap<>();
		map.put("productName", name);
		map.put("pageindex", pageindex);
		map.put("pagesize", pagesize);
		List<ProductInfo> list = new ArrayList<>();
		list = proInfoDao.search(map);
		if(list.isEmpty())
			return new ResponseData(400,"No match was found",null);
		Integer num = proInfoDao.searchNum(name);
		return new ResponseData(200,"success",list,num);
	}

	@Override
	public ResponseData modify(ProductInfo productInfo) {
		proInfoDao.modify(productInfo);
		deletRedis();
		return new ResponseData(200,"modify success",null);
	}


	@Override
	public ResponseData modifyAuditStatus(String id, String status) {
		Map<String, String> map = new HashMap<>();
		map.put("productId", id);
		map.put("auditStatus", status);
		proInfoDao.modifyAuditStatus(map);
		deletRedis();
		return new ResponseData(200,"modifyAuditStatus success",null);
	}

	@Override
	public ResponseData modifyShelfStatus(String id, String status) {
		Map<String, String> map = new HashMap<>();
		map.put("productId", id);
		map.put("publishStatus", status);
		proInfoDao.modifyShelfStatus(map);
		deletRedis();
		return new ResponseData(200,"modifyShelfStatus success",null);
	}

	@Override
	public ResponseData getListByCategory(String id,Integer pageindex, Integer pagesize) {
		if(pageindex == null)
			pageindex = 0;
		if(pagesize == null)
			pagesize = 20;
		Map<String,Object> map = new HashMap<>();
		map.put("oneCategoryId", id);
		map.put("twoCategoryId", id);
		map.put("threeCategoryId", id);
		map.put("pageindex", pageindex);
		map.put("pagesize", pagesize);
		List<ProductInfo> list = new ArrayList<>();
		list = proInfoDao.getListByCategory(map);
		if(list.isEmpty())
			return new ResponseData(400,"No data",null);
		Integer num = proInfoDao.getListByCategoryNum(id);
		return new ResponseData(200,"success",list,num);
	}

	@Override
	public ResponseData getDetailByCode(String id) {
		ProductInfo productInfo = proInfoDao.getDetailByCode(id);
		if(productInfo != null) {
			return new ResponseData(200,"success",productInfo);
		}else {
			return new ResponseData(400,"no data",null);
		}
	}

	@Override
	public ResponseData getIfcheckedList(String status,Integer pageindex, Integer pagesize) {
		if(pageindex == null)
			pageindex = 0;
		if(pagesize == null)
			pagesize = 20;
		Map<String,Object> map = new HashMap<>();
		map.put("auditStatus", status);
		map.put("pageindex", pageindex);
		map.put("pagesize", pagesize);
		List<ProductInfo> list = proInfoDao.getIfcheckedList(map);
		if(list != null) {
			Integer num = proInfoDao.getIfcheckedListNum(status);
			return new ResponseData(200,"success",list,num);
		}else {
			return new ResponseData(400,"no data",null);
		}
	}

	@Override
	public ResponseData getIfShelfList(String status,Integer pageindex, Integer pagesize) {
		if(pageindex == null)
			pageindex = 0;
		if(pagesize == null)
			pagesize = 20;
		Map<String,Object> map = new HashMap<>();
		map.put("publishStatus", status);
		map.put("pageindex", pageindex);
		map.put("pagesize", pagesize);
		List<ProductInfo> list = proInfoDao.getIfShelfList(map);
		if(list != null) {
			Integer num = proInfoDao.getIfShelfListNum(status);
			return new ResponseData(200,"success",list,num);
		}else {
			return new ResponseData(400,"no data",null);
		}
	}

	@Override
	public ResponseData getListByCondition(String name, String procode, String categoryId1, String categoryId2,
			String categoryId3, String status1, String status2,Integer pageindex, Integer pagesize) {
		if(pageindex == null)
			pageindex = 0;
		if(pagesize == null)
			pagesize = 20;
		Map<String, Object> map = new HashMap<>();
		map.put("productName", name);
		map.put("productCode", procode);
		map.put("oneCategoryId", categoryId1);
		map.put("twoCategoryId", categoryId2);
		map.put("threeCategoryId", categoryId3);
		map.put("publishStatus", status1);
		map.put("auditStatus", status2);
		map.put("pageindex", pageindex);
		map.put("pagesize", pagesize);
		List<ProductInfo> list = proInfoDao.getListByCondition(map);
		if(list != null) {
			Map<String, Object> map1 = new HashMap<>();
			map1.put("productName", name);
			map1.put("productCode", procode);
			map1.put("oneCategoryId", categoryId1);
			map1.put("twoCategoryId", categoryId2);
			map1.put("threeCategoryId", categoryId3);
			map1.put("publishStatus", status1);
			map1.put("auditStatus", status2);
			Integer num = proInfoDao.getListByConditionNum(map1);
			return new ResponseData(200,"success",list,num);
		}else {
			return new ResponseData(400,"no data",null);
		}
	}

	@Override
	public ResponseData getSalesByIdSearch(String id) {
		return new ResponseData(200,"success",SolrJUtil.search(1,1,"productId:"+id,new String[] {"productId", "productName","pscore","collect","cart","sales"},null,null,null));
	}	
	
}
