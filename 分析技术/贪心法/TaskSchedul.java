import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
 ����������
 �ڵ��������Ͼ������޺ͳͷ��ĵ�λʱ������������⣨�α�P239��
 ʵ��Ҫ��
 ��1��ʵ����������̰���㷨
 ��2����ÿ��wi �滻Ϊ max{w1,w2,...,wn}-wi�������㷨�ȽϽ�� 
 */

/**
 * @author WSYW126
 * @version ����ʱ�䣺2016��6��4�� ����8:29:32 ��˵����Alljava
 */
public class TaskSchedul {
	private List<Task> tasks = null; // ��������
	private List<Task> earlyTasks = null; // ������
	private List<Task> lateTasks = null; // ������
	private Comparator<Task> comparator_d = null;
	private Comparator<Task> comparator_w = null;

	public static void main(String[] args) {
		int[] d = { 4, 2, 4, 3, 1, 4, 6 }; // ���������deadline
		int[] w = { 70, 60, 50, 40, 30, 20, 10 }; // ��������ĳͷ�

		TaskSchedul ts = new TaskSchedul(d, w);
		ts.scheduleTask(); // �������
		ts.printList(); // �����Ϣ
		System.out
				.println("----------------------------��max{w1,12,...,wn}-wi�滻��Ľ��-----------------------------");
		ts.rescheduleTask(); // ��max{w1,12,...,wn}-wi�滻wi���������
		ts.printList(); // �����Ϣ
	}

	public void rescheduleTask() { // ��max{w1,12,...,wn}-wi�滻wi
		int max = tasks.get(0).getWeight(); // ��init()�������Ѿ���tasks�е����񰴳ͷ���С��������
		for (Task task : tasks) {
			task.setWeight(max - task.getWeight());
		}
		System.out.print("����Ϊ��");
		for (Task task : tasks) {
			System.out.print("a" + (task.getId() + 1) + "(d:"
					+ task.getDeadLine() + ",w:" + task.getWeight() + ") ");
		}
		System.out.println();
		Collections.sort(tasks, comparator_w); // �����񰴳ͷ���С��������
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
	//���������������ж������Ƿ��Ƕ�����
	public void scheduleTask() { // ʱ�临�Ӷ�ΪO(n^2)
		int n = tasks.size();
		int[] NA = new int[n]; // ��ǣ������ж������Ƿ����
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

	
	public void init(int[] d, int[] w) { // �����ʼ����
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
		System.out.print("����Ϊ��");
		for (Task task : tasks) {
			System.out.print("a" + (task.getId() + 1) + "(d:"
					+ task.getDeadLine() + ",w:" + task.getWeight() + ") ");
		}
		System.out.println();
		Collections.sort(tasks, comparator_w); // �����񰴳ͷ���С��������
	}

	public void initComparator() {
		comparator_d = new Comparator<Task>() { // ��deadline������������
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
		//�����������ڲ���Ĵ��� �����ڲ����й����� ����ͨ��ʵ����ʼ����
		comparator_w = new Comparator<Task>() { // ��(�ͷ�)w������������
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
		System.out.print("̰���㷨ѡ������Ϊ��");
		for (Task t : earlyTasks) {
			System.out.print("a" + (t.getId() + 1) + " ");
		}
		System.out.print("\n���ͷ�����Ϊ��");
		for (Task t : lateTasks) {
			System.out.print("a" + (t.getId() + 1) + " ");
		}
		System.out.print("\n�ܵĳͷ���Ϊ��");
		for (Task t : lateTasks) {
			punish += t.getWeight();
		}
		System.out.println(punish + "");
	}
}

class Task { // ������
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
