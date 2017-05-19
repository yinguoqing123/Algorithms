import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
 问题描述：
 在单处理器上具有期限和惩罚的单位时间任务调度问题（课本P239）
 实验要求：
 （1）实现这个问题的贪心算法
 （2）将每个wi 替换为 max{w1,w2,...,wn}-wi，运行算法比较结果 
 */

/**
 * @author WSYW126
 * @version 创建时间：2016年6月4日 下午8:29:32 类说明：Alljava
 */
public class TaskSchedul {
	private List<Task> tasks = null; // 所有任务
	private List<Task> earlyTasks = null; // 早任务
	private List<Task> lateTasks = null; // 晚任务
	private Comparator<Task> comparator_d = null;
	private Comparator<Task> comparator_w = null;

	public static void main(String[] args) {
		int[] d = { 4, 2, 4, 3, 1, 4, 6 }; // 各个任务的deadline
		int[] w = { 70, 60, 50, 40, 30, 20, 10 }; // 各个任务的惩罚

		TaskSchedul ts = new TaskSchedul(d, w);
		ts.scheduleTask(); // 任务调度
		ts.printList(); // 输出信息
		System.out
				.println("----------------------------用max{w1,12,...,wn}-wi替换后的结果-----------------------------");
		ts.rescheduleTask(); // 用max{w1,12,...,wn}-wi替换wi后，任务调度
		ts.printList(); // 输出信息
	}

	public void rescheduleTask() { // 用max{w1,12,...,wn}-wi替换wi
		int max = tasks.get(0).getWeight(); // 在init()函数中已经将tasks中的任务按惩罚大小降序排序
		for (Task task : tasks) {
			task.setWeight(max - task.getWeight());
		}
		System.out.print("任务集为：");
		for (Task task : tasks) {
			System.out.print("a" + (task.getId() + 1) + "(d:"
					+ task.getDeadLine() + ",w:" + task.getWeight() + ") ");
		}
		System.out.println();
		Collections.sort(tasks, comparator_w); // 将任务按惩罚大小降序排序
		earlyTasks = new ArrayList<Task>();
		lateTasks = new ArrayList<Task>();
		scheduleTask();
	}
	
	/*
	public void scheduleTask()
	{
		Collections.sort(tasks,comparator_w);
		int n = tasks.size();
		int max=0;						
		for (int i = 0; i < n; i++) 
		{
			if(tasks.get(i).getDeadLine()>max)
				max=tasks.get(i).getDeadLine();
		}
		int mmax=max>n?max:n;
		int[] time=new int[mmax+1];
		for(int i=0;i<=mmax;i++)
		{
			time[i]=-1;
		}
		int end=mmax;
		for(int i=0;i<n;i++)
		{
			int j=tasks.get(i).getDeadLine();
			if(time[j]<0)
			{
				time[j]=tasks.get(i).getId();
			}
			else{
				boolean flag=false;
				for(int begin=j-1;begin>0;begin--)
				{
					if(time[begin]<0)
					{
						time[begin]=tasks.get(i).getId();
						flag=true;
						break;
					}
				}
				if(!flag)
				{
					time[end]=tasks.get(i).getId();
					end--;
				}
			}
		}
		for(int i=1;i<=end&&time[i]>=0;i++)
			earlyTasks.add(tasks.get(time[i]));
		for(int i=end+1;i<=mmax;i++)
			lateTasks.add(tasks.get(time[i]));
		Collections.sort(earlyTasks, comparator_d);
	}
	 */
	//看不懂他是怎样判断任务集是否是独立的
	public void scheduleTask() { // 时间复杂度为O(n^2)
		int n = tasks.size();
		int[] NA = new int[n]; // 标记，用来判断任务集是否独立
		for (int i = 0; i < n; i++) { // NA[0..n-1];
			NA[i] = 0;
		}
		for (int i = 0; i < n && NA[i] == 0; i++)
		{
			int flag = 1;
			Task task = tasks.get(i);
			for (int j = i + 1; j < n && NA[i] == 0 && NA[j] == 0; j++) 
			{
				Task temp = tasks.get(j);
				if (task.getDeadLine() >= temp.getDeadLine()) 
				{
					if (flag <= task.getDeadLine())
					{
						flag++;
					}
					if (flag > task.getDeadLine()) 
					{
						lateTasks.add(temp);
						NA[j] = 1;
					}
				}
			}
		}
		for (int i = 0; i < n; i++) {
			if (NA[i] == 0) {
				earlyTasks.add(tasks.get(i));
			}
		}
		Collections.sort(earlyTasks, comparator_d);
	}
	
	
	public TaskSchedul(int[] d, int[] w) {
		initComparator();
		init(d, w);
	}

	
	public void init(int[] d, int[] w) { // 导入初始任务
		tasks = new ArrayList<Task>();
		earlyTasks = new ArrayList<Task>();
		lateTasks = new ArrayList<Task>();
		int n = d.length;
		for (int i = 0; i < n; i++) {
			Task t = new Task();
			t.setId(i);
			t.setDeadLine(d[i]);
			t.setWeight(w[i]);
			tasks.add(t);
		}
		System.out.print("任务集为：");
		for (Task task : tasks) {
			System.out.print("a" + (task.getId() + 1) + "(d:"
					+ task.getDeadLine() + ",w:" + task.getWeight() + ") ");
		}
		System.out.println();
		Collections.sort(tasks, comparator_w); // 将任务按惩罚大小降序排序
	}

	public void initComparator() {
		comparator_d = new Comparator<Task>() { // 按deadline升序排列任务
			@Override
			public int compare(Task t1, Task t2) {
				if (t1.getDeadLine() > t2.getDeadLine()) {
					return 1;
				} else if (t1.getDeadLine() == t2.getDeadLine()) {
					return 0;
				} else {
					return -1;
				}
			}
		};
		//采用了匿名内部类的创建 类体内不能有构造器 可以通过实例初始化块
		comparator_w = new Comparator<Task>() { // 按(惩罚)w降序排列任务
			@Override
			public int compare(Task t1, Task t2) {
				if (t2.getWeight() > t1.getWeight()) {
					return 1;
				} else if (t2.getWeight() == t1.getWeight()) {
					return 0;
				} else {
					return -1;
				}
			}
		};
	}

	public void printList() {
		int punish = 0;
		System.out.print("贪心算法选择任务为：");
		for (Task t : earlyTasks) {
			System.out.print("a" + (t.getId() + 1) + " ");
		}
		System.out.print("\n被惩罚任务为：");
		for (Task t : lateTasks) {
			System.out.print("a" + (t.getId() + 1) + " ");
		}
		System.out.print("\n总的惩罚数为：");
		for (Task t : lateTasks) {
			punish += t.getWeight();
		}
		System.out.println(punish + "");
	}
}

class Task { // 任务定义
	private int id;
	private int deadLine;
	private int weight;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(int deadLine) {
		this.deadLine = deadLine;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
}
