import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;

public class GetNearestCity {
    public void getNearestCity(int type) throws IOException, InvalidFormatException {
        boolean createXls=false;
        File xlsFile = new File("/Users/duanmengyang/IdeaProjects/ChallengeCups/src/main/resources/TaobaoTown.xlsx");
        // 获得工作簿
        Workbook workbook = WorkbookFactory.create(xlsFile);
        // 获得工作表个数
        int sheetCount = workbook.getNumberOfSheets();
        // 遍历工作表
        for (int i = 0; i < sheetCount; i++)
        {
            Sheet sheet = workbook.getSheetAt(i);
            // 获得行数
            int rows = sheet.getLastRowNum() + 1;
            // 获得列数，先获得一行，在得到改行列数
            Row tmp = sheet.getRow(0);
            if (tmp == null)
            {
                continue;
            }
            // 读取数据
            for (int row = 0; row < rows; row++) {
                Row r = sheet.getRow(row);
                    double distance=Double.MAX_VALUE;
                    String nearestCity="";
                    String townName = r.getCell(0).getStringCellValue()+r.getCell(1).getStringCellValue();
                    townName=townName.replace(" ","");
                    for(FirstSecondTierCitiesEnum city:FirstSecondTierCitiesEnum.values()){
                        String cityName = String.valueOf(city);
                        double distanceItem = CalculateDisUtil.distance(townName,cityName);
                        if(distanceItem<distance&&distanceItem>0){
                            distance = distanceItem;
                            nearestCity = cityName;
                        }
                    }
                    if(type==2){
                        for(ThirdTierCitiesEnum city:ThirdTierCitiesEnum.values()){
                            String cityName = String.valueOf(city);
                            double distanceItem = CalculateDisUtil.distance(townName,cityName);
                            if(distanceItem<distance&&distanceItem>0){
                                distance = distanceItem;
                                nearestCity = cityName;
                            }
                        }
                    }
                    //System.out.printf("%10s", r.getCell(col).getStringCellValue());
                    String xlsName;
                    if(type==1){
                        xlsName = "距离最近的城市（一二线）.xls";
                    }
                    else{
                        xlsName = "距离最近的城市（总）.xls";
                    }
                    if(!createXls){
                        XlsUtil.createExcel(xlsName,townName,nearestCity,distance);
                        createXls=true;
                    }
                    else{
                        XlsUtil.appendToExcel(xlsName,townName,nearestCity,distance);
                    }
            }
        }
    }

    public static void main(String[] args) {
        GetNearestCity getNearestCity=new GetNearestCity();
        try {
            getNearestCity.getNearestCity(1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }
}



