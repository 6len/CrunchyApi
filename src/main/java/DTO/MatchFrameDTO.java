package DTO;

import java.util.List;
import java.util.Map;

public class MatchFrameDTO {
    public List<MatchEventDTO> events;
    public Map<Integer, MatchParticipantFrameDTO> participantFrames;
    public long timestamp;
}
