package boardgame.components.entity.models.competitor;

import boardgame.components.entity.Competitor;
import boardgame.components.type.EntityType;

public class Computer extends Competitor {
    public Computer(int field, int competitorId) {
        super(field, EntityType.COMPUTER, competitorId);
    }
    //TODO computer not yet implemented
}
