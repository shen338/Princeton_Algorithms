/**
 * Created by tongs on 3/9/2017.
 */

import edu.princeton.cs.algs4.*;

import java.util.ArrayList;
import java.util.HashMap;


public class BaseballElimination {
    private ArrayList<String> teams;
    private int w[];
    private int l[];
    private int r[];
    private int g[][];
    private int num;
    private HashMap<String,ArrayList<Integer>> certE;
    public BaseballElimination(String filename)       // create a baseball division from given filename in format specified below
    {
        certE = new HashMap<>();
        In data = new In(filename);
        num = data.readInt();
        teams = new ArrayList<>();
        w = new int[num];
        l = new int[num];
        r = new int[num];
        g = new int[num][num];
        int i = 0;

        while(!data.isEmpty()){
            teams.add(data.readString());
            w[i] = data.readInt();
            l[i] = data.readInt();
            r[i] = data.readInt();
            for(int j = 0;j<num;j++){
                g[i][j] = data.readInt();
            }
            i = i + 1;
        }

    }
    public int numberOfTeams()                        // number of teams
    {
        return num;
    }
    public Iterable<String> teams()                   // all teams
    {
        return teams;
    }
    public int wins(String team)                      // number of wins for given team
    {
        int loc = teamNum(team);
        return w[loc];
    }
    private int teamNum(String team){
        int loc = -1;
        if(teams.contains(team)){
            loc = teams.indexOf(team);
        }
        else{
            throw new IllegalArgumentException();
        }
        return loc;
    }
    public int losses(String team)                    // number of losses for given team
    {
        int loc = teamNum(team);
        return l[loc];
    }
    public int remaining(String team)                 // number of remaining games for given team
    {
        int loc = teamNum(team);
        return r[loc];
    }
    public int against(String team1, String team2)    // number of remaining games between team1 and team2
    {
        int loc1 = teamNum(team1);
        int loc2 = teamNum(team2);
        return g[loc1][loc2];
    }
    public FlowNetwork buildFlowNetwork(int teamNum){
        int count = (num-1)*(num-2)/2 + num -1 + 2;
        FlowNetwork network = new FlowNetwork(count);
        int temp = 0;
        int temp1 = 0;
        int plus = (num-1)*(num-2)/2;
        for(int i = 0; i < num;i++){
            int temp2 = 0;
            for(int j = 0;j < i;j++){
                if(i != teamNum && j != teamNum){
                    network.addEdge(new FlowEdge(count-2,temp,g[i][j]));
                    network.addEdge(new FlowEdge(temp,temp1 + plus, Double.POSITIVE_INFINITY));
                    network.addEdge(new FlowEdge(temp,temp2 + plus, Double.POSITIVE_INFINITY));
                    temp++;
                    temp2++;
                }
            }
            if(i != teamNum){
                temp1++;
            }
        }
        temp = 0;
        for(int p = 0;p<num;p++) {
            if (p != teamNum) {
                network.addEdge(new FlowEdge(temp + plus, count - 1, w[teamNum] + r[teamNum] - w[p]));
                temp++;
            }
        }
        return network;
    }
    private int nontrivialCheck(String team){
        int tNum = teamNum(team);
        int loc = -1;
        // solve the trivial problem
        for(int i = 0;i<num;i++){
            if(w[tNum] + r[tNum] < w[i]){
                return i;
            }
        }
        return loc;
    }
    public boolean isEliminated(String team)          // is given team eliminated?
    {
        int count = (num-1)*(num-2)/2 + num -1 + 2;
        int tNum = teamNum(team);
        if(nontrivialCheck(team) >= 0){
            return true;
        }
        //solve the nontrivial problem
        FlowNetwork network = buildFlowNetwork(tNum);
        FordFulkerson FF = new FordFulkerson(network,count - 2,count - 1);
        int maxC = 0;
        for(int p = 0;p<num;p++)
        {
            for(int q = 0;q<num;q++){
                if(p != tNum && q != tNum){
                    maxC = g[p][q] + maxC;
                }
            }
        }
        maxC = maxC/2;
        StdOut.println(maxC);
        StdOut.println(FF.value());
        if(FF.value() < maxC){
            for(int p = 0;p<count;p++){
                StdOut.println(FF.inCut(p));
            }
            return true;
        }
        else{
            return false;
        }
    }
    public Iterable<String> certificateOfElimination(String team)  // subset R of teams that eliminates given team; null if not eliminated
    {
        int count = (num-1)*(num-2)/2 + num -1 + 2;
        int tNum = teamNum(team);
        ArrayList<String> temp = new ArrayList<>();
        int i = nontrivialCheck(team);
        if(i >= 0){
            temp.add(teams.get(i));
            return temp;
        }
        else{
            FlowNetwork network = buildFlowNetwork(tNum);
            FordFulkerson FF = new FordFulkerson(network,count - 2,count - 1);
            int maxC = 0;
            for(int p = 0;p<num;p++)
            {
                for(int q = 0;q<num;q++){
                    if(p != tNum && q != tNum){
                        maxC = g[p][q] + maxC;
                    }
                }
            }
            maxC = maxC/2;
            int cerLoc = 0;
            if(FF.value() < maxC){
                for(int ddd = 0;ddd < num;ddd++){
                    if(ddd != tNum){
                        if(FF.inCut(cerLoc + (num-1)*(num-2)/2))
                           temp.add(teams.get(ddd));
                           cerLoc++;
                    }
                }
            }
        }
        return temp;
    }
    public static void main(String[] args){
         BaseballElimination temp = new BaseballElimination(args[0]);
         StdOut.println(temp.isEliminated("Detroit"));
         StdOut.println(temp.certificateOfElimination("Detroit"));
    }
}
