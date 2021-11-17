package leetcode.test0651to0700;

import java.util.Arrays;

public class Leetcode617 {
    public int findNumberOfLIS2(int[] nums) {
    	int[] ans = new int[2];
    	findNumberOfLIS2(nums, 0, -1, 0, ans);
    	System.out.print(Arrays.toString(ans));
    	
        return ans[1];
    }
    
    public void findNumberOfLIS2(int[] nums, int idxNow, int inxBef, int len, int[] ans) {
    	if(idxNow >= nums.length) {
    		if(len > ans[0]) {
    			ans[0] = len;
    			ans[1] = 1;
    		}else if(len == ans[0]) {
    			ans[1]++;
			}
    		
    		return;
    	}
    	
    	if(len + nums.length - idxNow < ans[0]) {
    		return;
    	}
    	int befor = inxBef >= 0 ? nums[inxBef] : Integer.MIN_VALUE;
    	
    	if(nums[idxNow] > befor) {
    		findNumberOfLIS2(nums, idxNow + 1, idxNow, len + 1, ans);
    	}
    	findNumberOfLIS2(nums, idxNow + 1, inxBef, len, ans);
    }
    
    
    public int findNumberOfLIS(int[] nums) {
        int n = nums.length, maxLen = 0, ans = 0;
        int[] dp = new int[n];//dp[i]存储以Nums[i]为结尾的最长递增子序列长度
        int[] cnt = new int[n];//dp[i]存储以Nums[i]为结尾的最长递增子序列的个数
        for (int i = 0; i < n; ++i) {
            dp[i] = 1;
            cnt[i] = 1;
            for (int j = 0; j < i; ++j) {
                if (nums[i] > nums[j]) {
                    if (dp[j] + 1 > dp[i]) {
                        dp[i] = dp[j] + 1;
                        cnt[i] = cnt[j]; // 重置计数
                    } else if (dp[j] + 1 == dp[i]) {
                        cnt[i] += cnt[j];
                    }
                }
            }
            if (dp[i] > maxLen) {
                maxLen = dp[i];
                ans = cnt[i]; // 重置计数
            } else if (dp[i] == maxLen) {
                ans += cnt[i];
            }
        }
        return ans;
    }

}

/*
 * 给定一个未排序的整数数组，找到最长递增子序列的个数。

示例 1:

输入: [1,3,5,4,7]
输出: 2
解释: 有两个最长递增子序列，分别是 [1, 3, 4, 7] 和[1, 3, 5, 7]。
示例 2:

输入: [2,2,2,2,2]
输出: 5
解释: 最长递增子序列的长度是1，并且存在5个子序列的长度为1，因此输出5。
注意: 给定的数组长度不超过 2000 并且结果一定是32位有符号整数。


方法一：动态规划
思路与算法

定义 \textit{dp}[i]dp[i] 表示以 \textit{nums}[i]nums[i] 结尾的最长上升子序列的长度，\textit{cnt}[i]cnt[i] 表示以 \textit{nums}[i]nums[i] 结尾的最长上升子序列的个数。设 \textit{nums}nums 的最长上升子序列的长度为 \textit{maxLen}maxLen，那么答案为所有满足 \textit{dp}[i]=\textit{maxLen}dp[i]=maxLen 的 ii 所对应的 \textit{cnt}[i]cnt[i] 之和。

我们从小到大计算 \textit{dp}dp 数组的值，在计算 \textit{dp}[i]dp[i] 之前，我们已经计算出 \textit{dp}[0 \ldots i-1]dp[0…i−1] 的值，则状态转移方程为：

\textit{dp}[i] = \max(\textit{dp}[j]) + 1, \text{其中} \, 0 \leq j < i \, \text{且} \, \textit{num}[j]<\textit{num}[i]
dp[i]=max(dp[j])+1,其中0≤j<i且num[j]<num[i]

即考虑往 \textit{dp}[0 \ldots i-1]dp[0…i−1] 中最长的上升子序列后面再加一个 \textit{nums}[i]nums[i]。由于 \textit{dp}[j]dp[j] 代表 \textit{nums}[0 \ldots j]nums[0…j] 中以 \textit{nums}[j]nums[j] 结尾的最长上升子序列，所以如果能从 \textit{dp}[j]dp[j] 这个状态转移过来，那么 \textit{nums}[i]nums[i] 必然要大于 \textit{nums}[j]nums[j]，才能将 \textit{nums}[i]nums[i] 放在 \textit{nums}[j]nums[j] 后面以形成更长的上升子序列。

对于 \textit{cnt}[i]cnt[i]，其等于所有满足 \textit{dp}[j]+1=\textit{dp}[i]dp[j]+1=dp[i] 的 \textit{cnt}[j]cnt[j] 之和。在代码实现时，我们可以在计算 \textit{dp}[i]dp[i] 的同时统计 \textit{cnt}[i]cnt[i] 的值。


[0,1,1,1,1,2,2,2,2,3,3,3,-109,-168,-112,-128,-116,-174,-175,-171,-162,-115,-122,-137,-142,4,-109,-100,-154,-183,-116,4,-177,-190,-150,-136,-146,-178,-181,-114,-186,-154,-155,-197,-142,-183,-134,-137,-122,-100,-198,-158,-103,-176,-149,-169,-147,-158,-106,-111,-199,-188,-172,-151,-129,-167,-176,-171,-185,-127,-172,-115,-102,-163,-195,-111,-105,-188,-118,-191,-122,-163,4,-159,-171,-196,-122,-196,-104,-176,-124,-123,-119,-155,-146,-194,-170,-185,-163,-102,-157,-152,-150,-154,-136,-172,-168,5,-177,-166,-122,-148,-118,-197,-152,-156,-121,-124,-104,-106,-153,-175,-184,-153,-180,-172,-135,-169,-100,-154,-183,-127,-105,-180,-114,-164,-158,-115,-133,-172,-149,-154,-155,-118,-187,-171,5,-158,-160,-192,-195,-200,-106,-197,-143,-160,-125,-133,-118,-148,-173,-188,-114,-199,-174,-104,-198,-114,-151,-116,-111,5,-144,6,-105,-178,-113,-108,-113,-141,-152,-172,6,-165,-160,-117,-165,-143,-198,-184,-135,-100,-144,-142,-152,-146,-153,-195,-134,-141,-136,-100,-100,-139,-195,-125,-170,-159,-137,-126,-122,-109,-134,-106,-132,-195,-140,-194,-151,-127,-110,6,-114,-134,-108,-148,-155,-118,-158,-138,-138,-148,-105,-134,-140,-149,-170,7,-177,-159,-126,-119,7,-154,-118,-178,-126,-167,-176,-123,-192,-155,-171,-183,-130,-123,-125,-166,-159,-105,-197,-111,-150,-177,-173,-103,-130,-186,-172,-167,-148,-168,-108,-139,-176,-116,-129,-154,-123,-127,-110,-106,-139,-175,-179,-100,-156,-186,7,-198,-199,8,-151,-180,-112,-151,-113,-174,-140,-143,8,-113,-120,-106,-173,-155,-190,-127,-131,-154,-146,-126,-148,-120,-133,-190,-109,-129,-169,-144,-101,-174,-107,-183,-109,-136,-161,-131,-145,-159,-186,-113,-169,-106,-195,-168,-138,-166,-115,-197,-164,-152,-188,-101,-126,-149,-124,8,-105,-173,-148,-144,-196,-155,-100,-175,-180,-113,-136,-158,-116,-190,-137,-146,-156,-194,-106,-121,-175,-122,-175,-176,-104,9,-146,-167,-200,-186,-195,-130,-176,-149,-134,-170,-129,-128,-158,-171,-185,9,-124,9,-181,-122,-101,-188,-174,-182,-112,-195,-114,-175,-161,-187,-127,-186,-169,-189,-128,-137,-108,-153,-186,-178,-186,-188,10,-155,-101,-135,-113,-141,-116,-102,-198,-128,-145,-190,-127,-108,-166,-116,-163,-181,-172,-148,-113,-191,-151,-123,-115,-135,-115,-144,-188,-129,-196,-105,-148,-112,-191,-160,-184,-120,-134,-149,-162,-174,-188,-134,-117,-175,-138,-192,-124,10,-180,-109,-130,-179,-154,-159,-177,-163,-160,-127,-147,-101,-134,-179,-133,-150,-168,-197,-145,-104,-122,-105,-189,-194,-152,-160,-110,-199,-196,-152,-187,-128,10,-198,-167,-180,-128,-111,-106,11,-151,-120,-156,-133,-132,-107,-183,-129,-133,-104,-106,11,-162,-136,11,-139,-133,-107,-192,-142,-196,-149,-169,-120,-198,-197,-165,-124,-198,-172,-115,-199,-130,-132,-164,-184,-106,-145,-130,12,-191,-115,-123,-169,-183,-107,-158,-124,-185,-116,-199,-184,-200,-125,-143,-135,-160,-170,-168,-155,-145,-171,-119,-139,-127,-127,-127,-189,-119,-179,-111,-100,-180,-135,12,-183,-136,-194,-191,-197,-168,-149,12,-152,-197,-156,-149,-130,-190,-107,-112,-199,-144,-186,-164,-141,-108,-199,-100,-173,-162,-159,-118,-176,-133,-107,-161,-182,-186,-179,-161,-190,-113,-145,-137,-169,-114,-178,-125,-116,-165,-196,-164,-163,-155,-121,13,-200,-170,-118,-133,-132,-150,-195,-132,-134,-148,-107,-137,-166,-138,-117,-182,-122,-176,-123,-171,-155,-151,-110,-181,-109,-129,-176,-178,-104,-128,-100,-108,-110,-169,-163,-119,-162,-174,-125,-102,-110,-170,-133,-119,-109,-110,-198,-153,13,-109,-160,-109,-111,-186,-126,-159,13,-111,-136,-140,-117,-122,-178,-182,-181,-104,-131,-126,-135,-103,-170,-180,-165,-139,-129,-126,-101,-141,-130,-119,-182,14,-122,-154,-148,-144,-129,-183,-122,-106,-185,-115,-196,-126,-112,-128,-157,-143,-106,-196,-198,-124,-184,-132,-190,-124,-121,-106,-106,-183,-153,-130,14,-181,-192,-179,-150,-179,-147,-122,-142,-151,-142,-129,-160,-191,-185,-140,-123,-134,-133,-180,-132,-169,-153,-192,-145,-195,-200,-150,-176,-164,-135,-112,-183,-193,-153,-101,-197,-125,-111,-116,-188,-119,-122,-175,-120,-198,-133,-106,-114,-143,-127,14,-145,-126,-171,-118,-147,-148,-155,-109,-132,-144,-125,-191,15,-124,-174,-129,-110,-169,-163,-165,-107,-114,15,-119,-114,-176,-141,-180,-135,-143,-131,-174,-165,-180,-131,-178,-129,-188,-108,-164,-195,-116,-200,-169,-162,-184,-122,-125,-165,-119,-149,-142,-120,-185,-148,-178,-107,-149,15,-145,-189,-164,-139,-139,-117,-111,-161,-154,-173,-135,-180,-138,-101,-169,-199,-160,-194,-125,-149,-134,-138,-164,-171,-121,-148,-157,-149,-187,-150,-180,-181,-156,-135,-120,-105,-135,-118,-142,-150,-142,16,-107,-187,-158,-186,-161,-138,-139,-142,-109,-135,-167,-159,-102,-190,-122,-144,-102,-117,-177,-158,-139,-125,-137,-148,-101,-182,-166,-142,-191,-173,-119,-173,-120,16,-140,-169,-119,-168,-156,-116,-198,-136,-128,-142,-174,-134,-156,-160,-145,-169,-189,-143,-190,-128,-133,-164,-175,-149,-141,-103,-179,-150,-138,16,-126,-107,-154,-116,-163,-199,-151,-185,-106,-186,-199,-198,-129,-188,-147,17,-183,-175,-159,-179,-181,-123,-186,-171,-189,-117,17,-136,-136,-135,-198,-102,-119,-128,-165,-199,-159,-180,-107,-142,-156,-101,-101,-175,-116,-179,-104,-149,-102,-199,-112,-159,-111,-128,-200,-181,-166,-130,18,-107,-148,-192,-155,-144,-172,18,-127,-122,-184,-193,-167,-108,-117,-147,-167,-104,-139,-136,-148,-187,-155,-112,-124,-133,-108,-155,-141,-170,-189,-139,-105,-166,-159,-171,-170,-113,-135,-177,-198,-103,-110,-189,-191,-143,-188,-189,-106,-134,-195,-151,-106,-108,-119,-106,-159,-191,19,-124,-166,-148,-174,-191,-145,-200,-183,-181,-197,-139,-115,-195,-180,-199,-138,-111,-150,-160,-163,-187,-176,-185,-188,-170,-160,-157,19,-104,-135,-151,-177,-124,-110,-143,-139,-182,-187,-131,-134,-140,-169,-148,-181,-157,-108,20,-129,-109,-139,20]
 */
