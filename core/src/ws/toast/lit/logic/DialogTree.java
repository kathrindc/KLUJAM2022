package ws.toast.lit.logic;

import lombok.AllArgsConstructor;
import lombok.var;
import ws.toast.lit.LITGame;
import ws.toast.lit.scenes.CreditsScreen;
import ws.toast.lit.scenes.DictionaryScreen;

public class DialogTree {

    public static class DialogTreeNode {

        public enum NodeType {
            TEXT_ONLY, CHOICE;
        }

        @AllArgsConstructor
        public static class NodeChoice {
            public String text;
            public int link;
        }

        public NodeType type;
        public String speaker;
        public boolean obfuscated;
        public String text;
        public NodeChoice[] choices;
        public int next;

        public DialogTreeNode(String speaker, boolean obfuscated, String text, int next) {
            this.type = NodeType.TEXT_ONLY;
            this.speaker = speaker;
            this.obfuscated = obfuscated;
            this.text = text;
            this.choices = null;
            this.next = next;
        }

        public DialogTreeNode(String speaker, boolean obfuscated, String text, NodeChoice[] choices) {
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
    private static final String UNKNOWN_SPEAKER = "??????";
    private static final String NO_SPEAKER = "";
    private static final DialogTreeNode[] MAIN_STORY = {
            new DialogTreeNode(UNKNOWN_SPEAKER, true, "OU IUR FAINELI AUAIK", 1), // 0
            new DialogTreeNode(NO_SPEAKER, false, "You slowly open your eyes.", 2),
            new DialogTreeNode(UNKNOWN_SPEAKER, false, "What do you say?", new DialogTreeNode.NodeChoice[]{
                    new DialogTreeNode.NodeChoice("Where am I?", 3),
                    new DialogTreeNode.NodeChoice("What?", 4)
            }),
            new DialogTreeNode(NO_SPEAKER, false, "The End", NEXT_SCENE_CHANGE_CREDITS),
            new DialogTreeNode(NO_SPEAKER, false, "To the Dictionary", NEXT_SCENE_CHANGE_DICTIONARY)
    };

    public LITGame game;
    public DialogTreeNode[] nodes;
    public int current;

    public DialogTree(LITGame game, int start) {
        this.game = game;
        this.nodes = MAIN_STORY;
        this.current = start;
    }

    public void follow() {
        if (nodes[current].type != DialogTreeNode.NodeType.TEXT_ONLY) {
            throw new RuntimeException("Cannot directly follow node with mismatched type");
        }

        current = nodes[current].next;

        checkForSceneChange();
    }

    public void follow(int choice) {
        if (nodes[current].type != DialogTreeNode.NodeType.CHOICE) {
            throw new RuntimeException("Cannot follow choice with mismatched type");
        }

        current = nodes[current].choices[choice].link;

        checkForSceneChange();
    }

    private void checkForSceneChange() {
        if (current == NEXT_SCENE_CHANGE_CREDITS) {
            var screen = new CreditsScreen(game);

            game.setScreen(screen);
        }

        if (current == NEXT_SCENE_CHANGE_DICTIONARY) {
            var screen = new DictionaryScreen(game);

            game.setScreen(screen);
        }
    }
}
