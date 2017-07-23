package bpsound.hackernewsapitest.apis;

import java.util.List;

/**
 * Created by elegantuniv on 2017. 7. 15..
 */

public class NewsItem {

    /**
     * by : olegkikin
     * descendants : 32
     * id : 14773964
     * kids : [14774437,14774262,14774545,14774597,14774124,14774723,14774043,14774178,14774458,14774256,14774632,14774476,14774362,14774463,14774477]
     * score : 139
     * time : 1500074050
     * title : Gpu.js – GPU Accelerated JavaScript
     * type : story
     * url : http://gpu.rocks/
     */

    private String by;
    private int descendants;
    private int id;
    private int score;
    private int time;
    private String title;
    private String type;
    private String url;
    private List<Integer> kids;

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public int getDescendants() {
        return descendants;
    }

    public void setDescendants(int descendants) {
        this.descendants = descendants;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Integer> getKids() {
        return kids;
    }

    public void setKids(List<Integer> kids) {
        this.kids = kids;
    }
}
