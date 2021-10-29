package apps.inailboost;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private final SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all questions from the database.
     *
     * @return a List of questions
     */
    public List<Question> loadQuestions() {
        List<Question> list = new ArrayList<>();
        Cursor c = database.rawQuery("SELECT * FROM questions", null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            list.add(new Question(c.getInt(0), // Question ID
                                  c.getString(1), // Text
                                  c.getString(2), // Right answer
                                  c.getString(3), // First wrong answer
                                  c.getString(4))); // Second wrong answer
            c.moveToNext();
        }
        c.close();
        return list;
    }
}