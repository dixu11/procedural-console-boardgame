package boardgame.components.entity.models.competitor;


import boardgame.components.entity.Competitor;
import boardgame.components.type.EntityType;

public class Player extends Competitor {
    public Player(int field, int competitorId) {
        super(field, EntityType.PLAYER, competitorId);
    }
}
