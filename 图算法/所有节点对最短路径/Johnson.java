/*
警戒 警戒  警戒  警戒   数组越界  数组范围不一致 不能赋值 
详情见90行

如何建立数组间的下标对应关系 

耦合严重

引用型数据的复制： Cloneable 接口
*/

public class Johnson
{
 static int max=Integer.MAX_VALUE;

private static void init(MDG gph,Vertex vex)
{
	for(int i=0;i<gph.vexs.length;i++)
	{
		gph.vexs[i].d=max;
	}
	vex.d=0;
}
private static void relax(MDG gph,Vertex begin,Vertex end)
{
	if(begin.d<max&&begin.d+gph.weight[begin.id][end.id]<end.d)
		end.d=begin.d+gph.weight[begin.id][end.id];
}
private static void print(MDG gph,Vertex vex)
{
	System.out.println("图的起点是:"+vex.id);
	System.out.println();
	for(int i=0;i<gph.vexs.length;i++)
	{
		if(i==vex.id)
			System.out.println("点"+i+"与起点的距离最小距离是:"+0+"  ");
		else
		{
			if(gph.vexs[i].d==max)
				System.out.println("点"+i+"与起点不可达"+"  ");
			else
				System.out.println("点"+i+"与起点的距离最小距离是:"+gph.vexs[i].d+" ");
		}
	}
	System.out.println("===============");
}
private static boolean bellman(MDG gph,Vertex vex)
{
	init(gph,vex);
	int len=gph.vexs.length;
	for(int i=0;i<len-1;i++)
	{
		for(int j=0;j<len;j++)
		{
			for(int k=0;k<len;k++)
			{
				if(gph.matrix[j][k]==true)
					relax(gph,gph.vexs[j],gph.vexs[k]);
			}
		}
	}
	for(int i=0;i<len;i++)
	{
		for(int j=0;j<len;j++)
		{
			if(gph.matrix[i][j])
			{
				if(gph.vexs[i].d+gph.weight[i][j]<gph.vexs[j].d)
			
				return false;
			}
		}
	}
	return true;
}

private static MDG reMDG(MDG gph)
{
	int id=gph.vexs.length;
	int len=id;
	//不用全部重建  只增加一行 
	/*
	boolean[] nnode=new int[len]; 
	for(int i=0;i<len;i++)
	{
		nnode[i]=true;
	}
	int[] nweight=new int[len];
	for(int i=0;i<len;i++)
	{
		nweight[i]=0;
	}
	*/
	
	
	Vertex v=new Vertex(id);
	Vertex[] nvexs=new Vertex[len+1];
	for(int i=0;i<len;i++)
		nvexs[i]=gph.vexs[i];
	nvexs[len]=v;
	
	
	boolean[][] nmatrix=new boolean[len+1][len+1];
	for(int i=0;i<len;i++)
	{
		for(int j=0;j<len;j++)
			nmatrix[i][j]=gph.matrix[i][j];
	}
	for(int i=0;i<len+1;i++)
	{
		nmatrix[len][i]=true;
	}
	for(int i=0;i<len+1;i++)
		nmatrix[i][len]=false;
	
	
	int[][] nweight=new int[len+1][len+1];
	for(int i=0;i<len;i++)
	{
		nweight[i]=gph.weight[i];
	}
	for(int i=0;i<len+1;i++)
	{
		nweight[len][i]=0;
	}
	/*
	for(int i=0;i<len+1;i++)
		nweight[i][len]=0;
	*/
	
	
	MDG ngph=new MDG(nvexs,nmatrix,nweight);
	
	return ngph;
}

private static int find(Vertex[] vexs,int id)
{
	int len=vexs.length;
	for(int i=0;i<len;i++)
	{
		if(vexs[i].id==id)
			return i;
	}
	return -1;
}

//使用二叉堆
private static void dijkstra(MDG gph,Vertex vex)
{
	init(gph,vex);
	int len=gph.vexs.length;
	BiHeap bhv=new BiHeap(gph.vexs);
	while(bhv.heapsize>0)
	{
		Vertex v=bhv.extractMin();
		
		for(int i=0;i<len;i++)
		{
			int k=find(gph.vexs,i); //此时终点的下标是k
			int q=find(gph.vexs,v.id);
			if(gph.matrix[v.id][i]&&gph.vexs[k].isHeap)
			{
				if(gph.vexs[q].d<max&&gph.vexs[k].d>gph.vexs[q].d+gph.weight[v.id][i])
					bhv.decrease(gph.vexs[k],k,v.d+gph.weight[v.id][i]);
			}
		}
	}
}

public static void johnson(MDG gph)
{
	MDG ngph=reMDG(gph);
	int nlen=ngph.vexs.length;
	int len=gph.vexs.length;
	boolean isExit=bellman(ngph,ngph.vexs[nlen-1]);
	if(!isExit)
		System.out.println("图中存在负权重的环路");
	else
	{
		int[] h=new int[len];
		for(int i=0;i<len;i++)
		{
			h[i]=ngph.vexs[i].d;
		}
		for(int i=0;i<len;i++)
		{
			for(int j=0;j<len;j++)
			{
				if(gph.matrix[i][j])
					gph.weight[i][j]+=ngph.vexs[i].d-ngph.vexs[j].d;
			}
		}
		//int[][] allShortd=new int[len][len]; 用矩阵存储信息
		for(int i=0;i<len;i++)
		{
			int k=find(gph.vexs,i);
			dijkstra(gph,gph.vexs[k]);
			System.out.println("图的起点是"+i+":");
			for(int j=0;j<len;j++)
			{
				if(j==i)
					System.out.print("到点"+j+"最小距离是:"+0+"  ");
				else
					System.out.print("到点"+j+"最小距离是:"+(gph.vexs[(find(gph.vexs,j))].d
							+h[j]-h[i])+"  ");
			}
			System.out.println();
		}
		
	}
}

public static void main(String[] args)
{
	Vertex[] vexs=new Vertex[5];
	for(int i=0;i<5;i++)
	{
		vexs[i]=new Vertex(i);
	}
	boolean[][] matrix=new boolean[5][5];
	matrix[0][1]=true;
	matrix[0][2]=true;
	matrix[0][4]=true;
	matrix[1][3]=true;
	matrix[1][4]=true;
	matrix[2][1]=true;
	matrix[3][0]=true;
	matrix[3][2]=true;
	matrix[4][3]=true;
	
	int[][] weight=new int[5][5];
	weight[0][1]=3;
	weight[0][2]=8;
	weight[0][4]=-4;
	weight[1][3]=1;
	weight[1][4]=7;
	weight[2][1]=4;
	weight[3][0]=2;
	weight[3][2]=-5;
	weight[4][3]=6;
	
	MDG gph=new MDG(vexs,matrix,weight);
	
	johnson(gph);

}
} 

class Vertex
{
int id;
int d; //距离
boolean isHeap;
public Vertex(int id)
{
	this.id=id;
}
}

class MDG
{
Vertex[] vexs;
boolean[][] matrix;
int[][] weight;
public MDG(Vertex[] vexs,boolean[][] matrix,int[][] weight)
{
	this.vexs=vexs;
	this.matrix=matrix;
	this.weight=weight;
	
}
}


class BiHeap
{
static int max=Integer.MAX_VALUE;
Vertex[] vexs;
int heapsize=0;

public BiHeap(Vertex[] arr)
{

	vexs=arr;
	heapsize=vexs.length;
	for(int i=0;i<vexs.length;i++)
		vexs[i].isHeap=true;
	
	for(int i=arr.length/2;i>=0;i--)
	{
		minHeapify(vexs,i,heapsize);
	}
}
private void minHeapify(Vertex[] vexs,int i,int heapsize)
{
	int l=0;
	l=2*i+1;
	int r=0;
	r=2*i+2;
	int smallest=i;
	if(l<heapsize&&vexs[l].d<vexs[i].d)
	{
		smallest=l;
	}
	if(r<heapsize&&vexs[r].d<vexs[smallest].d)
		smallest=r;
	if(smallest!=i)
	{
	    Vertex temp=vexs[smallest];
		vexs[smallest]=vexs[i];
		vexs[i]=temp;
		minHeapify(vexs,smallest,heapsize);
	}
}

/*
public  void buildMinHeap(Vertex[] arr)
{
	
	vexs=new Vertex[arr.length];
	heapsize=vexs.length;
	
	for(int i=0;i<arr.length;i++)
	{
		vexs[i]=arr[i];
	}
	
	for(int i=arr.length/2;i>=0;i--)
	{
		minHeapify(vexs,i,heapsize);
	}
}
*/

public  void decrease(Vertex vex,int i,int key)
{
	vex.d=key;
	int j=i/2;
	Vertex tmp;
	while(i>0)
	{
		if(vexs[i].d>=vexs[j].d)
			return;
		else
		{
			tmp=vexs[i];
			vexs[i]=vexs[j];
			vexs[j]=tmp;
		}
		i=j;
		j=i/2;
	}
}

public  Vertex extractMin()
{
	Vertex m=vexs[0]; 
	m.isHeap=false;
	Vertex tmp;
	tmp=vexs[heapsize-1];
	vexs[heapsize-1]=vexs[0];
	vexs[0]=tmp;
	heapsize--;
	minHeapify(vexs,0,heapsize);
	return m;
}
}