package whut.dao;

import java.util.List;

import whut.pojo.ManagerInfo;

public interface ManagerInfoDao {

	//当status为null时查全部信息
	List<ManagerInfo> getList(Integer status);

	void add(ManagerInfo managerInfo);

	ManagerInfo getDetail(int id);

	void modify(ManagerInfo managerInfo);

	void delete(int id);

}
