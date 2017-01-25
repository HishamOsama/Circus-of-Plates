package model.save;

public class Snapshot {

    private GameState game;
    private PlayersStacksData[] data;
    private SaverIF saver;

    public Snapshot(GameState game, PlayersStacksData[] data) {
        this.game = game;
        this.data = data;
        saver = XmlSaver.getInstance();
    }

    public Snapshot() {
        saver = XmlSaver.getInstance();
    }

    public void buildGameState(int[] scores, int elapsedTime) {
        game = new GameState(scores, elapsedTime);
    }

    public void buildGameState(GameState game) {
        this.game = game;
    }

    public void buildPlayer(PlayersStacksData data[]) {
        this.data = new PlayersStacksData[data.length];
        for (int i = 0; i < data.length; i++) {
            this.data[i] = data[i];
        }
    }

    public GameState getGameState() {
        return game;
    }

    public PlayersStacksData getDate(int index) {
        return data[index];
    }

    public int getDateSize() {
        return data.length;
    }

    public void saveShot(String path, String fileName) {
        // Saves data
        saver.save(this, path, fileName);
    }

    public void LoadDate(String path, String fileName) {
        // Load data
        Snapshot load = saver.load(path, fileName);
        this.game = load.getGameState();
        this.data = new PlayersStacksData[load.getDateSize()];
        for (int i = 0; i < load.getDateSize(); i++) {
            data[i] = load.getDate(i);
        }
    }

}
