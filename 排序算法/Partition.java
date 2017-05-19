public class Partition
{
	public static void main(String[] args)
	{
		int[] arr={9,8,6,8,4,3,2,3,2,3,2,3,23,3};
		partition(arr,0,13);
	}
	private static void partition(int[] arr,int low,int high)
	{
		int key=arr[high];
		int i=low-1;
		int t=low-1;
		for(int j=low;j<high;j++)
		{
			if(arr[j]<key)
			{
				i++;
				t++;
				int temp;
				temp=arr[t];
				arr[t]=arr[j];
				arr[j]=temp;
				if(i<t)
				{
					int temp1;
					temp1=arr[t];
					arr[t]=arr[i];
					arr[i]=temp1;
				}
			}
			if(arr[j]==key)
			{
				t++;
				int temp;
				temp=arr[t];
				arr[t]=arr[j];
				arr[j]=temp;
			}
		}
		arr[high]=arr[t+1];
		arr[t+1]=key;
		for(int k:arr)
		{
			System.out.print(k+" ");
		}
		System.out.println();
		System.out.println("小于"+key+"的值个数是"+(i-low+1));
		System.out.println("等于"+key+"的值个数是"+(t-i+1));
	}
}