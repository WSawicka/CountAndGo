package app.file;

import app.AppData;
import app.model.Item;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.text.DocumentException;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import org.joda.time.LocalDate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mloda on 2016-08-18.
 */
public class PDFCreate {
    private List<Item> items;
    private String name;
    private String title;
    private LocalDate date;
    private AppData appData;

    private PdfWriter writer;
    private PdfDocument pdf;
    private Document document;
    private PdfFont font;

    public PDFCreate(List<Item> items, AppData appData) throws IOException, DocumentException {
        this.items = new ArrayList<>();
        this.items.addAll(items);
        this.appData = appData;
        this.font = PdfFontFactory.createFont("src/main/resources/styles/arial.ttf", PdfEncodings.CP1250, true);
    }

    public void create() throws IOException, URISyntaxException {
        this.name = this.appData.getFileName();
        this.title = this.appData.getFileTitle();
        this.date = this.appData.getDate();
        prepare();
        write();
        this.document.close();
    }

    private void prepare() throws  IOException{
        this.writer = new PdfWriter(this.name + ".pdf");
        this.pdf = new PdfDocument(this.writer);
        this.document = new Document(this.pdf, new PageSize(612, 792));
        this.document.setMargins(50, 45, 50, 45);
    }

    private void write() throws IOException, URISyntaxException {
        setTitleAndDate();
        this.document.add(generateProductTable());
        setAdditionalValues();

        this.document.add(new Paragraph("Cena ogólna: " + this.appData.getPriceAll().toString()).setFont(this.font).setBold().setTextAlignment(TextAlignment.RIGHT));
    }

    private void setTitleAndDate(){
        this.document.add(new Paragraph(this.title).setFont(this.font).setBold().setFontSize(17).setTextAlignment(TextAlignment.CENTER));
        this.document.add(new Paragraph(dateToString()).setFont(this.font).setFontSize(8).setFontColor(Color.GRAY).setTextAlignment(TextAlignment.CENTER));
    }

    private Table generateProductTable(){
        Table table = new Table(new float[]{150, 80, 80});
        table.setBorder(Border.NO_BORDER);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.setMargin(30);

        table.addCell(new Cell().add("Nazwa produktu").setFont(this.font).setBold());
        table.addCell(new Cell().add("Użyta ilość").setFont(this.font).setBold());
        table.addCell(new Cell().add("Cena łączna").setFont(this.font).setBold());

        for(Item i : items){
            table.addCell(i.getProduct()).setFont(this.font);
            table.addCell(i.getAmount().toString()).setFont(this.font);
            table.addCell(i.getCost().toString()).setFont(this.font);
        }
        return table;
    }

    private void setAdditionalValues(){
        this.document.add(new Paragraph("Prąd: " + this.appData.getPriceEnergy()).setFont(this.font).setMarginLeft(60));
        this.document.add(new Paragraph("Woda: " + this.appData.getPriceWater()).setFont(this.font).setMarginLeft(60));
        this.document.add(new Paragraph("Czas pracy: " + this.appData.getPriceTime()).setFont(this.font).setMarginLeft(60));
    }

    private String dateToString(){
        return this.date.toString();
    }
}
