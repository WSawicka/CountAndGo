package app.model;

/**
 * Created by mloda on 2016-08-30.
 */
public enum AlertEnum {
    FILE_ERROR("Błąd pliku."),
    FILE_NOT_FOUND("Nie znaleziono pliku!"),
    INVALID_FILE_FORMAT("Zły format pliku!"),
    NOT_NUMERIC_VALUE("Tylko wartości numeryczne!");

    private String text;

    AlertEnum(String text){
        this.text = text;
    }

    public String getText(){
        return this.text;
    }
}