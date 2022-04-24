package ws.toast.lit.logic;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.var;
import ws.toast.lit.LITGame;
import ws.toast.lit.scenes.*;

public class DialogTree {

    public static class Node {

        public enum NodeType {
            TEXT_ONLY, CHOICE;
        }

        @RequiredArgsConstructor
        @AllArgsConstructor
        public static class Choice {
            public String text;
            public int link;
            public int requiredScore = 0;
            public boolean requiredBonus = false;

            public Choice(String text, int link, int requiredScore) {
                this.text = text;
                this.link = link;
                this.requiredScore = requiredScore;
            }
        }

        public NodeType type;
        public String speaker;
        public boolean obfuscated;
        public String text;
        public Choice[] choices;
        public int next;

        public int returnAt;

        public Node(String speaker, boolean obfuscated, String text, int next) {
            this.type = NodeType.TEXT_ONLY;
            this.speaker = speaker;
            this.obfuscated = obfuscated;
            this.text = text;
            this.choices = null;
            this.next = next;
        }
        public Node(String speaker, boolean obfuscated, String text, int next, int returnAt) {
            this(speaker, obfuscated, text, next);

            this.returnAt = returnAt;
        }

        public Node(String speaker, boolean obfuscated, String text, Choice[] choices) {
            this.type = NodeType.CHOICE;
            this.speaker = speaker;
            this.obfuscated = obfuscated;
            this.text = text;
            this.choices = choices;
            this.next = -1;
        }
    }

    public static final int NEXT_SCENE_CHANGE_DICTIONARY = 9999;
    public static final int NEXT_SCENE_CHANGE_CREDITS = 9998;
    public static final int NEXT_SCENE_CHANGE_INTERMISSION = 9997;
    public static final int NEXT_SCENE_CHANGE_CONVERSATION = 9996;
    public static final int NEXT_SCENE_CHANGE_JAIL_END = 9995;
    public static final int NEXT_SCENE_CHANGE_FINE_END = 9994;
    public static final int NEXT_SCENE_CHANGE_FREE_END = 9993;
    private static final String OFFICER = "Police Officer";
    private static final String YOU = "You";
    private static final String NOBODY = "";
    private static final Node[] MAIN_STORY = {
            // 0
            new Node(NOBODY, false, "Your head hurts like hell. You don't know where you are. All you know is that feel like throwing up.", 1),
            // 1
            new Node(NOBODY, false, "You are startled by the loud slam of a door as a police officer ", 2),
            // 2
            new Node(OFFICER, true, "Ah, you're finally awake.", 3),
            // 3
            new Node(OFFICER, true, "Do you know why you were arrested?", NEXT_SCENE_CHANGE_DICTIONARY, 4),
            // 4
            new Node(OFFICER, false, "Do you know why you were arrested?", new Node.Choice[]{
                    new Node.Choice("Uh... yes?", 5, 0),
                    new Node.Choice("No, I don't.", 9, 50)
            }),
            // 5
            new Node(OFFICER, true, "And what is that?", NEXT_SCENE_CHANGE_DICTIONARY, 6),
            // 6
            new Node(OFFICER, false, "And what is that?", new Node.Choice[]{
                    new Node.Choice("I... don't know.", 7, 0),
                    new Node.Choice("It's a misunderstanding.", 8, 60),
            }),
            // 7
            new Node(OFFICER, true, "You don't know? I find that hard to believe.", new Node.Choice[]{
                    new Node.Choice("...can I go now?", 14, 0)
            }),
            // 8
            new Node(OFFICER, true, "Then let's try and figure out what exactly happened.", new Node.Choice[]{
                    new Node.Choice("Okay!", 21, 0),
            }),
            // 9
            new Node(OFFICER, true, "Really? Then tell me why you tried to run away...", NEXT_SCENE_CHANGE_DICTIONARY, 10),
            // 10
            new Node(OFFICER, false, "Really? Then tell me why you tried to run away...", new Node.Choice[]{
                    new Node.Choice("I don't think I understand.", 8, 0),
                    new Node.Choice("I wasn't running away.", 11, 50),
            }),
            // 11
            new Node(OFFICER, true, "Then why were you running?", new Node.Choice[]{
                    new Node.Choice("I was jogging.", 12, 0)
            }),
            // 12
            new Node(OFFICER, false, "Jogging?", 13),
            // 13
            new Node(OFFICER, false, "At 4AM? At that speed? In this part of town?", new Node.Choice[]{
                    new Node.Choice("Yes.", 38, 0),
            }),
            // 14
            new Node(OFFICER, true, "Anyways, let's just make sure we're talking about the same things here...", 15),
            // 15
            new Node(OFFICER, true, "You are accused of trespassing at the zoo and breaking into the enclosure of the endangered Gamebert species.", NEXT_SCENE_CHANGE_DICTIONARY, 16),
            // 16
            new Node(OFFICER, false, "You are accused of trespassing at the zoo and breaking into the enclosure of the endangered Gamebert species.", new Node.Choice[]{
                    new Node.Choice("Yes, I do.", 23, 65),
                    new Node.Choice("I'm not sure.", 17, 0),
            }),
            // 17
            new Node(OFFICER, true, "You're not sure? So you were at the zoo?", NEXT_SCENE_CHANGE_DICTIONARY, 18),
            // 18
            new Node(OFFICER, false, "You're not sure? So you were at the zoo?", new Node.Choice[]{
                    new Node.Choice("Uhm... possibly?", 19, 0),
                    new Node.Choice("I mean, no I wasn't.", 20, 65),
            }),
            // 19
            new Node(OFFICER, false, "This doesn't look good for you.", 32),
            // 20
            new Node(OFFICER, false, "Hmm...", 33),
            // 21
            new Node(OFFICER, true, "You are accused of trespassing at the zoo and breaking into the enclosure of the endangered Gamebert species.", NEXT_SCENE_CHANGE_DICTIONARY, 22),
            // 22
            new Node(OFFICER, false, "You are accused of trespassing at the zoo and breaking into the enclosure of the endangered Gamebert species.", new Node.Choice[]{
                    new Node.Choice("I'm not sure.", 17, 65),
                    new Node.Choice("Yes, I deny.", 23, 0),
            }),
            // 23
            new Node(OFFICER, true, "But we have an eye witness who claims to have seen you scaling the gate.", NEXT_SCENE_CHANGE_DICTIONARY, 24),
            // 24
            new Node(OFFICER, false, "But we have an eye witness who claims to have seen you scaling the gate.", new Node.Choice[]{
                    new Node.Choice("Uhm... possibly?", 19, 0),
                    new Node.Choice("That could've been anyone.", 25, 25),
            }),
            // 25
            new Node(OFFICER, false, "The witness seems to disagree.", 33),
            // 26
            new Node(OFFICER, true, "You are accused of trespassing at the zoo and breaking into the enclosure of the endangered Gamebert species.", NEXT_SCENE_CHANGE_DICTIONARY, 27),
            // 27
            new Node(OFFICER, false, "You are accused of trespassing at the zoo and breaking into the enclosure of the endangered Gamebert species.", new Node.Choice[]{
                    new Node.Choice("Yes, I deny.", 23, 0),
                    new Node.Choice("There's a zoo?", 28, 65),
            }),
            // 28
            new Node(OFFICER, false, "Please stay on topic.", 29),
            // 29
            new Node(OFFICER, true, "We have an eye witness who claims to have seen you struggling to get over the gate.", NEXT_SCENE_CHANGE_DICTIONARY, 30),
            // 30
            new Node(OFFICER, false, "We have an eye witness who claims to have seen you struggling to get over the gate.", new Node.Choice[]{
                    new Node.Choice("Impossible!", 25, 0),
                    new Node.Choice("That could've been anyone.", 31, 60),
            }),
            // 31
            new Node(OFFICER, false, "Perhaps, but do you even have an alibi?", new Node.Choice[]{
                    new Node.Choice("Not yet...", 39, 0),
            }),
            // 32
            new Node(OFFICER, false, "To be honest, I'm not convinced. But that'll be all for now. I'll be right back.", 37),
            // 33
            new Node(OFFICER, false, "Well... we'll see about that.", 34),
            // 34
            new Node(NOBODY, false, "The officer walks to the door an knocks three times.", 35),
            // 35
            new Node(NOBODY, false, "An older guard opens the door from the outside and gestures for you to follow him.", 36),
            // 36
            new Node(NOBODY, false, "The guard walks you back to the precinct's holding cell.", NEXT_SCENE_CHANGE_INTERMISSION, 45),
            // 37
            new Node(NOBODY, false, "An older guard escorts you back to the holding cell.", NEXT_SCENE_CHANGE_INTERMISSION, 43),
            // 38
            new Node(OFFICER, false, "Oh, ok.", 26),
            // 39
            new Node(OFFICER, false, "Well, whatever. We're done here for now.", 40),
            // 40
            new Node(NOBODY, false, "The officer walks to the door an knocks three times.", 41),
            // 41
            new Node(NOBODY, false, "An older guard opens the door from the outside and gestures for you to follow him.", 42),
            // 42
            new Node(NOBODY, false, "The guard walks you back to the precinct's holding cell.", NEXT_SCENE_CHANGE_INTERMISSION, 44),
            // 43
            new Node(NOBODY, false, "You are once more lead to the interrogation room.", 49),
            // 44
            new Node(NOBODY, false, "You are once more lead to the interrogation room.", 59),
            // 45
            new Node(NOBODY, false, "You are once more lead to the interrogation room.", 46),
            // 46
            new Node(NOBODY, false, "The police officer from your previous interrogation enters the room.", 47),
            // 47
            new Node(OFFICER, true, "So you're still insisting that you have nothing to do with this?", NEXT_SCENE_CHANGE_DICTIONARY, 48),
            // 48
            new Node(OFFICER, false, "So you're still insisting that you have nothing to do with this?", new Node.Choice[]{
                    new Node.Choice("Maybe. Maybe not.", 49, 0),
                    new Node.Choice("Absolutely", 56, 35)
            }),
            // 49
            new Node(OFFICER, true, "Well, the evidence is decisive. You will be detained until further trial.", NEXT_SCENE_CHANGE_DICTIONARY, 50),
            // 50
            new Node(OFFICER, false, "Well, the evidence is decisive. You will be detained until further trial.", new Node.Choice[]{
                    new Node.Choice("Damn it!", NEXT_SCENE_CHANGE_JAIL_END, 0),
                    new Node.Choice("Wait!", 51, 35),
            }),
            // 51
            new Node(YOU, false, "I'm a tourist, you can't put me in jail!", 52),
            // 52
            new Node(OFFICER, false, "Oh for crying out loud.", 53),
            // 53
            new Node(OFFICER, false, "You know what?", 54),
            // 54
            new Node(OFFICER, false, "I REALLY don't have the time to fill out the paperwork for such cases.", 55),
            // 55
            new Node(OFFICER, false, "How about you pay a \"fine\" and we'll all forget this ever happened.", NEXT_SCENE_CHANGE_FINE_END),
            // 56
            new Node(OFFICER, false, "Well, we don't have enough evidence to put you in jail.", 57),
            // 57
            new Node(OFFICER, false, "But you will be fined since you the primary suspect.", NEXT_SCENE_CHANGE_FINE_END),
            // 58
            new Node(NOBODY, false, "You are once more lead to the interrogation room.", 46),
            // 59
            new Node(NOBODY, false, "The police officer from your previous interrogation enters the room.", 60),
            // 60
            new Node(OFFICER, true, "So you're still insisting that you have nothing to do with this?", NEXT_SCENE_CHANGE_DICTIONARY, 61),
            // 61
            new Node(OFFICER, false, "So you're still insisting that you have nothing to do with this?", new Node.Choice[]{
                    new Node.Choice("Absolutely", 56, 35),
                    new Node.Choice("Well, actually...", 62, 0, true),
            }),
            // 62
            new Node(OFFICER, false, "What?", 63),
            // 63
            new Node(YOU, false, "I know who you are really looking for!", 64),
            // 64
            new Node(YOU, false, "It's that other guy from the holding cell next to mine.", 65),
            // 65
            new Node(OFFICER, false, "And you saw him do everything?", 66),
            // 66
            new Node(YOU, false, "Sure did!", 67),
            // 67
            new Node(OFFICER, false, "Why didn't you say so from the start!?", 68),
            // 68
            new Node(OFFICER, false, "In that case, just get out of here already!", NEXT_SCENE_CHANGE_FREE_END)
    };
    private static final Node[] INTERMISSION = {
            // 0
            new Node(NOBODY, false, "As you arrive in the cell, you see your cell neighbour arguing with a guard.", NEXT_SCENE_CHANGE_DICTIONARY, 1),
            // 1
            new Node(NOBODY, false, "He is talking too fast. You don't understand a thing.", 4),
            // 2
            new Node(NOBODY, false, "You understand enough to realize, that he seems to be another suspect in your case.", 3),
            // 3
            new Node(NOBODY, false, "This knowledge might become useful later!", 4),
            // 4
            new Node(NOBODY, false, "After half an hour, the older guard returns and once more gestures that you should follow him.", NEXT_SCENE_CHANGE_CONVERSATION),
    };

    public LITGame game;
    public Node[] nodes;
    public int current;

    public DialogTree(LITGame game, int start) {
        this.game = game;
        this.nodes = MAIN_STORY;
        this.current = start;
    }

    public DialogTree(LITGame game, int start, boolean isIntermission) {
        this.game = game;
        this.nodes = isIntermission ? INTERMISSION : MAIN_STORY;
        this.current = start;
    }

    public void follow() {
        if (nodes[current].type != Node.NodeType.TEXT_ONLY) {
            throw new RuntimeException("Cannot directly follow node with mismatched type");
        }

        var cache = current;

        game.returnAt = nodes[current].returnAt;
        current = nodes[current].next;

        checkForSceneChange();

        if (game.fader.isFading()) {
            current = cache;
        }
    }

    public void follow(int choice) {
        if (nodes[current].type != Node.NodeType.CHOICE) {
            throw new RuntimeException("Cannot follow choice with mismatched type");
        }

        game.returnAt = current;
        current = nodes[current].choices[choice].link;

        checkForSceneChange();
    }

    private void checkForSceneChange() {
        if (current == NEXT_SCENE_CHANGE_CREDITS) {
            var screen = new CreditsScreen(game);

            game.fader.fade(screen);
        }

        if (current == NEXT_SCENE_CHANGE_DICTIONARY) {
            var screen = new DictionaryScreen(game);

            game.fader.fade(screen);
        }

        if (current == NEXT_SCENE_CHANGE_INTERMISSION) {
            var screen = new IntermissionScreen(game, 0);

            game.returnFromIntermission = game.returnAt;
            game.inIntermission = true;

            game.fader.fade(screen);
        }

        if (current == NEXT_SCENE_CHANGE_CONVERSATION) {
            var screen = new ConversationScreen(game, game.returnFromIntermission);

            game.inIntermission = false;

            game.fader.fade(screen);
        }

        if (current == NEXT_SCENE_CHANGE_JAIL_END) {
            var screen = new EndingScreen(game, 0);

            game.fader.fade(screen);
        }

        if (current == NEXT_SCENE_CHANGE_FINE_END) {
            var screen = new EndingScreen(game, 1);

            game.fader.fade(screen);
        }

        if (current == NEXT_SCENE_CHANGE_FREE_END) {
            var screen = new EndingScreen(game, 2);

            game.fader.fade(screen);
        }
    }
}
