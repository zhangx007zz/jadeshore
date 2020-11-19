package com.jadeshop.pro.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.jadeshop.pro.model.JadeShopDto;
import com.jadeshop.pro.service.jadeshop.JadeShopService;

@RestController
@RequestMapping("/jade")
public class JadeShopController {
	private final static Logger logger = LoggerFactory.getLogger(JadeShopController.class);

    @Autowired
    private JadeShopService jadeShopService;

    @RequestMapping("/getJade")
	public PageInfo<JadeShopDto> getAllUser(@RequestBody JadeShopDto dto) {    	
    	return jadeShopService.findAllJade(dto);
	} 
    
    
    @RequestMapping("/addJade")
    public boolean addJade(@RequestBody JadeShopDto dto){
    	jadeShopService.addJade(dto);
    	return true;
    }
    
    @RequestMapping("/delJade")
    public boolean delJade(@RequestParam("id") String id){
    	jadeShopService.delJade(id);
    	return true;
    }
    
    @RequestMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file,@RequestParam("id") String id) {
        if (file.isEmpty()) {
            return "上传图片失败，请选择文件";
        }
        try {
        	 byte[] imgByte= file.getBytes();
        	 JadeShopDto dto = new JadeShopDto();
        	 dto.setImg(imgByte);
        	 if(StringUtils.isNoneBlank(id)) {
        		 dto.setId(Integer.valueOf(id));
        		 jadeShopService.updateImg(dto);
        		 return "上传成功！";
        	 }
        	 int newId = jadeShopService.addImg(dto);
        	 return String.valueOf(newId);
		} catch (Exception e) {
			logger.error("获取异常", e);
		}      
        return "上传成功！";
    }

    @RequestMapping("/getImg")
	public void getImg(HttpServletResponse response, HttpServletRequest request, String id) {
    	JadeShopDto dto = new JadeShopDto();
    	dto = jadeShopService.selectJade(id);
    	if(dto==null||dto.getImg()==null){
    		return;
    	}
		byte[] bytes = dto.getImg();
		response.reset(); // 非常重要
		response.setContentType("image/*");
		try (OutputStream out = response.getOutputStream();) {
			response.setHeader("Content-Disposition", "inline; filename=" + java.net.URLEncoder.encode("demo", "UTF-8"));
			if (bytes != null) {
				out.write(bytes, 0, bytes.length);
			} else {
				logger.error("未获取到图片文件流");
			}
		} catch (Exception e) {
			logger.error("获取异常", e);
		}

	} 
    
    @SuppressWarnings("resource")
    @RequestMapping("/export")
    public void Export(HttpServletResponse response, String name,String stuff,String date,String source,String partner) {       
		HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("商品表");
        JadeShopDto dto = new JadeShopDto();
        dto.setDate(date);
        dto.setName(name);
        dto.setStuff(stuff);
        dto.setSource(source);
        dto.setPartner(partner);
        List<JadeShopDto> classmateList = jadeShopService.exportJade(dto);
        try (OutputStream out = response.getOutputStream()){
        	 // 设置要导出的文件的名字
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            String fileName = "jadeshop-"  + sdf.format(new Date()) + ".xls";

            // 新增数据行，并且设置单元格数据
            int rowNum = 1;
            
            // headers表示excel表中第一行的表头 在excel表中添加表头
            String[] headers = { "编号", "图片", "名称", "材料","描述", "成本", "工", "售价","折后价", "利润", "55分成利润", "结账金额","货主", "备注", "实际结款", "合作方","日期"};
            HSSFRow headRow = sheet.createRow(0);
            HSSFCellStyle headStyle = workbook.createCellStyle();
            HSSFFont font = workbook.createFont();
            font.setBold(true);
            headStyle.setFont(font);
            headStyle.setBorderBottom(BorderStyle.THIN); //下边框
            headStyle.setBorderLeft(BorderStyle.THIN);//左边框
            headStyle.setBorderTop(BorderStyle.THIN);//上边框
            headStyle.setBorderRight(BorderStyle.THIN);//右边框
            headStyle.setAlignment(HorizontalAlignment.CENTER);
            headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.index);
            headRow.setHeightInPoints(20);
            for(int j=0;j<headers.length;j++){
            	sheet.setColumnWidth(j, 4000);
                HSSFCell cell = headRow.createCell(j);               
                HSSFRichTextString text = new HSSFRichTextString(headers[j]);
                cell.setCellValue(text);
                cell.setCellStyle(headStyle);
            }
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            HSSFCellStyle style = workbook.createCellStyle();
            style.setBorderBottom(BorderStyle.THIN); //下边框
            style.setBorderLeft(BorderStyle.THIN);//左边框
            style.setBorderTop(BorderStyle.THIN);//上边框
            style.setBorderRight(BorderStyle.THIN);//右边框
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setWrapText(false);
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
            cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
            cellStyle.setBorderTop(BorderStyle.THIN);//上边框
            cellStyle.setBorderRight(BorderStyle.THIN);//右边框
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            HSSFDataFormat format= workbook.createDataFormat();
            cellStyle.setDataFormat(format.getFormat("#,##0.00"));
            //在表中存放查询到的数据放入对应的列
            for (int i=0;i<classmateList.size();i++) {
            	JadeShopDto item = classmateList.get(i);
                HSSFRow row = sheet.createRow(rowNum);
                row.setHeightInPoints(60);
                HSSFCell cellNumber = row.createCell(0);
                cellNumber.setCellStyle(style);
                cellNumber.setCellValue(item.getNumber());
                if(item.getImg()!=null&&item.getImg().length>0) {
                	 HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0,0,
                             (short) 1, (i + 1), (short) 2, (i+2));
                     anchor.setAnchorType(AnchorType.MOVE_DONT_RESIZE);            
                     // 插入图片
                     patriarch.createPicture(anchor, workbook.addPicture(item.getImg(), HSSFWorkbook.PICTURE_TYPE_JPEG)).resize(1);
                }           
                HSSFCell cellName = row.createCell(2);
                cellName.setCellStyle(style);
                cellName.setCellValue(item.getName());
                HSSFCell cellStuff = row.createCell(3);
                cellStuff.setCellStyle(style);
                cellStuff.setCellValue(item.getStuff());
                HSSFCell cellDescribe = row.createCell(4);
                cellDescribe.setCellStyle(style);
                cellDescribe.setCellValue(item.getDescribes());
                HSSFCell cellPrime = row.createCell(5);
                cellPrime.setCellStyle(cellStyle);
                if(null!=item.getPrime()) {
                	cellPrime.setCellValue(item.getPrime().doubleValue());
                }    
                HSSFCell cellType = row.createCell(6);
                cellType.setCellStyle(style);
                cellType.setCellValue(item.getType());
                HSSFCell cellPrice = row.createCell(7);
                cellPrice.setCellStyle(cellStyle);
                if(null!=item.getPrice()) {
                	cellPrice.setCellValue(item.getPrice().doubleValue());
                }   
                HSSFCell cellDisprice = row.createCell(8);
                cellDisprice.setCellStyle(cellStyle);
                if(null!=item.getDisprice()) {
                	cellDisprice.setCellValue(item.getDisprice().doubleValue());
                } 
                HSSFCell cellProfit = row.createCell(9);
                cellProfit.setCellStyle(cellStyle);
                if(null!=item.getProfit()) {
                	cellProfit.setCellValue(item.getProfit().doubleValue());
                }
                HSSFCell cellFiveprofit = row.createCell(10);
                cellFiveprofit.setCellStyle(cellStyle);
                if(null!=item.getFiveprofit()) {
                	cellFiveprofit.setCellValue(item.getFiveprofit().doubleValue());
                }
                HSSFCell cellCloseamount = row.createCell(11);
                cellCloseamount.setCellStyle(cellStyle);
                if(null!=item.getCloseamount()) {
                	cellCloseamount.setCellValue(item.getCloseamount().doubleValue());
                }
                HSSFCell cellSource = row.createCell(12);
                cellSource.setCellStyle(style);
                cellSource.setCellValue(item.getSource());
                HSSFCell cellRemark = row.createCell(13);
                cellRemark.setCellStyle(style);
                cellRemark.setCellValue(item.getRemark());
                HSSFCell cellRealamount = row.createCell(14);
                cellRealamount.setCellStyle(cellStyle);
                if(null!=item.getRealamount()) {
                	cellRealamount.setCellValue(item.getRealamount().toString());
                }    
                HSSFCell cellPartner = row.createCell(15);
                cellPartner.setCellStyle(style);
                cellPartner.setCellValue(item.getPartner());
                HSSFCell cellDate = row.createCell(16);
                cellDate.setCellStyle(style);
                cellDate.setCellValue(item.getDate());
                rowNum++;
            }

            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            response.flushBuffer();       
            workbook.write(out);
		} catch (Exception e) {
			logger.error("导出失败："+e.getMessage());
		}
       
    }
    
}