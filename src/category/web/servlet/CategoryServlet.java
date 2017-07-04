package category.web.servlet;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import category.dao.CategoryDao;
import category.domain.Category;
import category.service.CategoryService;
import cn.itcast.servlet.BaseServlet;

/**
 * 分类模块WEB层
 * @author yanni
 *
 */
public class CategoryServlet extends BaseServlet 
{
	private CategoryService categoryService=new CategoryService();
	
	public String findAll(HttpServletRequest request,HttpServletResponse reponse)throws ServletException,IOException{
		/*
		 * 通过service得到所有的分类
		 * 保存到request中，转发到left.jsp
		 */
		
		List<Category> parents=categoryService.findAll();
		request.setAttribute("parents",parents);
		return "f:/jsps/left.jsp";
		
	}
}
