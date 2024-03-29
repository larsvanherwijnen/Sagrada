package main.java.model;

import java.util.ArrayList;
import java.util.Map;

import main.java.db.ObjectiveCardDB;

public class ObjectiveCard {
    private int idObjectiveCard;
    private String name;
    private String description;
    private int points;

    public int getIdObjectiveCard() {
        return idObjectiveCard;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPoints() {
        return points;
    }

    public static ArrayList<ObjectiveCard> getObjectiveCards(final int idGame) {
        ArrayList<ObjectiveCard> objectiveCards = new ArrayList<>();

        for (Map<String, String> row : ObjectiveCardDB.getObjectiveCards(idGame)) {
            ObjectiveCard objCard = new ObjectiveCard();

            objCard.idObjectiveCard = Integer.parseInt(row.get("idpublic_objectivecard"));
            objCard.name = row.get("name");
            objCard.description = row.get("description");
            objCard.points = Integer.parseInt(row.get("points"));

            objectiveCards.add(objCard);
        }

        return objectiveCards;
    }

    public static ObjectiveCard get(final int idObjectiveCard) {
        ObjectiveCard objectiveCard = new ObjectiveCard();

        Map<String, String> row = ObjectiveCardDB.getObjectiveCard(idObjectiveCard);

        objectiveCard.idObjectiveCard = Integer.parseInt(row.get("idpublic_objectivecard"));
        objectiveCard.name = row.get("name");
        objectiveCard.description = row.get("description");
        objectiveCard.points = Integer.parseInt(row.get("points"));

        return objectiveCard;
    }
}
