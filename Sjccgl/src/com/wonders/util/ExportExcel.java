package com.wonders.util;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

import com.opensymphony.xwork2.ActionContext;
import com.wonders.main.model.TbCheckMattersType;


public class ExportExcel<T> {
    public void exportExcel(String title,Collection<T> dataset, OutputStream out) {
        exportExcel(title, null, dataset, out, "yyyy-MM-dd",null);
    }

    public void exportExcel(String title,String[] headers, Collection<T> dataset,
            OutputStream out) {
        exportExcel(title, headers, dataset, out, "yyyy-MM-dd",null);
    }
    
    public void exportDcgtInfo(String title,String[] headers, Collection<T> dataset,
            OutputStream out,List<TbCheckMattersType>checkMatterslist) {
        exportExcel(title, headers, dataset, out, "yyyy-MM-dd",checkMatterslist);
    }
    /**
     * 导出数据成Excel表格 
     * @param title
     * @param headers
     * @param dataset
     * @param out
     * @param pattern
     */
    @SuppressWarnings("unchecked")
    public void exportExcel(String title, String[] headers,Collection<T> dataset, OutputStream out, String pattern,List<TbCheckMattersType> checkMatterslist ) {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        // 把字体应用到当前的样式
        style.setFont(font);

        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        //表头标题样式
        HSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFillForegroundColor(HSSFColor.SEA_GREEN.index);// 设置背景色
        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        Region region1 = new Region(0, (short) 0, 0, (short)(headers.length-1)); 
        sheet.addMergedRegion(region1);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 18);
        titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        // 把字体应用到当前的样式
        titleStyle.setFont(titleFont);
        
        HSSFFont font1 = workbook.createFont();
        font1.setFontHeightInPoints((short) 12);
        // 把字体应用到当前的样式
        HSSFCellStyle style1 = workbook.createCellStyle();
        style1.setFillForegroundColor(HSSFColor.SEA_GREEN.index);// 设置背景色
        style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style1.setFont(font1);
        style1.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style1.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style1.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        //产生表头
        HSSFRow row0 = sheet.createRow(0);
        HSSFCell cell01 = row0.createCell(0);
        cell01.setCellStyle(titleStyle);
        cell01.setCellValue(title);
        
        HSSFRow row1 = sheet.createRow(1);
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");//设置日期格式
        HSSFFont titleFont1 = workbook.createFont();
        titleFont1.setFontHeightInPoints((short) 16);
        titleFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFont(titleFont1);
        row1.setRowStyle(style2);
        for (short i = 0; i < headers.length; i++) {
        	HSSFCell cell = row1.createCell(i);
            cell.setCellStyle(style1);
           switch (i) {
			case 0:
				cell.setCellValue("生成时间:");
				continue;
			case 2:
				cell.setCellValue(df.format(new Date()));
				continue;
			case 4:
				cell.setCellValue("生成人:");
				continue;
			case 5:
				Map session = ActionContext.getContext().getSession();
				cell.setCellValue(session.get("userName").toString());
				continue;
			default:
				break;
           }
        }
        // 产生表格标题行
        HSSFRow row = sheet.createRow(2);
        for (short i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style1);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        // 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 2;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = (T) it.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fields = t.getClass().getDeclaredFields();
            for (short i = 1; i < fields.length; i++) {
                HSSFCell cell = row.createCell(i-1);
                cell.setCellStyle(style);
                Field field = fields[i];
                String fieldName = field.getName();
                String getMethodName = "get"
                        + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);
                try {
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName,
                            new Class[] {});
                    Object value = getMethod.invoke(t, new Object[] {});
                    
                    // 判断值的类型后进行强制类型转换
                    String textValue = null;
                     if (value instanceof Integer) {
                         int intValue = (Integer) value;
                         cell.setCellValue(intValue);
                     }
                     else if (value instanceof Long) {
                         long longValue = (Long) value;
                         cell.setCellValue(longValue);
                     }
                     else if (value instanceof Boolean) {
                        boolean bValue = (Boolean) value;
                        textValue = "1";
                        if (!bValue) {
                            textValue = "0";
                        }
                    } else if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    } else {
                        // 其它数据类型都当作字符串简单处理
                        if(value == null)
                        {
                            textValue = "";
                        }
                        else {
                            textValue = value.toString();
                        }
                            
                    }
                    if (textValue != null) {
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            HSSFRichTextString richString = new HSSFRichTextString(
                                    textValue);
                            richString.applyFont(font);
                            cell.setCellValue(richString);
                        }
                    }
                    //数据转换
                    String[] names={"getIszd","getSfjbzxss","getSfjbgfgl","getSfsc"};
                    for (int j = 0; j < names.length; j++) {
                    	if(getMethodName.equals(names[j])){
                        	   if("1".equals(value)){
                        		cell.setCellValue("是");
                        	   }else if("0".equals(value)){
                        		cell.setCellValue("否");   
                        	   }
					    }
                    }
                    
                    //转换事项类别类型
                    if(getMethodName.equals("getHy")){
                    	
	                   for (TbCheckMattersType checkMattersType : checkMatterslist) {
	                	   
						  if (value.equals(checkMattersType.getCheckId())) {
							  cell.setCellValue(checkMattersType.getCheckMatters());
						 } 
					   }
				    } 
                    
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } finally {
                    // 清理资源
                }
            }
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void downloadExcel(String path, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            String name = file.getName();
            // 取得文件名。
            String filename = java.net.URLEncoder.encode(name,"utf-8");
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename="
                    + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(
                    response.getOutputStream());
            response.setContentType("application/ms-excel;charset=gb2312");
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    


}