package de.fr.todolist;

public class Entry
{
	private String desc;
	private int count;

	public Entry(String desc, int count)
	{
		this.desc = desc;
		this.count = count;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(desc + ", " + count);
		return builder.toString();
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

}
