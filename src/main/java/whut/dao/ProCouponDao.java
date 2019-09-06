package whut.dao;

import java.util.List;
import java.util.Map;

import whut.pojo.CouponInfo;
import whut.pojo.CouponLogs;
import whut.pojo.CouponReceive;

public interface ProCouponDao {

	//获取所有优惠券信息
	List<CouponInfo> getList(Map<String, Object> map);

	void addCoupon(CouponInfo couponInfo);

	CouponInfo getCouponDetailById(String id);

	void deleteCoupon(String id);

	List<CouponReceive> getCouponByUId(Map<String, Object> map);

	//查看优惠券消费记录
	List<CouponLogs> getCouponLogsList(Map<String, Object> map);

	//查看优惠券领取记录
	List<CouponReceive> getCouponReceiveList(Map<String, Object> map);

	void addCouponLogs(CouponLogs couponLogs);

	CouponLogs getCouponLogsDetail(Map<String, Object> map);

	List<CouponLogs> getCouponLogsListByStatus(Map<String, Object> map);

	void modifyCouponLogsStatus(String id);

	void addCouponReceive(CouponReceive couponReceive);

	void modifyCouponReceiveStatus(String id); 
	
	//根据优惠券名称和类型联合查询
	List<CouponInfo> getCouponByNameAndType(Map<String, String> map);

	//以下为分页新增查询总数
	//获取所有优惠券数量
	Integer getListNum();

	//查看优惠券领取记录数量
	Integer getCouponReceiveListNum();

	//查看优惠券消费记录数量
	Integer getCouponLogsListNum();


}
