import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
public class TaskSchedul2
{
	private List<Task> allTask=null;
	private List<Task> earlyTask=null;
	private List<Task> lateTask=null;
	private Comparator<Task> comparator_d=null;
	private Comparator<Task> comparator_w=null;
	public static void main(String[] args)
	{
		int[] deadLine={4, 2, 4, 3, 1, 4, 6 };
		int[] weight2={70, 60, 50, 40, 30, 20, 10};
		int[] weight={10,20,30,40,50,60,70};
		TaskSchedul2 ts = new TaskSchedul2(deadLine, weight);
		ts.scheduleTask(); // 任务调度
		ts.print(); // 输出信息
		System.out.println("-----------");
		
		TaskSchedul2 ts2=new TaskSchedul2(deadLine,weight2);
		ts2.scheduleTask();
		ts2.print();
		
	}
	TaskSchedul2(int[] deadLine,int[] weight)
	{
		initi(deadLine,weight);
		initComparator();
	}
	public void initi(int[] deadLine,int[] weight)
	{
		allTask=new ArrayList<Task>();
		earlyTask=new ArrayList<Task>();
		lateTask=new ArrayList<Task>();
		int n=deadLine.length;
		for(int i=0;i<n;i++)
		{
			Task tmp=new Task(deadLine[i],weight[i]);
			tmp.setId(i+1);
			allTask.add(tmp);
		}
		System.out.println("任务集为：");
		for(Task task:allTask)
		{
			System.out.println(task.getId()+" "+task.getDeadLine()+" "+task.getWeight());
		}
	}
	public void initComparator()
	{
		comparator_d=new Comparator<Task>()  //升序排列
		{
			public int compare(Task t1, Task t2)
			{
				if (t1.getDeadLine() > t2.getDeadLine()) {
					return 1;
				} else if (t1.getDeadLine() == t2.getDeadLine()) {
					return 0;
				} else {
					return -1;
			}
		}};
		comparator_w=new Comparator<Task>()  //降序排列
		{
			public int compare(Task t1,Task t2)
			{
				if(t1.getWeight()>t2.getWeight())
					return -1;
				else if(t1.getWeight()==t2.getWeight())
					return 0;
				else
					return 1;
			}
		};
	}
	/*
	public void scheduleTask() { 
		Collections.sort(allTask,comparator_w);
		int n = allTask.size();
		int[] NA = new int[n];
		int max=0;					//如果max很大，或只有一个max很大 造成内存的浪费  
									//别人写的那个判断独立看不懂
									//时间复杂度为O(n^2)
		for (int i = 0; i < n; i++) {
			NA[i]=0;
			if(allTask.get(i).getDeadLine()>max)
				max=allTask.get(i).getDeadLine();
		}
		for(int i=0;i<n;i++)
		{
			int[] arr=new int[max+1];
			for(int j=0;j<=i;j++)
			{
				if(NA[j]==0)
				{
					int t=allTask.get(j).getDeadLine();
					arr[t]=arr[t]+1;
				}
			}
			for(int t=1;t<=max;t++)
				arr[t]=arr[t-1]+arr[t];
			for(int k=1;k<=max;k++)
			{
				if(arr[k]>k)
					//NA[i]=1;
					//break;   之前的代码  这样break每次在k=1执行完后就跳出本次循环
				{
					NA[i]=1;
					break;
				}
				
			}
		}
		for (int p = 0; p < n; p++) {
			if (NA[p] == 0) 
				earlyTask.add(allTask.get(p));
			else 
				lateTask.add(allTask.get(p));
		}

		Collections.sort(earlyTask, comparator_d);
	}
	*/
	public void scheduleTask()
	{
		Collections.sort(allTask,comparator_w);
		int n = allTask.size();
		for(int i=0;i<n;i++)
		{
			allTask.get(i).setId(i+1);
		}
		int max=0;						
		for (int i = 0; i < n; i++) 
		{
			if(allTask.get(i).getDeadLine()>max)
				max=allTask.get(i).getDeadLine();
		}
		int[] time=new int[max+1];
		for(int i=0;i<=max;i++)
		{
			time[i]=-1;
		}
		for(int i=0;i<n;i++)
		{
			int j=allTask.get(i).getDeadLine();
			if(time[j]<0)
			{
				time[j]=allTask.get(i).getId();
			}
			else{
				for(int begin=j-1;begin>0;begin--)
				{
					if(time[begin]<0)
					{
						time[begin]=allTask.get(i).getId();
						break;
					}
				}
			}
		}
		
		for(int i=1;i<=max;i++)
		{
			if(time[i]<0)
				continue;     //有时会有洞  如第二种数据
			earlyTask.add(allTask.get(time[i]-1));   //排序后ArrayList中元素位置变了
			
		}
		/*
		for(int i=end+1;i<=mmax;i++)
			lateTask.add(allTask.get(time[i]-1));
		*/
		Collections.sort(earlyTask, comparator_d);
	}
	
	public void print()
	{
		int n=earlyTask.size();
		int m=lateTask.size();
		System.out.println("执行的任务是：");
		for(int i=0;i<n;i++)
			System.out.print("("+earlyTask.get(i).getId()+"  "+earlyTask.get(i).getDeadLine()+"  "+earlyTask.get(i).getWeight()+")"+"  ");
		System.out.println();
		/*
		System.out.println("未执行的任务是：");
		for(int i=0;i<m;i++)
			System.out.print("("+lateTask.get(i).getId()+"  "+lateTask.get(i).getDeadLine()+"   "+lateTask.get(i).getWeight()+")");
		System.out.println();
		*/
	}
	static class Task
	{
		private int id;
		private int weight;
		private int deadLine;
		Task(){}
		Task(int deadLine,int weight)
		{
			this.deadLine=deadLine;
			this.weight=weight;
		}
		public  void setId(int id)
		{
			this.id=id;
		}
		public int getId()
		{
			return id;
		}
		public  void setWeight(int weight)
		{
			this.weight=weight;
		}
		public int getWeight()
		{
			return weight;
		}
		public void setDeadLine(int deadLine)
		{
			this.deadLine=deadLine;
		}
		public int getDeadLine()
		{
			return this.deadLine;
		}
	}
}