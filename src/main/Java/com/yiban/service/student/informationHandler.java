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
 * <p>Description: ����ѧ����Ϣ��service��</p>
 *
 * @author ֣���
 * @date 2018/7/16 9:22
 */
@Service
public class informationHandler {
    @Autowired
    private com.yiban.mapper.studentMapper studentMapper;
    //���������logger
    private Logger logger = LoggerFactory.getLogger(informationHandler.class);

    /**
     * @param id
     * @return ѧ���������е���Ϣ
     */
    public Student select(String id) {
        return studentMapper.select(id);
    }

    /**
     *
     * @param size
     * @return ת���·�����
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
                return "δѡ��";
        }
    }

    /**
     *
     * @param department
     * @return ת��ѧԺ����
     */
    public String switchDeparment(int department){
        switch (department) {
            case 0:
                return "���������ѧԺ";
            case 1:
                return "����ѧԺ��֪ʶ��ȨѧԺ";
            case 2:
                return "������ѧѧԺ";
            case 3:
                return "�����뽡��ѧԺ";
            case 4:
                return "��ѧԺ";
            case 5:
                return "�����ѧԺ";
            case 6:
                return "��ѧ��ͳ��ѧԺ";
            case 7:
                return "������ѧѧԺ";
            case 8:
                return "��е����������ѧԺ";
            case 9:
                return "�������������ѧԺ";
            case 10:
                return "�������ѧ�����ѧԺ��������ѧԺ";
            case 11:
                return "�����뻯ѧ����ѧԺ";
            case 12:
                return "ʳƷ����ҩ����ѧԺ";
            case 13:
                return "��������ʷ�Ļ�ѧԺ";
            case 14:
                return "����ѧԺ";
            case 15:
                return "����ѧԺ";
            case 16:
                return "�е����ѧԺ";
            default:
                return "δѡ��";
        }
    }
    /**
     * @param student
     * @return ����ѧ��������Ϣ
     */
    @Transactional
    public Result updateStudentBaseInfo(Student student) {
        if (studentMapper.updateStudentBaseInfo(student) == 0) {
            logger.error("����ѧ����Ϣ����ʧ�ܣ�ѧ����Ϣ��{}", student.toString());
            return new Result(Dictionary.FAIL_OPERATION);
        } else {
            return new Result(Dictionary.SUCCESS);
        }
    }

    /**
     * @param student
     * @return ��ѧ�����ݲ��뵽���ݿ���
     */
    @Transactional
    public Result insert(Student student) {
        if (studentMapper.insert(student) == 0) {
            logger.error("���ѧ����Ϣ����ʧ�ܣ�ѧ����Ϣ��{}", student.toString());
            return new Result(Dictionary.FAIL_OPERATION);
        }
        return new Result(Dictionary.SUCCESS);
    }

    public boolean exportInformation(String path)
    {
        List<Student> informationList =studentMapper.selectAll();
    	/*
		 * ���ñ�ͷ����Excelÿ��ȡ��(���������ȡ�����ݱ�д)
		 */
        String[] tableHeader = {"���","ѧ��","����","ѧԺ", "�༶","����"};
    	/*
		 * ����Ķ����Կ������ñ�д
		 */
        short  cellNumber = (short) tableHeader.length ;// �������
        HSSFWorkbook workbook = new HSSFWorkbook();; // ����һ��excel
        HSSFCell cell = null; // Excel����
        HSSFRow row = null; // Excel����
        HSSFCellStyle style = workbook.createCellStyle(); // ���ñ�ͷ������
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFCellStyle style1 = workbook.createCellStyle(); // ������������
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont font = workbook.createFont(); // ��������
        HSSFSheet sheet = workbook.createSheet("sheet1"); // ����һ��sheet
        HSSFHeader header = sheet.getHeader();// ����sheet��ͷ
        try {
            /*
             * �����Ƿ�ȡ�����ݣ�����header��Ϣ
             */
            if(informationList.size()<1)
            {
                header.setCenter("��������");
            }
            else {
                header.setCenter("ѧ��������Ϣ��");
                row = sheet.createRow(0);
                row.setHeight((short) 400);
                for (int k = 0; k < cellNumber; k++) {
                    cell = row.createCell(k);// ������0�е�k��
                    cell.setCellValue(tableHeader[k]);// ���õ�0�е�k�е�ֵ
                    sheet.setColumnWidth(k, 8000);// �����еĿ��
                    font.setColor(HSSFFont.COLOR_NORMAL); // ���õ�Ԫ���������ɫ.
                    font.setFontHeight((short) 350); // ���õ�Ԫ����߶�
                    style1.setFont(font);// ����������
                    cell.setCellStyle(style1);
                }

                int index=0;
                // ��excel�������
                for (int i=0;i<informationList.size();i++)
                {
                    Student information = informationList.get(i);
                    row = sheet.createRow((short) (i + 1));// ������i+1��
                    row.setHeight((short) 400);// �����и�
                    cell=row.createCell(0);
                    cell.setCellValue(index++);
                    cell.setCellStyle(style);// ���÷��

                    setRow(information,row,style);
                }
            }
        } catch (SecurityException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        // ����һ��HttpServletResponse����
        FileOutputStream out = null;
        // ����һ�����������
        try {
            // ��ʼ��HttpServletResponse����
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
        String tmp;//�м����
        int tmp2;
        if((tmp = information.getStudent_id())!=null)
        {
            cell=row.createCell(1);
            cell.setCellValue(tmp);
            cell.setCellStyle(style);// ���÷��
        }
        if((tmp = information.getName())!=null)
        {
            cell=row.createCell(2);
            cell.setCellValue(tmp);
            cell.setCellStyle(style);// ���÷��
        }
        if((tmp2 = information.getDepartment()) >= 0)
        {
            cell =row.createCell(3);
            tmp = switchDeparment(tmp2);//��ѧԺ���ת��������
            cell.setCellValue(tmp);
            cell.setCellStyle(style);// ���÷��
        }
        if((tmp = information.getClass_name())!=null)
        {
            cell =row.createCell(4);
            cell.setCellValue(tmp);
            cell.setCellStyle(style);// ���÷��
        }
        if((tmp2 = information.getSsize()) >= 0)
        {
            cell =row.createCell(5);
            tmp = switchSize(tmp2);//���ߴ���ת������
            cell.setCellValue(tmp);
            cell.setCellStyle(style);// ���÷��
        }
    }

}
