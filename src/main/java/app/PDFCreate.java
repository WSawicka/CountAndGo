package app;

import app.model.Item;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.*;

import java.io.IOException;
import java.io.InputStream;
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
    AppData appData;

    private PDDocument document;
    private PDPage page;
    private PDFont font;
    private PDPageContentStream stream;

    public PDFCreate(List<Item> items, AppData appData){
        this.items = new ArrayList<>();
        this.items.addAll(items);
        this.appData = appData;
    }

    public void create() throws IOException {
        this.name = this.appData.getFileName();
        this.title = this.appData.getFileTitle();
        this.date = this.appData.getDate();
        prepare();
        write();
        close();
    }

    private void prepare() throws  IOException{
        document = new PDDocument();
        page = new PDPage();
        document.addPage(page);
        stream = new PDPageContentStream(document, page);
    }

    private void write() throws IOException{
        //TODO: dopracuj wygląd
        stream.beginText();
        font = PDType1Font.TIMES_BOLD;
        stream.setFont(font, 18);

        stream.moveTextPositionByAmount(160, 750);
        stream.drawString(title);

        stream.moveTextPositionByAmount(-50, -40);
        font = PDType1Font.TIMES_ROMAN;
        stream.setFont(font, 13);
        stream.drawString("Nazwa produktu");
        tab();
        stream.drawString("Uzyta ilosc");
        tab();
        stream.drawString("Cena laczna");
        stream.setFont(font, 12);
        for(Item i : items){
            stream.moveTextPositionByAmount(-200, -20);
            stream.drawString(i.getProduct());
            tab();
            stream.drawString(i.getAmount().toString());
            tab();
            stream.drawString(i.getCost().toString());
        }

        stream.moveTextPositionByAmount(-200, -40);
        stream.drawString("Prad:");
        tab();
        stream.drawString(this.appData.getPriceEnergy().toString());

        stream.moveTextPositionByAmount(-100, -20);
        stream.drawString("Woda:");
        tab();
        stream.drawString(this.appData.getPriceWater().toString());

        stream.moveTextPositionByAmount(-100, -20);
        stream.drawString("Czas pracy:");
        tab();
        stream.drawString(this.appData.getPriceTime().toString());

        tab();
        stream.drawString("Cena laczna");
        tab();
        stream.drawString(String.valueOf(this.appData.getPriceAll()));

        //TODO: dopisz datę
        stream.endText();
    }

    private void close() throws IOException{
        stream.close();
        document.save(name + ".pdf");
        document.close();
    }

    private void tab() throws IOException {
        stream.moveTextPositionByAmount(100, 0);
    }
}
