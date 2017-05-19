//̫���� �����׳���  
import java.util.*;
public class ConnectedComponents
{
	int time=0;
	int size;
	Vertex[] v;
	LinkedList[] list;
	LinkedList[] list1;
	ConnectedComponents(int size)
	{
		this.size=size;
		v=new Vertex[size];
		list=new LinkedList[size];
		//�走  ��ȻҪ����
		for(int i=0;i<size;i++)
		{
			list[i]=new LinkedList();
		}
	}
	public static void main(String[] args)
	{
		ConnectedComponents test=new ConnectedComponents(8);
		for(int i=0;i<8;i++)
			test.addVertex(i);
		test.addEge(0,1);
		test.addEge(1,2);
		test.addEge(1,4);
		test.addEge(1,5);
		test.addEge(2,3);
		test.addEge(2,6);
		test.addEge(3,2);
		test.addEge(3,7);
		test.addEge(4,0);
		test.addEge(4,5);
		test.addEge(5,6);
		test.addEge(6,5);
		test.addEge(6,7);
		test.addEge(7,7);
		test.connectedComponent();
		//�����������ǿ��ͨ����
		for(int i=0;i<test.size;i++)
		{
			test.print(test.v[i]);
			System.out.println();
		}
	}
	public void addVertex(int i)
	{
		v[i]=new Vertex(i);	
	}
	public void addEge(int begin,int end)
	{
		if(v[begin]!=null&&v[end]!=null)
		{
			list[begin].add(v[end]);
		}
	}
	public void connectedComponent()
	{
		dfs();
		inverse(); //ͼ��ת
		initColor(); 
		initP();
		sortVertex();
		time=0;
		list=list1;
		dfs(); 
	}
	private void initP()
	{
		for(int i=0;i<size;i++)
		{
			v[i].p=null;
		}
	}
	private void initColor()
	{
		for(int i=0;i<size;i++)
			v[i].color=Color.White;
	}
	void sortVertex()
	{
		for(int i=0;i<size-1;i++)
		{
			for(int j=i+1;j>0;j--)
			{
				if(v[j].end>v[j-1].end)
				{
					Vertex tmp=v[j-1];
					v[j-1]=v[j];
					v[j]=tmp;
				}
			}
		}
	}
	public void dfs()
	{
		int i=0;
		//�������еĶ���
		
		//while(v[i]!=null&&i<size)  ����һ����  ��ʾ����Խ��
		while(i<size&&v[i]!=null)
		{
			if(v[i].color==Color.White)
			{
				dfs(v[i],v[i].id);
			}
			i++;
		}
	}
	
	public void dfs(Vertex v,int i)
	{
		time=time+1;
		v.color=Color.Gray;
		v.begin=time;
		//����Ѱ��һ��������һ����ɫ�Ķ���
		Iterator it=list[i].iterator();
		while(it.hasNext())
		{
			Vertex tmp=(Vertex)it.next();
			if(tmp.color==Color.White)
			{
				dfs(tmp,tmp.id);
				tmp.p=v;
			}
		}
		//Ѱ����� �ٴη��ص��ýڵ�
		time=time+1;
		v.end=time;
		v.color=Color.Black;
	}
	public void print(Vertex v)
	{
		Vertex tmp=v;
		while(tmp!=null)
		{
			System.out.print(tmp.id+" "+tmp.begin+" "+tmp.end+" ");
			tmp=tmp.p;
			System.out.println();
		}
	}
	
	public void inverse()
	{
		list1=new LinkedList[size];
		for(int i=0;i<size;i++)
		{
			list1[i]=new LinkedList();
		}
		int n=list.length;
		for(int i=0;i<n;i++)
		{
			Iterator it=list[i].iterator();
			while(it.hasNext())
			{
				Vertex tmp=(Vertex)it.next();
				if(tmp!=null)
				{
					list1[tmp.id].add(v[i]);	
				}
			}
		}
	}
	
	static class Vertex
	{
		int id;
		Color color;
		int begin;
		int end;
		Vertex p;
		Vertex(){}
		Vertex(int id)
		{
			this.id=id;
			this.color=Color.White;
			this.begin=0;
		}
	}
	static enum Color
	{
		White,Gray,Black;
	}
}