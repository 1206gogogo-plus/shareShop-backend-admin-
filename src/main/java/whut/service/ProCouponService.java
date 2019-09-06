package whut.service;

import whut.pojo.CouponInfo;
import whut.pojo.CouponLogs;
import whut.pojo.CouponReceive;
import whut.utils.ResponseData;

public interface ProCouponService {

	ResponseData getList(Integer pageindex, Integer pagesize);

	ResponseData addCoupon(CouponInfo couponInfo);

	ResponseData getCouponDetailById(String id);

	ResponseData deleteCoupon(String id);

	ResponseData getCouponByUId(String id,Integer pageindex, Integer pagesize);

	ResponseData getCouponLogsList(Integer pageindex, Integer pagesize);

	ResponseData getCouponReceiveList(Integer pageindex, Integer pagesize);

	ResponseData addCouponLogs(CouponLogs couponLogs);

	ResponseData getCouponLogsDetail(String id, String status, String orderNum, Integer pageindex, Integer pagesize);

	ResponseData getCouponLogsListByStatus(String status,Integer pageindex, Integer pagesize);

	ResponseData modifyCouponLogsStatus(String id);

	ResponseData addCouponReceive(CouponReceive couponReceive);

	ResponseData modifyCouponReceiveStatus(String id);

	ResponseData getCouponByNameAndType(String name, String type);

}
