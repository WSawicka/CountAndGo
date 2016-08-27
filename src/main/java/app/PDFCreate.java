package app;

import app.model.Item;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mloda on 2016-08-18.
 */
public class PDFCreate {
    private List<Item> items;
    private String name;
    private String title;
    private Date date;
    private AppData appData;

    private PdfWriter writer;
    private PdfDocument pdf;
    private Document document;
    private PdfFont font;
    private PdfFont bold;

    public PDFCreate(List<Item> items, AppData appData) throws IOException {
        this.items = new ArrayList<>();
        this.items.addAll(items);
        this.appData = appData;
        this.font = PdfFontFactory.createFont(FontConstants.HELVETICA);
        this.bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
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
        this.document = new Document(pdf);
        this.document.setMargins(55, 40, 55, 40);
    }

    private void write() throws IOException, URISyntaxException {
        this.document.add(new Paragraph(title));
        this.document.add(new Paragraph(date.toString()));

        //title;
        //date.toString();
        //"Nazwa produktu";
        //"Uzyta ilosc";
        //"Cena laczna";
        //for(Item i : items){
        //    i.getProduct().toString();
        //    i.getAmount().toString();
        //    i.getCost().toString();
        //}

        //"Prad:";
        //this.appData.getPriceEnergy().toString();
        //"Woda:";
        //this.appData.getPriceWater().toString();
        //"Czas pracy:";
        //this.appData.getPriceTime().toString();

        //stream.showText("Cena laczna");
        //stream.showText(String.valueOf(this.appData.getPriceAll());
    }
}
