package whut.service;

import whut.utils.ResponseData;

public interface MemberOrderReturnService {

	ResponseData getListByUser(Integer pageindex, Integer pagesize, Integer userId);

	ResponseData getListByStatus(Integer pageindex, Integer pagesize, String statusAll);

	ResponseData getListByOrderId(Integer orderId);

	ResponseData getListByReturnType(Integer pageindex, Integer pagesize, Integer type);

	ResponseData getReturnStatus();

}
