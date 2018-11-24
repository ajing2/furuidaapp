package com.furuida.utils;

import com.furuida.model.Order;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.formula.functions.T;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: jinjiaoyang
 * Date: 14-8-28
 * Time: 下午3:59
 * To change this template use File | Settings | File Templates.
 */
public class ExportExcel {

    private static final Logger log = Logger.getLogger(ExportExcel.class);

    private static String PARAM_TITLE = "文件标题不能为空";
    private static String PARAM_FIELDNAMES = "列表列名不能为空";
    private static String PARAM_LIST = "列表不能为空";
    private static String MAX_NUM_INFO = "数据量太大，不能导出";
    private static int MAX_NUM = 80000;
    private static int PAGE_NUM = 50000;
    public static String EXPORT_TYPE_EXCEL="1";
    public static String EXPORT_TYPE_CSV="2";
//    static ProjectInfoService projectInfoService = AppContext.getBean(ProjectInfoService.class);
//    static MachineRoomService machineRoomService = AppContext.getBean(MachineRoomService.class);
//    static DictService dictService = AppContext.getBean(DictService.class);

    /**
     * 导出EXCEL公共方法 导出带标题的空文件
     */
    public static <T> HSSFWorkbook exportExcel(String fileName, String[] fieldNames, String title, String comments,
                                               String header) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(fileName);
        sheet.setDefaultColumnWidth(15);
        // 生成excel表格title
        try {
            createCell(sheet, 0, 0, getTitleStyle(workbook))
                    .setCellValue(title);
            createCell(sheet, 1, 0, getCommentStyle(workbook))
            .setCellValue(comments); //comments
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        String[] headerstr = header.split(";");
        addMergedRegion(sheet, 0, 0, 0, 13);
        addMergedRegion(sheet, 1, 1, 0, headerstr.length - 1);
        sheet.getRow(0).setHeight(
                (short) (512));
        sheet.getRow(1).setHeight(
                (short) (1100));
        // 生成excel表格表头
        for (int index = 0; index < headerstr.length; index++) {
            String headers = headerstr[index];
            createCell(sheet, 2, index, getHeaderStyle(workbook))
                    .setCellValue(headers);
        }
        //宽度调整
        sheet.setColumnWidth(3, "服务器的VIP地址".getBytes().length*2*120);
        sheet.setColumnWidth(6, "宿主机ip地址（Docker必填）".getBytes().length*2*120);
        // 创建隐藏的sheet-分类
        HSSFSheet hidden = (HSSFSheet) workbook.createSheet("hidden");
        // 数据源sheet页不显示
        workbook.setSheetHidden(1, true);

        // 填充下拉框数据，这种方式对字符数量没有限制
//        List<ExcelDropDownBox> list = getDropDownData();
//        String[] machineRoom = null; // 机房名称
//        String[] projectInfo = null; // 所属项目
//        String[] area = null; // 所属区域
//        String[] serverUseType = null; // 机器用途
//        String[] machineType = null; // 机器类型
//        String[] raidLevel = null; // raidLevel
//        String[] healthStatus = null; // 故障状态
//        String[] serverOper = null; // 运维组
//        String[] status = null; // 状态
//        if (null != list) {
//            machineRoom = list.get(0).getStr().split(";");
//            projectInfo = list.get(1).getStr().split(";");
//            area = list.get(2).getStr().split(";");
//            serverUseType = list.get(3).getStr().split(";");
//            machineType = list.get(4).getStr().split(";");
//            raidLevel = list.get(5).getStr().split(";");
//            healthStatus = list.get(6).getStr().split(";");
//            serverOper = list.get(7).getStr().split(";");
//            status = list.get(8).getStr().split(";");
//
//        }
        HSSFCellStyle style = workbook.createCellStyle();
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFRow row = null;
        HSSFCell c = null;
//        for (int i = 0, length = machineRoom.length; i < length; i++) {
//        	row = (HSSFRow) hidden.getRow(i);
//            if (null != row) {
//            	c = getCell(row, c, 0);
//                c.setCellValue(machineRoom[i]);
//            } else {
//                row = (HSSFRow) hidden.createRow(i);
//                c = getCell(row, c, 0);
//                c.setCellValue(machineRoom[i]);
//            }
//
//        }
//        org.apache.poi.hssf.usermodel.DVConstraint constraint = createHiddenSheet("A", machineRoom, workbook); //机房名称
//
//        for (int i = 0, length = projectInfo.length; i < length; i++) {
//        	row = (HSSFRow) hidden.getRow(i);
//        	if (null != row) {
//        		c = getCell(row, c, 1);
//                c.setCellValue(projectInfo[i]);
//            } else {
//                row = (HSSFRow) hidden.createRow(i);
//                c = getCell(row, c, 1);
//                c.setCellValue(projectInfo[i]);
//            }
//        }
//        org.apache.poi.hssf.usermodel.DVConstraint constraint2 = createHiddenSheet("B", projectInfo, workbook); //所属项目
//
//
//        if (null != machineType) {
//        	for (int i = 0, length = machineType.length; i < length; i++) {
//        		row = (HSSFRow) hidden.getRow(i);
//        		if (null != row) {
//        			c = getCell(row, c, 2);
//                    c.setCellValue(machineType[i]);
//                } else {
//                    row = (HSSFRow) hidden.createRow(i);
//                    c = getCell(row, c, 2);
//                    c.setCellValue(machineType[i]);
//                }
//            }
//        }
//        org.apache.poi.hssf.usermodel.DVConstraint constraint3 = createHiddenSheet("C", machineType, workbook); //机器类型
//
//
//        if (null != serverUseType) {
//        	for (int i = 0, length = serverUseType.length; i < length; i++) {
//        		row = (HSSFRow) hidden.getRow(i);
//        		if (null != row) {
//        			c = getCell(row, c, 3);
//                    c.setCellValue(serverUseType[i]);
//                } else {
//                    row = (HSSFRow) hidden.createRow(i);
//                    c = getCell(row, c, 3);
//                    c.setCellValue(serverUseType[i]);
//                }
//            }
//        }
//        org.apache.poi.hssf.usermodel.DVConstraint constraint4 = createHiddenSheet("D", serverUseType, workbook); //机器用途
//
//
//        if (null != healthStatus) {
//        	for (int i = 0, length = healthStatus.length; i < length; i++) {
//        		row = (HSSFRow) hidden.getRow(i);
//        		if (null != row) {
//        			c = getCell(row, c, 4);
//                    c.setCellValue(healthStatus[i]);
//                } else {
//                    row = (HSSFRow) hidden.createRow(i);
//                    c = getCell(row, c, 4);
//                    c.setCellValue(healthStatus[i]);
//                }
//            }
//        }
//        org.apache.poi.hssf.usermodel.DVConstraint constraint5 = createHiddenSheet("E", healthStatus, workbook); //故障信息
//
//        if (null != raidLevel) {
//        	for (int i = 0, length = raidLevel.length; i < length; i++) {
//        		row = (HSSFRow) hidden.getRow(i);
//        		if (null != row) {
//        			c = getCell(row, c, 5);
//                    c.setCellValue(raidLevel[i]);
//                } else {
//                    row = (HSSFRow) hidden.createRow(i);
//                    c = getCell(row, c, 5);
//                    c.setCellValue(raidLevel[i]);
//                }
//            }
//        }
//        org.apache.poi.hssf.usermodel.DVConstraint constraint6 = createHiddenSheet("F", raidLevel, workbook); //raid level
//
//
//        if (null != serverOper) {
//        	for (int i = 0, length = serverOper.length; i < length; i++) {
//        		row = (HSSFRow) hidden.getRow(i);
//        		if (null != row) {
//        			c = getCell(row, c, 6);
//                    c.setCellValue(serverOper[i]);
//                } else {
//                    row = (HSSFRow) hidden.createRow(i);
//                    c = getCell(row, c, 6);
//                    c.setCellValue(serverOper[i]);
//                }
//            }
//        }
//        org.apache.poi.hssf.usermodel.DVConstraint constraint7 = createHiddenSheet("G", serverOper, workbook); //运维组
//
//
//        if (null != status) {
//        	for (int i = 0, length = status.length; i < length; i++) {
//        		row = (HSSFRow) hidden.getRow(i);
//        		if (null != row) {
//        			c = getCell(row, c, 7);
//                    c.setCellValue(status[i]);
//                } else {
//                    row = (HSSFRow) hidden.createRow(i);
//                    c = getCell(row, c, 7);
//                    c.setCellValue(status[i]);
//                }
//            }
//        }
//        org.apache.poi.hssf.usermodel.DVConstraint constraint8 = createHiddenSheet("H", status, workbook); //状态
//
//        //区域名称
//        for (int i = 0, length = area.length; i < length; i++) {
//        	row = (HSSFRow) hidden.getRow(i);
//            if (null != row) {
//            	c = getCell(row, c, 8);
//                c.setCellValue(area[i]);
//            } else {
//                row = (HSSFRow) hidden.createRow(i);
//                c = getCell(row, c, 8);
//                c.setCellValue(area[i]);
//            }
//
//        }
//        org.apache.poi.hssf.usermodel.DVConstraint constraint9 = createHiddenSheet("I", area, workbook); //区域名称
//        for (int i = 3; i < 1000; i++) {
//        	createDropDownBox(sheet, i, 0, constraint9, style); //区域
//        	createDropDownBox(sheet, i, 1, constraint, style); //机房名称
//        	createDropDownBox(sheet, i, 2, constraint2, style); //所属项目
//        	createDropDownBox(sheet, i, 8, constraint3, style); //机器类型
//        	createDropDownBox(sheet, i, 9, constraint4, style); //机器用途
////        	createDropDownBox(sheet, i, 20, constraint5, style); //故障信息
////        	createDropDownBox(sheet, i, 25, constraint6, style); //raid level
//        	createDropDownBox(sheet, i, 19, constraint7, style); //运维组
//        	createDropDownBox(sheet, i, 22, constraint8, style); //状态
//        }
        return workbook;
    }
    private static HSSFCell getCell(HSSFRow r, HSSFCell c, int num) {
    	if (null != r.getCell(num)) {
    		return r.getCell(num);
    	} else {
    		return r.createCell(num);
    	}
    }
	/**
     * 生成下拉框.
     * @param sheet
     * @param rowNum
     * @param cellNum
     * @param constraint
     * @param style
     */
    private static void createDropDownBox(HSSFSheet sheet, int rowNum, int cellNum,
                                          org.apache.poi.hssf.usermodel.DVConstraint constraint, HSSFCellStyle style) {
    	HSSFRow row = (HSSFRow) sheet.createRow(rowNum);
    	HSSFCell c = row.createCell(cellNum);
        c.setCellStyle(style);
        org.apache.poi.hssf.util.CellRangeAddressList addressList = new org.apache.poi.hssf.util.CellRangeAddressList(rowNum, rowNum, cellNum, cellNum);
        org.apache.poi.hssf.usermodel.HSSFDataValidation validation = new org.apache.poi.hssf.usermodel.HSSFDataValidation(addressList, constraint);
        sheet.addValidationData(validation);
        validation.setShowErrorBox(false); // 取消弹出错误框
    }
    /**
     * 隐藏列.
     * @param name
     * @param data
     * @param workbook
     * @return
     */
    private static org.apache.poi.hssf.usermodel.DVConstraint
    	createHiddenSheet(String name, String[] data, HSSFWorkbook workbook) {
    	HSSFName namedCell = workbook.createName();
        namedCell.setNameName("hidden" + name);
        namedCell.setRefersToFormula("hidden!$" + name + "$1:$" + name + "$" + data.length);
        org.apache.poi.hssf.usermodel.DVConstraint constraint = org.apache.poi.hssf.usermodel.DVConstraint
                .createFormulaListConstraint("hidden" + name);
		return constraint;
    }
    
    /**
     * Gets the comment style.
     *
     * @param workbook
     *            the workbook
     * @return the title style
     */
    private static HSSFCellStyle getCommentStyle(HSSFWorkbook workbook) {
    	HSSFFont titleFont = workbook.createFont();
        titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        titleFont.setFontHeightInPoints((short) 10);

        HSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        titleStyle.setWrapText(true);
        // titleStyle.setBorderBottom((short) 10); //下边框
        // titleStyle.setBorderLeft((short) 1); //左边框
        // titleStyle.setBorderTop((short) 0); //上边框
        // titleStyle.setBorderRight((short) 1); //右边框
        return titleStyle;
    }


	/*private static List<com.jd.base.common.util.ExcelDropDownBox> getDropDownData() {
    	List<ExcelDropDownBox> list = new ArrayList<ExcelDropDownBox>();
        // 机房名称
    	StringBuffer roomName = new StringBuffer();
    	Map<String, Object> rec = (Map<String, Object>) machineRoomService.getMachineRoomNameList();
    	List<Object> data = (List<Object>) rec.get("results");
    	if (null != data) {
    		for (Object o : data) { 
    			Map<String, Object> citydata = (Map<String, Object>) o;
    			roomName.append(citydata.get("text") + ";");
      	  } 
    	}
        ExcelDropDownBox box = new ExcelDropDownBox();
        box.setColumn(1);
        box.setStr(roomName.toString());
        list.add(box);
        // 缓存资产列表
        // 项目名称
    	StringBuffer projectName = new StringBuffer();
    	Map<String, Object> projectMap = (Map<String, Object>) projectInfoService.getProjectInfoNameList();
    	List<Object> projectRes = (List<Object>) projectMap.get("results");
    	if (null != projectRes) {
    		for (Object o : projectRes) { 
    			Map<String, Object> citydata = (Map<String, Object>) o;
    			projectName.append(citydata.get("text") + ";");
      	  } 
    	}
        ExcelDropDownBox box2 = new ExcelDropDownBox();
        box2.setColumn(6);
        box2.setStr(projectName.toString());
        list.add(box2);
        // 缓存区域列表
        StringBuffer areaName = new StringBuffer();
    	Map<String, List<String>> dictMap = (Map<String, List<String>>) dictService.getExcelDictMap();
		List<String> dictList = dictMap.get("areaInfo");
    	if (null != dictList) {
    		for (String o : dictList) { 
    			areaName.append(o + ";");
      	  } 
    	}
        ExcelDropDownBox box3 = new ExcelDropDownBox();
        box3.setColumn(1);
        box3.setStr(areaName.toString());
        list.add(box3);

        // 缓存区域列表
        StringBuffer serverUseType = new StringBuffer();
        dictList = dictMap.get("serverUseType");
        if (null != dictList) {
            for (String o : dictList) {
                serverUseType.append(o + ";");
            }
        }
        ExcelDropDownBox box4 = new ExcelDropDownBox();
        box4.setColumn(1);
        box4.setStr(serverUseType.toString());
        list.add(box4);

        StringBuffer machineType = new StringBuffer();
        dictList = dictMap.get("machineType");
        if (null != dictList) {
            for (String o : dictList) {
                machineType.append(o + ";");
            }
        }
        ExcelDropDownBox box5 = new ExcelDropDownBox();
        box5.setColumn(1);
        box5.setStr(machineType.toString());
        list.add(box5);

        StringBuffer raidLevel = new StringBuffer();
        dictList = dictMap.get("raidLevel");
        if (null != dictList) {
            for (String o : dictList) {
                raidLevel.append(o + ";");
            }
        }
        ExcelDropDownBox box6 = new ExcelDropDownBox();
        box6.setColumn(1);
        box6.setStr(raidLevel.toString());
        list.add(box6);

        StringBuffer healthStatus = new StringBuffer();
        dictList = dictMap.get("healthStatus");
        if (null != dictList) {
            for (String o : dictList) {
                healthStatus.append(o + ";");
            }
        }
        ExcelDropDownBox box7 = new ExcelDropDownBox();
        box7.setColumn(1);
        box7.setStr(healthStatus.toString());
        list.add(box7);

        StringBuffer serverOper = new StringBuffer();
        dictList = dictMap.get("serverOper");
        if (null != dictList) {
            for (String o : dictList) {
                serverOper.append(o + ";");
            }
        }
        ExcelDropDownBox box8 = new ExcelDropDownBox();
        box8.setColumn(1);
        box8.setStr(serverOper.toString());
        list.add(box8);

        StringBuffer status = new StringBuffer();
        dictList = dictMap.get("status");
        if (null != dictList) {
            for (String o : dictList) {
                status.append(o + ";");
            }
        }
        ExcelDropDownBox box9 = new ExcelDropDownBox();
        box9.setColumn(1);
        box9.setStr(status.toString());
        list.add(box9);

        return list;
	}*/

	/**
     * 导出EXCEL公共方法 导出带数据的文件
     */
    public static void  exportFile(HttpServletResponse response, String fileName,
                                   String title, String comments, String headers, String[] titles, String[] properties, List<Order> list) throws Exception {
    	ServletOutputStream sos = response.getOutputStream();
    	try {
//			if(type.contains(EXPORT_TYPE_EXCEL)){//excel导出
				setResponseParam(response, fileName);
				HSSFWorkbook workbook = null;
				if(list.size()==0){
					workbook = exportExcel(fileName,titles, title, comments, headers);
				}else{
					workbook = exportExcel( fileName, titles,  properties,list);
				}
				workbook.write(sos);
//			}else{ //
//				//excel导出
//				setResponseParam(response, fileName);
//				HSSFWorkbook workbook = null;
//				if(list.size()==0){
//					workbook = exportExcel(fileName,titles, title, headers, comments);
//				}else{
//					workbook = exportExcel( fileName, titles,  properties,list);
//				}
//				workbook.write(sos);
//			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			sos.flush();
            sos.close();
		}
    }
    /**
     * 导出EXCEL公共方法 导出带数据的文件
     */
    public static HSSFWorkbook exportExcel(String fileName, String[] fieldNames, String[] fieldPreNames, List<Order> list) throws Exception {
        // 1.校验传入参数是否正确
        if (null == fileName || fileName.equals("")) {
            throw new Exception(PARAM_TITLE);
        }
        if (null == fieldNames || fieldNames.length == 0) {
            throw new Exception(PARAM_FIELDNAMES);
        }
        if (null == list || list.size() == 0) {
            throw new Exception(PARAM_LIST);
        }
//        if (list.size() > MAX_NUM){
//            throw new Exception(MAX_NUM_INFO);
//        }

        HSSFWorkbook workbook = new HSSFWorkbook();
        
//        int size = (list.size()-1)/PAGE_NUM+1;
        int size = list.size();
//        for (int i = 0; i < size; i++) {
//        	List<Order> subList = list.subList(PAGE_NUM*i, PAGE_NUM*(i+1)>list.size()?list.size():PAGE_NUM*(i+1));
//        	if(i>=1){
//        		fileName = fileName+i;
//        	}
        	 HSSFSheet sheet = workbook.createSheet(fileName);
             sheet.setDefaultColumnWidth(20);
             // 设置报表标题
             createColumHeader(workbook, sheet, fieldNames);

             try {
                 //实体类处理
                 Field[] columnFields = createColumnFileds(fieldPreNames, list.get(0).getClass());
                 //创建单元格
                
                 int rowCount = 1;
                 
                 HSSFCellStyle style = workbook.createCellStyle();
                 HSSFFont font = workbook.createFont();
                 font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                 style.setFont(font);  
                 style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                 style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                 style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                 style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                 HSSFRow row = null;
                 for (Order t : list) {
                     row = sheet.createRow(rowCount);
                     createCell( row, style, columnFields, t);
                     rowCount++;
                 }
             } catch (Exception e) {
             	e.printStackTrace();
                 log.error("导出excel创建单元格是出现异常:" + e.getMessage(), e);
             }
//		}
       
        return workbook;
    }

    /**
     * 设置列头
     * @param workbook
     * @param sheet
     * @param fieldNames
     */
    @Deprecated
    private static void createColumHeader(HSSFWorkbook workbook, HSSFSheet sheet, String[] fieldNames) {
        // 设置列头
        HSSFRow row = sheet.createRow(0);
        // 指定行高
        row.setHeight((short) 400);

        HSSFCellStyle cellStyle = workbook.createCellStyle();
        // 指定单元格居中对齐
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 指定单元格垂直居中对齐
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 指定单元格自动换行
        cellStyle.setWrapText(true);

        // 单元格字体
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeight((short) 200);
        cellStyle.setFont(font);
        // 设置单元格的边框为粗体
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        // 设置单元格的边框颜色
        cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setTopBorderColor(HSSFColor.BLACK.index);

        // 设置单元格背景色
        //cellStyle.setFillForegroundColor(HSSFColor.RED.index);
        //cellStyle.setFillPattern(HSSFCellStyle.NO_FILL);

        HSSFCell cell = null;

        for (int i = 0; i < fieldNames.length; i++) {
            cell = row.createCell(i);
            cell.setCellType(HSSFCell.ENCODING_UTF_16);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(new HSSFRichTextString(fieldNames[i]));
        }

    }

    /**
     * 实体类处理
     * @param <T>
     * @param fieldNames
     * @param fieldClass
     * @return
     * @throws NoSuchFieldException
     */
    private static <T> Field[] createColumnFileds(String[] fieldNames, Class<T> fieldClass) throws NoSuchFieldException {
        Field[] fields = new Field[fieldNames.length];
        for (int i = 0; i < fieldNames.length; i++) {
            Field field = fieldClass.getDeclaredField(fieldNames[i]);
            field.setAccessible(true);
            fields[i] = field;
        }
        return fields;
    }

    /**
     * 创建单元格
     * @param <T>
     * @param row
     * @param style
     * @param columnFields
     * @param t
     */
    private static  <T> void createCell(HSSFRow row, HSSFCellStyle style, Field[] columnFields, T t) {
    	 PropertyDescriptor pd = null;
    	 Method getMethod =  null;
    	 HSSFCell cell = null;
    	 Object field;
    	for (int i = 0; i < columnFields.length; i++) {
            cell = row.createCell(i);
            try {
            	pd =  new PropertyDescriptor(columnFields[i].getName(),  
                         t.getClass()); 
            	  getMethod = pd.getReadMethod();//获得get方法  
            	   field = getMethod.invoke(t);//
               
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                
                //判断值是否为空
                if(null == field){
                    cell.setCellValue("");
                }else if (field instanceof Date){
                    cell.setCellValue((Date) field);
                }else {
                    cell.setCellValue((String) (field+""));
                }
                cell.setCellStyle(style);
            } catch (Exception e) {
                log.error("导出excel创建单元格是出现异常",e);
            }
        }
    }

    /**
     * 导出设置参数
     * @param response
     * @param fileName
     */
    public static void setResponseParam(HttpServletResponse response, String fileName) {
        response.setContentType("application/msexcel;charset=GBK");
        fileName = fileName + ".xls";
        try {
            response.setHeader("Content-Disposition", "attachment;filename=".concat(new String(fileName.getBytes("GBK"),"iso8859-1")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setHeader("Connection", "close");
        response.setHeader("Content-Type", "application/vnd.ms-excel");
    }
public static void main(String[] args) {
	System.out.println((200000-1)/PAGE_NUM+1);
}
/**
 * Gets the title style.
 *
 * @param workbook
 *            the workbook
 * @return the title style
 */
private static HSSFCellStyle getTitleStyle(HSSFWorkbook workbook) {
    HSSFFont titleFont = workbook.createFont();
    titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    titleFont.setFontHeightInPoints((short) 14);

    HSSFCellStyle titleStyle = workbook.createCellStyle();
    titleStyle.setFont(titleFont);
    titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    return titleStyle;
}
/**
 * Adds the merged region.
 *
 * @param sheet
 *            the sheet
 * @param startRow
 *            the start row
 * @param endRow
 *            the end row
 * @param startCol
 *            the start col
 * @param endCol
 *            the end col
 */
@SuppressWarnings("deprecation")
private static void addMergedRegion(HSSFSheet sheet, int startRow, int endRow,
                                    int startCol, int endCol) {
    if (sheet != null) {
        org.apache.poi.hssf.util.CellRangeAddress mergeRegion = new org.apache.poi.hssf.util.CellRangeAddress(startRow,
                endRow, startCol, endCol);
        sheet.addMergedRegion(mergeRegion);
    }
}
/**
 * Creates the cell.
 *
 * @param workSheet
 *            the work sheet
 * @param rowIndex
 *            the row index
 * @param cellIndex
 *            the cell index
 * @param style
 *            the style
 * @return the cell
 */
private static HSSFCell createCell(HSSFSheet workSheet, int rowIndex, int cellIndex,
                                   HSSFCellStyle style) {
    HSSFRow row = workSheet.getRow(rowIndex);
    if (row == null) {
        row = workSheet.createRow(rowIndex);
    }
    HSSFCell cell = row.getCell(cellIndex);
    if (cell == null) {
        cell = row.createCell(cellIndex);
        if (style != null) {
            cell.setCellStyle(style);
        }
    }
    return cell;
}
/**
 * Gets the header style.
 *
 * @param workbook
 *            the workbook
 * @return the header style
 */
private static HSSFCellStyle getHeaderStyle(HSSFWorkbook workbook) {
    HSSFFont headerFont = workbook.createFont();
    headerFont.setColor(HSSFColor.WHITE.index);
    headerFont.setFontHeightInPoints((short) 12);

    HSSFCellStyle headerStyle = workbook.createCellStyle();
    headerStyle.setFont(headerFont);
    headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
    headerStyle.setFillForegroundColor(HSSFColor.GREEN.index);
    headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    return headerStyle;
}
}
