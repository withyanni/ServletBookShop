package pager;

public class Expression
{
	//select * from t_book where pid=1;
	private String name;//对应pid,即查询的名称
	private String operator;//对应=，即操作符
	private String value;//对应1，即数据，也有可能是is null
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name=name;
	}
	public String getOperator()
	{
		return operator;
	}
	public void setOperator(String operator)
	{
		this.operator=operator;
	}
	public String getValue()
	{
		return value;
	}
	public void setValue(String value)
	{
		this.value=value;
	}
	@Override
	public String toString()
	{
		return "Expression [name="+name+", operator="+operator+", value="+value
				+"]";
	}
	public Expression(String name,String operator,String value)
	{
		super();
		this.name=name;
		this.operator=operator;
		this.value=value;
	}
	public Expression()
	{
		super();
	}
}
