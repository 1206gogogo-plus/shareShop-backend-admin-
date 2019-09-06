package whut.dao;

import java.util.List;
import java.util.Map;

import whut.pojo.OrderDetail;
import whut.pojo.OrderMaster;
import whut.pojo.ProductSales;
import whut.pojo.SellerBill;

public interface OrderDao {

	//String pageindex, String pagesize, String id
	List<OrderMaster> getListByUser(Map<String, Integer> map);

	//String pageindex, String pagesize, int id
	List<OrderDetail> getListByPro(Map<String, Integer> map);
	
	OrderMaster getMasterByOrderId(int orderId);

	List<OrderDetail> getDetailListByOrderId(int orderId);

	void modifyOrder(OrderMaster orderMaster);

	void modifyPro(OrderDetail orderDetail);

	//int orderId, Byte status
	void modifyOrderStatus(Map<String, Integer> map);

	//int orderDetailId, Byte status
	void modifyProStatus(Map<String, Integer> map);

	OrderDetail getOrderDetailByOrderDetailId(Integer orderDetailId);

	//通过订单号修改订单下所有商品对应的状态
	//int orderId, Byte status
	void modifyProStatusByOrderId(Map<String, Integer> map);

	//int pageindex, int pagesize, String user, String timebe, String timeen	获取用户订单
	List<SellerBill> getRecordByUser(Map<String, Object> map);

	//获取某天的订单数
	Integer getCountADay(String day);

	//获取某天的订单总额2018-04-12
	Double getAmountADay(String day);

	//获取某天出售商品的总成本
	Double getAverageCostADay(String day);

	//获取某月的订单数2018-04
	Integer getCountAMonth(String month);

	//获取某月的订单总额
	Double getAmountAMonth(String month);

	//获取某月出售商品的总成本
	Double getAverageCostAMonth(String month);

	//获取某商品某月的销量map :proId、date
	Integer getCountAMonthAPro(Map<String, Object> map);
	
	//获取某商品某月的成本map :proId、date
	Double getAverageCostAMonthAPro(Map<String, Object> map);
	
	//获取某商品某月的销售额map :proId、date
	Double getAmountAMonthAPro(Map<String, Object> map);
	
	//获取某商品某天的销量map :proId、date
	Integer getCountADayAPro(Map<String, Object> map);
	
	//获取某商品某天的成本map :proId、date
	Double getAverageCostADayAPro(Map<String, Object> map);
	
	//获取某商品某天的销售额map :proId、date
	Double getAmountADayAPro(Map<String, Object> map);

	
	//获取某分类下商品的总销量map:cateId\cateLevel分类级别\date2018-08
	//level不需要/serviceImpl中已注释掉
	Integer getCountAMonthForClass(Map<String, Object> map);

	//获取某分类下商品的成本map:cateId\cateLevel分类级别\date2018-08
	//level不需要/serviceImpl中已注释掉
	Double getAverageCostAMonthForClass(Map<String, Object> map);

	//获取某分类下商品的销售额map:cateId\cateLevel分类级别\date2018-08
	//level不需要/serviceImpl中已注释掉
	Double getAmountAMonthForClass(Map<String, Object> map);

	//通过以下字段中的一个或多个查询订单,userId为0表示不需要该条件
	//int pageindex, int pagesize, int status, String orderNumber, int id, String consignee, String timeBe, String timeEn
	List<OrderMaster> getListSearch(Map<String, Object> map);

	//cateId,pageindex,pagesize
	//cateId为0返回所有的，否则返回该分类下商品的
	//返回数据需要的字段：商品名、销量、销售额
	List<ProductSales> getCountClassForOneGood(Map<String, Object> map);
}
