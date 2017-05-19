public class DichotomySearch
{
	public static void main(String[] args)
	{
		//如果出现多个相同值，输出情况有变化
		int[] arr=new int[] {1,2,3,4,4,6,7,8,9};
		dichotomySearch(arr,0,8,5);
	}
	public static void dichotomySearch(int[] arr,int first,int last,int key)
	{
		if(first<last)
		{
			int middle=(first+last)/2;
			if(arr[middle]>key)
				{
					System.out.println("执行一次");
					dichotomySearch(arr,first,middle,key);
				}
			else 
				if(arr[middle]<key)
				{
					System.out.println("执行一次");
					dichotomySearch(arr,middle+1,last,key);
				}
				else if(arr[middle]==key)
				{
					System.out.println("执行一次");
					System.out.println("数组中的索引值"+middle); 
				}
		}
		//不会无限递归调用下去
		else
		{
			if(arr[first]==key)
			{
				System.out.println("执行一次");
				System.out.println("数组中的索引值"+first); 
			}
			else
			{
				System.out.println("执行一次");
				System.out.println("错误");
			}
		}
	}
}