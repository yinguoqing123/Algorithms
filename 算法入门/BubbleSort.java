public class BubbleSort
{
	public static void main(String[] args)
	{
		int[] arr=new int[] {7,6,5,4,3,2,1};
		bulleSort(arr);
		for(int i:arr)
		{
			System.out.println(i);
		}
	}
	public static void  bulleSort(int[] arr)
	{
		for(int i=0;i<arr.length-1;i++)
		{
			for(int j=i+1;j<arr.length;j++)
			{
				if(arr[i]>=arr[j])
				{
					int temp=0;
					temp=arr[i];
					arr[i]=arr[j];
					arr[j]=temp;
				}
			}
		}
	}
}