package whut.service.impl;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import whut.dao.OrderDao;
import whut.dao.OrderReturnDao;
import whut.dao.ProCategoryDao;
import whut.dao.ProSpecsDao;
import whut.dao.SellerBillDao;
import whut.dao.UserLoginDao;
import whut.pojo.OrderDetail;
import whut.pojo.OrderMaster;
import whut.pojo.ProductCategory;
import whut.pojo.ProductSales;
import whut.pojo.ReturnRecord;
import whut.pojo.SellerBill;
import whut.service.MemberOrderService;
import whut.trade.TradeRequest;
import whut.utils.JsonUtils;
import whut.utils.ResponseData;

@Service
public class MemberOrderServiceImpl implements MemberOrderService {

	@Autowired
	private OrderDao dao;
	
	@Autowired
	private ProSpecsDao proSpecsDao;
	
	@Autowired
	private UserLoginDao loginDao;
	
	@Autowired
	private OrderReturnDao orderReturnDao;
	
	@Autowired
	private ProCategoryDao proCategoryDao;
	
	@Autowired
	private TradeRequest tradeRequest;
	
	@Autowired
	private SellerBillDao sellerBillDao;

	@Override
	public ResponseData getListByUser(Integer pageindex, Integer pagesize, int id) {
		if(pageindex == null) {
			pageindex = 0;
		}
		if(pagesize == null) {
			pagesize = 20;
		}
		Map<String, Integer> map = new HashMap<>();
		map.put("pageindex", pageindex);
		map.put("pagesize", pagesize);
		map.put("id", id);
		List<OrderMaster> list = dao.getListByUser(map);
		if(list.isEmpty()) {
			return new ResponseData(400,"no data satify request",null);
		}else {
			return new ResponseData(200,"success",list);
		}
	}
	
	@Override
	public ResponseData getListSearch(Integer pageindex, Integer pagesize, Integer status, String orderNumber, String username, 
			String consignee, String timeBe, String timeEn) {
		if(pageindex == null) {
			pageindex = 0;
		}
		if(pagesize == null) {
			pagesize = 20;
		}
		if(status == 0) {
			status = null;
		}
		Integer userid = null;
		if(username != null) {
			try {
				userid = loginDao.getLoginInfo(username).getUserId();
			}catch(Exception e) {
				return new ResponseData(4001,"the user does not exist",null);
			}
		}

		Map<String, Object> map = new HashMap<>();
		map.put("pageindex", pageindex);
		map.put("pagesize", pagesize);
		map.put("id", userid);
		map.put("status", status);
		map.put("orderNumber", orderNumber);
		map.put("consignee", consignee);
		map.put("timeBe", timeBe);
		map.put("timeEn", timeEn);
		
		List<OrderMaster> list = dao.getListSearch(map);
		if(list.isEmpty()) {
			return new ResponseData(4002,"the user has no order record",null);
		}else {
			return new ResponseData(200,"success",list);
		}
	}

	@Override
	public ResponseData getListByPro(Integer pageindex, Integer pagesize, int id) {
		if(pageindex == null) {
			pageindex = 0;
		}
		if(pagesize == null) {
			pagesize = 20;
		}
		Map<String, Integer> map = new HashMap<>();
		map.put("pageindex", pageindex);
		map.put("pagesize", pagesize);
		map.put("id", id);
		
		List<OrderDetail> list = dao.getListByPro(map);
		if(list.isEmpty()) {
			return new ResponseData(400,"no data satify request",null);
		}else {
			return new ResponseData(200,"success",list);
		}
	}
	
	@Override
	public ResponseData getDetailListByOrderId(int orderId) {
		List<OrderDetail> list = dao.getDetailListByOrderId(orderId);
		if(list.isEmpty()) {
			return new ResponseData(400,"no data satify request",null);
		}else {
			return new ResponseData(200,"success",list);
		}
	}

	@Override
	public ResponseData getDetail(int orderId) {
		OrderMaster  orderMaster = dao.getMasterByOrderId(orderId);
		if(orderMaster != null) {
			return new ResponseData(200,"success", Arrays.asList(orderMaster));
		}else {
			return new ResponseData(400,"no data satify request",null);
		}
	}

	/**
	 * 修改订单信息
	 * 需要判断订单状态（订单已关闭等状态、禁止修改）
	 * 订单已发货（就不能修改物流信息<地址单号等>）
	 * 订单完成等状态也不能修改了
	 */
	@Override
	public ResponseData modifyOrder(OrderMaster orderMaster) {
		OrderMaster orderMasterOld = dao.getMasterByOrderId(orderMaster.getOrderId());
		//判断当前订单状态
		if( orderMasterOld.getOrderStatus()==1 ||  orderMasterOld.getOrderStatus()==2) {
			//满足条件可以修改
		}else {
			return new ResponseData(4061,"current status prohibits modification",null);
		}

		
		//只修改部分允许修改的数据
		orderMasterOld.setConsigneeName(orderMaster.getConsigneeName());
		orderMasterOld.setConsigneePhone(orderMaster.getConsigneePhone());
		orderMasterOld.setPostalCode(orderMaster.getPostalCode());
		orderMasterOld.setState(orderMaster.getState());
		orderMasterOld.setCity(orderMaster.getCity());
		orderMasterOld.setFirstAddr(orderMaster.getFirstAddr());
		orderMasterOld.setSecondAddr(orderMaster.getSecondAddr());
		orderMasterOld.setExpressNumber(orderMaster.getExpressNumber());
		
		dao.modifyOrder(orderMasterOld);
		return new ResponseData(200,"success",null);
	
	}

	/**
	 * 通过订单状态判断是否允许修改
	 */
	@Override
	public ResponseData modifyPro(OrderDetail orderDetail) {
		OrderDetail orderDetailOld = dao.getOrderDetailByOrderDetailId(orderDetail.getOrderDetailId());
		
		//判断当前订单状态
		if( orderDetailOld.getStatus()==1 ||  orderDetailOld.getStatus()==2) {
			//满足条件可以修改
		}else {
			return new ResponseData(4061,"current status prohibits modification",null);
		}
		int productIdOld = proSpecsDao.getProSpecsBySpecsId(orderDetailOld.getProductSpecsId()).getProductId();
		int productId = proSpecsDao.getProSpecsBySpecsId(orderDetail.getProductSpecsId()).getProductId();
		if( productIdOld != productId ) {
			return new ResponseData(4062,"no exchange for commodity except for color ..",null);
		}
		
		if( orderDetailOld.getProductPrice() != orderDetail.getProductPrice()) {
			return new ResponseData(4063,"replacement of goods at different prices",null);
		}
		
		//通过修改商品名来修改商品颜色分类，并且是同一个商品，即商品名相同
		orderDetailOld.setProductSpecsId(orderDetail.getProductSpecsId());
		
		dao.modifyPro(orderDetailOld);
		return new ResponseData(200,"success",null);
	
	}

	@Override
	public ResponseData getRecordByUser(Integer pageindex, Integer pagesize, String user, String timebe, String timeen) {
		if(pageindex == null) {
			pageindex = 0;
		}
		if(pagesize == null) {
			pagesize = 20;
		}
		int id = 0;
		try {
			id = loginDao.getLoginInfo(user).getUserId();
		}catch(Exception e) {
			return new ResponseData(4001,"the user does not exist",null);
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("pageindex", pageindex);
		map.put("pagesize", pagesize);
		map.put("id", id);
		map.put("timebe", timebe);
		map.put("timeen", timeen);
		
		List<SellerBill> list = dao.getRecordByUser(map);
		if(list.isEmpty()) {
			return new ResponseData(4002,"the user has no order record",null);
		}else {
			return new ResponseData(200,"success",list);
		}
	}

	@Override
	public ResponseData getCountWeekOrYear(int type) {
		ObjectMapper mapper = new ObjectMapper();
		//生成数组结点
		ArrayNode arrNode = mapper.createArrayNode();
		
		if(type == 1) {
			//一年中每个月的记录
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
			Calendar cal=Calendar.getInstance();
			Date d=cal.getTime();
			for(int i=0;i<12;i++) {
				String day = df.format(d);
				
				//生成对象结点
				ObjectNode objNode = mapper.createObjectNode();
				objNode.put("date", day);    /*在jdk1.8中，简单值用put设置*/
				objNode.put("count", dao.getCountAMonth(cal.get(1)+"-"+cal.get(2)+1) );
				objNode.put("averageCost", dao.getAverageCostAMonth( cal.get(1)+"-"+cal.get(2)+1 ));
				objNode.put("amount", dao.getAmountAMonth( cal.get(1)+"-"+cal.get(2)+1 ));
				arrNode.add(objNode);    /*数组结点添加元素不做简单值和结点类的区分*/
				

		        cal.add(Calendar.MONTH,-1);
		        d=cal.getTime();
			}
		}else if(type == 7) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal=Calendar.getInstance();
			Date d=cal.getTime();
			for(int i=0;i<7;i++) {
				String day = df.format(d);
				
				
				//生成对象结点
				ObjectNode objNode = mapper.createObjectNode();
				objNode.put("date", day);    /*在jdk1.8中，简单值用put设置*/
				objNode.put("count", dao.getCountADay(day) );
				objNode.put("averageCost", dao.getAverageCostADay(day) );
				objNode.put("amount", dao.getAmountADay(day) );
				arrNode.add(objNode);    /*数组结点添加元素不做简单值和结点类的区分*/
				
				
		        cal.add(Calendar.DATE,-1);
		        d=cal.getTime();
			}
	        
			
		}else {
			return new ResponseData(406,"parameters incorrect",null);
		}
		return new ResponseData(200,"success",arrNode);
	}

	@Override
	public ResponseData getCountWeekOrYearForOnePro(int type, int proId) {
		ObjectMapper mapper = new ObjectMapper();
		//生成数组结点
		ArrayNode arrNode = mapper.createArrayNode();
		
		
		Map<String, Object> map = new HashMap<>();
		map.put("proId", proId);
		if(type == 1) {
			//一年中每个月的记录
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
			Calendar cal=Calendar.getInstance();
			Date d=cal.getTime();
			for(int i=0;i<12;i++) {
				String day = df.format(d);
				map.put("date", cal.get(1)+"-"+cal.get(2)+1);
				
				//生成对象结点
				ObjectNode objNode = mapper.createObjectNode();
				objNode.put("date", day);    /*在jdk1.8中，简单值用put设置*/
				objNode.put("count", dao.getCountAMonthAPro(map) );
				objNode.put("averageCost", dao.getAverageCostAMonthAPro(map) );
				objNode.put("amount", dao.getAmountAMonthAPro(map) );
				arrNode.add(objNode);    /*数组结点添加元素不做简单值和结点类的区分*/

		        cal.add(Calendar.MONTH,-1);
		        d=cal.getTime();
			}
		}else if(type == 7) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal=Calendar.getInstance();
			Date d=cal.getTime();
			for(int i=0;i<7;i++) {
				String day = df.format(d);
				map.put("date", day);
				
				//生成对象结点
				ObjectNode objNode = mapper.createObjectNode();
				objNode.put("date", day);    /*在jdk1.8中，简单值用put设置*/
				objNode.put("count", dao.getCountADayAPro(map) );
				objNode.put("averageCost", dao.getAverageCostADayAPro(map) );
				objNode.put("amount", dao.getAmountADayAPro(map));
				arrNode.add(objNode);    /*数组结点添加元素不做简单值和结点类的区分*/

		        cal.add(Calendar.DATE,-1);
		        d=cal.getTime();
			}
		}else {return new ResponseData(406,"parameters incorrect",null);}

		return new ResponseData(200,"success",arrNode);
	}

	@Override
	public ResponseData getCountForOneClass(int cateId) {
		ObjectMapper mapper = new ObjectMapper();
		//生成数组结点
		ArrayNode arrNode = mapper.createArrayNode();
		
		ProductCategory productCategory = proCategoryDao.getCategoryById(cateId);
		if(productCategory == null) {
			return new ResponseData(406,"parameters incorrect",null);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("cateId", cateId);
		//map.put("cateLevel", productCategory.getCategoryLevel());
		//一年中每个月的记录
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		Calendar cal=Calendar.getInstance();
		Date d=cal.getTime();
		for(int i=0;i<12;i++) {
			String day = df.format(d);
			map.put("day",  cal.get(1)+"-"+cal.get(2)+1 );
			
			//生成对象结点
			ObjectNode objNode = mapper.createObjectNode();
			objNode.put("date", day);    /*在jdk1.8中，简单值用put设置*/
			objNode.put("count", dao.getCountAMonthForClass(map) );
			objNode.put("averageCost", dao.getAverageCostAMonthForClass(map) );
			objNode.put("amount", dao.getAmountAMonthForClass(map) );
			arrNode.add(objNode);    /*数组结点添加元素不做简单值和结点类的区分*/

	        cal.add(Calendar.MONTH,-1);
	        d=cal.getTime();
		}
		
		return new ResponseData(200,"success",arrNode);
	}

	@Override
	public ResponseData getCountClassForOneGood(int cateId, Integer pageindex, Integer pagesize, String timeBe, String timeEn) {
		if(pageindex == null) {
			pageindex = 0;
		}
		if(pagesize == null) {
			pagesize = 20;
		}
		if(cateId!=0) {
			ProductCategory productCategory = proCategoryDao.getCategoryById(cateId);
			if(productCategory == null) {
				return new ResponseData(406,"parameters incorrect",null);
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("cateId", cateId);
		map.put("pageindex", pageindex);
		map.put("pagesize", pagesize);
		map.put("timeBe", timeBe);
		map.put("timeEn", timeEn);
		
		List<ProductSales> productSales = dao.getCountClassForOneGood(map);
		
		return new ResponseData(productSales);
	}

	@Override
	public ResponseData getOrderStatus() {
		ObjectMapper mapper = new ObjectMapper();
		 
		//生成数组结点
		ArrayNode arrNode = mapper.createArrayNode();
		
		//生成对象结点
		ObjectNode objNode0 = mapper.createObjectNode();
		objNode0.put("全部", "0");
		arrNode.add(objNode0);
		ObjectNode objNode1 = mapper.createObjectNode();
		objNode1.put("待付款", 1);
		arrNode.add(objNode1);
		ObjectNode objNode2 = mapper.createObjectNode();
		objNode2.put("待发货", 2);
		arrNode.add(objNode2);
		ObjectNode objNode3 = mapper.createObjectNode();
		objNode3.put("待收货", 3);
		arrNode.add(objNode3);
		ObjectNode objNode4 = mapper.createObjectNode();
		objNode4.put("已完成", 4);
		arrNode.add(objNode4);
		ObjectNode objNode5 = mapper.createObjectNode();
		objNode5.put("退货中", 5);
		arrNode.add(objNode5);
		ObjectNode objNode6 = mapper.createObjectNode();
		objNode6.put("换货中", 6);
		arrNode.add(objNode6);
		ObjectNode objNode10 = mapper.createObjectNode();
		objNode10.put("其它", 10);
		arrNode.add(objNode10);
		
		return new ResponseData(200,"success",arrNode);
	}

	@Override
	public ResponseData dealReturn(String jsonString) {
		JsonUtils jsonUtils = new JsonUtils(jsonString);
		
		int returnId = jsonUtils.getIntValue("returnId");
		int isAgree = jsonUtils.getIntValue("isAgree");
		
		ReturnRecord returnRecord = orderReturnDao.getReturnRecordByReturnId(returnId);
		
		if(returnRecord == null) {
			return new ResponseData(4061,"该退货记录不存在",null);
		}
		if(returnRecord.getStatus()!=21) {
			//发货后申请退货
			return new ResponseData(4062,"该退货记录不符合处理要求",null);
		}
		
		//修改子订单状态，订单状态不修改-------------------------------------------------------
		Map<String, Integer> map = new HashMap<>();
		if(isAgree == 1) {
			map.put("status", 22);
		}else {
			map.put("status", 28);
		}
		map.put("orderDetailId", returnRecord.getOrderDetailId());
		dao.modifyProStatus(map);
		orderReturnDao.modifyStatusByOrderDetailId(map);
		return new ResponseData(200,"success",null);
	}
	

	@Override
	public ResponseData dealReturnWait(String jsonString) {
		JsonUtils jsonUtils = new JsonUtils(jsonString);
		
		int orderId = jsonUtils.getIntValue("returnId");
		int isAgree = jsonUtils.getIntValue("isAgree");
		
		OrderMaster orderMaster = dao.getMasterByOrderId(orderId);
		
		if(orderMaster == null) {
			return new ResponseData(4061,"该退货记录不存在",null);
		}
		if(orderMaster.getOrderStatus()!=11) {
			//发货后申请退货
			return new ResponseData(4062,"该退货记录不符合处理要求",null);
		}
		
		Map<String, Integer> map = new HashMap<>();
		map.put("orderId", orderId);
		if(isAgree == 1) {
			map.put("status", 12);
			dao.modifyOrderStatus(map);
			dao.modifyProStatusByOrderId(map);
			//修改退货表状态
			orderReturnDao.modifyStatusByOrderId(map);
			//处理收益表
			map.put("status", -1);
			sellerBillDao.modifyStatusByOrderId(map);
			//处理退货，支付退款
			tradeRequest.tradeRefund(String.valueOf(orderMaster.getOrderId()), "", orderMaster.getPaymentMoney(), 
					"商户取消订单", "0", "退款商品名");
			
		}else {
			map.put("status", 28);
			orderReturnDao.modifyStatusByOrderId(map);
		}
		return new ResponseData(200,"success",null);
	}

	@Override
	public ResponseData dealReturnBack(String jsonString) {
		JsonUtils jsonUtils = new JsonUtils(jsonString);
		
		int returnId = jsonUtils.getIntValue("returnId");
		int isAgree = jsonUtils.getIntValue("isAgree");
		
		ReturnRecord returnRecord = orderReturnDao.getReturnRecordByReturnId(returnId);
		
		if(returnRecord == null) {
			return new ResponseData(4061,"该退货记录不存在",null);
		}
		if(returnRecord.getStatus()!=22) {
			return new ResponseData(4062,"该退货记录不符合处理要求",null);
		}
		
		//修改子订单状态，订单状态不修改
		Map<String, Integer> map = new HashMap<>();
		map.put("orderDetailId", returnRecord.getOrderDetailId());
		if(isAgree == 1) {
			map.put("status", 29);
			//处理退货，支付退款
			tradeRequest.tradeRefund(String.valueOf(returnRecord.getOrderId()), "", returnRecord.getReturnMoney(), 
					"商户取消订单", "001", "退款商品名");
			//处理seller表
			map.put("status", -1);
			sellerBillDao.modifyStatusByOrderDetailId(map);
		}else {
			map.put("status", 23);
		}
		//修改退货表状态
		orderReturnDao.modifyStatusByOrderDetailId(map);
		return new ResponseData(200,"success",null);
	}

	@Override
	public ResponseData deliver(String jsonString) {
		JsonUtils jsonUtils = new JsonUtils(jsonString);
		int orderId = jsonUtils.getIntValue("orderId");
		
		OrderMaster orderMaster = dao.getMasterByOrderId(orderId);
		
		if(orderMaster == null) {
			return new ResponseData(4061,"该订单记录不存在",null);
		}
		if(orderMaster.getOrderStatus()!=2 ) {
			return new ResponseData(4062,"订单未支付或其它不符合发货条件",null);
		}
		
		//修改子订单状态，订单状态不修改-------------------------------------------------------
		Map<String, Integer> map = new HashMap<>();
		map.put("status", 4);
		map.put("orderId", orderId);
		dao.modifyOrderStatus(map);
		dao.modifyProStatusByOrderId(map);
		return new ResponseData(200,"success",null);
	}

	@Override
	public ResponseData cancel(String jsonString) {
		JsonUtils jsonUtils = new JsonUtils(jsonString);
		int orderId = jsonUtils.getIntValue("orderId");
		
		OrderMaster orderMaster = dao.getMasterByOrderId(orderId);
		
		if(orderMaster == null) {
			return new ResponseData(4061,"该订单记录不存在",null);
		}
		
		if(orderMaster.getOrderStatus() == 1 ) {
			//修改子订单状态，订单状态和收益表状态
			Map<String, Integer> map = new HashMap<>();
			map.put("status", 15);
			map.put("orderId", orderId);
			dao.modifyOrderStatus(map);
			dao.modifyProStatusByOrderId(map);
			//收益表数据失败
			map.put("status", -1);
			sellerBillDao.modifyStatusByOrderId(map);
			return new ResponseData(200,"success",null);
		}else if(orderMaster.getOrderStatus() == 2 ) {
			//修改子订单状态，订单状态和收益表状态
			Map<String, Integer> map = new HashMap<>();
			map.put("status", 15);
			map.put("orderId", orderId);
			dao.modifyOrderStatus(map);
			dao.modifyProStatusByOrderId(map);
			//收益表数据失败
			map.put("status", -1);
			sellerBillDao.modifyStatusByOrderId(map);
			//支付退款
			tradeRequest.tradeRefund(String.valueOf(orderMaster.getOrderId()), "", orderMaster.getPaymentMoney(), 
					"商户取消订单", "0", "退款商品名");
			return new ResponseData(200,"success",null);
		}else {
			return new ResponseData(4062,"订单未支付或其它不符合发货条件",null);
		}
	}

}
