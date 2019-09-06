package whut.service;

import whut.utils.ResponseData;

public interface SellerInfoService {

	ResponseData getSellerList(Integer pageindex, Integer pagesize);

	ResponseData addSeller(String id);

	ResponseData deleteSeller(String id);

}
