import DTO.SummonerDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import spark.Filter;

import static spark.Spark.*;

public class Crunchy {

    public static void main(String [] args) {
        RiotRequester riotRequester = new RiotRequester();

        port(getHerokuAssignedPort()); // define spark port

        after((Filter) (request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET");
        });

        get("/summoner/:summonerName", (request, response) -> {
            String summonerName = request.params(":summonerName");
            OkHttpClient client = new OkHttpClient().newBuilder().build();

            return riotRequester.getAll(summonerName);
        });
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 1233;
    }
}