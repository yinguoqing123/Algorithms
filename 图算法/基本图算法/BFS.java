//无向图  关联矩阵表示
import java.util.*;
public class BFS
{
	int maxSize;
	GraphVertex[] graphVertex;
	int[][] matrix;
	boolean white=true;
	boolean gray=false;
	public BFS(int maxSize)
	{
		this.maxSize=maxSize;
		graphVertex=new GraphVertex[maxSize];
		matrix=new int[maxSize][maxSize];
	}
	public static void main(String[] args)
	{
		BFS test=new BFS(7);
		test.addVertex(0);
		test.add(1,0,1);
		test.add(2,1,2);
		test.add(3,1,3);
		test.add(4,3,4);
		test.add(5,1,5);
		test.add(6,2,6);
		test.bfs(test.graphVertex[0]);
		test.print(test.graphVertex[4]);
		test.print(test.graphVertex[6]);
		
	}
	private  void addVertex(int id)
	{
		graphVertex[id]=new GraphVertex(id);
	}
	private  void add(int id,int start,int end)
	{
		graphVertex[id]=new GraphVertex(id);
		matrix[start][end]=1;
		matrix[end][start]=1;
	}
	private void bfs(GraphVertex vertex)
	{
		Deque<GraphVertex> que=new ArrayDeque<>();
		vertex.color=gray;
		que.addFirst(vertex);
		while(que.size()!=0)
		{
			GraphVertex tp=que.poll();
			int n=tp.id;
			for(int i=0;i<maxSize;i++)
			{
				if(matrix[n][i]==1)
				{
					if(graphVertex[i].color==white)
					{
						graphVertex[i].color=gray;
						graphVertex[i].p=tp;
						graphVertex[i].d=tp.d+1;
						que.addFirst(graphVertex[i]);
					}
				}
			}
		}
		
	}
	private void print(GraphVertex vertex)
	{
		do
		{
			System.out.print(vertex.id+"  ");
			vertex=vertex.p;
		}while(vertex.p!=null);
		System.out.print(vertex.id);
		System.out.println();
	}
	//顶点中附加其余属性
	static class GraphVertex
	{
		int id;
		GraphVertex p;
		int d=0;
		boolean white=true;
		boolean gray=false;
		boolean color=white;
		GraphVertex(int id)
		{
			this.id=id;
		}
		GraphVertex(){}
	}
}