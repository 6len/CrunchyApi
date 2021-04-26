import DTO.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
public class RiotRequester {
    private OkHttpClient client = new OkHttpClient().newBuilder().build();
    private ObjectMapper objectMapper = new ObjectMapper();
    private String riotApiKey = "RGAPI-72a661f6-e1fe-4635-a560-3219cf6a6646";

    public String getAll(String summonerName) throws IOException {
        SummonerDTO summonerDTO = getSummoner(summonerName);
        MatchListDTO matchListDTO = getMatchList(summonerDTO.accountId);
        List<LeagueEntryDTO> summonerStats = getSummonerInfo(summonerDTO.id);
        List<MatchDTO> matchDTOlist = getLastTenMatches(matchListDTO);

        return objectMapper.writeValueAsString(new MatchRequestDTO(summonerDTO, matchDTOlist, summonerStats));
    }

    public SummonerDTO getSummoner(String summonerName) throws IOException {
        Request request = new Request.Builder()
                .url("https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + summonerName)
                .method("GET", null)
                .addHeader("X-Riot-Token", riotApiKey)
                .build();
        Response response = client.newCall(request).execute();
        return objectMapper.readValue(response.body().byteStream(), SummonerDTO.class);
    }

    public List<LeagueEntryDTO> getSummonerInfo(String summonerId) throws IOException {
        Request request = new Request.Builder()
                .url("https://euw1.api.riotgames.com/lol/league/v4/entries/by-summoner/" + summonerId)
                .method("GET", null)
                .addHeader("X-Riot-Token", riotApiKey)
                .build();
        Response response = client.newCall(request).execute();
        return objectMapper.readValue(response.body().byteStream(), new TypeReference<List<LeagueEntryDTO>>() {
        });
    }

    public MatchListDTO getMatchList(String accountId) throws IOException {
        Request request = new Request.Builder()
                .url("https://euw1.api.riotgames.com/lol/match/v4/matchlists/by-account/" + accountId)
                .method("GET", null)
                .addHeader("X-Riot-Token", riotApiKey)
                .build();
        Response response = client.newCall(request).execute();
        return objectMapper.readValue(response.body().byteStream(), MatchListDTO.class);
    }

    public List<MatchDTO> getLastTenMatches(MatchListDTO matchListDTO) throws IOException {
        List<MatchDTO> matchList = new ArrayList<>();
        matchListDTO.matches.subList(0, 10).forEach(match -> {
            try {
                matchList.add(getMatchStats(match.gameId));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return matchList;
    }

    ;

    public MatchDTO getMatchStats(long matchId) throws IOException {
        Request request = new Request.Builder()
                .url("https://euw1.api.riotgames.com/lol/match/v4/matches/" + matchId)
                .method("GET", null)
                .addHeader("X-Riot-Token", riotApiKey)
                .build();
        Response response = client.newCall(request).execute();
        return objectMapper.readValue(response.body().byteStream(), MatchDTO.class);
    }

    public String getMatchTimeline(long matchId) throws IOException {
        Request request = new Request.Builder()
                .url("https://euw1.api.riotgames.com/lol/match/v4/timelines/by-match/" + matchId)
                .method("GET", null)
                .addHeader("X-Riot-Token", riotApiKey)
                .build();
        Response response = client.newCall(request).execute();
        return objectMapper.writeValueAsString(objectMapper.readValue(response.body().byteStream(), MatchTimelineDTO.class));
    }
}