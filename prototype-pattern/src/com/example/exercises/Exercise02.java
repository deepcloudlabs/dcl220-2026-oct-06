package com.example.exercises;


public class Exercise02 {

	public static void main(String[] args) throws Exception {
		Document original = new Document(
                "Monthly Report",
                "Original report content",
                "Arial",
                12
        );

        Document copy = original.copy();

        copy.setTitle("April Report");
        copy.setContent("April report content");

        System.out.println("Original:");
        System.out.println(original);

        System.out.println("Copy:");
        System.out.println(copy);
	}

}

interface Prototype<T> {
    T copy();
}

class Document implements Prototype<Document> {

    private String title;
    private String content;
    private String font;
    private int fontSize;

    public Document(
            String title,
            String content,
            String font,
            int fontSize) {

        this.title = title;
        this.content = content;
        this.font = font;
        this.fontSize = fontSize;
    }

    // Copy constructor
    private Document(Document source) {
        this.title = source.title;
        this.content = source.content;
        this.font = source.font;
        this.fontSize = source.fontSize;
    }

    @Override
    public Document copy() {
        return new Document(this);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return """
                Document {
                    title    = '%s'
                    content  = '%s'
                    font     = '%s'
                    fontSize = %d
                }
                """.formatted(
                title,
                content,
                font,
                fontSize
        );
    }
}