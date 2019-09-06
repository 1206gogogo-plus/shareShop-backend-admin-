package whut.service;


import whut.pojo.UserInfo;
import whut.utils.ResponseData;

public interface MemberInfoService {

	public ResponseData getList(Integer status,Integer pageindex, Integer pagesize);

	public ResponseData delete(String jsonString);

	public ResponseData search(Integer pagesize, Integer pageindex, String username, String phoneNumber,String name,
			String identityCardNo, String level,Integer status, String email);

	public ResponseData modify(UserInfo user);

	public ResponseData getDetail(int id);

	public ResponseData getMemberListBySeller(Integer pagesize, Integer pageindex, String username);

	public ResponseData getCountAWeek();
}
