package DTO;

import java.util.List;

public class MatchRequestDTO {
    public SummonerDTO summoner;
    public List<MatchDTO> matchList;

    public MatchRequestDTO(SummonerDTO summoner, List<MatchDTO> matchList) {
        this.summoner = summoner;
        this.matchList = matchList;
    }
}
