package com.maven.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.maven.entity.EnterpriseActivityCustomization;
import com.maven.entity.EnterpriseActivityPay;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.UserLogs;
import com.maven.service.EnterpriseActivityCustomizationService;
import com.maven.service.EnterpriseEmployeeService;

public class ExcelRead {
    // 总行数
    private int totalRows = 0;
    // 总列数
    private int totalColumns = 0;
    // 错误信息接收器
    private StringBuilder errorMsg = new StringBuilder();

    private Map<String, Object> result = new HashMap<String, Object>();

    /** 获取总行数 */
    public int getTotalRows() {
        return totalRows;
    }

    /** 获取总列数 */
    public int getTotalColumns() {
        return totalColumns;
    }

    /** 获取错误信息 */
    public String getErrorInfo() {
        return errorMsg.toString();
    }

    public HashMap<String, Object> getResult() {
        return (HashMap<String, Object>) result;
    }

    private EnterpriseActivityCustomizationService enterpriseActivityCustomizationService;
    private EnterpriseEmployeeService enterpriseEmployeeService;
    private EnterpriseEmployee eex;

    // 构造方法
    public ExcelRead() {
    }

    // 构造方法
    public ExcelRead(EnterpriseActivityCustomizationService enterpriseActivityCustomizationService,
            EnterpriseEmployeeService enterpriseEmployeeService, EnterpriseEmployee eex) {
        this.enterpriseActivityCustomizationService = enterpriseActivityCustomizationService;
        this.enterpriseEmployeeService = enterpriseEmployeeService;
        this.eex = eex;
    }

    /**
     * 获取客户信息集合
     * 
     * @param fielName
     * @return
     * @throws IOException
     */
    public void getExcelInfo(MultipartFile Mfile) throws Exception {
        if (null == Mfile)
            throw new IOException("文件为空");
        Workbook wb = getWorkbook(Mfile);
        if (null == wb)
            return;
        readExcelValue(wb);
    }

    /**
     * 读取Excel里的数据
     * 
     * @param wb
     * @return
     * @throws Exception
     */
    private void readExcelValue(Workbook wb) throws Exception {
        // 得到第一个sheet
        Sheet sheet = wb.getSheetAt(0);
        // 得到sheet页中的行数
        this.totalRows = sheet.getPhysicalNumberOfRows();
        // 得到sheet页中的列数
        if (totalRows >= 1 && sheet.getRow(0) != null)
            this.totalColumns = sheet.getRow(0).getPhysicalNumberOfCells();
        List<EnterpriseActivityPay> eapList = new ArrayList<EnterpriseActivityPay>();
        List<UserLogs> logList = new ArrayList<UserLogs>();
        List<EnterpriseEmployee> _list = null;
        EnterpriseActivityCustomization _eac = null;
        EnterpriseActivityPay _eap = null;
        EnterpriseEmployee _ee = null;
        Row row;
        Cell cell;
        // 循环sheet页中的行数,从第二行(r==1)开始。标题(r==0)不入库
        loopRow: for (int r = 1; r < totalRows; r++) {
            row = sheet.getRow(r);
            if (null == row)
                continue;
            _eap = new EnterpriseActivityPay();
            // 循环Excel的列
            for (int c = 0; c < this.totalColumns; c++) {
                cell = row.getCell(c);
                if (null != cell) {
                    switch (c) {
                    case 0:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        _eac = enterpriseActivityCustomizationService.selectByPrimaryKey(cell.getStringCellValue());
                        if (null == _eac) {
                            this.errorMsg.append("活动编码【");
                            this.errorMsg.append(cell.getStringCellValue());
                            this.errorMsg.append("】不存在<br/>");
                            continue loopRow; // 失败后跳过该行数据，读取后面的数据
                        } else {
                            _eap.setAcname(_eac.getActivityname());
                            _eap.setEcactivitycode(_eac.getEcactivitycode());
                        }
                        break;
                    case 1:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        _list = enterpriseEmployeeService.queryEmployeeIsExist(cell.getStringCellValue());
                        if (null == _list || _list.size() != 1) {
                            this.errorMsg.append("用户账号【");
                            this.errorMsg.append(cell.getStringCellValue());
                            this.errorMsg.append("】不正确<br/>");
                            continue loopRow; // 失败后跳过该行数据，读取后面的数据
                        } else {
                            _ee = _list.get(0);
                            _eap.setBrandcode(_ee.getBrandcode());
                            _eap.setEmployeecode(_ee.getEmployeecode());
                            _eap.setEnterprisecode(_ee.getEnterprisecode());
                            _eap.setLoginaccount(_ee.getLoginaccount());
                            _eap.setParentemployeecode(_ee.getParentemployeecode());
                        }
                        break;
                    case 2:
                        _eap.setPaymoneyaudit(cell.getNumericCellValue());
                        _eap.setPaymoneyreal(cell.getNumericCellValue());
                        break;
                    case 3:
                        _eap.setLsbs(cell.getNumericCellValue() + "");
                        break;
                    case 4:
                        _eap.setDesc(cell.getStringCellValue());
                        break;
                    default:
                        System.err.println("无效列");
                        break;
                    }
                } else {
                    this.errorMsg.append("第" + r + "条数据，");
                    switch (c) {
                    case 0:
                        this.errorMsg.append("活动编码不能为空");
                        break;
                    case 1:
                        this.errorMsg.append("用户账号不能为空");
                        break;
                    case 2:
                        this.errorMsg.append("充值金额不能为空");
                        break;
                    case 3:
                        this.errorMsg.append("流水倍数不能为空");
                        break;
                    case 4:
                        this.errorMsg.append("充值说明不能为空");
                        break;
                    }
                    continue loopRow;
                }
            }
            _eap.setCreatetime(new Date());
            _eap.setPaystatus(EnterpriseActivityPay.Enum_paystatus.待审核.value);
            _eap.setPaytype(EnterpriseActivityPay.Enum_paytype.人工发放.value);
            // 添加数据
            eapList.add(_eap);
            logList.add(new UserLogs(_ee.getEnterprisecode(), _ee.getEmployeecode(), _ee.getLoginaccount(),
                    UserLogs.Enum_operatype.活动充值业务, "员工提交活动充值:" + _eap.getPaymoneyreal(), eex.getLoginaccount(),
                    _eap.getDescs()));
        }
        this.result.put("eapList", eapList);
        this.result.put("logList", logList);
    }

    /**
     * 获取Excel文件的workbook
     * 
     * @param Mfile
     * @return
     * @throws IOException
     */
    public Workbook getWorkbook(MultipartFile Mfile) throws IOException {
        Workbook wb = null;
        if (null != Mfile && !Mfile.isEmpty() && validateExcel(Mfile.getOriginalFilename())) {
            String fileName = Mfile.getOriginalFilename();
            if (ExcelRead.isExcel2003(fileName))
                wb = new HSSFWorkbook(Mfile.getInputStream());
            if (ExcelRead.isExcel2007(fileName))
                wb = new XSSFWorkbook(Mfile.getInputStream());
        }

        return wb;
    }

    /**
     * 验证EXCEL文件
     * 
     * @param filePath
     * @return
     */
    public boolean validateExcel(String filePath) {
        if (filePath == null || !(ExcelRead.isExcel2003(filePath) || ExcelRead.isExcel2007(filePath))) {
            errorMsg.append("文件名不是excel格式");
            return false;
        }
        return true;
    }

    /**
     * 通过文件后缀名判断文件是否是Excel2003
     * 
     * @param filePath
     * @return
     *
     */
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    /**
     * 通过文件后缀判断文件是否是Excel2007及以后版本
     * 
     * @param filePath
     * @return
     */
    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }
}