/*
    Write the Music class in Java (including the equals method shown on page 3 of Lesson 1). The toString method should return a String with the values of each instance variable.
*/

public class Music{
    private String title;
    private String composer;
    private int    opus;

    // +Music()
    public Music(){
    }

    // +Music(ti: String, comp: String, op: int)
    public Music(String ti, String comp, int op){
        this.title = ti;
        this.composer = comp;
        this.opus = op;
    }

    // +getTitle(): String
    public String getTitle(){
        return this.title;
    }

    // +getComposer(): String
    public String getComposer(){
        return this.composer;
    }

    // +getOpus(): int
    public int getOpus(){
        return this.opus;
    }

    // +setTitle( newTitle: String ) 
    public void setTitle(String newTitle){
        this.title = newTitle;
    }

    // +setComposer( newComp: String) 
    public void setComposer(String newComp){
        this.composer = newComp;
    }

    // +setOpus( newOpus: int )
    public void setOpus(int newOpus){
        this.opus = newOpus;
    }

    // +equals(other: Object): boolean
    public boolean equals(Object other){
        Music obj = (Music)other;
        return (
            this.title == obj.title &&
            this.composer == obj.composer &&
            this.opus == obj.opus
            );
    }

    // +toString(): String
    public String toString(){
        return this.title + " " + this.composer + " " + this.opus;
    }
}