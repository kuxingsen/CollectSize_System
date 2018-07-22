package com.yiban.controller;

import com.yiban.entity.Dictionary;
import com.yiban.entity.Result;
import com.yiban.entity.Student;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import com.yiban.service.student.informationHandler;


/**
 * <p>Title:����ѧ����Ϣ��controller </p>
 * <p>Description: </p>
 *
 * @author ֣���
 * @date 2018/7/15 12:37
 */
@Controller
@RequestMapping(value = "/collect")
public class InformationController {

    @Autowired
    private informationHandler informationHandler;

    private Logger logger=LoggerFactory.getLogger(InformationController.class);
    @RequestMapping("/index")
    public String index()
    {
        return "index";
    }
    /**
     * @return ����ѧ���ĸ�����Ϣ--������Ϣ
     */
    @RequestMapping(value = "/studentInfo", method = RequestMethod.GET)
    @ResponseBody
    public Result getStuInformation(HttpSession session) {
        Student student = null;
        String yiban_id = (String) session.getAttribute("yiban_id");
        if ((student = informationHandler.select(yiban_id)) != null) {
            String flag = "1";//���ݿ������ѧ���ļ�
            session.setAttribute("flag",flag);
            return new Result<Student>(Dictionary.SUCCESS,student);
        }

        return new Result(Dictionary.FAIL_OPERATION);
    }

    /**
     *
     * @return ��������ѧ����Ϣ��controller
     */
    @RequestMapping(value = "/submit",method = RequestMethod.POST)
    @ResponseBody
    public Result stuInfo(Student student,HttpSession session){
        System.out.println(student);
        String yiban_id= (String) session.getAttribute("yiban_id");
        student.setYiban_id(yiban_id);
        String flag= (String) session.getAttribute("flag");
        //��ȡѧ����Ϣ
        if (flag == null || flag.equals("")){
            Result result=informationHandler.insert(student);
            logger.info("result��ֵ��{}",result.toString());
            return result;
        }else {
            return informationHandler.updateStudentBaseInfo(student);
        }
    }

    @RequestMapping(value ="/error")
    public String error(){
        return "error";
    }

    /**
     * �����ݵ���Ϊexcel����Ҫ�����˺Ų��ܵ���
     * @param request
     * @param session
     * @return
     * @throws IOException
     */
    @RequestMapping("/toExcel")
    public ResponseEntity<byte[]> downloadExcel(HttpServletRequest request,HttpSession session) throws IOException {
        String yiban_id= (String) session.getAttribute("yiban_id");
        if(!yiban_id.equals("8118009")){
            return null;
        }

        String path = request.getSession().getServletContext().getRealPath("/temp/");
        String filePath = "ѧ��������Ϣ��" + System.currentTimeMillis() + ".xls";

        File downFile = new File(path, filePath);

        if (!downFile.getParentFile().exists()) {
            boolean mk=downFile.getParentFile().mkdirs();
            System.err.println("create:"+mk);
        }

        if (informationHandler.exportInformation(path + File.separator + filePath)) {
            File file = new File(path + File.separator + filePath);


            HttpHeaders headers = new HttpHeaders();

            //������ʾ���ļ������������������������

            /*String downloadFielName = new String(filePath.substring(filePath.lastIndexOf("//"),filePath.length()).getBytes("UTF-8"),
                    "iso-8859-1");*/

            //֪ͨ�������attachment�����ط�ʽ��
            headers.setContentDispositionFormData("attachment", filePath);

            //application/octet-stream �� �����������ݣ�������ļ����أ���
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);


//            return new ResponseEntity <byte[]>(FileUtils.readFileToByteArray(file),
//                    headers, HttpStatus.CREATED);
            /**
             * ���IE���������ļ�����
             */
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                    headers, HttpStatus.OK);
        }
        return null;
    }
}
