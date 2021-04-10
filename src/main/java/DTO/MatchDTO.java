package DTO;

import java.util.List;

public class MatchDTO {
    public long gameCreation;
    public long gameDuration;
    public long gameId;
    public String gameMode;
    public String gameType;
    public String gameVersion;
    public int mapId;
    public List<ParticipantIdentityDTO> participantIdentities;
    public List<ParticipantDTO> participants;
    public String platformId;
    public int queueId;
    public int seasonId;
    public List<TeamStatsDTO> teams;
}
