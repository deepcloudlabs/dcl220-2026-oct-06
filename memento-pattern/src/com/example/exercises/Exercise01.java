package com.example.exercises;

import java.util.ArrayDeque;
import java.util.Deque;

public class Exercise01 {

	public static void main(String[] args) {
		Editor editor = new Editor();
		History history = new History(editor);

		history.backup();
		editor.type("Hello");
		history.backup();
		editor.type(", world");
		history.backup();
		editor.moveCursor(5);
		editor.type(" there");
		System.out.println("typed a bit    : " + editor);

		history.backup();
		editor.backspace(50); 
		System.out.println("oops           : " + editor);

		history.undo();
		System.out.println("undo           : " + editor);
		history.undo();
		System.out.println("undo again     : " + editor);
		history.redo();
		System.out.println("redo           : " + editor);

		history.backup();
		editor.moveCursorToEnd();
		editor.type("!"); 
		System.out.println("new edit       : " + editor);
		System.out.println("redo possible? : " + history.redo());
	}
}

class Editor {

	private final StringBuilder text = new StringBuilder();
	private int cursor = 0;

	void type(String s) {
		text.insert(cursor, s);
		cursor += s.length();
	}

	void moveCursor(int position) {
		cursor = Math.max(0, Math.min(position, text.length()));
	}

	void moveCursorToEnd() {
		cursor = text.length();
	}

	void backspace(int count) {
		int from = Math.max(0, cursor - count);
		text.delete(from, cursor);
		cursor = from;
	}

	Snapshot save() {
		return new Snapshot(text.toString(), cursor);
	}

	void restore(Snapshot snapshot) {
		text.setLength(0);
		text.append(snapshot.text);
		cursor = snapshot.cursor;
	}

	@Override
	public String toString() {
		return "\"" + text + "\" (cursor at " + cursor + ")";
	}

	static final class Snapshot {
		private final String text;
		private final int cursor;

		private Snapshot(String text, int cursor) {
			this.text = text;
			this.cursor = cursor;
		}
	}
}

class History {

	private final Editor editor;
	private final Deque<Editor.Snapshot> undoStack = new ArrayDeque<>();
	private final Deque<Editor.Snapshot> redoStack = new ArrayDeque<>();

	History(Editor editor) {
		this.editor = editor;
	}

	void backup() {
		undoStack.push(editor.save());
		redoStack.clear();
	}

	boolean undo() {
		if (undoStack.isEmpty()) {
			return false;
		}
		redoStack.push(editor.save());
		editor.restore(undoStack.pop());
		return true;
	}

	boolean redo() {
		if (redoStack.isEmpty()) {
			return false;
		}
		undoStack.push(editor.save());
		editor.restore(redoStack.pop());
		return true;
	}
}
