package leetcode.test0801to0850;

import java.util.LinkedList;
import java.util.List;

public class Leetcod989 {
/*
 * 对于非负整数 X 而言，X 的数组形式是每位数字按从左到右的顺序形成的数组。例如，如果 X = 1231，那么其数组形式为 [1,2,3,1]。

给定非负整数 X 的数组形式 A，返回整数 X+K 的数组形式。

 

示例 1：

输入：A = [1,2,0,0], K = 34
输出：[1,2,3,4]
解释：1200 + 34 = 1234
示例 2：

输入：A = [2,7,4], K = 181
输出：[4,5,5]
解释：274 + 181 = 455
示例 3：

输入：A = [2,1,5], K = 806
输出：[1,0,2,1]
解释：215 + 806 = 1021
示例 4：

输入：A = [9,9,9,9,9,9,9,9,9,9], K = 1
输出：[1,0,0,0,0,0,0,0,0,0,0]
解释：9999999999 + 1 = 10000000000

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/add-to-array-form-of-integer
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
	
	
    public List<Integer> addToArrayForm(int[] A, int K) {
    	List<Integer> list = new LinkedList<Integer>();
    	int add = 0;
    	int i = A.length - 1;
    	while (i >= 0 && K != 0) {
    		int tmp = K%10;
        	int sum = tmp + A[i] + add;
        	list.add(0, sum%10);
        	add = sum/10;
        	K = K/10;
			i--;
		}
        for(; i >= 0; i--) {
        	int sum = A[i] + add;
        	list.add(0, sum%10);
        	add = sum/10;
        }
        K = K + add;
        while (K != 0) {
    		int tmp = K%10;
        	list.add(0, tmp);
        	K = K/10;
		}
        return list;
    }
}
