import java.util.*;

/* 
  1��ʹ��pop()ʱ����
  2������ȫ�ֻ�ֲ�����ʹ�ô���(�����еĶ�ջӦ����ȫ�ֵģ���Ӧ�ڵݹ麯����ʹ��)
  3��LinkedeList��nosuchelement����(��LinkedList������ջʹ�ã�
	pop()������ɾ������ջΪ��ʱ��pop()�����ᱨnosuchelement����)
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
				if(!((Vertex)(now.getEndVertex())).isVisited()) //δ���� 
				{
					tarjin((Vertex)(now.getEndVertex()));
					int low=(tmp.getLow()<((Vertex)(now.getEndVertex())).getLow())?
							tmp.getLow():((Vertex)(now.getEndVertex())).getLow();
					tmp.setLow(low);
				}
				//��ջ��
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
				//while((Vertex)list.pop()!=tmp); �ֶ൯��һ��
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
    private List<Edge> edgeList;//���ö����ڽӵ�ı�,ʵ����java.util.LinkedList�洢
    private boolean visited;//��ʶ�����Ƿ��ѷ���
	private boolean instack;
    private double cost;//�����Ȩֵ,��ߵ�ȨֵҪ������
	private int dfn;  //Tarjin�㷨����
	private int low;
    

    public Vertex(char vertexLabel){
        label = vertexLabel;
        edgeList = new LinkedList<Edge>();//��Vertex������,˵��ÿ�����㶼��һ��edgeList�����洢������ö����ϵ�ı�
        visited = false;
        cost = 0;
		instack=false;
    }
    
	  class Edge  {
        private Vertex vertex;// �յ�
        private double weight;//Ȩֵ
        
        //Vertex �౾��ʹ��������,���������ֻ���ṩ endVertex���Ϳ��Ա�ʾһ������
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
     *Task: ��������һ��������������ʾ��,��Ҫ�ǿ��ǵ���Ȩֵ�ı�
     *���Կ���,Edge���װ��һ�������һ��double���ͱ��� 
     *������Ҫ����Ȩֵ,���Բ���Ҫ��������һ��Edge������ʾ��,ֻ��Ҫһ�����涥����б���
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
		//��Ҫǿ������ת�� ���͵Ĳ�������  ����������
		//����������		
		return (LinkedList<Edge>)edgeList;
	}
	
	
    public void  connect(Vertex endVertex, double edgeWeight) {
        
         edgeList.add(new Edge(endVertex, edgeWeight));//���һ���±�
    
 
	}
	
	 public void  connect(Vertex endVertex) {
        
         edgeList.add(new Edge(endVertex, 0));//���һ���±�
    
 
	}
   
    
    public boolean hasNeighbor() {
        return !(edgeList.isEmpty());//�ڽӵ�ʵ���Ǵ洢��List��
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


