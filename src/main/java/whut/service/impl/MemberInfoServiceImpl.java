package whut.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import whut.dao.UserInfoDao;
import whut.dao.UserLoginDao;
import whut.pojo.UserInfo;
import whut.pojo.UserLogin;
import whut.service.MemberInfoService;
import whut.utils.JsonUtils;
import whut.utils.ResponseData;
@Service
public class MemberInfoServiceImpl implements MemberInfoService {

	@Autowired
	private UserInfoDao dao;

	@Autowired
	private UserLoginDao loginDao;
	
	@Autowired
	private OrderDao orderDao;

	@Override
	public ResponseData getList(Integer status,Integer pageindex, Integer pagesize) {
		if(pageindex == null) {
			pageindex = 0;
		}
		if(pagesize == null) {
			pagesize = 20;
		}
		Map<String,Integer> map = new HashMap<>();
		map.put("status", status);
		map.put("pageindex", pageindex);
		map.put("pagesize", pagesize);
		
		List<UserInfo> list = dao.getList(map);
		if(list.isEmpty()) {
			return new ResponseData(400,"no data satify request",null);
		}else {
			return new ResponseData(200,"success",list);
		}

	}

	@Override
	public ResponseData delete( String jsonString) {
		int id = new JsonUtils(jsonString).getIntValue("id");
		UserLogin userLogin = loginDao.getLoginInfoById(id);
		if(userLogin == null) {
			return new ResponseData(406,"user does not exist",null);
		}
		
		//判断用户状态（已是删除状态禁止删除）
		if(userLogin.getStatus()==0) {
			return new ResponseData(4061,"user status exception",null);
		}
		//店主禁止删除（该操作入口禁止删除）
		if(userLogin.getLevel()==3) {
			return new ResponseData(4062,"prevent deletion of Shopkeeper identity",null);
		}
		
		//判断该用户是否产生过订单
		Map<String, Integer> map = new HashMap<>();
		map.put("pageindex", 1);
		map.put("pagesize", 1);
		map.put("id", id);
		if(orderDao.getListByUser(map)!=null) {
			return new ResponseData(4063,"the user has order information",null);
		}
		
		dao.delete(id);
		return new ResponseData(200,"success",null);
	}

	@Override
	public ResponseData search(Integer pagesize, Integer pageindex, String username, String phoneNumber,String name
			,String identityCardNo, String level,Integer status, String email) {
		if(pageindex == null) {
			pageindex = 0;
		}
		if(pagesize == null) {
			pagesize = 20;
		}
		//通过用户名直接查询，不再进行其他条件判断
		int userId = 0;
		try {
			if(username!=null && !username.equals("")) {
				userId = loginDao.getLoginInfo(username).getUserId();
			}
		}catch(Exception e) {
			return new ResponseData(400,"no specified user",null);
		}

		List<UserInfo> list = new ArrayList<>();
		if(userId!=0) {
			//获取列表
			list.add( dao.getUserInfo(String.valueOf(userId)) );
			return new ResponseData(200,"success",list);
		}
			
		//查询，处分页都可能为空
		Map<String,Object> map = new HashMap<>();
		map.put("pageindex", pageindex);
		map.put("pagesize", pagesize);
		map.put("username", username);
		map.put("phoneNumber", phoneNumber);
		map.put("name", name);
		map.put("identityCardNo", identityCardNo);
		map.put("level", level);
		map.put("email", email);
		map.put("status", status);
		
		list = dao.searchAllInfoByUserInfo(map);
		if(list!=null) {
			return new ResponseData(200,"success",list);
		}
		
		return  new ResponseData(400,"no specified user",null);
	}

	@Override
	public ResponseData modify(UserInfo user) {
		UserInfo userOld = dao.getUserInfo(String.valueOf(user.getUserId()));
		//修改用户信息，密码、登录名、证件号、账户余额禁止修改(编号识别要修改的用户)。需要判断是否满足指定条件，如果用户状态已经是注销状态禁止修改。
		
		//判断当前用户状态
		if( userOld.getUserLogin().getStatus() == 0 ) {
			return new ResponseData(4061,"user status exception",null);
		}
		
		//只处理部分参数的修改
		userOld.setName(user.getName());
		userOld.setIdentityCardType(user.getIdentityCardType());
		userOld.setIdentityCardNo(user.getIdentityCardNo());
		userOld.setPhoneNumber(user.getPhoneNumber());
		userOld.setEmail(user.getEmail());
		userOld.setGender(user.getGender());
		userOld.setBirthday(user.getBirthday());
		
		dao.modify(userOld);
		return new ResponseData(200,"success",null);

	}

	@Override
	public ResponseData getDetail(int id) {
		UserInfo info = dao.getUserInfo(String.valueOf(id));
		if(info != null) {
			return new ResponseData(200,"success",Arrays.asList(info));
		}else {
			return new ResponseData(400,"no data satify request",null);
		}
	}

	@Override
	public ResponseData getMemberListBySeller(Integer pagesize, Integer pageindex, String username) {
		if(pageindex == null) {
			pageindex = 0;
		}
		if(pagesize == null) {
			pagesize = 20;
		}
		int superiorId;
		try {
			superiorId = loginDao.getLoginInfo(username).getUserId();
		}catch(Exception e) {
			return new ResponseData(4061,"user does not exist",null);
		}

		List<UserInfo> list = null;
		Map<String,Integer> map = new HashMap<>();
		map.put("pageindex", pageindex);
		map.put("pagesize", pagesize);
		map.put("superiorId", superiorId);
		list = dao.getMemberBySellerId(map);
		if(list==null || list.isEmpty() ) {
			return new ResponseData(4062,"promoter has not downline",null);
		}
		
		return new ResponseData(200,"success",list);
	}

	@Override
	public ResponseData getCountAWeek() {
		ObjectMapper mapper = new ObjectMapper();
		//生成数组结点
		ArrayNode arrNode = mapper.createArrayNode();
		
		Map<String,Object> map = new HashMap<>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal=Calendar.getInstance();
		Date d=cal.getTime();
		for(int i=0;i<7;i++) {
			String day = df.format(d);
			map.put("day", day);
			//生成对象结点
			ObjectNode objNode = mapper.createObjectNode();
			objNode.put("date", day);    /*在jdk1.8中，简单值用put设置*/
			map.put("level", 1);
			objNode.put("user", loginDao.getCountADay(map) );
			map.put("level", 2);
			objNode.put("member", loginDao.getCountADay(map) );
			map.put("level", 3);
			objNode.put("seller", loginDao.getCountADay(map) );
			arrNode.add(objNode);    /*数组结点添加元素不做简单值和结点类的区分*/
			
	        cal.add(Calendar.DATE,-1);
	        d=cal.getTime();
		}
		
		return  new ResponseData(200,"success",arrNode);
	}

}