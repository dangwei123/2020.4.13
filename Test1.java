实现一个基本的计算器来计算一个简单的字符串表达式的值。

字符串表达式仅包含非负整数，+， - ，*，/ 四种运算符和空格  。 整数除法仅保留整数部分
class Solution {
    public int calculate(String s) {
        Stack<Integer> stack=new Stack<>();
        int n=0;
        char sign='+';
        for(int i=0;i<s.length();i++){
            char c=s.charAt(i);
            if(c>='0'){
                n=n*10-'0'+c;
            }
            if((c<'0'&&c!=' ')||i==s.length()-1){
                if(sign=='+'){
                    stack.push(n);
                }else if(sign=='-'){
                    stack.push(-n);
                }else if(sign=='*'){
                    stack.push(stack.pop()*n);
                }else if(sign=='/'){
                     stack.push(stack.pop()/n);
                }
                n=0;
                sign=c;
            }
            
        }
        int res=0;
        while(!stack.isEmpty()){
            res+=stack.pop();
        }
        return res;
    }
}

给你一个字符串 S、一个字符串 T，请在字符串 S 里面找出：包含 T 所有字母的最小子串。
class Solution {
    public String minWindow(String s, String t) {
        if (s == null || s == "" || t == null || t == "" || s.length() < t.length()) {
            return "";
        }


        Map<Character,Integer> needs=new HashMap<>();
        Map<Character,Integer> window=new HashMap<>();
        for(int i=0;i<t.length();i++){
            char c=t.charAt(i);
            needs.put(c,needs.getOrDefault(c,0)+1);
        }
        int match=0;
        int left=0;
        int start=0;
        int minlen=s.length() + 1;
        for(int right=0;right<s.length();right++){
            char c=s.charAt(right);
            if(needs.containsKey(c)){
                window.put(c,window.getOrDefault(c,0)+1);
                if(window.get(c).compareTo(needs.get(c))==0){
                    match++;
                }
            }
            while(match==needs.size()){
                if(right-left+1<minlen){
                    minlen=right-left+1;
                    start=left;
                }
                char c2=s.charAt(left);
                if(needs.containsKey(c2)){
                    int num=window.get(c2);
                    window.put(c2,num-1);
                    
                    
                    if(num-1<needs.get(c2)){
                        match--;
                    }
                }
                left++;
            }
        }
        return minlen==(s.length() + 1)?"":s.substring(start,start+minlen);
    }
}

给定一个字符串 s 和一个非空字符串 p，找到 s 中所有是 p 的字母异位词的子串，返回这些子串的起始索引。

字符串只包含小写英文字母，并且字符串 s 和 p 的长度都不超过 20100。

说明：

字母异位词指字母相同，但排列不同的字符串。
不考虑答案输出的顺序。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/find-all-anagrams-in-a-string
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        int[] needs=new int[26];
        int[] window=new int [26];
        int len=0;
        for(int i=0;i<p.length();i++){
            if(needs[p.charAt(i)-'a']==0) len++;
            needs[p.charAt(i)-'a']++;
        }
        List<Integer> res=new ArrayList<>();
        int left=0;
        int right=0;
        int count=0;
        while(right<s.length()){
            int a=s.charAt(right)-'a';
            if(needs[a]>0){
                window[a]++;
                if(window[a]==needs[a]){
                    count++;
                }
            }
            while(count==len){
                if(right-left+1==p.length()){
                    res.add(left);
                }
                int b=s.charAt(left)-'a';
                if(needs[b]>0){
                    window[b]--;
                    if(window[b]<needs[b]){
                       count--;
                    }
                }
                left++;
            }
            right++;
        }
        return res;
    }
}

