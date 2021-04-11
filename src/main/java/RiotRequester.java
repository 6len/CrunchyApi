import DTO.MatchDTO;
import DTO.MatchListDTO;
import DTO.MatchRequestDTO;
import DTO.SummonerDTO;
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
    private String riotApiKey = "RGAPI-fb69464a-7352-4f63-9307-15c7f58e4b10";

    public String getAll(String summonerName) throws IOException {
        SummonerDTO summonerDTO = getSummoner(summonerName);
        MatchListDTO matchListDTO = getMatchList(summonerDTO.accountId);
        List<MatchDTO> matchDTOlist = getLastTenMatches(matchListDTO);

        return objectMapper.writeValueAsString(new MatchRequestDTO(summonerDTO, matchDTOlist));
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
        matchListDTO.matches.subList(0,10).forEach(match -> {
            try {
                matchList.add(getMatchStats(match.gameId));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return matchList;
    };

    public MatchDTO getMatchStats(long matchId) throws IOException {
        Request request = new Request.Builder()
                .url("https://euw1.api.riotgames.com/lol/match/v4/matches/" + matchId)
                .method("GET", null)
                .addHeader("X-Riot-Token", riotApiKey)
                .build();
        Response response = client.newCall(request).execute();
        return objectMapper.readValue(response.body().byteStream(), MatchDTO.class);
    }
}