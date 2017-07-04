package book.web.Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import book.domain.Book;
import book.service.BookService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import pager.PageBean;

public class BookServlet extends BaseServlet
{
	private BookService bookService=new BookService();

	/**
	 * 获取当前页的页码
	 * 
	 * @param request
	 * @return
	 */
	private int getPc(HttpServletRequest request)
	{

		int pc=1;// 如果没有值，则默认是1
		String param=request.getParameter("pc");
		if(param!=null&&!param.trim().isEmpty())
		{
			try
			{
				pc=Integer.parseInt(param);
			}
			catch(RuntimeException e)
			{
			}
		}
		return pc;
	}

	/**
	 * 按bid查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String load(HttpServletRequest req ,HttpServletResponse resp)
			throws ServletException,IOException
	{
		String bid=req.getParameter("bid");// 获取链接的参数bid
		Book book=bookService.load(bid);// 通过bid得到book对象
		req.setAttribute("book",book);// 保存到req中
		return "f:/jsps/book/desc.jsp";// 转发到desc.jsp
	}

	/**
	 * 截取url，页面中的分页导航中需要使用它作为超链接的目标
	 * 
	 * @param request
	 * @return
	 */
	/*
	 * http://localhost:8080/goods/BookServlet?methed=findByCategory&cid=xxx&pc=3
	 * /goods/BookServlet + methed=findByCategory&cid=xxx&pc=3
	 */
	private String getUrl(HttpServletRequest request)
	{
		String url=request.getRequestURI()+"?"+request.getQueryString();
		/*
		 * 如果url中存在pc参数，那么需要截取掉，如果不存在，那就不用截取
		 */
		int index=url.lastIndexOf("&pc=");
		if(index!=-1)
		{
			url=url.substring(0,index);
		}
		return url;
	}

	/**
	 * 按分类查
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByCategory(HttpServletRequest request ,
			HttpServletResponse response) throws ServletException,IOException
	{
		/*
		 * 1得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		int pc=getPc(request);
		/*
		 * 2得到url
		 */
		String url=getUrl(request);
		/*
		 * 3获取查询条件，本方法就是cid，也就是分类的id
		 */
		String cid=request.getParameter("cid");
		/*
		 * 4使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Book> pb=bookService.findByCategory(cid,pc);
		/*
		 * 5给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		request.setAttribute("pb",pb);
		return "f:/jsps/book/list.jsp";
	}

	/**
	 * 按作者查
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByAuthor(HttpServletRequest req ,HttpServletResponse resp)
			throws ServletException,IOException
	{
		/*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		int pc=getPc(req);
		/*
		 * 2. 得到url：...
		 */
		String url=getUrl(req);
		/*
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
		String author=req.getParameter("author");
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Book> pb=bookService.findByAuthor(author,pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb",pb);
		return "f:/jsps/book/list.jsp";
	}

	/**
	 * 按出版社查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByPress(HttpServletRequest req ,HttpServletResponse resp)
			throws ServletException,IOException
	{
		req.setCharacterEncoding("UTF-8");

		/*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		int pc=getPc(req);
		/*
		 * 2. 得到url：...
		 */
		String url=getUrl(req);
		/*
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
		String press=req.getParameter("press");
		System.out.println(press);
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Book> pb=bookService.findByPress(press,pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb",pb);
		return "f:/jsps/book/list.jsp";
	}

	/**
	 * 按图书名查
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByBname(HttpServletRequest req ,HttpServletResponse resp)
			throws ServletException,IOException
	{
		/*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		int pc=getPc(req);
		/*
		 * 2. 得到url：...
		 */
		String url=getUrl(req);
		/*
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
		String bname=req.getParameter("bname");
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Book> pb=bookService.findByBname(bname,pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb",pb);
		return "f:/jsps/book/list.jsp";
	}

	/**
	 * 多条件组合查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByCombination(HttpServletRequest req ,
			HttpServletResponse resp) throws ServletException,IOException
	{
		/*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		int pc=getPc(req);
		/*
		 * 2. 得到url：...
		 */
		String url=getUrl(req);
		/*
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
		Book criteria=CommonUtils.toBean(req.getParameterMap(),Book.class);
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Book> pb=bookService.findByCombination(criteria,pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb",pb);
		return "f:/jsps/book/list.jsp";
	}

}
