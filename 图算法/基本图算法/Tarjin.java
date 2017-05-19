import java.util.*;

/* 
  1、使用pop()时错误
  2、关于全局或局部变量使用错误(本题中的堆栈应该是全局的，不应在递归函数里使用)
  3、LinkedeList报nosuchelement错误(将LinkedList当做堆栈使用，
	pop()操作即删除，当栈为空时，pop()方法会报nosuchelement错误)
*/
public class Tarjin
{
	public static void main(String[] args)
	{
		Vertex[] v=new Vertex[6];
		v[0]=new Vertex('a');
		v[1]=new Vertex('b');
		v[2]=new Vertex('c');
		v[3]=new Vertex('d');
		v[4]=new Vertex('e');
		v[5]=new Vertex('f');
		v[0].connect(v[1]);
		v[0].connect(v[3]);
		v[1].connect(v[2]);
		v[1].connect(v[4]);
		v[2].connect(v[5]);
		v[3].connect(v[4]);
		v[4].connect(v[5]);
		v[4].connect(v[0]);
		Graph gra=new Graph(6,v);
		gra.tarjin();
	}
}
class Graph{
	int size;
	Vertex[] v;
	LinkedList<Vertex> list=new LinkedList<>();
	int time;
	Graph(int size,Vertex[] v)
	{
		this.size=size;
		//for(int i=0;i<size;i++)
			//v[i]=new Vertex();
		this.v=v;
	}
	public void tarjin(Vertex tmp)
	{
		if(!tmp.isVisited())
		{
			time++;
			tmp.setDfn(time);
			tmp.setLow(time);
			tmp.visit();
			list.push(tmp);
			tmp.setInStack();
			List<Vertex.Edge> list1=new LinkedList<>();
			list1=tmp.getLinkedList();
			Iterator it=list1.iterator();
			while(it.hasNext())
			{
				Vertex.Edge now=(Vertex.Edge)it.next();
				if(!((Vertex)(now.getEndVertex())).isVisited()) //未访问 
				{
					tarjin((Vertex)(now.getEndVertex()));
					int low=(tmp.getLow()<((Vertex)(now.getEndVertex())).getLow())?
							tmp.getLow():((Vertex)(now.getEndVertex())).getLow();
					tmp.setLow(low);
				}
				//在栈内
				else if((Vertex)(now.getEndVertex().getInStack())  
				{
					int low=(tmp.getLow()<((Vertex)(now.getEndVertex())).getDfn())?
							tmp.getLow():((Vertex)(now.getEndVertex())).getDfn();
					tmp.setLow(low);
				}
			}
			if(tmp.getDfn()==tmp.getLow())
			{
				Vertex cao;
				do{
					cao=(Vertex)list.pop();
					cao.setOutStack();
					System.out.print(cao.getLabel()+"  ");
				}while(cao!=tmp);
				//while((Vertex)list.pop()!=tmp); 又多弹出一次
				System.out.println();
				
			}
		}
	}
	public void tarjin()
	{
		for(int i=0;i<size;i++)
		{
			if(!v[i].isVisited())
			{
				time=0;
				tarjin(v[i]);
			}
		}
	}
}
class Vertex {

    private char label;
    private List<Edge> edgeList;//到该顶点邻接点的边,实际以java.util.LinkedList存储
    private boolean visited;//标识顶点是否已访问
	private boolean instack;
    private double cost;//顶点的权值,与边的权值要区别开来
	private int dfn;  //Tarjin算法所需
	private int low;
    

    public Vertex(char vertexLabel){
        label = vertexLabel;
        edgeList = new LinkedList<Edge>();//是Vertex的属性,说明每个顶点都有一个edgeList用来存储所有与该顶点关系的边
        visited = false;
        cost = 0;
		instack=false;
    }
    
	  class Edge  {
        private Vertex vertex;// 终点
        private double weight;//权值
        
        //Vertex 类本身就代表顶点对象,因此在这里只需提供 endVertex，就可以表示一条边了
         Edge(Vertex endVertex, double edgeWeight){
            vertex = endVertex;
            weight = edgeWeight;
        }
        
        Vertex getEndVertex(){
            return vertex;
        }
        double getWeight(){
            return weight;
        }
    }

	
	
    /**
     *Task: 这里用了一个单独的类来表示边,主要是考虑到带权值的边
     *可以看出,Edge类封装了一个顶点和一个double类型变量 
     *若不需要考虑权值,可以不需要单独创建一个Edge类来表示边,只需要一个保存顶点的列表即可
     * @author hapjin
     */
     
    public char getLabel() {
        return label;
    }

    public void visit() {
        this.visited = true;
    }


    public void unVisit() {
        this.visited = false;
    }

   
    public boolean isVisited() {
        return visited;
    }

	public  LinkedList<Edge> getLinkedList()
	{
		//需要强制类型转换 泛型的擦除机制  ！！！！！
		//！！！！！		
		return (LinkedList<Edge>)edgeList;
	}
	
	
    public void  connect(Vertex endVertex, double edgeWeight) {
        
         edgeList.add(new Edge(endVertex, edgeWeight));//添加一条新边
    
 
	}
	
	 public void  connect(Vertex endVertex) {
        
         edgeList.add(new Edge(endVertex, 0));//添加一条新边
    
 
	}
   
    
    public boolean hasNeighbor() {
        return !(edgeList.isEmpty());//邻接点实质是存储是List中
    }

  
  
    public void setCost(double newCost) {
        cost = newCost;
    }

    
    public double getCost() {
        return cost;
    }
    
    
	public int getDfn()
	{
		return this.dfn;
	}
	public void setDfn(int dfn)
	{
		this.dfn=dfn;
	}
	public int getLow()
	{
		return this.low;
	}
	public void setLow(int low)
	{
		this.low=low;
	}
	public boolean getInStack()
	{
		return instack;
	}
	public void setInStack()
	{
		instack=true;
	}
	public void setOutStack()
	{
		instack=false;
	}
	
}


