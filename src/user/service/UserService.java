package user.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;

import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import user.dao.UserDao;
import user.domain.User;
import user.exception.UserException;

/**
 * 用户模块业务层
 * @author yanni
 *
 */
public class UserService
{
	//该层依赖UserDao，所以创建一个UserDao
	private UserDao userDao=new UserDao();

	/**
	 * 登录功能
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public User login(User user) throws SQLException
	{
		return userDao.findByLoginnameAndLoginpass(user.getLoginname(),
				user.getLoginpass());
	}

	/**
	 * 用户名校验
	 * @param loginname
	 * @return
	 */
	public boolean ajaxValidateLoginname(String loginname)
	{
		try
		{

			return userDao.ajaxValidateLoginname(loginname);
		}
		catch(SQLException e)
		{
			throw new RuntimeException(e);
		}
	}

	public void updatePassword(String uid ,String oldPass ,String newPass)
			throws UserException
	{
		/*
		 * 校验老密码
		 */
		try
		{
			boolean bool=userDao.findByUidAndPassword(uid,oldPass);
			if(!bool)
			{//如果老密码错误
				throw new UserException("老密码错误");
			}
			/*
			 * 修改密码
			 */
			userDao.updatePassword(uid,newPass);
		}
		catch(SQLException e)
		{
			throw new RuntimeException();
		}
		
	}

	/**
	 * 激活用户
	 * @param code
	 * @throws UserException 
	 */
	public void activation(String code) throws UserException
	{
		/*
		 * 1通过激活码查询用户
		 * 2如果User为null时，说明是通过激活码查不到该用户，也就是不存在，抛异常（无效激活码）
		 * 3如果查到用户，状态是已经就是true，抛异常（二次激活）
		 * 4修改用户状态为true
		 */

		try
		{
			User user=userDao.findByCode(code);
			if(user==null)
			{
				throw new UserException("无效激活码！");
			}
			if(user.isStatus())
			{
				throw new UserException("已经激活过的用户，请直接登录");
			}
			userDao.updateStatus(user.getUid(),true);
		}
		catch(SQLException e)
		{
			throw new RuntimeException(e);
		}

	}

	/**
	 * Email校验
	 * @param email
	 * @return
	 */
	public boolean ajaxValidateEmail(String email)
	{
		try
		{
			return userDao.ajaxValidateEmail(email);
		}
		catch(SQLException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * 注册用户
	 * @param user
	 */
	public void regist(User user)
	{
		//1数据补齐
		user.setUid(CommonUtils.uuid());
		user.setStatus(false);
		user.setActivationCode(CommonUtils.uuid()+CommonUtils.uuid());
		//2向数据库插入数据
		try
		{
			userDao.add(user);
		}
		catch(SQLException e)
		{
			throw new RuntimeException(e);
		}
		//3.发激活邮件

		//加载配置文件
		Properties prop=new Properties();
		try
		{
			prop.load(this.getClass().getClassLoader()
					.getResourceAsStream("email_template.properties"));
		}
		catch(IOException e1)
		{
			throw new RuntimeException(e1);
		}
		//登录邮件服务器，得到session
		String host=prop.getProperty("host");//服务器主机名
		String name=prop.getProperty("username");//登录名
		String pass=prop.getProperty("password");//登录密码
		Session session=MailUtils.createSession(host,name,pass);

		//创建mail对象
		String from=prop.getProperty("from");//来自
		String to=user.getEmail();//发送给
		String subject=prop.getProperty("subject");//主题
		String content=MessageFormat.format(prop.getProperty("content"),
				user.getActivationCode());//内容
		// MessageForm.format方法会把第一个参数中的{0},使用第二个参数来替换。
		// 例如MessageFormat.format("你好{0}, 你{1}!", "张三", "去死吧"); 返回“你好张三，你去死吧！”
		Mail mail=new Mail(from,to,subject,content);

		//发送
		try
		{
			MailUtils.send(session,mail);
		}
		catch(MessagingException e)
		{
			throw new RuntimeException(e);
		}
		catch(IOException e)
		{
			throw new RuntimeException(e);
		}
	}
}
