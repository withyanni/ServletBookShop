package user.dao;

import java.sql.SQLException;

import javax.swing.text.DefaultEditorKit.InsertBreakAction;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import cn.itcast.jdbc.TxQueryRunner;
import user.domain.User;

/**
 * 用户模块持久层
 * 
 * @author yanni
 */
public class UserDao
{

	private QueryRunner qr=new TxQueryRunner();

	/**
	 * 通过uid和密码查找，返回boolean
	 * 
	 * @param uid
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public boolean findByUidAndPassword(String uid ,String password)
			throws SQLException
	{
		String sql="select count(*) from t_user where uid=? and loginpass=?";
		Number number=(Number)qr.query(sql,new ScalarHandler(),uid,password);
		return number.intValue()>0;
	}

	/**
	 * 修改密码
	 * 
	 * @param uid
	 * @param password
	 * @throws SQLException
	 */
	public void updatePassword(String uid ,String password) throws SQLException
	{
		String sql="update t_user set loginpass=? where uid=?";
		qr.update(sql,password,uid);
	}

	/**
	 * 登录账密信息确认。
	 * 
	 * @param name
	 * @param pass
	 * @return
	 * @throws SQLException
	 */
	public User findByLoginnameAndLoginpass(String name ,String pass)
			throws SQLException
	{
		String sql="select *from t_user where loginname=? and loginpass=?";
		return qr.query(sql,new BeanHandler<User>(User.class),name,pass);

	}

	/**
	 * 通过激活码查询用户
	 * 
	 * @param code
	 * @return
	 * @throws SQLException
	 */
	public User findByCode(String code) throws SQLException
	{
		String sql="select * from t_user where activationCode=?";
		return qr.query(sql,new BeanHandler<User>(User.class),code);
	}

	/**
	 * 修改用户激活状态
	 * 
	 * @param uid
	 * @param status
	 * @throws SQLException
	 */
	public void updateStatus(String uid ,boolean status) throws SQLException
	{
		String sql="update t_user set status=? where uid=?";
		qr.update(sql,status,uid);
	}

	/**
	 * 检测用户名是否已经注册
	 * 
	 * @param loginname
	 * @return
	 * @throws SQLException
	 */
	public boolean ajaxValidateLoginname(String loginname) throws SQLException
	{
		String sql="select count(1) from t_user where loginname=?";
		Number number=(Number)qr.query(sql,new ScalarHandler(),loginname);
		return number.intValue()==0;
	}

	/**
	 * 校验email是否已经注册
	 * 
	 * @param eamil
	 * @return
	 * @throws SQLException
	 */
	public boolean ajaxValidateEmail(String eamil) throws SQLException
	{
		String sql="select count(1) from t_user where email=?";
		Number number=(Number)qr.query(sql,new ScalarHandler(),eamil);
		return number.intValue()==0;
	}

	/**
	 * 添加用户
	 * 
	 * @throws SQLException
	 */
	public void add(User user) throws SQLException
	{
		String sql="insert into t_user values(?,?,?,?,?,?)";
		Object[] params=
		{user.getUid(),user.getLoginname(),user.getLoginpass(),user.getEmail(),
				user.isStatus(),user.getActivationCode()};
		qr.update(sql,params);

	}

}
