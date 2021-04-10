import DTO.MatchDTO;
import DTO.MatchListDTO;
import DTO.SummonerDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

@NoArgsConstructor
public class RiotRequester {
    private OkHttpClient client = new OkHttpClient().newBuilder().build();
    private ObjectMapper objectMapper = new ObjectMapper();

    public String getAll(String summonerName) throws IOException {
        SummonerDTO summonerDTO = getSummoner(summonerName);
        MatchListDTO matchListDTO = getMatchList(summonerDTO.accountId);
        MatchDTO matchDTO = getMatchStats(matchListDTO.matches.get(0).gameId);

        return objectMapper.writeValueAsString(matchDTO);
    }

    public SummonerDTO getSummoner(String summonerName) throws IOException {
        Request request = new Request.Builder()
                .url("https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + summonerName)
                .method("GET", null)
                .addHeader("X-Riot-Token", "RGAPI-8ee109bb-597d-46ce-8f72-fe15dd06297a")
                .build();
        Response response = client.newCall(request).execute();
        return objectMapper.readValue(response.body().byteStream(), SummonerDTO.class);
    }

    public MatchListDTO getMatchList(String accountId) throws IOException {
        Request request = new Request.Builder()
                .url("https://euw1.api.riotgames.com/lol/match/v4/matchlists/by-account/" + accountId)
                .method("GET", null)
                .addHeader("X-Riot-Token", "RGAPI-8ee109bb-597d-46ce-8f72-fe15dd06297a")
                .build();
        Response response = client.newCall(request).execute();
        return objectMapper.readValue(response.body().byteStream(), MatchListDTO.class);
    }

    public MatchDTO getMatchStats(long matchId) throws IOException {
        Request request = new Request.Builder()
                .url("https://euw1.api.riotgames.com/lol/match/v4/matches/" + matchId)
                .method("GET", null)
                .addHeader("X-Riot-Token", "RGAPI-8ee109bb-597d-46ce-8f72-fe15dd06297a")
                .build();
        Response response = client.newCall(request).execute();
        return objectMapper.readValue(response.body().byteStream(), MatchDTO.class);
    }
}