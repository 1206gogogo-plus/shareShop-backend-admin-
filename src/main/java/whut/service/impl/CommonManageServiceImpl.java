package whut.service.impl;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient.RemoteSolrException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import whut.dao.ProDiscountDao;
import whut.pojo.ProductDiscount;
import whut.service.CommonManageService;
import whut.utils.JedisUtil;
import whut.utils.ResponseData;
import whut.utils.SolrJUtil;

@Service
public class CommonManageServiceImpl implements CommonManageService {
	
	@Autowired
	public ProDiscountDao proDiscountDao;
	
	@Override
	public ResponseData getDiscount(int category) {
		if(category != 0) {
			return new ResponseData(4001,"parameter error",null);
		}
		//站点总体折扣在折扣表主键为5，若修改代码也要改--------
		ProductDiscount productDiscount = proDiscountDao.search(String.valueOf(0));
		if(productDiscount==null) {
			return new ResponseData(4002,"exceptions (1) to database storage, please contact the administrator",null);
		}
		if(productDiscount.getDiscountId() != 5) {
			return  new ResponseData(4003,"exceptions (2) to database storage, please contact the administrator",null);
		}
		return new ResponseData(200,"success",productDiscount);
	}

	@Override
	public ResponseData modifyDiscount(ProductDiscount productDiscountNew) {
		int category = productDiscountNew.getCategoryId();
		if(category != 0) {
			return new ResponseData(4001,"parameter error",null);
		}
		//站点总体折扣在折扣表主键为5，若修改代码也要改--------
		ProductDiscount productDiscount = proDiscountDao.search(String.valueOf(0));
		if(productDiscount==null) {
			return new ResponseData(4002,"exceptions (1) to database storage, please contact the administrator",null);
		}
		if(productDiscount.getDiscountId() != 5) {
			return  new ResponseData(4003,"exceptions (2) to database storage, please contact the administrator",null);
		}
		
		//数据库存储无异常
		//处理修改
		productDiscountNew.setDiscountId(5);
		proDiscountDao.modify(productDiscountNew);
		return new ResponseData(200,"success",null);
	}

	@Override
	public ResponseData getSysInfo() {
		
		ObjectMapper mapper = new ObjectMapper();
		//ArrayNode arrNode = mapper.createArrayNode();
		
		ObjectNode objNode = mapper.createObjectNode();
		try {
			SolrJUtil.getSolrClient().commit("products_core");
			objNode.put("solr", "ok");
		}catch(SolrServerException e1) {
			//无法连接到Solr服务器异常
			objNode.put("solr", "error");
		}catch(Exception e) {
			//
		}
		//arrNode.add(objNode1);
		
		//ObjectNode objNode2 = mapper.createObjectNode();
		try {
			JedisUtil.closeJedis(JedisUtil.getJedis());
			objNode.put("redis", "ok");
		}catch(Exception e) {
			//redis服务器异常
			objNode.put("redis", "error");
		}
		//arrNode.add(objNode2);
		
		return new ResponseData(200,"success",objNode);
	}

}
