package app.model;

/**
 * Created by mloda on 2016-08-30.
 */
public enum AlertEnum {
    FILE_ERROR("Błąd pliku."),
    ERROR_WHILE_WRITE_XML("Błąd zapisu pliku XML."),
    WRITE_SUCCESS("Zapisano plik."),
    READ_SUCCESS("Odczytano plik."),
    ERROR_WHILE_READ_XML("Błąd odczytu pliku XML."),
    FILE_NOT_FOUND("Nie znaleziono pliku!"),
    INVALID_FILE_FORMAT("Zły format pliku!"),
    EMPTY_FILE("Plik jest pusty."),
    NOT_NUMERIC_VALUE("Tylko wartości numeryczne!"),
    PRODUCT_NOT_FOUND("Nie ma takiego produktu.");

    private String text;

    AlertEnum(String text){
        this.text = text;
    }

    public String getText(){
        return this.text;
    }
}
