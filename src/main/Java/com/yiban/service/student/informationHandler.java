package com.yiban.service.student;

import com.yiban.entity.Dictionary;
import com.yiban.entity.Result;
import com.yiban.entity.Student;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * <p>Title: SERVICE</p>
 * <p>Description: 处理学生信息的service类</p>
 *
 * @author 郑达成
 * @date 2018/7/16 9:22
 */
@Service
public class informationHandler {
    @Autowired
    private com.yiban.mapper.studentMapper studentMapper;
    //创建此类的logger
    private Logger logger = LoggerFactory.getLogger(informationHandler.class);

    /**
     * @param id
     * @return 学生个人所有的信息
     */
    public Student select(String id) {
        return studentMapper.select(id);
    }

    /**
     *
     * @param size
     * @return 转换衣服尺码
     */
    public String switchSize(int size) {
        switch (size) {
            case 0:
                return "S";
            case 1:
                return "M";
            case 2:
                return "L";
            case 3:
                return "XL";
            case 4:
                return "XXL";
            case 5:
                return "XXXL";
            default:
                return "未选择";
        }
    }

    /**
     *
     * @param department
     * @return 转换学院名称
     */
    public String switchDeparment(int department){
        switch (department) {
            case 0:
                return "经济与管理学院";
            case 1:
                return "政法学院、知识产权学院";
            case 2:
                return "教育科学学院";
            case 3:
                return "体育与健康学院";
            case 4:
                return "文学院";
            case 5:
                return "外国语学院";
            case 6:
                return "数学与统计学院";
            case 7:
                return "生命科学学院";
            case 8:
                return "机械与汽车工程学院";
            case 9:
                return "电子与电气工程学院";
            case 10:
                return "计算机科学与软件学院、大数据学院";
            case 11:
                return "环境与化学工程学院";
            case 12:
                return "食品与制药工程学院";
            case 13:
                return "旅游与历史文化学院";
            case 14:
                return "音乐学院";
            case 15:
                return "美术学院";
            case 16:
                return "中德设计学院";
            default:
                return "未选择";
        }
    }
    /**
     * @param student
     * @return 更新学生基本信息
     */
    @Transactional
    public Result updateStudentBaseInfo(Student student) {
        if (studentMapper.updateStudentBaseInfo(student) == 0) {
            logger.error("更新学生信息出现失败，学生信息：{}", student.toString());
            return new Result(Dictionary.FAIL_OPERATION);
        } else {
            return new Result(Dictionary.SUCCESS);
        }
    }

    /**
     * @param student
     * @return 把学生数据插入到数据库中
     */
    @Transactional
    public Result insert(Student student) {
        if (studentMapper.insert(student) == 0) {
            logger.error("添加学生信息出现失败，学生信息：{}", student.toString());
            return new Result(Dictionary.FAIL_OPERATION);
        }
        return new Result(Dictionary.SUCCESS);
    }

    public boolean exportInformation(String path)
    {
        List<Student> informationList =studentMapper.selectAll();
    	/*
		 * 设置表头：对Excel每列取名(必须根据你取的数据编写)
		 */
        String[] tableHeader = {"序号","学号","姓名","学院", "班级","尺码"};
    	/*
		 * 下面的都可以拷贝不用编写
		 */
        short  cellNumber = (short) tableHeader.length ;// 表的列数
        HSSFWorkbook workbook = new HSSFWorkbook();; // 创建一个excel
        HSSFCell cell = null; // Excel的列
        HSSFRow row = null; // Excel的行
        HSSFCellStyle style = workbook.createCellStyle(); // 设置表头的类型
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFCellStyle style1 = workbook.createCellStyle(); // 设置数据类型
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont font = workbook.createFont(); // 设置字体
        HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
        HSSFHeader header = sheet.getHeader();// 设置sheet的头
        try {
            /*
             * 根据是否取出数据，设置header信息
             */
            if(informationList.size()<1)
            {
                header.setCenter("查无资料");
            }
            else {
                header.setCenter("学生尺码信息表");
                row = sheet.createRow(0);
                row.setHeight((short) 400);
                for (int k = 0; k < cellNumber; k++) {
                    cell = row.createCell(k);// 创建第0行第k列
                    cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
                    sheet.setColumnWidth(k, 8000);// 设置列的宽度
                    font.setColor(HSSFFont.COLOR_NORMAL); // 设置单元格字体的颜色.
                    font.setFontHeight((short) 350); // 设置单元字体高度
                    style1.setFont(font);// 设置字体风格
                    cell.setCellStyle(style1);
                }

                int index=0;
                // 给excel填充数据
                for (int i=0;i<informationList.size();i++)
                {
                    Student information = informationList.get(i);
                    row = sheet.createRow((short) (i + 1));// 创建第i+1行
                    row.setHeight((short) 400);// 设置行高
                    cell=row.createCell(0);
                    cell.setCellValue(index++);
                    cell.setCellStyle(style);// 设置风格

                    setRow(information,row,style);
                }
            }
        } catch (SecurityException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        // 创建一个HttpServletResponse对象
        FileOutputStream out = null;
        // 创建一个输出流对象
        try {
            // 初始化HttpServletResponse对象
            out = new FileOutputStream(path);
            workbook.write(out);
            out.flush();
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;

    }
    private void setRow(Student information, HSSFRow row, HSSFCellStyle style)
    {
        Cell cell;
        String tmp;//中间变量
        int tmp2;
        if((tmp = information.getStudent_id())!=null)
        {
            cell=row.createCell(1);
            cell.setCellValue(tmp);
            cell.setCellStyle(style);// 设置风格
        }
        if((tmp = information.getName())!=null)
        {
            cell=row.createCell(2);
            cell.setCellValue(tmp);
            cell.setCellStyle(style);// 设置风格
        }
        if((tmp2 = information.getDepartment()) >= 0)
        {
            cell =row.createCell(3);
            tmp = switchDeparment(tmp2);//将学院编号转化成名称
            cell.setCellValue(tmp);
            cell.setCellStyle(style);// 设置风格
        }
        if((tmp = information.getClass_name())!=null)
        {
            cell =row.createCell(4);
            cell.setCellValue(tmp);
            cell.setCellStyle(style);// 设置风格
        }
        if((tmp2 = information.getSsize()) >= 0)
        {
            cell =row.createCell(5);
            tmp = switchSize(tmp2);//将尺寸编号转成码数
            cell.setCellValue(tmp);
            cell.setCellStyle(style);// 设置风格
        }
    }

}
