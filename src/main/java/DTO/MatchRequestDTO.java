package DTO;

import java.util.List;

public class MatchRequestDTO {
    public SummonerDTO summoner;
    public List<MatchDTO> matchList;
    public List<LeagueEntryDTO> summonerStats;

    public MatchRequestDTO(SummonerDTO summoner, List<MatchDTO> matchList, List<LeagueEntryDTO> summonerStats) {
        this.summoner = summoner;
        this.matchList = matchList;
        this.summonerStats = summonerStats;
    }
}
