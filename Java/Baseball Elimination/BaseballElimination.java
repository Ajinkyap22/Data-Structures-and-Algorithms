import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BaseballElimination {
    private final int num;
    // arr for teams
    private final String[] teams;
    // arr for wins
    private final int[] wins;
    // arr for remaining
    private final int[] r;
    // arr for loses
    private final int[] losses;
    // 2d arr for games
    private final int[][] games;

    private final Map<String, Integer> allTeams;
    private int maxWin;
    private int maxID;
    private List<String> list;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        if (filename == null) throw new IllegalArgumentException("Filename is Null");

        In in = new In(filename);
        num = Integer.parseInt(in.readLine());
        allTeams = new LinkedHashMap<>();
        teams = new String[num];
        wins = new int[num];
        losses = new int[num];
        r = new int[num];
        games = new int[num][num];

        int i = 0;
        maxWin = 0;

        while (!in.isEmpty()) {
            String name = in.readString();
            allTeams.put(name, i);
            teams[i] = name;

            wins[i] = in.readInt();
            losses[i] = in.readInt();
            r[i] = in.readInt();

            for (int j = 0; j < num; j++) {
                games[i][j] = in.readInt();
            }

            if (wins[i] > maxWin) {
                maxWin = wins[i];
                maxID = i;
            }

            i++;
        }
    }

    // number of teams
    public int numberOfTeams() {
        return teams.length;
    }

    // all teams
    public Iterable<String> teams() {
        return allTeams.keySet();
    }

    // number of wins for given team
    public int wins(String team) {
        validate(team);
        return wins[allTeams.get(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        validate(team);
        return losses[allTeams.get(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        validate(team);
        return r[allTeams.get(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        validate(team1);
        validate(team2);
        return games[allTeams.get(team1)][allTeams.get(team2)];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        validate(team);

        if (trivial(team)) return true;
        else return nonTrivial(team);
    }

    public Iterable<String> certificateOfElimination(String team) {
        validate(team);

        if (!isEliminated(team)) return null;

        if (trivial(team)) {
            list = new LinkedList<>();
            list.add(teams[maxID]);
        }
        else {
            nonTrivial(team);
        }

        return list;
    }

    private void validate(String team) {
        if (team == null) throw new IllegalArgumentException("Null Argument");

        for (String str : teams) {
            if (team.equals(str)) return;
        }

        throw new IllegalArgumentException("Team does not exist");
    }

    private boolean trivial(String team) {
        return wins[allTeams.get(team)] + r[allTeams.get(team)] < maxWin;
    }

    private boolean nonTrivial(String team) {
        // Team id
        int teamID = allTeams.get(team);
        // Size of network
        int size = (num * (num - 1)) / 2;
        // Network
        FlowNetwork network = new FlowNetwork(size + 2);
        // s and t
        int t = size + 1;
        // other variables
        int gameIndex = num - 1, firstTeam, secondTeam, remaining = 0;

        // for loop
        for (int i = 0; i < num; i++) {
            if (i == teamID) continue;

            if (i > teamID) firstTeam = i - 1;
            else firstTeam = i;

            for (int j = i + 1; j < num; j++) {
                if (j == teamID) continue;

                if (j > teamID) secondTeam = j - 1;
                else secondTeam = j;

                // edges from source to games
                network.addEdge(new FlowEdge(size, gameIndex, games[i][j]));
                remaining += games[i][j];
                // edges from games to teams
                network.addEdge(new FlowEdge(gameIndex, firstTeam, Double.POSITIVE_INFINITY));
                network.addEdge(new FlowEdge(gameIndex++, secondTeam, Double.POSITIVE_INFINITY));
            }
        }

        for (int i = 0; i < num; i++) {
            if (i == teamID) continue;

            if (i > teamID) firstTeam = i - 1;
            else firstTeam = i;

            int capacity = wins[teamID] + r[teamID] - wins[i];
            if (capacity < 0) continue;

            // edges from source teams to t
            network.addEdge(new FlowEdge(firstTeam, t, capacity));
        }

        FordFulkerson ff = new FordFulkerson(network, size, t);

        // Create R
        list = new LinkedList<>();
        for (int i = 0; i < num; i++) {
            if (i == teamID) continue;

            if (i > teamID) firstTeam = i - 1;
            else firstTeam = i;

            if (ff.inCut(firstTeam)) list.add(teams[i]);
        }

        return (int) ff.value() != remaining;
    }


    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}

