package category.domain;

import java.util.List;
/**
 * 分类模块的实体类
 * @author yanni
 *
 */
public class Category
{
	//这个类双向自身关联，因为一个分类只能有一个父分类，但可以有多个子分类，所以要双向关联自身。
	private String cid;//主键
	private String cname;//分类名称	
	private Category parent;//父分类
	private String desc;//分类描述
	private List<Category> children;//子分类
	public String getCid()
	{
		return cid;
	}
	public void setCid(String cid)
	{
		this.cid=cid;
	}
	public String getCname()
	{
		return cname;
	}
	public void setCname(String cname)
	{
		this.cname=cname;
	}
	public Category getParent()
	{
		return parent;
	}
	public void setParent(Category parent)
	{
		this.parent=parent;
	}
	public String getDesc()
	{
		return desc;
	}
	public void setDesc(String desc)
	{
		this.desc=desc;
	}
	public List<Category> getChildren()
	{
		return children;
	}
	public void setChildren(List<Category> children)
	{
		this.children=children;
	}

	
}
