package whut.dao;

import java.util.List;
import java.util.Map;

import whut.pojo.ReturnRecord;

public interface OrderReturnDao {

	//修改orderId对应的所有记录（可能有多条）的状态map中数据为orderId与status
	void modifyStatusByOrderId(Map<String, Integer> map);

	//修改orderDetailId对应的记录（一条）map中数据orderDetailId和status
	void modifyStatusByOrderDetailId(Map<String, Integer> map);

	//int pageindex, int pagesize, int userId
	List<ReturnRecord> getListByUser(Map<String, Integer> map);

	//int pageindex, int pagesize, String statusAll(申请退货apply [21]、退货失败fail [29]、完成退货success [25,28]、退货中in[23,23])
	List<ReturnRecord> getListByStatus(Map<String, Object> map);

	List<ReturnRecord> getListByOrderId(int orderId);

	//int pageindex, int pagesize, int type
	List<ReturnRecord> getListByReturnType(Map<String, Integer> map);

	//通过退货id获取退货信息
	ReturnRecord getReturnRecordByReturnId(int returnId);
	
}
