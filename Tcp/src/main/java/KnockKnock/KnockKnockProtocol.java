package KnockKnock;

public class KnockKnockProtocol {
    // ESTADOS: Definen en qué punto de la conversación estamos
    private static final int WAITING = 0;    // Esperando para empezar
    private static final int SENT_KNOCK = 1; // Ya envié "Knock Knock"
    private static final int SENT_CLUE = 2;  // Ya envié la pista (ej: "Turnip")
    private static final int ANOTHER = 3;    // Chiste terminado, preguntar si quiere otro

    private int state = WAITING; // Empezamos esperando
    private int currentJoke = 0; // Para rotar entre chistes

    // Los chistes (Pista y Remate)
    private String[] clues = { "Turnip", "Little Old Lady", "Atch" };
    private String[] answers = { "Turnip the heat, it's cold in here!",
            "I didn't know you could yodel!",
            "Bless you!" };

    // METODO PRINCIPAL: Recibe lo que dijo el cliente y decide qué responder
    public String processInput(String theInput) {
        String theOutput = null;

        if (state == WAITING) {
            theOutput = "Knock! Knock!";
            state = SENT_KNOCK; // Cambiamos el estado: ahora esperamos el "Who's there"
        }
        else if (state == SENT_KNOCK) {
            if (theInput.equalsIgnoreCase("Who's there?")) {
                theOutput = clues[currentJoke];
                state = SENT_CLUE; // Cambiamos estado: ahora esperamos "Turnip who?"
            } else {
                theOutput = "You're supposed to say \"Who's there?\"! Try again. Knock! Knock!";
            }
        }
        else if (state == SENT_CLUE) {
            if (theInput.equalsIgnoreCase(clues[currentJoke] + " who?")) {
                theOutput = answers[currentJoke] + " Want another? (y/n)";
                state = ANOTHER; // Chiste terminado
            } else {
                theOutput = "You're supposed to say \"" + clues[currentJoke] + " who?\"! Try again.";
            }
        }
        else if (state == ANOTHER) {
            if (theInput.equalsIgnoreCase("y")) {
                theOutput = "Knock! Knock!";
                if (currentJoke == (clues.length - 1))
                    currentJoke = 0;
                else
                    currentJoke++;
                state = SENT_KNOCK; // Reiniciamos el ciclo
            } else {
                theOutput = "Bye."; // Mensaje de despedida
            }
        }
        return theOutput;
    }
}