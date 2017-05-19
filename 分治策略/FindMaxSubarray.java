//算法复杂度为nlgn  动态规划的算法复杂度更低为n
public class FindMaxSubarray
{
	public static void main(String[] args)
	{
		int[] arr1=new int[3];
		int[] arr2=new int[3];
		int[] arr=new int[] {1,2,3,4,5,7,4,3,5,-3,6,-8};
		arr1=findmaxsubarray(arr,0,11);
		//问题在哪里呢 输出结果是3 8 28 O MY GOD 
		//66行当时写成了arr[2]=arr[low]
		arr2=findcrossingsubarray(arr,0,4,8);
		for(int i:arr1)
		{
			System.out.println(i);
		}
		/*
		for(int i:arr2)
		{
			System.out.println(i);
		}
		*/
	}
	public static int[] findcrossingsubarray(int[] arr,int low,int mid,int high)
	{
		int[] arr1=new int[3];
		int leftsum=arr[mid];
		int rightsum=arr[mid+1];
		int maxleft=mid;
		int maxright=mid+1;
		int sumleft=arr[mid];
		int sumright=arr[mid+1];
		for(int i=mid-1;i>=low;i--)
		{
			sumleft=sumleft+arr[i];
			if(sumleft>leftsum)
			{
				leftsum=sumleft;
				maxleft=i;
			}
		}
		for(int j=mid+2;j<=high;j++)
		{
			sumright=sumright+arr[j];
			if(sumright>rightsum)
			{
				rightsum=sumright;
				maxright=j;
			}
		}
		arr1[0]=maxleft;
		arr1[1]=maxright;
		arr1[2]=leftsum+rightsum;
		return arr1;
	}
	public static  int[] findmaxsubarray(int[] arr,int low,int high)
	{
		int[] arr1=new int[3];
		int[] arrleft=new int[3];
		int[] arrright=new int[3];
		int[] arrcross=new int[3];
		int mid=(low+high)/2;
		//注意迭代停止的情况
		if(low==high)
		{
			arr1[0]=low;
			arr1[1]=high;
			arr1[2]=arr[low];
		}
		else
		{
			
			arrleft=findmaxsubarray(arr,low,mid);
			arrright=findmaxsubarray(arr,mid+1,high);
			arrcross=findcrossingsubarray(arr,low,mid,high);
			if(arrleft[2]>=arrright[2]&&arrleft[2]>=arrcross[2])
			{
				arr1=arrleft;
			}
			if(arrright[2]>=arrleft[2]&&arrright[2]>=arrcross[2])
			{
				arr1=arrright;
			}
			if(arrcross[2]>=arrleft[2]&&arrcross[2]>=arrright[2])
			{
				arr1=arrcross;
			}
		}
		/*for(int i:arr1)
		{
			System.out.println(i);
		}*/
		return arr1;
	}
}