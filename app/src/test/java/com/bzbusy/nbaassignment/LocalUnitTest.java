package com.bzbusy.nbaassignment;

import com.bzbusy.nbaassignment.Util.Constant;
import com.bzbusy.nbaassignment.Util.Util;
import com.bzbusy.nbaassignment.model.NbaTeam;
import com.bzbusy.nbaassignment.model.Player;
import com.google.gson.JsonArray;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)
public class LocalUnitTest {
    @Test
    public void Nba_team_class_constructor_test (){
        NbaTeam nbaTeam = new NbaTeam(1, "Boston Celtics", 50, 32, new JsonArray());
        Assert.assertEquals(nbaTeam.getClass(), NbaTeam.class);
    }

    @Test
    public void Nba_team_class_get_test (){
        NbaTeam nbaTeam = new NbaTeam(1, "Boston Celtics", 50, 32, new JsonArray());
        Assert.assertEquals(nbaTeam.getFull_name(), "Boston Celtics");
        Assert.assertEquals(nbaTeam.getWins(), 50);
        Assert.assertEquals(nbaTeam.getLosses(), 32);
    }

    @Test
    public void Nba_team_player_class_constructor_test (){
        Player player = new Player(1, "James", "Harden", "SG", 13);
        Assert.assertEquals(player.getClass(), Player.class);
    }

    @Test
    public void Nba_team_player_class_get_test (){
        Player player = new Player(1, "James", "Harden", "SG", 13);
        Assert.assertEquals(player.getFirst_name(), "James");
        Assert.assertEquals(player.getLast_name(), "Harden");
        Assert.assertNotEquals(player.getNumber(), 0);
        Assert.assertEquals(player.getPosition(), "SG");
    }

    @Test
    public void team_sort_test (){
        List<NbaTeam> nbaTeams = new ArrayList<NbaTeam>();
        NbaTeam boston = new NbaTeam(1, "Boston Celtics", 50, 32, new JsonArray());
        NbaTeam chicago = new NbaTeam(3, "Chicago Bulls", 33, 62, new JsonArray());
        NbaTeam philadelphia = new NbaTeam(4, "Philadelphia 76ers", 13, 52, new JsonArray());
        NbaTeam houston = new NbaTeam(2, "Houston Rockets", 44, 22, new JsonArray());
        nbaTeams.add(boston);
        nbaTeams.add(chicago);
        nbaTeams.add(philadelphia);
        nbaTeams.add(houston);

        // Test sort by team name
        nbaTeams = Util.sortTeamsByKeys(nbaTeams, Constant.Sort.TeamName);
        Assert.assertTrue(nbaTeams.get(0).equals(boston));
        Assert.assertTrue(nbaTeams.get(1).equals(chicago));
        Assert.assertTrue(nbaTeams.get(2).equals(houston));
        Assert.assertTrue(nbaTeams.get(3).getFull_name().equals("Philadelphia 76ers"));

        // Test sort by win Desc
        nbaTeams = Util.sortTeamsByKeys(nbaTeams, Constant.Sort.Wins);
        Assert.assertTrue(nbaTeams.get(0).equals(boston));
        Assert.assertTrue(nbaTeams.get(1).equals(houston));
        Assert.assertTrue(nbaTeams.get(2).equals(chicago));
        Assert.assertTrue(nbaTeams.get(3).equals(philadelphia));

        // Test sort by loss Asc
        nbaTeams = Util.sortTeamsByKeys(nbaTeams, Constant.Sort.Losses);
        Assert.assertTrue(nbaTeams.get(0).equals(houston));
        Assert.assertTrue(nbaTeams.get(1).equals(boston));
        Assert.assertTrue(nbaTeams.get(2).equals(philadelphia));
        Assert.assertTrue(nbaTeams.get(3).equals(chicago));
    }
}