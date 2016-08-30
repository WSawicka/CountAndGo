package app.file;

import app.AlertWindow;
import app.AppData;
import app.model.AlertEnum;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by mloda on 2016-08-30.
 */
public class ExcelWrite {
    private AppData appData = AppData.getInstance();
    private Sheet sheet;

    public void openFile(){
        try {
            FileInputStream file = new FileInputStream(new File(this.appData.getExcelFilePath()));
            Workbook workbook = WorkbookFactory.create(file); //reference to .xlsx file
            this.sheet = workbook.getSheetAt(0); //first/desired sheet from the workbook
        } catch(FileNotFoundException fnfex){
            new AlertWindow().show(AlertEnum.FILE_NOT_FOUND);
        } catch(InvalidFormatException ifex){
            new AlertWindow().show(AlertEnum.INVALID_FILE_FORMAT);
        } catch(IOException ioex){
            new AlertWindow().show(AlertEnum.FILE_ERROR);
        }
    }

    public int findRow(String cellContent) {
        for (Row row : this.sheet) {
            for (Cell cell : row) {
                if (cell.getCellType() == Cell.CELL_TYPE_STRING && cell.getRichStringCellValue().getString().trim().equals(cellContent)) {
                    return row.getRowNum();
                }
            }
        }
        return 0;
    }
}
