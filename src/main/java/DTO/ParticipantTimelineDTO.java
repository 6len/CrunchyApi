package DTO;

import java.util.Map;

public class ParticipantTimelineDTO {
    public Map<String, Double> creepsPerMinDeltas;
    public Map<String, Double> csDiffPerMinDeltas;
    public Map<String, Double> damageTakenDiffPerMinDeltas;
    public Map<String, Double> damageTakenPerMinDeltas;
    public Map<String, Double> goldPerMinDeltas;
    public String lane;
    public int participantId;
    public String role;
    public Map<String, Double> xpDiffPerMinDeltas;
    public Map<String, Double> xpPerMinDeltas;
}
