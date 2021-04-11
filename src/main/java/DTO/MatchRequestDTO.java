package DTO;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class MatchRequestDTO {
    public SummonerDTO summoner;
    public List<MatchDTO> matchList;
}
