/*
Given two words (start and end), and a dictionary, find all shortest transformation sequence(s) from start to end, such that:

Only one letter can be changed at a time
Each intermediate word must exist in the dictionary
 Notice
All words have the same length.
All words contain only lowercase alphabetic characters.
Have you met this question in a real interview? Yes
Example
Given:
start = "hit"
end = "cog"
dict = ["hot","dot","dog","lot","log"]
Return
  [
    ["hit","hot","dot","dog","cog"],
    ["hit","hot","lot","log","cog"]
  ]
Tags 
Backtracking Depth First Search Breadth First Search
Related Problems 
Medium Word Ladder 22 %
*/
///////////---------------copied from solution, didn't implement-----------------------------
public class Solution {
    public List<List<String>> findLadders(String start, String end,
            Set<String> dict) {
        List<List<String>> ladders = new ArrayList<List<String>>();
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        Map<String, Integer> distance = new HashMap<String, Integer>();

        dict.add(start);
        dict.add(end);
 
        bfs(map, distance, start, end, dict);
        
        List<String> path = new ArrayList<String>();
        
        dfs(ladders, path, end, start, distance, map);

        return ladders;
    }

    void dfs(List<List<String>> ladders, List<String> path, String crt,
            String start, Map<String, Integer> distance,
            Map<String, List<String>> map) {
        path.add(crt);
        if (crt.equals(start)) {
            Collections.reverse(path);
            ladders.add(new ArrayList<String>(path));
            Collections.reverse(path);
        } else {
            for (String next : map.get(crt)) {
                if (distance.containsKey(next) && distance.get(crt) == distance.get(next) + 1) { 
                    dfs(ladders, path, next, start, distance, map);
                }
            }           
        }
        path.remove(path.size() - 1);
    }

    void bfs(Map<String, List<String>> map, Map<String, Integer> distance,
            String start, String end, Set<String> dict) {
        Queue<String> q = new LinkedList<String>();
        q.offer(start);
        distance.put(start, 0);
        for (String s : dict) {
            map.put(s, new ArrayList<String>());
        }
        
        while (!q.isEmpty()) {
            String crt = q.poll();

            List<String> nextList = expand(crt, dict);
            for (String next : nextList) {
                map.get(next).add(crt);
                if (!distance.containsKey(next)) {
                    distance.put(next, distance.get(crt) + 1);
                    q.offer(next);
                }
            }
        }
    }

    List<String> expand(String crt, Set<String> dict) {
        List<String> expansion = new ArrayList<String>();

        for (int i = 0; i < crt.length(); i++) {
            for (char ch = 'a'; ch <= 'z'; ch++) {
                if (ch != crt.charAt(i)) {
                    String expanded = crt.substring(0, i) + ch
                            + crt.substring(i + 1);
                    if (dict.contains(expanded)) {
                        expansion.add(expanded);
                    }
                }
            }
        }

        return expansion;
    }
}
/////////////////////////_____________my solution_____pure dfs, time limit exceeded____________
// public class Solution {
//     /**
//       * @param start, a string
//       * @param end, a string
//       * @param dict, a set of string
//       * @return a list of lists of string
//       */
//     public List<List<String>> findLadders(String start, String end, Set<String> dict) {
//         // write your code here  
//         dict.add(start);
//         dict.add(end);
//         ArrayList<String> allStrs = new ArrayList<>(dict);
//         List<List<String>> result = new ArrayList<>();
//         List<String> curList = new ArrayList<>();
//         boolean[] visited = new boolean[allStrs.size()];
//         curList.add(start);
//         dfs(result, curList, allStrs, start, end, visited);
//         return result;
//     }
//     private void dfs(List<List<String>> result, List<String> curList,
//                      ArrayList<String> allStrs, String curStart, String end,
//                      boolean[] visited){
//         int curShortest = Integer.MAX_VALUE;
//         if(result.size() > 0)
//             curShortest = result.get(0).size();
        
//         if(curList.get(curList.size() - 1).equals(end)){
//             List<String> newList = new ArrayList<>(curList);
//             if(curList.size() <= curShortest){
//                 if(curList.size() < curShortest)
//                     result.clear();
//                 result.add(newList);
//             }
//             return;
//         }// if reaches end
//         for(int i = 0; i < allStrs.size(); i ++){
//             String newStr = allStrs.get(i);
//             if(!canChange(curStart, newStr) || visited[i]){
//                 continue;
//             }
//             curList.add(newStr);
//             if(curList.size() > curShortest){
//                 curList.remove(curList.size() - 1);
//                 visited[i] = false;
//                 continue;
//             }
//             visited[i] = true;
//             dfs(result, curList, allStrs, newStr, end, visited);
//             curList.remove(curList.size() - 1);
//             visited[i] = false;
//         }//for i
//     }//void dfs
    
//     private boolean canChange(String prevStr, String newStr){
//         int sum = 0;
//         for(int i = 0; i < prevStr.length(); i++){
//             if(prevStr.charAt(i) != newStr.charAt(i)){
//                 sum ++;
//                 if(sum > 1)
//                     return false;
//             }
//         }
//         if(sum > 1){
//             return false;
//         }
//         return true;
//     }//boolean canChange
// }