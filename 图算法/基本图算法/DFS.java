//����ͼ ���ӱ� DFS ʱ�临�Ӷ�(V+E)
//���Դ���һ��ͼ�� ͼ�����ж�����������ӱ�
//���ӱ���ôʵ��?
import java.util.*;
public class DFS
{
	int time=0;
	int size;
	Vertex[] v;
	LinkedList[] list;
	DFS(int size)
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
		DFS test=new DFS(6);
		for(int i=0;i<6;i++)
			test.addVertex(i);
		test.initEdge();
		test.addEge(0,1);
		test.addEge(0,3);
		test.addEge(1,2);
		test.addEge(2,3);
		test.addEge(3,1);
		test.addEge(4,5);
		test.addEge(4,2);
		test.addEge(5,5);
		test.dfs();
		test.print(test.v[0]);
		System.out.println();
		test.print(test.v[1]);
		System.out.println();
		test.print(test.v[2]);
		System.out.println();
		test.print(test.v[3]);
		System.out.println();
		test.print(test.v[4]);
		System.out.println();
		test.print(test.v[5]);
	}
	public void initEdge()
	{
		for(int i=0;i<size;i++)
		{
			list[i].add(v[i]);
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
	public void dfs()
	{
		int i=0;
		//�������еĶ���
		
		//while(v[i]!=null&&i<size)  ����һ����  ��ʾ����Խ��
		while(i<size&&v[i]!=null)
		{
			if(v[i].color==Color.White)
			{
				dfs(v[i],i);
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