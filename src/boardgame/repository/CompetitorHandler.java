package boardgame.repository;

import boardgame.components.entity.Competitor;

import java.util.List;

//simple interface for main components boardgame.repository to ease access to main components like players
public interface CompetitorHandler {

    List<Competitor> getCompetitors();

    Competitor getCompetitor(int competitorId);
}
